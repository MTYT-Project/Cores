/*
 * Translation system designed and run by IToncek.
 * Copyright (c) 2022.
 */

package me.javasyntaxerror.listeners;

import me.javasyntaxerror.Cores;
import me.javasyntaxerror.methods.others.GameState;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;

public class PlayerPickupItemListener implements Listener {

    @EventHandler
    public void onPick (PlayerPickupItemEvent event) {
        if (Cores.getInstance().state == GameState.INGAME) {
            if (Cores.getInstance().team.get(event.getPlayer().getName()) != null) {
                String team = Cores.getInstance().team.get(event.getPlayer().getName());
                if (team.equals("Spectator")) {
                    event.setCancelled(true);
                    return;
                }
            }
            event.setCancelled(false);
            return;
        } else {
            event.setCancelled(true);
        }
    }

}
