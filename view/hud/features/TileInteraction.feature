Feature: Tile Interaction
  As a player
  I want to pick up and place tiles
  So that I can reshape the world

  Background:
    Given a world with a player standing on a grass tile

  Scenario: Pick up a grass tile
    When the player interacts with the tile under them by pressing Q
    Then the tile under the player should be SOIL
    And the player inventory should contain a "GRASS Tile"

  Scenario: Cannot pick up a soil tile
    Given the player is standing on a SOIL tile
    When the player interacts with the tile under them by pressing Q
    Then the tile under the player should be SOIL
    And the player inventory should be empty

  Scenario: Place a tile from equipped main hand
    Given the player has picked up a grass tile
    And the player equips the tile in main hand
    And the player is standing on a SOIL tile
    When the player interacts with the tile under them by pressing Q
    Then the tile under the player should be GRASS
    And the player inventory should be empty

  Scenario: Cannot place a tile without equipping it
    Given the player has picked up a grass tile
    And the player is standing on a SOIL tile
    When the player interacts with the tile under them by pressing Q
    Then the tile under the player should be SOIL
    And the player inventory should contain a "GRASS Tile"

  Scenario: Pick up tile when inventory is full
    Given the player inventory is full
    When the player interacts with the tile under them by pressing Q
    Then the tile under the player should be GRASS
    And the player inventory size should remain the same

  Scenario: Player remains visible after placing a tile
    Given the player has picked up a grass tile
    And the player equips the tile in main hand
    And the player is standing on a SOIL tile
    When the player interacts with the tile under them by pressing Q
    Then the tile under the player should contain the player



