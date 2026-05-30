package org.minicraft02160.model.TileTypes;

import org.minicraft02160.view.Tile;
import org.minicraft02160.view.World;
import org.minicraft02160.model.worldengine.TerrainType;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class SoilTile extends Tile {

    public SoilTile(World world) {
        super(world);

        try {
            image = ImageIO.read(
                    getClass().getClassLoader().getResourceAsStream("soil.png")
            );
        } catch (IOException e) {
            image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
        }

        setTerrainType(TerrainType.SOIL);
    }

    @Override
    public boolean isWalkable() {
        return true;
    }
}

