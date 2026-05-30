package org.minicraft02160.controller;

import org.minicraft02160.model.extras.AttackResult;
import org.minicraft02160.model.extras.ResourceType;
import org.minicraft02160.model.Player;
import org.minicraft02160.model.TileModel;
import org.minicraft02160.model.TileTypes.TileFactory;
import org.minicraft02160.model.WorldModel;
import org.minicraft02160.model.worldengine.TerrainType;
import org.minicraft02160.view.Tile;
import org.minicraft02160.view.TileViewManager;
import org.minicraft02160.view.World;

import javax.swing.*;

public class WorldController {

	private final GameTransitionController transitionController;
	private final DroppedItemManager droppedItemManager;
	private final RegrowthManager regrowthManager;
	private final DayNightCycle dayNightCycle;
	private final TileActionController tileActionController;
	private InteractionService interactionService;
	private CombatController combatController;
	private final WorldModel worldModel;
	private final TileViewManager tileViewManager;
	private MobSpawner mobSpawner;
	private DropCollector dropCollector;

	public WorldController(WorldModel model, ChunkManager chunkManager, TileViewManager tileViewManager) {
		this.transitionController = new GameTransitionController(chunkManager);
		this.droppedItemManager = new DroppedItemManager();
		this.regrowthManager = new RegrowthManager();
		this.dayNightCycle = new DayNightCycle(180);
		this.tileActionController = new TileActionController();
		this.tileViewManager = tileViewManager;
		this.worldModel = model;
	}

	public boolean transition(World world, Player player, int dx, int dy) {
		return transitionController.transition(world, player, dx, dy);
	}

	public void triggerInteraction(World world) {
		Player player = worldModel.getPlayer();
		if (player == null) return;

		int resourceX = player.getX();
		int resourceY = player.getY();

		switch (player.getDirection()) {
			case E -> resourceX += 1;
			case W -> resourceX -= 1;
			case N -> resourceY -= 1;
			case S -> resourceY += 1;
		}

		if (!world.isInBounds(resourceX, resourceY)) return;

		if (combatController != null) {
			AttackResult result = combatController.playerAttackMob(resourceX, resourceY);

			// combat gets priority over resource interaction on the same target tile.
			if (result == AttackResult.MOB_HIT || result == AttackResult.MOB_KILLED) {
				world.repaint();
				return;
			}
		}

		Tile tile = world.getTileView(resourceX, resourceY);
		if (tile == null) return;
		ResourceType resourceType = tile.getResourceType();

		if (interactionService != null) {
			interactionService.handleResourceInteraction(resourceType, tile);
		}
	}

	public void setMobSpawner(MobSpawner spawner) {
		this.mobSpawner = spawner;
	}

	public Player getPlayer() {
		return worldModel.getPlayer();
	}

	public void setPlayer(Player player) {
		worldModel.setPlayer(player);
	}

	public void populateFromMap(TerrainType[][] map, World world) {
		if (map == null || world == null) return;
		final int rows = map.length;
		final int cols = (rows > 0) ? map[0].length : 0;

		Runnable populate = () -> {
			for (int r = 0; r < rows; r++) {
				for (int c = 0; c < cols; c++) {
					TerrainType terrain = map[r][c];
					Tile tile = TileFactory.create(terrain, world);
					tileViewManager.setTileView(r, c, tile);
				}
			}
			tileViewManager.addAllToContainer(world);
		};
		// Swing components must be modified on the EDT.
		if (SwingUtilities.isEventDispatchThread()) {
			populate.run();
		} else {
			try {
				SwingUtilities.invokeAndWait(populate);
			} catch (InterruptedException | java.lang.reflect.InvocationTargetException e) {
				throw new RuntimeException(e);
			}
		}
	}

	public void setInteractionService(InteractionService interactionService) {
		this.interactionService = interactionService;
	}

	public void setCombatController(CombatController combatController) {
		this.combatController = combatController;
	}

	public void setDropCollector(DropCollector dropCollector) {
		this.dropCollector = dropCollector;
	}

	public void handleMove(World world, int dx, int dy) {
		if (dx == 0 && dy == 0) return;

		Player player = worldModel.getPlayer();
		if (player == null) return;

		int nextX = player.getX() + dx;
		int nextY = player.getY() + dy;

		System.out.println("handleMove called: dx=" + dx + ", dy=" + dy
				+ " player=(" + player.getX() + "," + player.getY() + ")");

		// leaving world bounds triggers a chunk transition
		if (!world.isInBounds(nextX, nextY)) {
			transitionController.startTransition(world, player, dx, dy);
			return;
		}

		TileModel currentTile = worldModel.getTileModel(player.getX(), player.getY());
		if (currentTile != null) currentTile.setContainsPlayer(false);

		if (dx == 1) player.moveEast();
		else if (dx == -1) player.moveWest();
		else if (dy == 1) player.moveSouth();
		else player.moveNorth();

		TileModel newTile = worldModel.getTileModel(player.getX(), player.getY());
		if (newTile != null) newTile.setContainsPlayer(true);

		// collect dropped items after movement so pickup happens on the new tile.
		if (dropCollector != null) {
			dropCollector.tryCollect(player);
		}

		world.repaint();
	}

	public void tick(World world) {
		if (world == null) return;
		droppedItemManager.tick();
		if (dayNightCycle != null) dayNightCycle.tick();
		if (regrowthManager != null) regrowthManager.tick(world);
	}

	public DayNightCycle getDayNightCycle() { return dayNightCycle; }

	public void handleTileAction(World world) {
		tileActionController.handleTileAction(world);
	}

	public Tile getTileView(World world, int x, int y) {
		return tileViewManager.getTile(x, y);
	}

	public void setResource(World world, int row, int col, ResourceType resource) {
		if (world == null) return;

		Tile tile = tileViewManager.getTile(col, row);
		if (tile == null) return;
		tile.setResource(resource);
		Runnable update = () -> tileViewManager.updateContainerCell(world, row, col);
		if (SwingUtilities.isEventDispatchThread()) {
			update.run();
		} else {
			SwingUtilities.invokeLater(update);
		}
	}

}