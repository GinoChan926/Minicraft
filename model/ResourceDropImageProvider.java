package org.minicraft02160.model;

import org.minicraft02160.model.extras.ConsumableItem;

import javax.imageio.ImageIO;
import java.awt.Image;
import java.io.InputStream;
import java.util.EnumMap;
import java.util.Map;
import java.util.logging.Logger;

// ResourceDropImageProvider provides the images for the dropped resources, using the Strategy Pattern it implements the DropImageProvider
public class ResourceDropImageProvider implements DropImageProvider {

    private static final Logger LOG =
            Logger.getLogger(ResourceDropImageProvider.class.getName());

    private final Map<ConsumableItem, Image> cache = new EnumMap<>(ConsumableItem.class);

    @Override
    public Image getImageFor(ConsumableItem item) {
        return cache.computeIfAbsent(item, this::loadImage);
    }

    private Image loadImage(ConsumableItem item) {
        String path = item.getImagePath();
        try (InputStream is = getClass().getResourceAsStream(path)) {
            if (is == null) {
                LOG.warning("Image not found at path: " + path);
                return null;
            }
            return ImageIO.read(is);
        } catch (Exception e) {
            LOG.warning("Failed to load image: " + path + " - " + e.getMessage());
            return null;
        }
    }
}