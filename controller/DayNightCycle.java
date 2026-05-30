package org.minicraft02160.controller;

import org.minicraft02160.model.extras.EquipmentSlot;
import org.minicraft02160.model.EquipableItem;
import org.minicraft02160.model.Player;
import org.minicraft02160.model.LightSource;


public class DayNightCycle {
    private final int dayLength;
    private final int nightStart;
    private int totalSeconds;

    public DayNightCycle(int dayLength) {
        this.dayLength = dayLength;
        this.nightStart = dayLength * 2 / 3;
        this.totalSeconds = 0;
    }

    public void tick() {
        totalSeconds++;
    }

    // true if current time is passed night start
    public boolean isNight() {
        return totalSeconds % dayLength >= nightStart;
    }

    // manages darkness of specific tile
    public int getDarkness(int tileX, int tileY, Player player) {
        if (!isNight()) {
            return 0; // no darkness during day
        }

        //Check if player has a light source
        EquipableItem equipped = player.getInventory().getEquipped(EquipmentSlot.MAIN_HAND);
        if (equipped instanceof LightSource lightSource) {
            int distance = Math.max(
                    Math.abs(tileX - player.getX()),
                    Math.abs(tileY - player.getY())
            );
            return lightSource.getLightRadiusAt(distance); // darkness with light source based on distance
        }
        return 215; // default
    }

}
