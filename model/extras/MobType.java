package org.minicraft02160.model.extras;

import javax.imageio.ImageIO;
import java.awt.Image;
import java.awt.image.BufferedImage;

public enum MobType {
    SHEEP(20, 1, 5, 5, 1.2, ConsumableItem.WOOL),
    CHICKEN(10, 1, 5, 5, 1.0, ConsumableItem.FUR);

    private final int baseHealth;
    private final int baseDamage;
    private final double speed;
    private final int aggressionRange;
    private final double dropMultiplier;
    private Image sprite;
    private final ConsumableItem drop;
    MobType(int health, int damage, double speed, int aggression, double dropMultiplier, ConsumableItem drop) {
        this.baseHealth = health;
        this.baseDamage = damage;
        this.speed = speed;
        this.aggressionRange = aggression;
        this.dropMultiplier = dropMultiplier;
        this.drop = drop;
        loadSprite();
    }

    private void loadSprite() {
        String[] extensions = {".png"};
        for (String ext : extensions) {
            String path = "/" + name().toLowerCase() + ext;
            try {
                var stream = getClass().getResourceAsStream(path);
                if (stream == null) continue;
                BufferedImage raw = ImageIO.read(stream);
                this.sprite = raw.getScaledInstance(56, 56, Image.SCALE_SMOOTH);
                System.out.println("✅ Loaded mob sprite: " + path);
                return;
            } catch (Exception e) {
            }
        }
        this.sprite = null;
        System.err.println("❌ Missing mob sprite for: " + name().toLowerCase());
    }

    public int getBaseHealth() {
        return baseHealth;
    }
    public int getBaseDamage() {
        return baseDamage;
    }
    public double getSpeed() {
        return speed;
    }
    public int getAggressionRange() {
        return aggressionRange;
    }
    public double getDropMultiplier() {
        return dropMultiplier;
    }
    public Image getSprite() {
        return sprite;
    }
    public ConsumableItem getDrop() {
        return drop;
    }

    public boolean hasSprite() {
        return sprite != null;
    } //TODO: Remove if not used
}