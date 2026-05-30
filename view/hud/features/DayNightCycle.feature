
Feature: Day and Night Cycle
  As game loop, I want to have day and night so that it feels more realistic.


  Scenario: Game starts during daytime
    When 0 seconds have passed
    Then it should be daytime

  Scenario: Night starts after two thirds of day length
    Given a day length of 30 seconds
    When 20 seconds have passed
    Then it should be nighttime

  Scenario: Full darkness at night without lighter
    Given a day length of 30 seconds
    And the player does not have a Lighter with Fire
    When 20 seconds have passed
    Then the darkness at tile 10, 10 should be 215

  Scenario: Player tile is dark without lighter at night
    Given a day length of 30 seconds
    And the player does not have a Lighter with Fire
    When 20 seconds have passed
    Then the darkness at tile 4, 4 should be 215

  Scenario: Player tile is fully lit with lighter at night
    Given a day length of 30 seconds
    And the player has a Lighter with Fire equipped
    When 20 seconds have passed
    Then the darkness at tile 4, 4 should be 0

  Scenario: Adjacent tiles are partially lit with lighter at night
    Given a day length of 30 seconds
    And the player has a Lighter with Fire equipped
    When 20 seconds have passed
    Then the darkness at tile 5, 4 should be 120
    And the darkness at tile 4, 5 should be 120
    And the darkness at tile 3, 4 should be 120
    And the darkness at tile 4, 3 should be 120

  Scenario: Diagonal adjacent tiles are partially lit with lighter
    Given a day length of 30 seconds
    And the player has a Lighter with Fire equipped
    When 20 seconds have passed
    Then the darkness at tile 5, 5 should be 120
    And the darkness at tile 3, 3 should be 120
    And the darkness at tile 5, 3 should be 120
    And the darkness at tile 3, 5 should be 120

  Scenario: Two tiles away is darker with lighter
    Given a day length of 30 seconds
    And the player has a Lighter with Fire equipped
    When 20 seconds have passed
    Then the darkness at tile 6, 4 should be 120
    And the darkness at tile 2, 4 should be 120

  Scenario: Far tiles are fully dark even with lighter
    Given a day length of 30 seconds
    And the player has a Lighter with Fire equipped
    When 20 seconds have passed
    Then the darkness at tile 10, 10 should be 215

  Scenario: Darkness changes when lighter is unequipped at night
    Given a day length of 30 seconds
    And the player has a Lighter with Fire equipped
    When 20 seconds have passed
    Then the darkness at tile 4, 4 should be 0
    When the player unequips the Lighter with Fire
    Then the darkness at tile 4, 4 should be 215

  Scenario: Darkness changes when lighter is equipped at night
    Given a day length of 30 seconds
    And the player does not have a Lighter with Fire
    When 20 seconds have passed
    Then the darkness at tile 4, 4 should be 215
    When the player equips a Lighter with Fire
    Then the darkness at tile 4, 4 should be 0