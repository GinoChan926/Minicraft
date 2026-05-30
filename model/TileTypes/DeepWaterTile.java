package org.minicraft02160.model.TileTypes;

import org.minicraft02160.model.extras.ResourceType;
import org.minicraft02160.view.Tile;
import org.minicraft02160.view.World;
import org.minicraft02160.model.worldengine.TerrainType;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class DeepWaterTile extends Tile {

    public DeepWaterTile(World world) {
        super(world);

        try {
            image = ImageIO.read(
                    getClass().getClassLoader().getResourceAsStream("deepwater.png")
            );
        } catch (IOException e) {
            image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
        }

        setTerrainType(TerrainType.DEEP_WATER);
        setResource(ResourceType.WATER); // or DEEP_WATER if you create it
    }

    @Override
    public boolean isWalkable() {
        return false;
    }


}