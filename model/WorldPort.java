package org.minicraft02160.model;


// Used to remove the player's dependency on the world and the view, used by the player to interact with the world
public interface WorldPort {
    void repaint();
    boolean isTileWalkable(int x, int y);
    void dropItemAt(Item item, int x, int y);
}