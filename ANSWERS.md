# SENG301 Assignment 5 (2022) - Student answers

Rui Zhang

## Task 1 - Identify the patterns in the code

### Pattern 1

#### What pattern is it?

Strategy

#### What is its goal in the code?

1. A new concrete strategy can be easily introduced. 
2. The implementation of Concrete Strategy class(es) can be changed without affecting the Context class. (Well decoupled)
3. The Context class(App.java) can switch strategies at run-time. Strategies can be used interchangeably to alter App behavior.

#### Name of UML Class diagram attached:

Strategy.png

#### Mapping to GoF pattern elements:

| GoF element       | Code element  |
| Context           | App           |
| Strategy          | Guesser       |
| ConcreteStrategyA | RandomGuesser |
| ConcreteStrategyB | SmartGuesser  |
| ConcreteStrategyC | ManualGuesses | 
| algorithm         | getGuess()    |

### Pattern 2

#### What pattern is it?

Proxy

#### What is its goal in the code?

1. Clients (ManualGuesser/SmartGuesser/RandomGuesser classes) can be chosen to work with Proxy or RealSubject.
2. Proxy and RealSubject implement same interface, so the Proxy can disguise itself.
3. RealSubject encapsulates the service in its class. Client does not need to know about real subject providing service.
4. The implementation of RealSubject class can be changed without affecting the Client class.

#### Name of UML Class diagram attached:

Proxy.png

#### Mapping to GoF pattern elements:

| GoF element | Code element      |
| Subject     | DictionaryQuery   |
| RealSubject | DictionaryService |
| Proxy       | Dictionary        |
| Client      | ManualGuesser     |
| Client      | RandomGuesser     |
| Client      | SmartGuesser      |
| Client      | App               |

## Task 2 - Full UML Class diagram

### Name of file of full UML Class diagram attached

WordleApp.png

## Task 3 - Implement new feature

### What pattern fulfils the need for the feature?

Memento 

### What is its goal and why is it needed here?

1. Produce snapshots of the ManualGuesser’s guess stack without violating its encapsulation.
2. Allow user to "undo"/"redo" a guess by choosing a proper Memento.
3. Simplify the Originator(ManualGuesser)’s code by letting the CareTaker class maintain the history of the Originator’s state.
4. Both of Command pattern and Memento pattern can be used for implementing "undo". 
Memento pattern undo by restoring a snapshot of the originator' state, while Command pattern undo by executing compensating actions.
By comparing with command and memento pattern, memento is more suitable for a game.
Memento is better than command is when compensating actions costs huge.
i.e. Image that there are thousands of receivers and every receiver needs to do some logic to back to the previous step.
Another pro is that if we’d like to back to some specific earlier snapshots.
i.e. if we want to add a new keyword “undo2 or undoX” to make game to undo twice or x times. 
Definitely the command needs a loop which costs more than memento.
In our scenario, if Command pattern is used, there will be multiple Receivers (guesses, backup guesses and numGuesses). 
Also, numGuesses is an "int" type pass-in parameter of Command. So it is not convenient to change this value inside Command. 

### Name of UML Class diagram attached:

Memento.png

### Mapping to GoF pattern elements:

| GoF element   | Code element       |
| Originator    | ManualGuesser      |
| Caretaker     | CareTaker          |
| Memento       | Memento            |
| State         | guesses            |
| State         | numGuesses         |
| setMemento    | saveStateToMemento |
| createMemento | getStateFromMemento|

## Task 4 - BONUS - Acceptance tests for Task 4

### Feature file (Cucumber Scenarios)

u5-undo-redo-guess.feature

### Java class implementing the acceptance tests

UndoRedoGuessFeature.java
