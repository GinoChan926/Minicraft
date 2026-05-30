package org.minicraft02160.model;

import org.minicraft02160.model.extras.ConsumableItem;
import java.awt.Image;

// Introduced after refactoring using the Strategy Pattern, to provide images for each dropped item, which allows us to have different implementations for the different items
public interface DropImageProvider {
    Image getImageFor(ConsumableItem item);
}