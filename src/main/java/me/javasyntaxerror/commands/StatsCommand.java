/*
 * Translation system designed and run by IToncek.
 * Copyright (c) 2022.
 */

package me.javasyntaxerror.commands;

import me.javasyntaxerror.Cores;
import me.javasyntaxerror.methods.mysql.Stats;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.ResultSet;

public class StatsCommand implements CommandExecutor {

    @SuppressWarnings ("deprecation")
    public boolean onCommand (CommandSender sender, Command cmd, String label, String[] args) {
        Player p = (Player) sender;

        if (Cores.getInstance().mysqlactivated) {
            if (args.length > 0) {
                OfflinePlayer p2 = Bukkit.getOfflinePlayer(args[0]);

                if ((p2.getUniqueId().toString() == null) || (Stats.playerExists(p2.getUniqueId().toString()))) {
                    p.sendMessage(Cores.getInstance().prefix + "§7Der Spieler §a" + p2.getName() + " §7war noch nie Online");

                } else {

                    Cores.getInstance().executorService.submit(() -> {
                        int wins = Stats.getWonneneSpiele(p2.getUniqueId().toString());
                        int vspiele = Stats.getVerloreneSpiele(p2.getUniqueId().toString());
                        int gespielte = Stats.getGespielteSpiele(p2.getUniqueId().toString());
                        int ranking = getUserRanking(p2.getUniqueId().toString());

                        p.sendMessage(Cores.translation("stats.header", "§7§m->-----------§r §aCores §7§m-----------<-"));
                        p.sendMessage(Cores.translation("stats.line1", "§8» §aSpielername §8● §e") + p2.getName());
                        p.sendMessage("");
                        p.sendMessage(Cores.translation("stats.line3", " §8» §7Gespielte Spiele §8● §e") + gespielte);
                        p.sendMessage(Cores.translation("stats.line4", " §8» §7Gewonnene Spiele §8● §e") + wins);
                        p.sendMessage(Cores.translation("stats.line5", " §8» §7Verlorene Spiele §8● §e") + vspiele);
                        p.sendMessage("");
                        p.sendMessage(Cores.translation("stats.line6", " §8» §7Ranking §8● §e") + ranking);
                        p.sendMessage(Cores.translation("stats.header", "§7§m->-----------§r §aCores §7§m-----------<-"));
                    });

                }

            } else {
                Cores.getInstance().executorService.submit(() -> {
                    int wins = Stats.getWonneneSpiele(p.getUniqueId().toString());
                    int vspiele = Stats.getVerloreneSpiele(p.getUniqueId().toString());
                    int gespielte = Stats.getGespielteSpiele(p.getUniqueId().toString());
                    int ranking = getUserRanking(p.getUniqueId().toString());

                    p.sendMessage(Cores.translation("stats.header", "§7§m->-----------§r §aCores §7§m-----------<-"));
                    p.sendMessage(Cores.translation("stats.line1", "§8» §aSpielername §8● §e") + p.getName());
                    p.sendMessage("");
                    p.sendMessage(Cores.translation("stats.line3", " §8» §7Gespielte Spiele §8● §e") + gespielte);
                    p.sendMessage(Cores.translation("stats.line4", " §8» §7Gewonnene Spiele §8● §e") + wins);
                    p.sendMessage(Cores.translation("stats.line5", " §8» §7Verlorene Spiele §8● §e") + vspiele);
                    p.sendMessage("");
                    p.sendMessage(Cores.translation("stats.line6", " §8» §7Ranking §8● §e") + ranking);
                    p.sendMessage(Cores.translation("stats.header", "§7§m->-----------§r §aCores §7§m-----------<-"));
                });
            }
        } else {
            Cores.getInstance().messages.sendMessage(p, Cores.translation("warnings.mysql", "§7MySQL ist derzeitig Deaktiviert."));
        }
        return true;


    }

    public static Integer getUserRanking (String uuid) {
        boolean done = false;
        int n = 0;

        try {
            ResultSet rs = Cores.getInstance().mysql.query("SELECT UUID FROM Cores ORDER BY GESPIELE DESC;");
            while ((rs.next()) && (! done)) {
                n++;
                if (rs.getString(1).equalsIgnoreCase(uuid)) {
                    done = true;
                }
            }
            rs.close();
        } catch (Exception err) {
            System.err.println("[] gSystem-Error-User-getUserRanking []");
            err.printStackTrace();
            System.err.println("[] gSystem-Error-User-getUserRanking []");
        }
        return n;
    }

}
