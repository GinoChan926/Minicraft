package org.minicraft02160.controller;

import org.minicraft02160.model.EquipableItem;
import org.minicraft02160.model.Inventory;
import org.minicraft02160.model.PlaceableTile;
import org.minicraft02160.model.Player;
import org.minicraft02160.model.TileTypes.SoilTile;
import org.minicraft02160.model.TileTypes.TileFactory;
import org.minicraft02160.model.worldengine.TerrainType;
import org.minicraft02160.model.extras.EquipmentSlot;
import org.minicraft02160.view.Tile;
import org.minicraft02160.view.World;
import org.minicraft02160.model.Items.TileItem;

public class TileActionController {

    public void handleTileAction(World world) {
        Player player = world.getPlayerView();
        if (player == null) return;

        int x = player.getX();
        int y = player.getY();

        Tile tile = world.getTileView(x, y);
        if (tile == null) return;

        TerrainType topLayer = tile.getTerrainType();
        var currentItemInMainHand = player.getInventory().getEquipped(EquipmentSlot.MAIN_HAND);

        if (currentItemInMainHand instanceof PlaceableTile placeable && placeable.canBePlaced()) {
            placeTile(world, player, x, y);
        } else {
            pickUpTile(world, player, tile, x, y);
        }
    }

    private void pickUpTile(World world, Player player, Tile tile, int x, int y) {
        if (!isPickable(tile.getTerrainType())) return;

        Inventory inventory = player.getInventory();
        TileItem tileItem = new TileItem(tile.getTerrainType());

        if (!inventory.add(tileItem)) return;

        Tile soilTile = new SoilTile(world);
        world.setTileAt(y, x, soilTile);
        soilTile.setContainsPlayer(true);
        world.repaint();
    }

    private void placeTile(World world, Player player, int x, int y) {
        Inventory inventory = player.getInventory();
        EquipableItem equipped = inventory.getEquipped(EquipmentSlot.MAIN_HAND);

        if (equipped == null) return;

        if (!(equipped instanceof PlaceableTile placeable)) return;

        if (!placeable.canBePlaced()) return;

        TerrainType typeToPlace = placeable.getPlaceableTerrainType();
        if (typeToPlace == null) return;

        inventory.unequip(equipped);
        inventory.remove(equipped);

        Tile newTile = TileFactory.create(typeToPlace, world);
        world.setTileAt(y, x, newTile);
        newTile.setContainsPlayer(true);
        world.repaint();
    }

    private boolean isPickable(TerrainType type) {
        return type != TerrainType.DEEP_WATER
                && type != TerrainType.SOIL;
    }
}