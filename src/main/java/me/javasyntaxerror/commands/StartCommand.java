/*
 * Translation system designed and run by IToncek.
 * Copyright (c) 2022.
 */

package me.javasyntaxerror.commands;

import me.javasyntaxerror.Cores;
import me.javasyntaxerror.methods.countdowns.Lobby_Countdown;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StartCommand implements CommandExecutor {

    @Override
    public boolean onCommand (CommandSender s, Command cmd, String label, String[] args) {
        if (s instanceof Player) {
            Player player = (Player) s;

            if (cmd.getName().equalsIgnoreCase("Start")) {
                if (player.hasPermission("Cores.Start")) {
                    if (Lobby_Countdown.lobbyCountdown <= 11) {
                        Cores.getInstance().messages.sendMessage(player, Cores.translation("info.already_starting", "§cDas Spiel startet bereits."));
                        return false;
                    }

                    Lobby_Countdown.lobbyCountdown = 11;
                    Cores.getInstance().messages.sendMessage(player, Cores.translation("info.started", "§aDas Spiel wurde gestartet."));
                }
            }
        }
        return false;
    }

}
