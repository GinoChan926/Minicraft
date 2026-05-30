package org.minicraft02160;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.minicraft02160.view.Tile;
import org.minicraft02160.model.TileTypes.GrassTile;
import org.minicraft02160.model.TileTypes.WaterTile;
import org.minicraft02160.view.World;
import org.minicraft02160.controller.WorldGenerator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class WorldGenerationSteps {

    private int rows;
    private int cols;
    private String tileType;
    private World world;

    @Given("{int} rows")
    public void rows(int rows) {
        this.rows = rows;
    }

    @Given("{int} columns")
    public void columns(int cols) {
        this.cols = cols;
    }

    @Given("{word} type of tile")
    public void type_of_tile(String tileType) {
        this.tileType = tileType;
    }

    @When("asked to generate")
    public void asked_to_generate() {
        world = WorldGenerator.generate(rows, cols);
    }

    @Then("A {int} by {int} world of one tile exists")
    public void a_by_world_of_one_tile_exists(int expectedRows, int expectedCols) {
        assertEquals(expectedRows, world.getRows());
        assertEquals(expectedCols, world.getCols());

    }

    @And("different types of tiles")
    public void differentTypesOfTiles() {
        world = WorldGenerator.generate(rows, cols);

    }

    @Then("a {int} by {int} world with different tiles exits")
    public void aByWorldWithDifferentTilesExits(int rows, int cols) {
        world = WorldGenerator.generate(rows, cols);
        assertEquals(rows, world.getRows());
        assertEquals(cols, world.getCols());

        boolean foundGrass = false;
        boolean foundWater = false;
        for (int y = 0; y < world.getRows(); y++){
            for( int x = 0 ; x< world.getCols(); x++){
                Tile tile = world.getTileView(x,y);

                if( tile instanceof GrassTile){
                    foundGrass= true ;
                }
                if( tile instanceof WaterTile){
                    foundWater = true;
                }
            }
        }
        assertTrue(foundGrass);
        assertTrue(foundWater);
    }
}