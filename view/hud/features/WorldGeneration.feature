Feature: World generation

  Scenario: Generate basic world
    Given 20 rows
    And 20 columns
    And grass type of tile
    When asked to generate
    Then A 20 by 20 world of one tile exists

  Scenario: Generate world with different tiles
    Given 20 rows
    And 20 columns
    And different types of tiles
    When asked to generate
    Then a 20 by 20 world with different tiles exits
