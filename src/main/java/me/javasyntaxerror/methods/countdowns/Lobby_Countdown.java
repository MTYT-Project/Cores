/*
 * Translation system designed and run by IToncek.
 * Copyright (c) 2022.
 */

package me.javasyntaxerror.methods.countdowns;

import me.javasyntaxerror.Cores;
import me.javasyntaxerror.methods.mysql.Stats;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class Lobby_Countdown {

    public static Integer lobbyCountdown = 61;
    private static boolean started = false;

    @SuppressWarnings ("static-access")
    public static void startLobbyCountdown () {
        try {
            if (! started) {
                started = true;

                Thread t = Thread.currentThread();

                for (int i = 1000; i != - 1; i--) {
                    lobbyCountdown--;

                    for (Player all : Bukkit.getOnlinePlayers()) {
                        if (Cores.getInstance().team.get(all.getName()) != null) {
                            Cores.getInstance().actionBar.sendActionBar(all, Cores.getInstance().teamManager.get(Cores.getInstance().team.get(all.getName())).getPrefix() + "Team " + Cores.getInstance().team.get(all.getName()));
                        } else {
                            Cores.getInstance().actionBar.sendActionBar(all, Cores.translation("lobby.teamchoosing", "§fWähle dein Team!"));
                        }
                    }

                    if (Bukkit.getOnlinePlayers().size() != 0) {
                        if (Bukkit.getOnlinePlayers().size() < Cores.getInstance().minPlayers) {
                            if (lobbyCountdown <= 52) {
                                i = 1000;
                                lobbyCountdown = 61;
                                for (Player all : Bukkit.getOnlinePlayers()) {
                                    all.setLevel(60);
                                }

                                int required = Cores.getInstance().minPlayers - Bukkit.getOnlinePlayers().size();

                                if (required == 1) {
                                    Bukkit.broadcastMessage(Cores.getInstance().prefix + Cores.translation("lobby.missingplayer", "§cEs wird noch auf §eeinen §cweiteren Spieler gewartet!"));
                                } else {
                                    Bukkit.broadcastMessage(Cores.getInstance().prefix + Cores.replace(Cores.translation("lobby.missingplayers", "§cEs wird noch auf §e%NUMBER% §cweitere Spieler gewartet!"), "%NUMBER%", String.valueOf(required)));
                                }
                            }
                        } else {
                            for (Player all : Bukkit.getOnlinePlayers()) {
                                all.setLevel(lobbyCountdown);
                            }

                            if (lobbyCountdown == 60 || lobbyCountdown == 30 || lobbyCountdown == 15) {
                                for (Player all : Bukkit.getOnlinePlayers()) {
                                    all.playSound(all.getLocation(), Sound.NOTE_BASS_GUITAR, 1F, 1F);
                                }
                                Bukkit.broadcastMessage(Cores.getInstance().prefix + Cores.replace(Cores.translation("lobby.roundcountdown", "§7Die Runde beginnt in §e%NUMBER% §7Sekunden"), "%NUMBER%", String.valueOf(lobbyCountdown)));
                            } else if (lobbyCountdown == 10) {
                                for (Player all : Bukkit.getOnlinePlayers()) {
                                    all.playSound(all.getLocation(), Sound.NOTE_BASS_GUITAR, 1F, 1F);
                                }
                                Bukkit.broadcastMessage(Cores.getInstance().prefix + Cores.replace(Cores.translation("lobby.roundcountdown", "§7Die Runde beginnt in §e%NUMBER% §7Sekunden"), "%NUMBER%", String.valueOf(lobbyCountdown)));
                            } else if (lobbyCountdown == 3 || lobbyCountdown == 2 || lobbyCountdown == 1) {
                                for (Player all : Bukkit.getOnlinePlayers()) {
                                    all.playSound(all.getLocation(), Sound.NOTE_BASS_GUITAR, 1F, 1F);
                                }
                                Bukkit.broadcastMessage(Cores.getInstance().prefix + Cores.replace(Cores.translation("lobby.roundcountdown", "§7Die Runde beginnt in §e%NUMBER% §7Sekunden"), "%NUMBER%", String.valueOf(lobbyCountdown)));
                            } else if (lobbyCountdown == 0) {
                                Cores.getInstance().actionBar.sendAllActionBar(Cores.getInstance().prefix + Cores.translation("lobby.gamestart", "§7Das Spiel beginnt"));

                                for (Player all : Bukkit.getOnlinePlayers()) {
                                    Cores.getInstance().teamVote.RandomTeam(all);
                                    Cores.getInstance().scoreboardManager.setScoreboardTeamInGame(all);
                                    Cores.getInstance().scoreboardManager.setInGameScoreboard(all);

                                    Bukkit.getScheduler().callSyncMethod(Cores.getInstance(), () -> {
                                        all.teleport(Cores.getInstance().locationManager.getSpawnLocation(all));
                                        all.playSound(all.getLocation(), Sound.LEVEL_UP, 1F, 1F);
                                        all.getInventory().clear();
                                        all.setGameMode(GameMode.SURVIVAL);

                                        Cores.getInstance().inventoryManager.setRespawnInventory(all);
                                        return null;
                                    });

                                    if (Cores.getInstance().mysqlactivated) {
                                        Stats.addGespielteSpiele(all.getUniqueId().toString(), 1);
                                    }
                                }

                                Bukkit.broadcastMessage(Cores.getInstance().prefix + Cores.translation("lobby.teleporte", "§eAlle werden auf die Map teleportiert!"));
                                Bukkit.broadcastMessage(Cores.getInstance().prefix + Cores.translation("lobby.gamestart", "§7Das Spiel beginnt"));

                                InGame_Countdown.start();
                                return;
                            }
                        }
                    } else {
                        lobbyCountdown = 61;
                        started = false;
                        return;
                    }

                    t.sleep(999);
                }
            }
        } catch (Exception e) {
            started = false;
            e.printStackTrace();
        }
    }

}
