/*
 * Translation system designed and run by IToncek.
 * Copyright (c) 2022.
 */

package me.javasyntaxerror.methods.others.manager;

import org.bukkit.entity.Player;

public class PlayerManager {

    private Integer kills, deaths;

    public PlayerManager (Player player) {
        this.kills = 0;
        this.deaths = 0;
    }

    public Integer getDeaths () {
        return deaths;
    }

    public void addDeath () {
        deaths += 1;
    }

    public void addKill () {
        kills += 1;
    }

    public Integer getKills () {
        return kills;
    }

}
