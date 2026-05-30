package org.minicraft02160.model;

// Introduced after refactoring to implement the Strategy Pattern, defines the light radius, can be used later on in the development process when we add other objects that give off light
public interface LightSource {
    int getLightRadiusAt(int distance);
}
