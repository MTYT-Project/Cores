/*
 * Translation system designed and run by IToncek.
 * Copyright (c) 2022.
 */

package me.javasyntaxerror.methods.countdowns;

import me.javasyntaxerror.Cores;
import me.javasyntaxerror.methods.others.GameState;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Restart_Countdown {

    @SuppressWarnings ("static-access")
    public static void start () {
        Thread t = Thread.currentThread();

        Cores.getInstance().setState(GameState.RESTART);

        for (int i = 10; i >= 0; i--) {
            Cores.getInstance().actionBar.sendAllActionBar(Cores.getInstance().prefix + "§7Das Spiel startet in §e" + i + " §7Sekunden neu.");

            if (i != 1) {
                Bukkit.broadcastMessage(Cores.getInstance().prefix + Cores.replace(Cores.translation("lobby.restart", "§7Das Spiel startet in §e%NUMBER% §7Sekunden neu."), "%NUMBER%", String.valueOf(i)));
            } else {
                Bukkit.broadcastMessage(Cores.getInstance().prefix + Cores.replace(Cores.translation("lobby.restart", "§7Das Spiel startet in §e%NUMBER% §7Sekunden neu."), "%NUMBER%", String.valueOf(i)));
            }

            if (i == 10) {
                Bukkit.getScheduler().callSyncMethod(Cores.getInstance(), () -> {
                    for (Player all : Bukkit.getOnlinePlayers()) {
                        all.teleport(Cores.getInstance().locationManager.getLobbyLocation(all));
                        all.getInventory().clear();
                        all.getInventory().setArmorContents(null);
                        all.setPassenger(null);
                    }
                    return null;
                });
            }

            if (i == 0) {
                Bukkit.getScheduler().callSyncMethod(Cores.getInstance(), () -> {
                    for (Player all : Bukkit.getOnlinePlayers()) {
                        all.kickPlayer("Restarting");
                    }
                    return null;
                });

                Bukkit.shutdown();
                return;
            }

            try {
                t.sleep(999);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
