Feature: Create Mission

  Scenario: Create Mission
    Given user wants to create a mission with the following attributes
      | name           | completed |
      | Young Avengers | false       |

    When the user saves new missions
    Then the save is SUCCESSFUL