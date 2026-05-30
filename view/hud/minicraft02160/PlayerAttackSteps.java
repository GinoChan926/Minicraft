package org.minicraft02160;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.minicraft02160.controller.*;
import org.minicraft02160.model.EquipableItem;
import org.minicraft02160.model.WorldModel;
import org.minicraft02160.model.extras.AttackResult;
import org.minicraft02160.model.extras.CardinalPoints;
import org.minicraft02160.model.extras.EquipmentSlot;
import org.minicraft02160.model.extras.MobType;
import org.minicraft02160.model.Items.IronAxeItem;
import org.minicraft02160.model.MobModel;
import org.minicraft02160.model.Player;
import org.minicraft02160.model.worldengine.*;
import org.minicraft02160.view.Tile;
import org.minicraft02160.view.TileViewManager;
import org.minicraft02160.view.World;
import org.minicraft02160.view.WorldFactory;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerAttackSteps {

    private World world;
    private Player player;
    private MobSpawner spawner;
    private CombatController combatController;
    private MobController targetMob;
    private AttackResult lastResult;
    private int initialHealth;
    private int initialDurability;
    private ChunkManager chunkManager;
    private WorldModel model;
    private TileViewManager tileViewManager;
    private WorldController worldController;
    private MobDeathHandler deathHandler;


    @Given("a world with a player at position {int} {int} facing {word}")
    public void aWorldWithAPlayerAtPositionFacing(int x, int y, String direction) {
        int rows = 10, cols = 10;
        List<TerrainGeneration> steps = List.of(
                new BuildGrassBase(),
                new BuildCluster(TerrainType.WATER, 2, 20),
                new BuildCluster(TerrainType.STONE, 3, 5),
                new BuildCluster(TerrainType.WATER, 2, 4),
                new BuildSandEdges(),
                new BuildDeepWater()
        );

        TerrainType[][] map = new TerrainType[rows][cols];
        for (TerrainGeneration step : steps) {
            step.apply(map, rows, cols);
        }

        chunkManager = new ChunkManager();
        model = new WorldModel(rows, cols);
        tileViewManager = new TileViewManager(model);
        worldController = new WorldController(model, chunkManager, tileViewManager);
        world = WorldFactory.create(model, worldController, tileViewManager);
        worldController.populateFromMap(map, world);
        player = new Player(x, y, CardinalPoints.E);
        player.setWorld(world);
        DroppedConsumableManager droppedConsumableManager = new DroppedConsumableManager(
                item -> null,
                world::getTileView,
                () -> {}
        );
        deathHandler = new MobDeathHandler(droppedConsumableManager);
        combatController = new CombatController(spawner, player, deathHandler);
        worldController.setPlayer(player);
        model.setPlayer(player);
        spawner = new MobSpawner(world, player);
        combatController = new CombatController(spawner, player, deathHandler);
    }

    @Given("a {word} mob is spawned at position {int} {int}")
    public void aMobIsSpawnedAtPosition(String mobTypeName, int x, int y) {
        MobType type = MobType.valueOf(mobTypeName.toUpperCase());
        MobModel mobModel = new MobModel(type, x, y);
        MobController mobController = new MobController(mobModel);
        spawner.addMob(mobController);
        Tile tile = world.getTileView(x, y);
        if (tile != null) {
            tile.setMob(mobModel);
        }
        targetMob = mobController;
        initialHealth = mobController.getMaxHealth();
    }

    @Given("the player has no weapon equipped")
    public void thePlayerHasNoWeaponEquipped() {
        assertFalse(player.getInventory().hasEquipped(EquipmentSlot.MAIN_HAND));
    }

    @Given("the player has an Iron Axe equipped")
    public void thePlayerHasAnIronAxeEquipped() {
        IronAxeItem axe = new IronAxeItem();
        player.getInventory().add(axe);
        axe.use(player);
        assertTrue(axe.isEquipped());
    }

    @Given("the weapon durability is {int}")
    public void theWeaponDurabilityIs(int durability) {
        EquipableItem mainHand = player.getEquippedMainHand();
        assertNotNull(mainHand);
        initialDurability = mainHand.getDurability();
        assertEquals(durability, initialDurability);
    }

    @When("the player attacks the faced tile")
    public void thePlayerAttacksTheFacedTile() {
        int targetX = player.getX();
        int targetY = player.getY();
        switch (player.getDirection()) {
            case E -> targetX += 1;
            case W -> targetX -= 1;
            case N -> targetY -= 1;
            case S -> targetY += 1;
        }
        lastResult = combatController.playerAttackMob(targetX, targetY);
    }

    @When("the player attacks the faced tile until the mob dies")
    public void thePlayerAttacksTheFacedTileUntilTheMobDies() {
        int targetX = player.getX();
        int targetY = player.getY();
        switch (player.getDirection()) {
            case E -> targetX += 1;
            case W -> targetX -= 1;
            case N -> targetY -= 1;
            case S -> targetY += 1;
        }
        for (int i = 0; i < 100; i++) {
            lastResult = combatController.playerAttackMob(targetX, targetY);
            if (lastResult == AttackResult.MOB_KILLED) break;
        }
    }

    @Then("the attack result should be {word}")
    public void theAttackResultShouldBe(String expected) {
        assertEquals(AttackResult.valueOf(expected), lastResult);
    }

    @Then("the mob at position {int} {int} should still be alive")
    public void theMobAtPositionShouldStillBeAlive(int x, int y) {
        assertTrue(targetMob.isAlive());
    }

    @Then("the mob at position {int} {int} should have lost health")
    public void theMobAtPositionShouldHaveLostHealth(int x, int y) {
        assertTrue(targetMob.getCurrentHealth() < initialHealth);
    }

    @Then("the mob at position {int} {int} should be dead")
    public void theMobAtPositionShouldBeDead(int x, int y) {
        assertFalse(targetMob.isAlive());
    }

    @Then("the weapon durability should be {int}")
    public void theWeaponDurabilityShouldBe(int expected) {
        EquipableItem mainHand = player.getEquippedMainHand();
        assertNotNull(mainHand);
        assertEquals(expected, mainHand.getDurability());
    }
}


