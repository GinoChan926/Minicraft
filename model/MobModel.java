package org.minicraft02160.model;

import org.minicraft02160.model.extras.ConsumableItem;
import org.minicraft02160.model.extras.MobType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// Class for the mob in the game, implements the DamageableEntity which allows the mob to take damage, as the player does
public class MobModel implements DamageableEntity {

    private final MobType type;
    private int x;
    private int y;
    private int currentHealth;
    private boolean alive;
    private boolean isChasing;
    private final List<ConsumableItem> drops;

    public MobModel(MobType type, int x, int y) {
        this.type = type;
        this.x = x;
        this.y = y;
        this.currentHealth = type.getBaseHealth();
        this.alive = true;
        this.isChasing = false;
        this.drops = initDrops();
    }

    // creates the drops for the mob based on its type and drop multiplier
    private List<ConsumableItem> initDrops() {
        List<ConsumableItem> items = new ArrayList<>();
        int multiplier = (int) Math.ceil(type.getDropMultiplier());
        for (int i = 0; i < multiplier; i++) {
            ConsumableItem drop = type.getDrop();
            if (drop != null) items.add(drop);
        }
        return items;
    }

    public MobType getType() { return type; }
    public int getX() { return x; }
    public int getY() { return y; }
    public int getCurrentHealth() { return currentHealth; }
    public int getMaxHealth() { return type.getBaseHealth(); }
    public boolean isChasing() { return isChasing; }
    public int getDamage() { return type.getBaseDamage(); }

    public List<ConsumableItem> getDrops() {
        return Collections.unmodifiableList(drops);
    }

    public void setX(int x) { this.x = x; }
    public void setY(int y) { this.y = y; }
    public void setAlive(boolean alive) { this.alive = alive; }
    public void setChasing(boolean chasing) { this.isChasing = chasing; }

    // defines the behaviour for the mob to take damage and update its health and alive status
    @Override
    public void takeDamage(int damage) {
        if (!alive) return;
        currentHealth = Math.max(0, currentHealth - damage);
        if (currentHealth <= 0) alive = false;
    }

    @Override
    public boolean isAlive() { return alive; }
}