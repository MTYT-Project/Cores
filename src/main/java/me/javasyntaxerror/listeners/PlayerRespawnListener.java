/*
 * Translation system designed and run by IToncek.
 * Copyright (c) 2022.
 */

package me.javasyntaxerror.listeners;

import me.javasyntaxerror.Cores;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

public class PlayerRespawnListener implements Listener {

    @EventHandler
    public void onRespawn (PlayerRespawnEvent event) {
        Player player = event.getPlayer();

        if (Cores.getInstance().team.get(player.getName()).equals("Spectator")) {
            event.setRespawnLocation(Cores.getInstance().locationManager.getSpecLocation(player));

            player.teleport(Cores.getInstance().locationManager.getSpecLocation(player));
            player.setGameMode(GameMode.SPECTATOR);

            Cores.getInstance().scoreboardManager.setScoreboardTeamSpec(player);
            Cores.getInstance().messages.sendMessage(player, Cores.translation("event.player.spectator_respawn", "Du bist dem Team §8Spectator §7beigetreten."));

            for (Player all : Bukkit.getOnlinePlayers()) {
                if (! Cores.getInstance().team.get(all.getName()).equals("Spectator")) {
                    all.hidePlayer(player);
                }
            }
        } else {
            event.setRespawnLocation(Cores.getInstance().locationManager.getSpawnLocation(player));
            player.teleport(Cores.getInstance().locationManager.getSpawnLocation(player));

            Bukkit.getScheduler().runTaskLater(Cores.getInstance(), new Runnable() {

                @Override
                public void run () {
                    Cores.getInstance().inventoryManager.setRespawnInventory(player);
                }
            }, 20);
        }
    }

}
