# solitaire-solver
Simple code written in Java to solve the game solitaire (see https://en.wikipedia.org/wiki/Peg_solitaire). 
The game is simple compared to games like chess or go and has been solved completely by a computer program already. Nevertheless I think it's interesting to work on it and try to find my own solutions.
## Current status
A class SolitaireField is available which represents a position. A test class is also available which can calculate solutions for different positions.
## Todos
* Write proper test class for the SolitaireField class.
* Command line interpreter to allow calculation of different start positions without changing the code for every position.
* GUI which allows the user to play the game on his own or to calculate a solution for the current position.
* Optimization for the calculation of a solution. Currently only exhaustive search is used. Different optimizations might be possible.
