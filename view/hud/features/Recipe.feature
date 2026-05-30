Feature: Recipe
  As a player
  I want to craft items using recipes
  So that I can create useful items from materials

  Scenario: Cannot craft without materials
    Given a Backpack recipe exists
    And the player has an empty inventory
    Then the player should not be able to craft the recipe

  Scenario: Can craft with correct materials
    Given a Backpack recipe exists
    And the player has these ingredients
      | Fur    | 1 |
      | Wool     | 2 |
    Then the player should be able to craft the recipe

  Scenario: Crafting consumes ingredients
    Given a Backpack recipe exists
    And the player has these ingredients
      | Fur    | 1 |
      | Wool     | 2 |
    When the player crafts the recipe
    Then the inventory should not contain "Fur"
    And the inventory should not contain "Wool"

  Scenario: Crafting produces correct item
    Given a Backpack recipe exists
    And the player has these ingredients
      | Fur    | 1 |
      | Wool     | 2 |
    When the player crafts the recipe
    Then the crafted item name should be "Backpack"

  Scenario: Backpack recipe requires workbench
    Given a Backpack recipe exists
    Then the recipe should require a workbench