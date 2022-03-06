/*
 * Translation system designed and run by IToncek.
 * Copyright (c) 2022.
 */

package me.javasyntaxerror;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import me.javasyntaxerror.commands.CoresCommand;
import me.javasyntaxerror.commands.StartCommand;
import me.javasyntaxerror.commands.StatsCommand;
import me.javasyntaxerror.listeners.*;
import me.javasyntaxerror.methods.countdowns.Lobby_Countdown;
import me.javasyntaxerror.methods.mysql.MySQL;
import me.javasyntaxerror.methods.others.GameState;
import me.javasyntaxerror.methods.others.PacketScoreboard;
import me.javasyntaxerror.methods.others.manager.*;
import me.javasyntaxerror.methods.others.team.TeamManager;
import me.javasyntaxerror.methods.others.team.TeamVote;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Cores extends JavaPlugin {

    private static Cores instance;
    public GameState state;
    public MySQL mysql;

    public Integer maxPlayersInTeam, minPlayers;
    public String prefix = "§7[§bCores§7] ";
    public boolean mysqlactivated = true;

    public ExecutorService executorService = Executors.newCachedThreadPool();
    public ActionBar actionBar = new ActionBar();
    public ItemManager itemManager = new ItemManager();
    public TeamVote teamVote = new TeamVote();
    public Messages messages = new Messages();
    public InventoryManager inventoryManager = new InventoryManager();
    public LocationManager locationManager = new LocationManager();

    public HashMap<String, String> team = new HashMap<>();
    public HashMap<String, TeamManager> teamManager = new HashMap<>();
    public HashMap<String, PacketScoreboard> scoreBoard = Maps.newHashMap();
    public HashMap<String, PlayerManager> playerManager = Maps.newHashMap();
    public ScoreboardManager scoreboardManager = new ScoreboardManager();

    public List<String> teams = Lists.newArrayList();

    public void onEnable () {
        instance = this;
        saveDefaultConfig();
        setState(GameState.LOBBY);
        setUpTeams();
        registerCommands();
        registerListener();

        mysqlactivated = getConfig().getBoolean("MySQL");
        executorService.submit(() -> {
            Lobby_Countdown.startLobbyCountdown();

            if (mysqlactivated) {
                connectMySQL();
            }
        });

        maxPlayersInTeam = Integer.valueOf(getConfig().getString("PlayerInTeam"));

        for (World worlds : Bukkit.getWorlds()) {
            Bukkit.unloadWorld(worlds, false);
            worlds.setAutoSave(false);
            worlds.setTime(1000);
            worlds.setGameRuleValue("doDaylightCycle", "false");
        }

        File filee = new File("MapBackup");
        filee.mkdir();

        FileConfiguration cfg = YamlConfiguration.loadConfiguration(new File("spigot.yml"));

        cfg.set("stats.disable-saving", true);
        cfg.set("world-settings.default.verbose", false);

        try {
            cfg.save(new File("spigot.yml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String replace (String src, String replace, String trg) {
        return src.replace(replace, trg);
    }

    public static String translation (String id) {
        if (Cores.getInstance().getConfig().getString(id) == null) {
            return ChatColor.RED + "There was an error on our side, please contact an admin with this text: " + id + " and screenshot of this message. Thanks for your help :)";
        } else {
            return Cores.getInstance().getConfig().getString(id);
        }
    }

    public static String translation(String id, String ignored) {
        return translation(id);
    }

    public void onDisable () {
        File file = new File("MapBackup");
        file.mkdir();

        File map = new File(getConfig().getString("ResetMap"));

        try {
            copyFolder(file, map);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void registerCommands () {
        getCommand("Cores").setExecutor(new CoresCommand());
        getCommand("Start").setExecutor(new StartCommand());
        getCommand("Stats").setExecutor(new StatsCommand());
    }

    private void registerListener () {
        getServer().getPluginManager().registerEvents(new PlayerDamageListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerDropListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerInteractListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerPickupItemListener(), this);
        getServer().getPluginManager().registerEvents(new InventoryClickListener(), this);
        getServer().getPluginManager().registerEvents(new FoodLevelChangeListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerRespawnListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerDeathListener(), this);
        getServer().getPluginManager().registerEvents(new BlockChangeListener(), this);
        getServer().getPluginManager().registerEvents(new WeatherChangeListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerQuitListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerMoveListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerChatListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerPingListener(), this);
    }

    public static Cores getInstance () {
        return instance;
    }

    public GameState getState () {
        return state;
    }

    public void setState (GameState state) {
        this.state = state;
    }

    public HashMap<String, PacketScoreboard> getScoreBoard () {
        return scoreBoard;
    }

    private void connectMySQL () {
        mysql = new MySQL(getConfig().getString("Host"), getConfig().getString("Datenbank"), getConfig().getString("Benutzer"), getConfig().getString("Passwort"));
        mysql.update("CREATE TABLE IF NOT EXISTS Cores(UUID varchar(64), GSPIELE int, VSPIELE int, GESPIELE int);");
    }

    private void setUpTeams () {
        int maxplayersinteam = Integer.parseInt(getConfig().getString("PlayerInTeam"));

        teamManager.put("Rot", new TeamManager(translation("team.red.color", "§c"), 2, itemManager.getLeatherBoots(translation("team.red.longname", "§cTeam Rot"), Color.RED), Color.RED, translation("team.red.numid", "0000Rot")));
        teamManager.put("Blau", new TeamManager(translation("team.blue.color", "§9"), 6, itemManager.getLeatherBoots(translation("team.blue.longname", "§9Team Blau"), Color.BLUE), Color.BLUE, translation("team.blue.numid", "0001Blau")));

        maxPlayersInTeam = maxplayersinteam;
        minPlayers = maxplayersinteam + 1;

        teams.addAll(Arrays.asList(translation("team.red.shortname", "Rot"), translation("team.blue.shortname", "Blau")));
    }

    public static void copyFolder (File src, File dest) throws IOException {
        if (src.isDirectory()) {
            if (! dest.exists()) {
                dest.mkdir();
            }
            String[] files = src.list();
            assert files != null;
            for (String file : files) {
                File srcFile = new File(src, file);
                File destFile = new File(dest, file);
                copyFolder(srcFile, destFile);
            }
        } else {
            InputStream in = new FileInputStream(src);
            OutputStream out = new FileOutputStream(dest);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = in.read(buffer)) > 0) {
                out.write(buffer, 0, length);
            }
            in.close();
            out.close();
        }
    }

}
