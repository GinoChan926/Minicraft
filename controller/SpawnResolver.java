package org.minicraft02160.controller;

import java.awt.*;

public class SpawnResolver {
    @FunctionalInterface
    public interface WalkableChecker {
        boolean isWalkable(int x, int y);
    }

    private final int rows;
    private final int cols;
    private final WalkableChecker walkableChecker;

    public SpawnResolver(int rows, int cols, WalkableChecker walkableChecker) {
        this.rows = rows;
        this.cols = cols;
        this.walkableChecker = walkableChecker;
    }

    private boolean isTileWalkable(int x, int y) {
        return walkableChecker.isWalkable(x, y);
    }

    public Point resolveSafeSpawn(int preferredX, int preferredY, int dx, int dy) {
        if (isTileWalkable(preferredX, preferredY)) {
            return new Point(preferredX, preferredY);
        }

        if (dx != 0) {
            int x = preferredX; // left or right edge
            for (int dist = 1; dist < rows; dist++) {
                int up = preferredY - dist;
                int down = preferredY + dist;
                if (up >= 0 && isTileWalkable(x, up)) return new Point(x, up);
                if (down < rows && isTileWalkable(x, down)) return new Point(x, down);
            }
        } else if (dy != 0) {
            int y = preferredY;
            for (int dist = 1; dist < cols; dist++) {
                int left = preferredX - dist;
                int right = preferredX + dist;
                if (left >= 0 && isTileWalkable(left, y)) return new Point(left, y);
                if (right < cols && isTileWalkable(right, y)) return new Point(right, y);
            }
        }
        return null;
    }
}
