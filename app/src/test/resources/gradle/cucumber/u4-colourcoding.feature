Feature: U4 - Return Colour coded words to the player

  Scenario: AC1 - When a player gets the correct word, it should be displayed as all green characters
    Given I am guessing the word "crane"
    When I make the guess "crane"
    Then All letters are green

  Scenario: AC2 - When a player guesses a word with some correct, out-of-place, and incorrect words the colour coding informs them of this
    Given I am guessing the word "crane"
    When I make the guess "cakes"
    Then The appropriate letters are colour coded green "c", yellow "a", gray "k", yellow "e", and gray "s"