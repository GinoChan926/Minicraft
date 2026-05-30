package org.minicraft02160.model;

import org.minicraft02160.model.extras.ResourceType;
import org.minicraft02160.model.worldengine.TerrainType;


// The ScreenData class stores the data for the current screen, including the terrain types and resources
public class ScreenData {
    public TerrainType[][] tileTypes;
    public ResourceType[][] resources;

    public ScreenData(int rows, int cols) {
        this.tileTypes = new TerrainType[rows][cols];
        this.resources = new ResourceType[rows][cols];
    }

}
