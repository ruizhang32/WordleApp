Feature: U1 - Create a new game record

  Scenario:  AC1 - Create a game record with player, and number of guesses
    Given I have a player "John"
    When I create a game record with the player, word "crane", and 4 guesses
    Then The game record is created with the correct user, word, number of guesses, and the current timestamp

  Scenario:  AC2 - Create a second game record with player, and number of guesses
    Given I have a player "John"
    And I have already created a game record with the player, word "crane", and 4 guesses
    And I create a game record with the player, word "crane", and 4 guesses
    Then The game record is created with the correct user, word, number of guesses, and the current timestamp
    And there are now two game records with identical values except timestamp

  Scenario: AC3 - I can not create a game record with 0 or fewer guesses
    Given I have a player "John"
    When I create a game record with the player, word "crane", and 0 guesses
    Then I expect an exception that disallows me to create it