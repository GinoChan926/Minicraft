Feature: Item Drops and Inventory Management, Inventory Drop on Player Death, Inventory Expansion, Crafting Recipe Visibility

  As a player
  I want dropped items to remain in the world temporarily
  So that I can recover them or others can pick them up

  Scenario: Dropped item persists in the world
    Given a player drops an item
    When the item is placed in the world
    Then the item should remain visible for a limited time

  Scenario: Dropped item disappears after timeout
    Given a player drops an item
    When the timeout duration expires
    Then the item should be removed from the world


#  As a player
#  I want to drop my inventory when I die
#  So that there is risk in exploring dangerous areas

  Scenario: Player drops all inventory on death
    Given a player has items in their inventory
    When the player dies
    Then all inventory items should be dropped at the death location


#  As a player
#  I want to enlarge my inventory storage
#  So that I can carry more items as difficulty increases

  Scenario: Player crafts a backpack to increase inventory
    Given a player has required crafting materials
    When the player crafts a backpack
    Then the player’s inventory capacity should increase

  Scenario: Inventory capacity limits item pickup
    Given a player has a full inventory
    When the player attempts to pick up an item
    Then the item should not be added to the inventory

  Scenario: Increased capacity allows more items
    Given a player has an expanded inventory
    When the player picks up items
    Then the inventory should accept items up to the new limit

#  As a player
#  I want to see crafting recipes
#  So that I know what I can create with my materials

  Scenario: Player views available crafting recipes
    Given a player opens the crafting interface
    Then the player should see a list of available recipes

  Scenario: Recipes show required materials
    Given a player views a crafting recipe
    Then the recipe should display required materials and quantities

  Scenario: Only craftable recipes are highlighted
    Given a player has some materials
    When the player opens the crafting interface
    Then recipes that can be crafted should be highlighted
    And recipes that cannot be crafted should be disabled or dimmed

  Scenario: Recipe updates based on inventory
    Given a player gains or drop items
    When the player opens the crafting interface
    Then the list of craftable recipes should update accordingly
