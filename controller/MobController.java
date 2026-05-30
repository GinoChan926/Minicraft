package org.minicraft02160.controller;

import org.minicraft02160.model.extras.ConsumableItem;
import org.minicraft02160.model.extras.MobType;
import org.minicraft02160.model.MobModel;
import org.minicraft02160.model.Player;
import org.minicraft02160.view.Tile;
import org.minicraft02160.view.World;
import org.minicraft02160.model.worldengine.TerrainType;

import java.util.List;
import java.util.Random;

public class MobController {

    private final MobModel model;
    private final Random random;
    private int movementTickCounter;
    private final int ticksPerMove;

    public MobController(MobModel model) {
        this.model = model;
        this.random = new Random();
        this.movementTickCounter = 0;
        this.ticksPerMove = Math.max(1, (int) (3.0 / model.getType().getSpeed()));
    }

    public void update(World world, Player player) {
        if (!model.isAlive()) return;
        movementTickCounter++;
        // mobs do not move every game update.
        if (movementTickCounter >= ticksPerMove) {
            movementTickCounter = 0;
            handleMovement(world, player);
        }
    }

    private void handleMovement(World world, Player player) {
        int[] nextPos = chooseNextPosition(player);
        if (nextPos == null) return;

        int newX = nextPos[0];
        int newY = nextPos[1];

        if (!world.isInBounds(newX, newY)) return;

        Tile targetTile = world.getTileView(newX, newY);
        if (targetTile == null) return;
        if (!canMoveToTile(targetTile)) return;

        model.setX(newX);
        model.setY(newY);
    }

    private int[] chooseNextPosition(Player player) {
        MobType type = model.getType();

        if (type.getAggressionRange() > 0 && player != null) {
            int distToPlayer = calculateDistance(player.getX(), player.getY());
            if (distToPlayer <= type.getAggressionRange()) {
                model.setChasing(true);
                // do not move onto player, combat interaction managed separately
                if (distToPlayer <= 1) return null;
                int dx = Integer.compare(player.getX(), model.getX());
                int dy = Integer.compare(player.getY(), model.getY());
                // enable diagonal movement for mobs
                return new int[]{model.getX() + dx, model.getY() + dy};
            }
        }

        model.setChasing(false);
        //  {0, 0} included so that mobs sometime stay still
        int[][] directions = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}, {0, 0}};
        int[] dir = directions[random.nextInt(directions.length)];
        if (dir[0] == 0 && dir[1] == 0) return null;
        return new int[]{model.getX() + dir[0], model.getY() + dir[1]};
    }

    private boolean canMoveToTile(Tile tile) {
        TerrainType terrain = tile.getTerrainType();
        // block water tile, eventhogh normal water is walkable
        if (terrain == TerrainType.WATER || terrain == TerrainType.DEEP_WATER) return false;
        return tile.isWalkable();
    }

    public void takeDamage(int damage) {
        model.takeDamage(damage);
    }

    public int attackPlayer() {
        // only chasing mobs can attack
        if (!model.isAlive() || !model.isChasing()) return 0;
        int damage = model.getType().getBaseDamage();
        System.out.println(model.getType() + " attacks player for " + damage + " damage!");
        return damage;
    }

    private int calculateDistance(int targetX, int targetY) {
        //counts only horizontal + vertical steps
        return Math.abs(targetX - model.getX()) + Math.abs(targetY - model.getY());
    }

    public boolean isAdjacentToPlayer(Player player) {
        return calculateDistance(player.getX(), player.getY()) <= 1;
    }

    public MobType getType() { return model.getType(); }
    public int getX() { return model.getX(); }
    public int getY() { return model.getY(); }
    public int getCurrentHealth() { return model.getCurrentHealth(); }
    public int getMaxHealth() { return model.getMaxHealth(); }
    public boolean isAlive() { return model.isAlive(); }
    public boolean isChasing() { return model.isChasing(); }
    public int getDamage() { return model.getDamage(); }
    public List<ConsumableItem> getDrops() { return model.getDrops(); }
    public MobModel getModel() { return model; }
}