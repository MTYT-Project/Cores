/*
 * Translation system designed and run by IToncek.
 * Copyright (c) 2022.
 */

package me.javasyntaxerror.methods.others.manager;

import me.javasyntaxerror.Cores;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

public class LocationManager {

    private final File file = new File("plugins//Cores//Location.yml");
    private final FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);

    public void setLocation (Player player, String loc) {
        cfg.set("Cores." + loc + ".World", player.getWorld().getName());
        cfg.set("Cores." + loc + ".X", player.getLocation().getX());
        cfg.set("Cores." + loc + ".Y", player.getLocation().getY());
        cfg.set("Cores." + loc + ".Z", player.getLocation().getZ());
        cfg.set("Cores." + loc + ".Yaw", player.getLocation().getYaw());
        cfg.set("Cores." + loc + ".Pitch", player.getLocation().getPitch());

        player.sendMessage(Cores.getInstance().prefix + Cores.translation("info.location_set", "§7Die Location wurde gesetzt."));

        saveCfg();
    }

    public void setBlockLocation (Player player, String loc) {
        cfg.set("Cores." + loc + ".World", player.getWorld().getName());
        cfg.set("Cores." + loc + ".X", player.getLocation().subtract(0, 1, 0).getBlock().getLocation().getX());
        cfg.set("Cores." + loc + ".Y", player.getLocation().subtract(0, 1, 0).getBlock().getLocation().getY());
        cfg.set("Cores." + loc + ".Z", player.getLocation().subtract(0, 1, 0).getBlock().getLocation().getZ());
        cfg.set("Cores." + loc + ".Yaw", player.getLocation().subtract(0, 1, 0).getBlock().getLocation().getYaw());
        cfg.set("Cores." + loc + ".Pitch", player.getLocation().subtract(0, 1, 0).getBlock().getLocation().getPitch());

        player.sendMessage(Cores.getInstance().prefix + Cores.translation("info.location_set", "§7Die Location wurde gesetzt."));

        saveCfg();
    }

    public Location getSpawnLocation (Player player) {
        return getLocation(player, Cores.getInstance().team.get(player.getName()).toLowerCase() + "spawn");
    }

    public Location getLobbyLocation (Player player) {
        return getLocation(player, "lobby");
    }

    public Location getSpecLocation (Player player) {
        return getLocation(player, "spec");
    }

    public Location getLocation (Player player, String loc) {
        if (cfg.getString("Cores." + loc + ".World") != null) {
            World world = Bukkit.getWorld(cfg.getString("Cores." + loc + ".World"));
            double x = cfg.getDouble("Cores." + loc + ".X");
            double y = cfg.getDouble("Cores." + loc + ".Y");
            double z = cfg.getDouble("Cores." + loc + ".Z");

            Location location = new Location(world, x, y, z);
            location.setYaw(cfg.getInt("Cores." + loc + ".Yaw"));
            location.setPitch(cfg.getInt("Cores." + loc + ".Pitch"));

            return location;
        } else {
            return player.getLocation();
        }
    }

    private void saveCfg () {
        try {
            cfg.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
