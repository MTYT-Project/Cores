/*
 * Translation system designed and run by IToncek.
 * Copyright (c) 2022.
 */

package me.javasyntaxerror.listeners;

import me.javasyntaxerror.Cores;
import me.javasyntaxerror.methods.others.GameState;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class FoodLevelChangeListener implements Listener {

    @EventHandler
    public void onFoodLevelChange (FoodLevelChangeEvent event) {
        if (Cores.getInstance().state == GameState.INGAME) {
            if (Cores.getInstance().team.get(event.getEntity().getName()) != null) {
                String team = Cores.getInstance().team.get(event.getEntity().getName());
                if (team.equals("Spectator")) {
                    event.setCancelled(true);
                    return;
                }
            }
            return;
        } else {
            event.setCancelled(true);
        }
    }

}
