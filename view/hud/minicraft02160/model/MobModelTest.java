package org.minicraft02160.model;

import org.junit.jupiter.api.Test;
import org.minicraft02160.model.extras.MobType;

import static org.junit.jupiter.api.Assertions.*;

class MobModelTest {

    MobModel mobModel = new MobModel(MobType.SHEEP, 10, 2);

    @Test
    void isChasing() {
        assertTrue(!mobModel.isChasing());

        mobModel.setChasing(true);
        assertTrue(mobModel.isChasing());
    }

    @Test
    void getDamage() {
        assertEquals(MobType.SHEEP.getBaseDamage(), mobModel.getDamage());
    }

    @Test
    void setX() {
        mobModel.setX(15);
        assertEquals(15, mobModel.getX());
    }

    @Test
    void setY() {
        mobModel.setY(20);
        assertEquals(20, mobModel.getY());
    }

    @Test
    void setChasing() {
        mobModel.setChasing(true);
        assertTrue(mobModel.isChasing());

        mobModel.setChasing(false);
        assertFalse(mobModel.isChasing());
    }

    @Test
    void takeDamage() {
        int initialHealth = mobModel.getMaxHealth();

        mobModel.takeDamage(3);
        assertEquals(initialHealth - 3, mobModel.getCurrentHealth());
        assertTrue(mobModel.isAlive());

        mobModel.takeDamage(initialHealth);
        assertEquals(0, mobModel.getCurrentHealth());
        assertFalse(mobModel.isAlive());

        mobModel.takeDamage(5);
        assertEquals(0, mobModel.getCurrentHealth());
    }
}