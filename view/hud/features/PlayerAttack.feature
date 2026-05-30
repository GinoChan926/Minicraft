Feature: Player Attack Interaction
  As a player
  I want to attack mobs with my equipped weapon
  So that I can defeat them and survive

  Scenario: Player cannot attack without a weapon equipped
    Given a world with a player at position 5 5 facing E
    And a SHEEP mob is spawned at position 6 5
    And the player has no weapon equipped
    When the player attacks the faced tile
    Then the attack result should be NO_WEAPON
    And the mob at position 6 5 should still be alive

  Scenario: Player cannot attack when no mob is present
    Given a world with a player at position 5 5 facing E
    And the player has an Iron Axe equipped
    When the player attacks the faced tile
    Then the attack result should be NO_TARGET

  Scenario: Player hits a mob and reduces its health
    Given a world with a player at position 5 5 facing E
    And a SHEEP mob is spawned at position 6 5
    And the player has an Iron Axe equipped
    When the player attacks the faced tile
    Then the attack result should be MOB_HIT
    And the mob at position 6 5 should have lost health

  Scenario: Player kills a mob after enough hits
    Given a world with a player at position 5 5 facing E
    And a SHEEP mob is spawned at position 6 5
    And the player has an Iron Axe equipped
    When the player attacks the faced tile until the mob dies
    Then the attack result should be MOB_KILLED
    And the mob at position 6 5 should be dead

  Scenario: Weapon loses durability on each attack
    Given a world with a player at position 5 5 facing E
    And a SHEEP mob is spawned at position 6 5
    And the player has an Iron Axe equipped
    And the weapon durability is 10
    When the player attacks the faced tile
    Then the weapon durability should be 9

