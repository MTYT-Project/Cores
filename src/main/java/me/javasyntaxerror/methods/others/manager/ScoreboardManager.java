/*
 * Translation system designed and run by IToncek.
 * Copyright (c) 2022.
 */

package me.javasyntaxerror.methods.others.manager;

import me.javasyntaxerror.Cores;
import me.javasyntaxerror.methods.countdowns.InGame_Countdown;
import me.javasyntaxerror.methods.others.PacketScoreboard;
import me.javasyntaxerror.methods.others.team.TeamManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;

public class ScoreboardManager {

    public void setLobbyScoreboard (Player player) {
        if (! Cores.getInstance().scoreBoard.containsKey(player.getName())) {
            Cores.getInstance().scoreBoard.put(player.getName(), new PacketScoreboard(player));
            Cores.getInstance().scoreBoard.get(player.getName()).sendSidebar("    §b§lCores    ");
        }
        Bukkit.getScheduler().runTask(Cores.getInstance(), () -> {
            for (int i = 0; i < 15; i++) {
                Cores.getInstance().scoreBoard.get(player.getName()).removeLine(i);
            }

            Cores.getInstance().scoreBoard.get(player.getName()).setLine(5, Cores.translation("scoreboard.lobby.line1", " "));
            Cores.getInstance().scoreBoard.get(player.getName()).setLine(4, Cores.translation("scoreboard.lobby.line2", "§fMap§8:"));
            Cores.getInstance().scoreBoard.get(player.getName()).setLine(3, Cores.translation("scoreboard.lobby.line3", "§e") + Cores.getInstance().getConfig().getString("MapName"));
            Cores.getInstance().scoreBoard.get(player.getName()).setLine(2, Cores.translation("scoreboard.lobby.line4", "  "));
            Cores.getInstance().scoreBoard.get(player.getName()).setLine(1, Cores.translation("scoreboard.lobby.line5", "§fTeam§8:"));
            if (Cores.getInstance().team.get(player.getName()) != null) {
                Cores.getInstance().scoreBoard.get(player.getName()).setLine(0, "§7" + Cores.getInstance().teamManager.get(Cores.getInstance().team.get(player.getName())).getPrefix() + Cores.getInstance().team.get(player.getName()));
            } else {
                Cores.getInstance().scoreBoard.get(player.getName()).setLine(0, Cores.translation("scoreboard.x", "§4✖"));
            }
        });
    }

    public void setInGameScoreboard (Player player) {
        if (! Cores.getInstance().scoreBoard.containsKey(player.getName())) {
            Cores.getInstance().scoreBoard.put(player.getName(), new PacketScoreboard(player));
            Cores.getInstance().scoreBoard.get(player.getName()).sendSidebar("   §b§lCores §7| §3" + returnTime(InGame_Countdown.count) + "   ");
        }
        Bukkit.getScheduler().runTask(Cores.getInstance(), () -> {
            for (int i = 0; i < 15; i++) {
                Cores.getInstance().scoreBoard.get(player.getName()).removeLine(i);
            }
            TeamManager teamManagerRot = Cores.getInstance().teamManager.get("Rot");
            TeamManager teamManagerBlau = Cores.getInstance().teamManager.get("Blau");

            Cores.getInstance().scoreBoard.get(player.getName()).setLine(10, " ");
            Cores.getInstance().scoreBoard.get(player.getName()).setLine(9, Cores.translation("team.blue.longname"));
            if (teamManagerBlau.isLeftCore()) {
                Cores.getInstance().scoreBoard.get(player.getName()).setLine(8, Cores.translation("scoreboard.y") + Cores.translation("scoreboard.left_core"));
            } else {
                Cores.getInstance().scoreBoard.get(player.getName()).setLine(8, Cores.translation("scoreboard.x") + Cores.translation("scoreboard.left_core"));
            }
            if (teamManagerBlau.isRightCore()) {
                Cores.getInstance().scoreBoard.get(player.getName()).setLine(7, Cores.translation("scoreboard.y") + Cores.translation("scoreboard.right_core"));
            } else {
                Cores.getInstance().scoreBoard.get(player.getName()).setLine(7, Cores.translation("scoreboard.x") + Cores.translation("scoreboard.right_core"));
            }
            Cores.getInstance().scoreBoard.get(player.getName()).setLine(6, "  ");
            Cores.getInstance().scoreBoard.get(player.getName()).setLine(5, Cores.translation("team.red.longname"));
            if (teamManagerRot.isLeftCore()) {
                Cores.getInstance().scoreBoard.get(player.getName()).setLine(4, Cores.translation("scoreboard.y") + Cores.translation("scoreboard.left_core"));
            } else {
                Cores.getInstance().scoreBoard.get(player.getName()).setLine(4, Cores.translation("scoreboard.x") + Cores.translation("scoreboard.left_core"));
            }
            if (teamManagerRot.isRightCore()) {
                Cores.getInstance().scoreBoard.get(player.getName()).setLine(3, Cores.translation("scoreboard.y") + Cores.translation("scoreboard.right_core"));
            } else {
                Cores.getInstance().scoreBoard.get(player.getName()).setLine(3, Cores.translation("scoreboard.x") + Cores.translation("scoreboard.right_core"));
            }
            Cores.getInstance().scoreBoard.get(player.getName()).setLine(2, "   ");
            Cores.getInstance().scoreBoard.get(player.getName()).setLine(1, Cores.translation("scoreboard.kills"));
            Cores.getInstance().scoreBoard.get(player.getName()).setLine(0, "§e" + Cores.getInstance().playerManager.get(player.getName()).getKills());
        });
    }

