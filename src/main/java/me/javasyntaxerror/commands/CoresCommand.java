/*
 * Translation system designed and run by IToncek.
 * Copyright (c) 2022.
 */

package me.javasyntaxerror.commands;

import me.javasyntaxerror.Cores;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CoresCommand implements CommandExecutor {

    @Override
    public boolean onCommand (CommandSender s, Command cmd, String label, String[] args) {
        if (s instanceof Player) {
            Player player = (Player) s;

            if (cmd.getName().equalsIgnoreCase("Cores")) {
                if (player.hasPermission("Cores.Admin")) {
                    if (args.length == 3) {
                        if (args[0].equalsIgnoreCase("set")) {
                            List<String> teams = Arrays.asList("Rot", "Blau");
                            List<String> teamslittle = Arrays.asList("red", "blue");

                            if (teamslittle.contains(args[1].toLowerCase())) {
                                List<String> methods = Arrays.asList("LeftCore", "RightCore", "Spawn");
                                List<String> methodslittle = Collections.singletonList("spawn");

                                if (methodslittle.contains(args[2].toLowerCase())) {

                                    Cores.getInstance().locationManager.setLocation(player, args[1].toLowerCase() + args[2].toLowerCase());

                                } else if (args[2].equalsIgnoreCase("leftcore") || args[2].equalsIgnoreCase("rightcore")) {

                                    Cores.getInstance().locationManager.setBlockLocation(player, args[1].toLowerCase() + args[2].toLowerCase());

                                } else {
                                    Cores.getInstance().messages.sendMessage(player, Cores.translation("warning.methods", "§7Possible options:"));
                                    Cores.getInstance().messages.sendMessage(player, "§e" + methods);
                                }
                            } else {
                                Cores.getInstance().messages.sendMessage(player, Cores.translation("warning.teams", "§7Possible teams:"));
                                Cores.getInstance().messages.sendMessage(player, "§e" + teams);
                            }
                        }
                    } else if (args.length == 2) {
                        if (args[0].equalsIgnoreCase("set")) {
                            if (args[1].equalsIgnoreCase("lobby")) {
                                Cores.getInstance().locationManager.setLocation(player, "lobby");
                            } else if (args[1].equalsIgnoreCase("spec")) {
                                Cores.getInstance().locationManager.setLocation(player, "spec");
                            } else if (args[1].equalsIgnoreCase("grenze")) {
                                Cores.getInstance().locationManager.setLocation(player, "grenze");
                            } else {
                                Cores.getInstance().messages.sendMessage(player, Cores.translation("warning.methods", "§7Possible options:"));
                                Cores.getInstance().messages.sendMessage(player, "§eLobby, Spec, Grenze (border)");
                            }
                        }
                    } else {
                        Cores.getInstance().messages.sendMessage(player, Cores.translation("warning.methods", "§7Possible options:"));
                        Cores.getInstance().messages.sendMessage(player, "§7/Cores Set <Team> <LeftCore, RightCore, Spawn>");
                        Cores.getInstance().messages.sendMessage(player, "§7/Cores Set <Lobby, Spec, Grenze (border)>");
                    }
                }
            }
        }
        return false;
    }

}
