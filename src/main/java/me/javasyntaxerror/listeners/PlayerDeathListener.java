/*
 * Translation system designed and run by IToncek.
 * Copyright (c) 2022.
 */

package me.javasyntaxerror.listeners;

import me.javasyntaxerror.Cores;
import me.javasyntaxerror.methods.mysql.Stats;
import me.javasyntaxerror.methods.others.GameState;
import me.javasyntaxerror.methods.others.team.TeamManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeathListener implements Listener {

    @EventHandler
    public void onDeath (PlayerDeathEvent event) {
        event.setDeathMessage(null);
        event.getDrops().clear();
        event.setDroppedExp(0);

        if (Cores.getInstance().state == GameState.INGAME) {
            if (event.getEntity() != null) {
                if (event.getEntity().getKiller() != null) {
                    Cores.getInstance().playerManager.get(event.getEntity().getKiller().getName()).addKill();
                    Cores.getInstance().scoreboardManager.updateInGameKills(event.getEntity().getKiller());

                    String entityteam = Cores.getInstance().team.get(event.getEntity().getName());
                    String killerteam = Cores.getInstance().team.get(event.getEntity().getKiller().getName());

                    TeamManager entityteammanager = Cores.getInstance().teamManager.get(entityteam);
                    TeamManager killerteammanager = Cores.getInstance().teamManager.get(killerteam);

                    String killermessage = Cores.translation("event.death.killer", "You killed %PLAYER%");
                    killermessage = killermessage.replace("%PLAYER%", entityteammanager.getPrefix() + event.getEntity().getName());
                    String killedmessage = Cores.translation("event.death.victim", "You have been killed by %PLAYER%");
                    killedmessage = killedmessage.replace("%PLAYER%", killerteammanager.getPrefix() + event.getEntity().getKiller().getName());
                    String publicmessage = Cores.translation("event.death.public", "%KILLED% have been killed by %KILLER%");
                    publicmessage = publicmessage.replace("%KILLER%", Cores.getInstance().teamManager.get(killerteam).getPrefix() + event.getEntity().getKiller().getName());
                    publicmessage = publicmessage.replace("%KILLED%", entityteammanager.getPrefix() + event.getEntity().getName());

                    Cores.getInstance().messages.sendMessage(event.getEntity().getKiller(), killermessage);
                    Cores.getInstance().messages.sendMessage(event.getEntity(), killedmessage);
                    Cores.getInstance().messages.sendToAllMessage(publicmessage);

                    if (! entityteammanager.isLeftCore() && ! entityteammanager.isRightCore()) {
                        Cores.getInstance().team.put(event.getEntity().getName(), "Spectator");
                        entityteammanager.removePlayer(event.getEntity());

                        if (entityteammanager.getPlayersSize() == 0) {
                            Cores.getInstance().teams.remove(entityteam);
                            String teameliminated = Cores.translation("event.teamelimination", "Team %TEAM% was eliminated!");
                            teameliminated = teameliminated.replace("%TEAM", entityteam);
                            Cores.getInstance().messages.sendToAllMessage(Cores.getInstance().teamManager.get(entityteam).getPrefix() + teameliminated);
                            for (Player all : Bukkit.getOnlinePlayers()) {
                                Cores.getInstance().scoreboardManager.setInGameScoreboard(all);
                            }
                        }

                        if (Cores.getInstance().mysqlactivated) {
                            Cores.getInstance().executorService.submit(() -> Stats.addVerloreneSpiele(event.getEntity().getUniqueId().toString(), 1));
                        }

                        for (Player all : Bukkit.getOnlinePlayers()) {
                            Cores.getInstance().scoreboardManager.setInGameScoreboard(all);
                        }
                    }

                    Bukkit.getScheduler().callSyncMethod(Cores.getInstance(), () -> {
                        event.getEntity().spigot().respawn();
                        return null;
                    });
                } else {
                    String entityteam = Cores.getInstance().team.get(event.getEntity().getName());

                    TeamManager entityteammanager = Cores.getInstance().teamManager.get(entityteam);

                    Cores.getInstance().messages.sendToAllMessage(entityteammanager.getPrefix() + event.getEntity().getName() + Cores.translation("event.death.unknown", " §7died ._."));

                    if (! entityteammanager.isLeftCore() && ! entityteammanager.isRightCore()) {
                        Cores.getInstance().team.put(event.getEntity().getName(), "Spectator");
                        entityteammanager.removePlayer(event.getEntity());

                        if (entityteammanager.getPlayersSize() == 0) {
                            Cores.getInstance().teams.remove(entityteam);
                            String teameliminated = Cores.translation("event.teamelimination", "Team %TEAM% was eliminated!");
                            teameliminated = teameliminated.replace("%TEAM", entityteam);
                            Cores.getInstance().messages.sendToAllMessage(Cores.getInstance().teamManager.get(entityteam).getPrefix() + teameliminated);
                            for (Player all : Bukkit.getOnlinePlayers()) {
                                Cores.getInstance().scoreboardManager.setInGameScoreboard(all);
                            }
                        }

                        if (Cores.getInstance().mysqlactivated) {
                            Cores.getInstance().executorService.submit(() -> Stats.addVerloreneSpiele(event.getEntity().getUniqueId().toString(), 1));
                        }

                        for (Player all : Bukkit.getOnlinePlayers()) {
                            Cores.getInstance().scoreboardManager.setInGameScoreboard(all);
                        }
                    }

                    Bukkit.getScheduler().callSyncMethod(Cores.getInstance(), () -> {
                        event.getEntity().spigot().respawn();
                        return null;
                    });
                }
            }
        }
    }

}
