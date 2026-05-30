
Feature: Tile distribution
  Scenario: Water clusters
    Given a world exists
    When asked to generate a water patch
    Then a water patch is being created

  Scenario: Stone clusters
    Given a world
    When asked to generate a stone patch
    Then a stone patch is being created

  Scenario: Sand around the water
    Given a world
    And water patches are on
    When sand is being added
    Then sand should exist around water

  Scenario: Sand should only appear next to water
    Given a world
    When sand is being added
    Then every sand tile should be adjacent to at least one water tile

  Scenario: Deep water is created inside water areas
    Given a world
    And water patches are on
    And sand is being added
    When deep water is being added
    Then deep water tiles should exist inside water areas

  Scenario: Deep water should not be next to sand
    Given a world
    And water patches are on
    And sand is being added
    When deep water is being added
    Then no deep water tile should be adjacent to sand

  Scenario: Deep water should only replace water tiles
    Given a world
    And water patches are on
    When deep water is being added
    Then every deep water tile should come from a water tile

  Scenario: Deep water should not be walkable
    Given a world with deep water
    When a player tries to walk on deep water
    Then the movement should be blocked