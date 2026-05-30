Feature: Mob Behaviour
  As a game entity
  I want mobs to behave according to their rules
  So that the combat and loot system works correctly

  Scenario: Mob initializes with correct health
    Given a SHEEP mob is created at position 0 0
    Then the mob should be alive
    And the mob health should follow its pre-set value

  Scenario: Mob takes damage and loses health
    Given a SHEEP mob is created at position 0 0
    And the health of SHEEP mob is 20
    When the mob takes 5 damage
    Then the mob health should be 15
    And the mob should still be alive

  Scenario: Mob dies when health reaches zero
    Given a SHEEP mob is created at position 0 0
    And the health of SHEEP mob is 20
    When the mob takes 20 damage
    Then the mob should be dead
    And the mob health should be 0

  Scenario: Dead mob cannot take more damage
    Given a SHEEP mob is created at position 0 0
    And the mob takes 10 damage
    When the mob takes 10 more damage
    Then the mob health should remain 0
    And the mob should remain dead

  Scenario: Mob drops correct item on death
    Given a SHEEP mob is created at position 0 0
    When the mob is defeated
    Then the drops should contain its pre-set materials

  Scenario: Mob drop multiplier works
    Given a SHEEP mob is created at position 0 0
    When the mob is defeated
    Then the number of drops should be greater than zero