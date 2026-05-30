Feature: Dropped Item Manager
  As a player
  I want dropped items to remain in the world temporarily
  So that I can either recover them or let others pick them up

  Scenario: Drop an item into the world
    Given the dropped item manager exists
    When an item "Apple" is dropped at position 5 5
    Then there should be 1 dropped item in the world


  Scenario: Collect item at player position
    Given the dropped item manager exists
    And an item "Apple" is dropped at position 3 3
    When items are collected at position 3 3
    Then the collected items should contain "Apple"
    And there should be 0 dropped items in the world

  Scenario: Cannot drop null item
    Given the dropped item manager exists
    When a null item is dropped at position 5 5
    Then there should be 0 dropped items in the world
