package org.minicraft02160.model;

import org.minicraft02160.model.extras.CardinalPoints;
import org.minicraft02160.model.extras.EquipmentSlot;
import org.minicraft02160.view.PlayerRenderer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

// Represents the player, defines behaviour, position, inventory, and interactions with the world
// implements DamageableEntity to allow taking damage
public class Player implements DamageableEntity{

    private WorldPort world;
    private int x;
    private int y;
    private CardinalPoints direction;
    private BufferedImage imagePlayer;

    private final Lives lives;
    private boolean alive;
    private Inventory inventory;

    private final PlayerRenderer playerRenderer = new PlayerRenderer();

    public Player(int x, int y, CardinalPoints direction) {
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.lives = new Lives(5);
        this.alive = true;
        this.inventory = new Inventory();

        try {
            this.imagePlayer = ImageIO.read(
                    getClass().getClassLoader().getResourceAsStream("player.png")
            );
        } catch (IOException e) {
            this.imagePlayer = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
        }
    }

    // below 4 methods validates if the tile is walkable before moving
    public void moveNorth() {
        if (world.isTileWalkable(x, y - 1)) y = Math.max(0, y - 1);
        direction = CardinalPoints.N;
    }

    public void moveSouth() {
        if (world.isTileWalkable(x, y + 1)) y++;
        direction = CardinalPoints.S;
    }

    public void moveEast() {
        if (world.isTileWalkable(x + 1, y)) x++;
        direction = CardinalPoints.E;
    }

    public void moveWest() {
        if (world.isTileWalkable(x - 1, y)) x = Math.max(0, x - 1);
        direction = CardinalPoints.W;
    }

    // updates player image as it rotates in the world
    public BufferedImage getImagePlayer() {
        return playerRenderer.getDirectionalImage(imagePlayer, direction);
    }

    public int getX() { return x; }
    public int getY() { return y; }

    public void setPosition(int x, int y) {
        if (world.isTileWalkable(x, y)) {
            this.x = x;
            this.y = y;
        }
    }

    public CardinalPoints getDirection() { return direction; }
    public void setWorld(WorldPort world) { this.world = world; }

    public int getLives() { return lives.current(); }
    public int getMaxLives() { return lives.max(); }
    public boolean isAlive() { return alive; }

    public void takeDamage(int damage) {
        for (int i = 0; i < damage; i++) {
            lives.decrement();
        }
        if (lives.current() <= 0) {
            alive = false;
            onDeath();
        }
        if (world != null) world.repaint();
    }

    public void gainLife() {
        lives.increment();
        if (world != null) world.repaint();
    }

    public Inventory getInventory() { return inventory; }

    public void onDeath() {
        List<Item> itemsToDrop = inventory.getAllItems();
        for (Item item : itemsToDrop) {
            world.dropItemAt(item, this.x, this.y);
        }
        inventory.clear();
    }

    public EquipableItem getEquippedMainHand() {
        return inventory.getEquipped(EquipmentSlot.MAIN_HAND);
    }

    public EquipableItem getEquippedOffHand() {
        return inventory.getEquipped(EquipmentSlot.OFF_HAND);
    }

    public void unequipMainHand() {
        EquipableItem mainHand = inventory.getEquipped(EquipmentSlot.MAIN_HAND);
        if (mainHand != null) {
            var overflow = inventory.unequip(mainHand);
            for (Item item : overflow) {
                world.dropItemAt(item, this.x, this.y);
            }
            System.out.println("Unequipped " + mainHand.getName());
        }
    }

    public WorldPort getWorld() { return world; }
}