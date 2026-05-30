package org.minicraft02160.model;

import org.minicraft02160.model.recipe.RecipeRegistry;

import java.util.ArrayList;
import java.util.List;

// Takes care of the crafting menu state, open/close status, selected recipe, active tab, methods included are quite self-explanatory
public class CraftingMenuModel {

    private boolean isOpen;
    private int selectedIndex;
    private List<Recipe> recipes;
    private boolean atWorkbench;
    private int currentTab;

    public CraftingMenuModel() {
        this.isOpen = false;
        this.selectedIndex = 0;
        this.atWorkbench = true; // default at workbench, as workbench not implemented yet
        this.recipes = new ArrayList<>();
        this.currentTab = 0;
    }

    public void open() {
        isOpen = true;
        recipes = RecipeRegistry.getAllRecipes();
        selectedIndex = 0;
    }

    public void close() {
        isOpen = false;
    }

    public void toggle() {
        if (isOpen) close();
        else open();
    }

    public boolean isOpen() { return isOpen; }

    public int getSelectedIndex() { return selectedIndex; }
    public void setSelectedIndex(int index) { this.selectedIndex = index; }

    public List<Recipe> getRecipes() { return recipes; }

    public boolean isAtWorkbench() { return atWorkbench; }
    public void setAtWorkbench(boolean atWorkbench) { this.atWorkbench = atWorkbench; }

    public int getCurrentTab() { return currentTab; }
    public void setCurrentTab(int currentTab) { this.currentTab = currentTab; }
}