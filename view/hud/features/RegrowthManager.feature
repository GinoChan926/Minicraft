Feature: Regrowth Manager
  As a game system, I want resources to regrow after being harvested
  so that the player can collect them again later.

  Scenario: Pending count starts at zero
    Then the pending regrowth count should be 0

  Scenario: Adding regrowth increases pending count
    When a regrowth is scheduled at tile 5, 5 with type APPLE_TREE and delay 60
    Then the pending regrowth count should be 1

  Scenario: Adding multiple regrowths increases pending count
    When a regrowth is scheduled at tile 5, 5 with type APPLE_TREE and delay 60
    And a regrowth is scheduled at tile 10, 10 with type APPLE_TREE and delay 30
    Then the pending regrowth count should be 2

  Scenario: Resource regrows after more ticks than delay
    Given tile 5, 5 has no resource
    When a regrowth is scheduled at tile 5, 5 with type APPLE_TREE and delay 10
    And 15 ticks have passed
    Then tile 5, 5 should have resource APPLE_TREE
    And the pending regrowth count should be 0

  Scenario: Two resources regrow at different times
    Given tile 5, 5 has no resource
    And tile 10, 10 has no resource
    When a regrowth is scheduled at tile 5, 5 with type APPLE_TREE and delay 10
    And a regrowth is scheduled at tile 10, 10 with type APPLE_TREE and delay 20
    And 10 ticks have passed
    Then tile 5, 5 should have resource APPLE_TREE
    And tile 10, 10 should still have no resource
    And the pending regrowth count should be 1

  Scenario: Both resources eventually regrow
    Given tile 5, 5 has no resource
    And tile 10, 10 has no resource
    When a regrowth is scheduled at tile 5, 5 with type APPLE_TREE and delay 10
    And a regrowth is scheduled at tile 10, 10 with type APPLE_TREE and delay 20
    And 20 ticks have passed
    Then tile 5, 5 should have resource APPLE_TREE
    And tile 10, 10 should have resource APPLE_TREE
    And the pending regrowth count should be 0

  Scenario: Resource does not regrow on tile that already has a resource
    Given tile 5, 5 has resource APPLE_TREE
    When a regrowth is scheduled at tile 5, 5 with type APPLE_TREE and delay 10
    And 10 ticks have passed
    Then tile 5, 5 should have resource APPLE_TREE
    And the pending regrowth count should be 0
