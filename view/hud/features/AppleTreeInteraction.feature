Feature: Collect apples
  Scenario: Collect apple from tree
    Given world model of size row: 10, column: 20
    And a GRASS tile at with apple tree resource at coordinates row: 0, column: 19
    And player at coordinate row: 0, column: 18 and facing "E"
    When player interacts with apple tree
    Then tile resource updates to empty apple tree