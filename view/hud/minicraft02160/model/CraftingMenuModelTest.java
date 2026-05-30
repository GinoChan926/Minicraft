package org.minicraft02160.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CraftingMenuModelTest {

    CraftingMenuModel cmmodel = new CraftingMenuModel();

    @Test
    void close() {
        cmmodel.open();
        assertTrue(cmmodel.isOpen());

        cmmodel.close();
        assertFalse(cmmodel.isOpen());
    }

    @Test
    void toggle() {
        cmmodel.toggle();
        assertTrue(cmmodel.isOpen());

        cmmodel.toggle();
        assertFalse(cmmodel.isOpen());
    }

    @Test
    void isOpen() {
        assertFalse(cmmodel.isOpen());
        cmmodel.open();
        assertTrue(cmmodel.isOpen());
    }

    @Test
    void getSelectedIndex() {
        assertEquals(0, cmmodel.getSelectedIndex());
    }

    @Test
    void setSelectedIndex() {
        cmmodel.setSelectedIndex(3);
        assertEquals(3, cmmodel.getSelectedIndex());
    }

    @Test
    void isAtWorkbench() {
        assertTrue(cmmodel.isAtWorkbench());

        cmmodel.setAtWorkbench(false);
        assertFalse(cmmodel.isAtWorkbench());
    }

    @Test
    void getCurrentTab() {
        assertEquals(0, cmmodel.getCurrentTab());
    }

    @Test
    void setCurrentTab() {
        cmmodel.setCurrentTab(1);
        assertEquals(1, cmmodel.getCurrentTab());
    }
}