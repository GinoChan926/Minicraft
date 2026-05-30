Feature: Mob spawning over time
  As a world generation engine
  I want to create mobs at fixed time intervals
  So that players can harvest consumables

  Scenario: Create mobs at 30 second intervals
    Given the creation interval is set to 30 seconds
    When 30 seconds of game time has passed
    And one mob should have been created

  Scenario: Mobs creation only on valid tiles
    Given there is a list of survival region of the mobs
    When a mob is created
    Then it should be on its specific region of tiles

  Scenario: Limit mob density
    Given the maximum mobs per region is 10
    When mobs are created over time
    Then no region should exceed the maximum mob limit

  Scenario: Mobs provide consumables
    Given a mob is spawned
    When the player defeats the mob
    Then the mob should drop at least one consumable item
