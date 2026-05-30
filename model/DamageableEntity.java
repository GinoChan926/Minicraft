package org.minicraft02160.model;

// Introduced after refactoring to implement the Player and MobModel for the Strategy Pattern
public interface DamageableEntity {
    void takeDamage(int damage);
    boolean isAlive();
}