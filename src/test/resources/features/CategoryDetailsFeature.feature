Feature: This use case will validate parameters like name, CanRelist and description of a promotion item

  Background: User has access to category details end point

  Scenario:
    Given User has access to category details end point with parameter as "catalogue" equals "false"
    When User hits the endpoint and gets a response
    Then Validates "Name" key for value "Carbon credits"
    And Validates "CanRelist" key for another value "true"
    And Validates promotion item's "Name" "Gallery" has a "Description" "2x larger image"

