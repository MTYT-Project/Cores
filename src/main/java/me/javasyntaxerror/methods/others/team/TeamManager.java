/*
 * Translation system designed and run by IToncek.
 * Copyright (c) 2022.
 */

package me.javasyntaxerror.methods.others.team;

import com.google.common.collect.Lists;
import me.javasyntaxerror.Cores;
import org.bukkit.Color;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class TeamManager {

    private final List<String> players;
    private final String prefix;
    private boolean rightCore, leftCore;
    private final ItemStack stack;
    private final Color color;
    private final String teamname;
    private final Integer slot;
    private Integer left;
    private Integer right;

    public TeamManager (String prefix, Integer slot, ItemStack stack, Color color, String teamname) {
        this.players = Lists.newArrayList();
        this.prefix = prefix;
        this.rightCore = true;
        this.leftCore = true;
        this.color = color;
        this.stack = stack;
        this.teamname = teamname;
        this.slot = slot;
        this.left = 0;
        this.right = 0;
    }

    public String getPrefix () {
        return prefix;
    }

    public String getTeamName () {
        return teamname;
    }

    public List<String> getPlayers () {
        return players;
    }

    public Integer getPlayersSize () {
        return players.size();
    }

    public Color getColor () {
        return color;
    }

    public void addPlayer (Player player) {
        players.add(player.getName());
    }

    public Integer getSlot () {
        return slot;
    }

    public void removePlayer (Player player) {
        players.remove(player.getName());
    }

    public void setLeftCore (boolean leftCore) {
        this.leftCore = leftCore;
    }

    public boolean isRightCore () {
        return rightCore;
    }

    public void setRightCore (boolean rightCore) {
        this.rightCore = rightCore;
    }

    public boolean isLeftCore () {
        return leftCore;
    }

    public Integer getLeft () {
        return left;
    }

    public void setLeft (Integer left) {
        this.left = left;
    }

    public Integer getRight () {
        return right;
    }

    public void setRight (Integer right) {
        this.right = right;
    }

    public ItemStack getItem () {
        ItemStack item = stack;
        ItemMeta meta = item.getItemMeta();
        String[] split = stack.getItemMeta().getDisplayName().split(" ");

        meta.setDisplayName(split[0] + " " + split[1] + " §7(" + getPlayersSize() + "/" + Cores.getInstance().maxPlayersInTeam + " Spieler)");

        List<String> lore = Lists.newArrayList();
        lore.add("");

        if (players.size() != 0) {
            for (String strings : players) {
                lore.add("§7» " + prefix + strings);
            }
            if (getPlayersSize() < Cores.getInstance().maxPlayersInTeam) {
                for (int i = getPlayersSize(); i != Cores.getInstance().maxPlayersInTeam; i++) {
                    lore.add("§7» §8§m-");
                }
            }

        } else {
            for (int i = 0; i != Cores.getInstance().maxPlayersInTeam; i++) {
                lore.add("§7» §8§m-");
            }
        }
        lore.add("");
        lore.add("§7Klicke, um " + split[0] + " " + split[1] + " §7beizutreten!");

        meta.setLore(lore);

        item.setItemMeta(meta);

        return item;
    }

}
