package org.minicraft02160.model.TileTypes;

import org.minicraft02160.model.extras.ResourceType;
import org.minicraft02160.view.Tile;
import org.minicraft02160.view.World;
import org.minicraft02160.model.worldengine.TerrainType;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class WaterTile extends Tile {

    public WaterTile(World world) {
        super(world);

        try {
            image = ImageIO.read(
                    getClass().getClassLoader().getResourceAsStream("water.png")
            );
        } catch (IOException e) {
            image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
        }

        this.setResource(ResourceType.WATER);
        setTerrainType(TerrainType.WATER);
    }

    @Override
    public boolean isWalkable() {
        return true;
    }

}