    public void updateLeftCore (Player player, Integer integer, Integer left) {
        TeamManager teamManagerRot = Cores.getInstance().teamManager.get("Rot");
        TeamManager teamManagerBlau = Cores.getInstance().teamManager.get("Blau");

        if (integer == 0) {
            Cores.getInstance().scoreBoard.get(player.getName()).removeLine(4);

            if (teamManagerRot.isLeftCore()) {
                if (left == 0) {
                    Cores.getInstance().scoreBoard.get(player.getName()).setLine(4,  Cores.translation("scoreboard.y") + Cores.translation("scoreboard.left_core"));
                } else if (left == 1) {
                    Cores.getInstance().scoreBoard.get(player.getName()).setLine(4, Cores.translation("scoreboard.x") + Cores.translation("scoreboard.left_core"));
                }
            } else {
                Cores.getInstance().scoreBoard.get(player.getName()).setLine(4, Cores.translation("scoreboard.x") + Cores.translation("scoreboard.left_core"));
            }
        } else if (integer == 1) {
            Cores.getInstance().scoreBoard.get(player.getName()).removeLine(8);

            if (teamManagerBlau.isLeftCore()) {
                if (left == 0) {
                    Cores.getInstance().scoreBoard.get(player.getName()).setLine(8, Cores.translation("scoreboard.y") + Cores.translation("scoreboard.left_core"));
                } else if (left == 1) {
                    Cores.getInstance().scoreBoard.get(player.getName()).setLine(8, Cores.translation("scoreboard.arrow") + Cores.translation("scoreboard.left_core_c"));
                }
            } else {
                Cores.getInstance().scoreBoard.get(player.getName()).setLine(8, Cores.translation("scoreboard.x") + Cores.translation("scoreboard.left_core"));
            }
        }
    }

    public void updateRightCore (Player player, Integer integer, Integer right) {
        TeamManager teamManagerRot = Cores.getInstance().teamManager.get("Rot");
        TeamManager teamManagerBlau = Cores.getInstance().teamManager.get("Blau");

        if (integer == 0) {
            Cores.getInstance().scoreBoard.get(player.getName()).removeLine(3);

            if (teamManagerRot.isRightCore()) {
                if (right == 0) {
                    Cores.getInstance().scoreBoard.get(player.getName()).setLine(3, Cores.translation("scoreboard.y") + Cores.translation("scoreboard.right_core"));
                } else if (right == 1) {
                    Cores.getInstance().scoreBoard.get(player.getName()).setLine(3, Cores.translation("scoreboard.arrow") + Cores.translation("scoreboard.right_core_c"));
                }
            } else {
                Cores.getInstance().scoreBoard.get(player.getName()).setLine(3, Cores.translation("scoreboard.x") + Cores.translation("scoreboard.right_core"));
            }
        } else if (integer == 1) {
            Cores.getInstance().scoreBoard.get(player.getName()).removeLine(7);

            if (teamManagerBlau.isRightCore()) {
                if (right == 0) {
                    Cores.getInstance().scoreBoard.get(player.getName()).setLine(7, "§a✔ §fRightCore ");
                } else if (right == 1) {
                    Cores.getInstance().scoreBoard.get(player.getName()).setLine(7, "§6➤ §cRightCore ");
                }
            } else {
                Cores.getInstance().scoreBoard.get(player.getName()).setLine(7, "§4✖ §fRightCore ");
            }
        }
    }

