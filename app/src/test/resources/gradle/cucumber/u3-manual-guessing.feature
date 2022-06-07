Feature: U4 - Playing the game manually

  Scenario: AC1 - When playing the game with manual guesses each guess correctly adds to the number of guesses
    Given I am playing with the manual guesser
    When I make a guess
    Then The guess count is updated correctly
    And The Guess is added to the stack

  Scenario: AC2 - When playing the game, I cannot make a guess with a 4 letter word
    Given I am playing with the manual guesser
    When I make a guess "tall"
    Then I am not allowed to make the guess
    And The guess count is not changed
    And The Guess is not added to the stack
