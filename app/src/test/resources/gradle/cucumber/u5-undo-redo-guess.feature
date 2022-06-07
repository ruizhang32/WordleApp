Feature: U5 - As a manual player, I want to be able to undo and redo my guesses so that I can repeat a game.

  Scenario: AC1 - An "undone" guess is removed from my guesses.
    Given I am playing as a manual guesser
    When I make a guess with word input "house"
    And I make a guess with undo or redo input "undo"
    Then "house" is removed from the stack of guesses
  Scenario: AC2 - The number of guesses is decreased by one when I undo a guess.
    Given I am playing as a manual guesser
    When I make a guess with word input "house"
    And I make a guess with undo or redo input "undo"
    Then The size of guess stack decreases by one
  Scenario: AC3 - I can redo a guess only if I have undone a guess.
    Given I am playing as a manual guesser
    When I make a guess with undo or redo input "redo"
    Then I am not allowed to redo the guess
  Scenario: AC4 - When I redo a guess, the undone guess is re-added to the stack of guesses.
    Given I am playing as a manual guesser
    When I make a guess with word input "house"
    And I make a guess with undo or redo input "undo"
    And I make a guess with undo or redo input "redo"
    Then "house" is re-added to the stack of guesses
  Scenario: AC5 - I can redo all my guesses in the reverse order I made the guesses, i.e. if I guessed (in order) "fuses", "curls"
and "dwarf", then undone (in the reverse order they have been made) "dwarf", "curls" and "fuses", I can redo
them in their original order.
    Given I am playing as a manual guesser
    When I make guesses "fuses" "curls" "dwarf" in order
    And I make a guess with undo or redo input "undo"
    Then "fuses" "curls" exist orderly in the stack of guesses
    And I make a guess with undo or redo input "undo"
    Then "fuses" is the element in the stack of guesses
    And I make a guess with undo or redo input "undo"
    Then The stack of guesses is empty
    And I make a guess with undo or redo input "redo"
    Then "fuses" is the element in the stack of guesses
    And I make a guess with undo or redo input "redo"
    Then "fuses" "curls" exist orderly in the stack of guesses
    And I make a guess with undo or redo input "redo"
    Then "fuses" "curls" "dwarf" exist orderly in the stack of guesses