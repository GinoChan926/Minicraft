package org.minicraft02160.model.TileTypes;

import org.minicraft02160.model.extras.ResourceType;
import org.minicraft02160.view.Tile;
import org.minicraft02160.view.World;
import org.minicraft02160.model.worldengine.TerrainType;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

import java.util.Random;


public class GrassTile extends Tile {

    private Random rnd = new Random();

    public GrassTile(World world) {
        super(world);

        try {
            image = ImageIO.read(
                    getClass().getClassLoader().getResourceAsStream("grass.png")
            );
        } catch (IOException e) {
            image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
        }

        if (rnd.nextDouble() < 0.075) {
            this.setResource(ResourceType.APPLE_TREE);
        } else {
            this.setResource(ResourceType.GRASS);
        }
        setTerrainType(TerrainType.GRASS);

    }

    @Override
    public boolean isWalkable() {
        if (this.getResourceType() == ResourceType.GRASS) {
            return true;
        } else {
            return false;
        }
    }

}

