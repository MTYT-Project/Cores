/*
 * Translation system designed and run by IToncek.
 * Copyright (c) 2022.
 */

package me.javasyntaxerror.listeners;

import me.javasyntaxerror.Cores;
import me.javasyntaxerror.methods.others.GameState;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerMoveListener implements Listener {

    @EventHandler
    public void onPlayerMove (PlayerMoveEvent event) {
        if (Cores.getInstance().state == GameState.INGAME) {
            if (event.getPlayer().getLocation().getY() < Cores.getInstance().locationManager.getLocation(event.getPlayer(), "grenze").getY()) {
                if (Cores.getInstance().team.get(event.getPlayer().getName()).equals("Spectator")) {
                    return;
                }
                if (! event.getPlayer().isDead()) {
                    event.getPlayer().setHealth(0);
                }
            }
        }
    }

}
