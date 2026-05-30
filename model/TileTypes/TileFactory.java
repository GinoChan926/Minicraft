package org.minicraft02160.model.TileTypes;


import org.minicraft02160.view.Tile;
import org.minicraft02160.view.World;
import org.minicraft02160.model.worldengine.TerrainType;

import java.util.Map;
import java.util.function.Function;

// Used for the Factory Pattern to create tiles based on terrain type
public class TileFactory {

    private static final Map<TerrainType, Function<World, Tile>> registry = Map.of(
            TerrainType.GRASS, GrassTile::new,
            TerrainType.WATER, WaterTile::new,
            TerrainType.STONE, StoneTile::new,
            TerrainType.SAND, SandTile::new,
            TerrainType.DEEP_WATER, DeepWaterTile::new,
            TerrainType.SOIL, SoilTile::new
    );

    public static Tile create(TerrainType type, World world) {
        Function<World, Tile> creator = registry.get(type);

        if (creator == null) {
            throw new IllegalArgumentException("Unknown tile type: " + type);
        }

        return creator.apply(world);
    }
}