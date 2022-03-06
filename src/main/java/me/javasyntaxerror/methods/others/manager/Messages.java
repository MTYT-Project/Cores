/*
 * Translation system designed and run by IToncek.
 * Copyright (c) 2022.
 */

package me.javasyntaxerror.methods.others.manager;

import me.javasyntaxerror.Cores;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Messages {

    public void sendMessage (Player player, String message) {
        player.sendMessage(Cores.getInstance().prefix + message);
    }

    public void sendMessage2 (Player player, String message) {
        player.sendMessage(message);
    }

    public void sendToAllMessage (String message) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.sendMessage(Cores.getInstance().prefix + message);
        }
    }

    public void sendToAllWithOutPrefixMessage (String message) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.sendMessage(message);
        }
    }

}
