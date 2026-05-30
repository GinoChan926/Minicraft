Feature: Item
  As a game item
  I want to have correct properties
  So that players can identify me

  Scenario: Item has correct name
    Given an Iron Axe item is created
    Then the item name should be "Iron Axe"

  Scenario: Item name is stored correctly
    Given an Apple item is created
    Then the item name should be "Apple"

