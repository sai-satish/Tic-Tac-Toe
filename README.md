# Tic-Tac-Toe
### Java AWT

## Experience the Game
```
git clone https://github.com/sai-satish/Tic-Tac-Toe
cd Tic-Tac-Toe
java Game
```

## Game.java
- The Game works on Java AWT Swing components.
- This file includes the logic behind the working of entire game
- The Game starts with creating a new Game Board that consists of the JFrame which itself consists of two frames

  1. The First frame is the user instructions fields, which can be used to direct the user and also has a reset button
  2. The Second Frame is the Activity field, that contains AWT grid layout which helds 9 buttons to interact

- When the user taps on each button the button turn up with the name of the user occupied.

## GridInfo Class
- GridInfo defines each button in the game layout. This defines the properties of the Button.

## Board Class
- board class consists of all the UI components and the interactions defined for the components

## ResetButton Class
- when reset button is clicked the current game is disposed and new Game board is created

## CheckWin Class
- CheckWin class consists the logic behind declaring the winner of the game.
