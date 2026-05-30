package org.minicraft02160.view;

import org.minicraft02160.model.extras.CardinalPoints;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class PlayerRenderer {

    public BufferedImage getDirectionalImage(BufferedImage image, CardinalPoints direction) {
        return rotateImage(image, direction.getAngle());
    }

    public static BufferedImage rotateImage(BufferedImage image, double angle) {
        int width = image.getWidth();
        int height = image.getHeight();

        double radians = Math.toRadians(angle);
        double sin = Math.abs(Math.sin(radians));
        double cos = Math.abs(Math.cos(radians));
        int newWidth = (int) Math.floor(width * cos + height * sin);
        int newHeight = (int) Math.floor(height * cos + width * sin);

        BufferedImage rotatedImage = new BufferedImage(
                newWidth, newHeight, BufferedImage.TYPE_INT_ARGB
        );
        Graphics2D g2d = rotatedImage.createGraphics();

        AffineTransform transform = new AffineTransform();
        transform.translate((newWidth - width) / 2.0, (newHeight - height) / 2.0);
        transform.rotate(radians, width / 2.0, height / 2.0);

        g2d.setTransform(transform);
        g2d.drawImage(image, 0, 0, null);
        g2d.dispose();

        return rotatedImage;
    }
}