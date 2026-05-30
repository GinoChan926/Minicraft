#// As a player, I want to see resources on the screen so I can know where to retrieve them from.
Feature: Visible resource

  Scenario: Apple tree visible in top right of the screen
    Given world of size row: 10, column: 20
    And apple tree resource
    And resource coordinate row: 0, column: 19
    When world is drawn
    Then apple tree exists at coordinate row: 0, column: 19