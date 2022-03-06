/*
 * Translation system designed and run by IToncek.
 * Copyright (c) 2022.
 */

package me.javasyntaxerror.methods.others.team;

import me.javasyntaxerror.Cores;
import org.bukkit.entity.Player;

public class TeamVote {

    public void joinTeam (Player player, String name) {
        String team = null;

        if (Cores.getInstance().team.get(player.getName()) != null) {
            team = Cores.getInstance().team.get(player.getName());
        }

        if (team != null) {
            if (team.equals(name)) {
                Cores.getInstance().messages.sendMessage(player, "§cDu bist bereits in Team " + Cores.getInstance().teamManager.get(team).getPrefix() + team + "§c.");

                player.closeInventory();
                return;
            }
        }
        if (Cores.getInstance().teamManager.get(name).getPlayers().size() >= Cores.getInstance().maxPlayersInTeam) {
            Cores.getInstance().messages.sendMessage(player, "§cDas Team " + Cores.getInstance().teamManager.get(name).getPrefix() + name + " §cist bereits voll!");

            player.closeInventory();
            return;
        }

        if (team != null) {
            Cores.getInstance().teamManager.get(team).removePlayer(player);
        }

        Cores.getInstance().team.put(player.getName(), name);
        Cores.getInstance().teamManager.get(name).addPlayer(player);
        Cores.getInstance().messages.sendMessage(player, "§7Du bist nun in " + Cores.getInstance().teamManager.get(name).getPrefix() + "Team " + name + "§7!");
        Cores.getInstance().scoreboardManager.setScoreboardTeamInGame(player);
        Cores.getInstance().scoreboardManager.updateLobbyTeamScoreboard(player);

        player.closeInventory();
    }

    public void RandomTeam (Player player) {
        if (Cores.getInstance().team.get(player.getName()) != null) {
            return;
        }

        for (String strings : Cores.getInstance().teams) {
            if (Cores.getInstance().teamManager.get(strings).getPlayersSize() < Cores.getInstance().maxPlayersInTeam) {
                Cores.getInstance().teamManager.get(strings).addPlayer(player);
                Cores.getInstance().team.put(player.getName(), strings);

                Cores.getInstance().messages.sendMessage(player, "§7Du bist nun in " + Cores.getInstance().teamManager.get(strings).getPrefix() + "Team " + strings + "§7!");
                return;
            }
        }
    }

}
