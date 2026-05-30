Feature: World generation

  Scenario: Base grass fills the world
    Given a world
    When the world is generated
    Then every tile should initially be grass

  Scenario: Generated tiles are placed into the world
    Given a world
    When the world is generated
    Then every position in the world should contain a tile

  Scenario: World contains more than one tile type after generation
    Given a world
    When the world is generated
    Then the world should contain multiple tile types