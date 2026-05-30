package org.minicraft02160.controller;

import org.minicraft02160.model.Player;
import org.minicraft02160.model.MobModel;
import org.minicraft02160.view.World;
import org.minicraft02160.view.Tile;
import org.minicraft02160.model.extras.ConsumableItem;
import org.minicraft02160.model.extras.MobType;

import java.util.*;

public class MobSpawner {

    private static final int DEFAULT_INTERVAL = 30;
    private static final int DEFAULT_MAX_REGION = 10;
    private static final int MAX_SPAWN_ATTEMPTS = 100;
    private static final int REGION_SIZE = 5;

    private boolean enabled = false;
    private int spawnInterval = DEFAULT_INTERVAL;
    private int maxMobsPerRegion = DEFAULT_MAX_REGION;
    private Player player;

    private int elapsedSeconds = 0;
    private final World world;
    private final List<MobController> spawnedMobs = new ArrayList<>();
    private final Map<String, List<MobController>> regions = new HashMap<>();
    private final Random random = new Random();

    private final Map<MobType, List<String>> validTilesMap = new HashMap<>();

    public MobSpawner(World world, Player player) {
        this.world = world;
        this.player = player;
        initValidTiles();
    }

    private void initValidTiles() {
        // restrict spawning location
        validTilesMap.put(MobType.SHEEP, List.of("GrassTile"));
        validTilesMap.put(MobType.CHICKEN, List.of("GrassTile", "SandTile"));
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void setSpawnInterval(int seconds) {
        this.spawnInterval = seconds;
    }

    public void setMaxMobsPerRegion(int max) {
        this.maxMobsPerRegion = max;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void advanceTime(int seconds) {
        if (!enabled) {
            System.out.println("Spawner is disabled.");
            return;
        }

        elapsedSeconds += seconds;
         // allow multiple spawns if enough time has passed
        int intervals = elapsedSeconds / spawnInterval;
        elapsedSeconds = elapsedSeconds % spawnInterval;

        System.out.println("Time +" + seconds + "s"
                + " | Spawns: " + intervals
                + " | Remainder: " + elapsedSeconds + "s");

        for (int i = 0; i < intervals; i++) {
            trySpawnMob();
        }
    }

    private void trySpawnMob() {
        System.out.println("\nAttempting mob spawn...");

        MobType[] allMobs = MobType.values();

        MobType mobType = allMobs[random.nextInt(allMobs.length)];
        System.out.println("Selected: " + mobType);

        int[] position = findValidPosition(mobType);
        if (position == null) {
            System.out.println("No valid position found for: " + mobType);
            return;
        }

        int x = position[0];
        int y = position[1];
        System.out.println("Position: (" + x + ", " + y + ")");

        String regionKey = getRegionKey(x, y);
        List<MobController> regionMobs = regions.getOrDefault(regionKey, new ArrayList<>());

        // declaring region helps prevent mobs from spawning too much in the same area
        if (regionMobs.size() >= maxMobsPerRegion) {
            System.out.println("Region " + regionKey + " is full.");
            return;
        }

        Tile tile = world.getTileView(x, y);
        MobModel mobModel = new MobModel(mobType, x, y);
        MobController mobController = new MobController(mobModel);

        addMob(mobController);
        regionMobs.add(mobController);
        regions.put(regionKey, regionMobs);

        placeMobOnTile(mobController);

        System.out.println("Spawned: " + mobType
                + " at (" + x + "," + y + ")");
    }


    private int[] findValidPosition(MobType mobType) {
        // scanning smaller areas for valid position instead of the whole world
        List<String> allowedTileNames = validTilesMap.get(mobType);

        for (int attempt = 0; attempt < MAX_SPAWN_ATTEMPTS; attempt++) {
            int x = random.nextInt(world.getCols());
            int y = random.nextInt(world.getRows());
            Tile tile = world.getTileView(x, y);
            if (tile != null && allowedTileNames.contains(
                    tile.getClass().getSimpleName())) {
                return new int[]{x, y};
            }
        }
        return null;
    }

    // mobs per region limit
    private String getRegionKey(int x, int y) {
        return (x / REGION_SIZE) + ":" + (y / REGION_SIZE);
    }

    private void placeMobOnTile(MobController mob) {
        Tile tile = world.getTileView(mob.getX(), mob.getY());
        if (tile != null) {
            tile.setMob(mob.getModel());
        }
    }

    private void removeMobFromTile(MobController mob) {
        Tile tile = world.getTileView(mob.getX(), mob.getY());
        if (tile != null) {
            tile.setMob(null);
        }
    }

    public List<ConsumableItem> defeatMob(MobController mob) {
        if (mob == null) {
            System.out.println("Cannot defeat null mob.");
            return new ArrayList<>();
        }
        System.out.println("Player defeated: " + mob.getType());
        removeMobFromTile(mob);
        spawnedMobs.remove(mob);
        String regionKey = getRegionKey(mob.getX(), mob.getY());
        if (regions.containsKey(regionKey)) {
            regions.get(regionKey).remove(mob);
            if (regions.get(regionKey).isEmpty()) {
                regions.remove(regionKey);
            }
        }

        List<ConsumableItem> drops = mob.getDrops();
        System.out.println("Drops: " + drops);
        return drops;
    }

    public void updateAllMobs(Player player) {
        // copy spawnedMobs to avoid conflict while iterating over it
        List<MobController> mobsCopy = new ArrayList<>(spawnedMobs);
        for (MobController mob : mobsCopy) {
            if (!mob.isAlive()) {
                removeMob(mob);
                continue;
            }
            String oldRegionKey = getRegionKey(mob.getX(), mob.getY());
            removeMobFromTile(mob); // clear old tile, no duplication on tiles
            mob.update(world, player);
            placeMobOnTile(mob);
            String newRegionKey = getRegionKey(mob.getX(), mob.getY());
            if (!oldRegionKey.equals(newRegionKey)) {
                updateMobRegion(mob, oldRegionKey, newRegionKey);
            }
            if (!world.isInBounds(mob.getX(), mob.getY())) {
                System.out.println("Mob " + mob.getType()
                        + " out of bounds. Removing.");
                removeMob(mob);
            }
        }
    }

    private void updateMobRegion(MobController mob,
                                 String oldRegionKey,
                                 String newRegionKey) {
        if (regions.containsKey(oldRegionKey)) {
            regions.get(oldRegionKey).remove(mob);
            // Remove empty region for later use, and region map stays clear
            if (regions.get(oldRegionKey).isEmpty()) {
                regions.remove(oldRegionKey);
            }
        }
        regions.computeIfAbsent(newRegionKey, k -> new ArrayList<>()).add(mob);

        System.out.println("Mob " + mob.getType()
                + " moved region: " + oldRegionKey
                + " -> " + newRegionKey);
    }

    private void removeMob(MobController mob) {
        removeMobFromTile(mob);
        spawnedMobs.remove(mob);
        String regionKey = getRegionKey(mob.getX(), mob.getY());
        if (regions.containsKey(regionKey)) {
            regions.get(regionKey).remove(mob);
            if (regions.get(regionKey).isEmpty()) {
                regions.remove(regionKey);
            }
        }
    }

    public void addMob(MobController mob) {
        spawnedMobs.add(mob);
    }

    public List<MobController> getSpawnedMobs() {
        return Collections.unmodifiableList(spawnedMobs);
    }

    public Map<String, List<MobController>> getRegions() {
        return Collections.unmodifiableMap(regions);
    }

    public int getMaxMobsPerRegion() {
        return maxMobsPerRegion;
    }

    public int getTotalMobCount() {
        return spawnedMobs.size();
    }

    public boolean isEnabled() {
        return enabled;
    }

    public List<String> getValidTilesForMob(MobType type) {
        return validTilesMap.getOrDefault(type, Collections.emptyList());
    }

    public int getMobCountInRegion(int x, int y) {
        String regionKey = getRegionKey(x, y);
        return regions.getOrDefault(regionKey,
                Collections.emptyList()).size();
    }

    public List<MobController> getMobsByType(MobType type) {
        List<MobController> result = new ArrayList<>();
        for (MobController mob : spawnedMobs) {
            if (mob.getType() == type) {
                result.add(mob);
            }
        }
        return result;
    }

    public int getSpawnInterval() {
        return spawnInterval;
    }
}