    public void updateInGameKills (Player player) {
        Cores.getInstance().scoreBoard.get(player.getName()).removeLine(0);
        Cores.getInstance().scoreBoard.get(player.getName()).setLine(0, "§e" + Cores.getInstance().playerManager.get(player.getName()).getKills());
    }

    public void updateTime (Player player) {
        Cores.getInstance().scoreBoard.get(player.getName()).setName("   §b§lCores §7| §3" + returnTime(InGame_Countdown.count) + "   ");
    }

    public void updateLobbyTeamScoreboard (Player player) {
        Cores.getInstance().scoreBoard.get(player.getName()).removeLine(0);
        Cores.getInstance().scoreBoard.get(player.getName()).setLine(0, "§7" + Cores.getInstance().teamManager.get(Cores.getInstance().team.get(player.getName())).getPrefix() + Cores.getInstance().team.get(player.getName()));
    }

    @SuppressWarnings ("deprecation")
    public void setScoreboardTeamLobby (Player player) {
        Team team = Bukkit.getScoreboardManager().getMainScoreboard().getTeam("0000Lobby");

        if (team == null) {
            Bukkit.getScoreboardManager().getMainScoreboard().registerNewTeam("0000Lobby");
            team = Bukkit.getScoreboardManager().getMainScoreboard().getTeam("0000Lobby");
            team.setPrefix("§7");
        }
        team.setPrefix("§7");
        team.addPlayer(player);

        player.setPlayerListName("§7" + player.getName());
    }

    @SuppressWarnings ("deprecation")
    public void setScoreboardTeamSpec (Player player) {
        Team team = Bukkit.getScoreboardManager().getMainScoreboard().getTeam("0009Lobby");

        if (team == null) {
            Bukkit.getScoreboardManager().getMainScoreboard().registerNewTeam("0009Lobby");
            team = Bukkit.getScoreboardManager().getMainScoreboard().getTeam("0009Lobby");
            team.setPrefix("§7[§4✖§7] §7| §7");
        }

        team.setPrefix("§7[§4✖§7] §7| §7");
        team.addPlayer(player);

        player.setPlayerListName("§7[§4✖§7] §7| §7" + player.getName());
    }

    @SuppressWarnings ("deprecation")
    public void setScoreboardTeamInGame (Player player) {
        String teamName = Cores.getInstance().team.get(player.getName());
        TeamManager manager = Cores.getInstance().teamManager.get(teamName);
        Team team = Bukkit.getScoreboardManager().getMainScoreboard().getTeam(manager.getTeamName());

        if (team == null) {
            Bukkit.getScoreboardManager().getMainScoreboard().registerNewTeam(manager.getTeamName());
            team = Bukkit.getScoreboardManager().getMainScoreboard().getTeam(manager.getTeamName());
            team.setPrefix(manager.getPrefix() + teamName + " §7| " + manager.getPrefix());
        }

        team.setPrefix(manager.getPrefix() + teamName + " §7| " + manager.getPrefix());
        team.addPlayer(player);

        player.setPlayerListName(manager.getPrefix() + teamName + " §7| " + manager.getPrefix() + player.getName());
    }

    public String returnTime (Integer integer) {
        int minutes = 0;

        while (integer >= 60) {
            integer -= 60;
            minutes++;
        }

        if (minutes == 0 && integer < 10) {
            return "00:0" + integer;
        } else if (minutes == 0) {
            return "00:" + integer;
        } else if (minutes < 10 && integer < 10) {
            return "0" + minutes + ":0" + integer;
        } else if (minutes < 10) {
            return "0" + minutes + ":" + integer;
        } else if (integer < 10) {
            return "" + minutes + ":0" + integer;
        } else {
            return "" + minutes + ":" + integer;
        }
    }

}
