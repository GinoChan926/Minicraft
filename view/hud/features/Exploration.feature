Feature: Exploration across chunks
  As a player
  I want to move within the map and across its edges
  So that exploration feels continuous

  Scenario: Move to a walkable tile
    Given a generated world with a player at row 4, col 4
    And the tile at row 4, col 5 is walkable
    When the player presses right
    Then the player position becomes row 4, col 5

  Scenario Outline: Crossing any edge transitions to the adjacent chunk
    Given a generated world with a player at <startBoundary> boundary
    And current chunk coordinates are x 0, y 0
    When the player presses <key>
    Then the current chunk coordinates are x <dx>, y <dy>
    And the player spawns on <spawnEdge> edge of the new chunk

    Examples:
      | startBoundary | key   | dx | dy | spawnEdge |
      | east          | right | 1  | 0  | west      |
      | west          | left  | -1 | 0  | east      |
      | north         | up    | 0  | -1 | south     |
      | south         | down  | 0  | 1  | north     |

  Scenario: Returning to a visited chunk restores its state
    Given a player transitions from chunk x 0, y 0 to chunk x 1, y 0
    And a resource is placed in chunk x 1, y 0 at row 0, col 0
    When the player returns to chunk x 1, y 0 later
    Then the resource still exists at row 0, col 0
