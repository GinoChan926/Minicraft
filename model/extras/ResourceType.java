package org.minicraft02160.model.extras;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public enum ResourceType {
    APPLE_TREE("apple_tree.png", 60),
    EMPTY_APPLE_TREE("apple_tree_empty.png", 0),
    WATER(0),
    GRASS(60),
    STONE(0);

    private String pictureFile;
    private BufferedImage image;
    private final boolean hasImage;
    private final int regrowthTime;

    private ResourceType(String pictureFile, int regrowthTime) {
        this.pictureFile = pictureFile;
        this.hasImage = true;
        this.regrowthTime = regrowthTime;
        try {
            this.image = ImageIO.read(getClass().getClassLoader().getResourceAsStream(this.getPictureFile()));
        } catch (IOException e) {
            this.image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
        }
    }

    private ResourceType(int regrowthTime) {
        this.hasImage = false;
        this.regrowthTime = regrowthTime;

    }
    public boolean hasImage() {
        return hasImage;
    }

    public boolean canRegrow() {
        return regrowthTime > 0;
    }

    public String getPictureFile() {
        return pictureFile;
    }

    public BufferedImage getImage() {
        return this.image;
    }

    public int getRegrowthTime() {
        return regrowthTime;
    }

}
