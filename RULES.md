# Competition rules

## TLDR:

Two teams, four robots (each team gets two). Defend your own circle, "capture" the other teams'.
*Points are awarded only when 1) atleast one of your robots are in the other teams circle, 2) no robots are in your own circle, and 3) none of the other teams robots are in their own circle.*

## Teams & Robots

Each team get to have two robots. One for attacking, and one for defending.
Your job is to take these robots and make them great again, ehrm... make them better.
Modify the robots, modify the code. May the best team win!

## Robot communication

The robots talk to a server using WebSockets.
* The robots register with the server
* The server starts the game (all robots start at the same time)
* The server tells you which color is your target color
* The server lets the other robots know where "you" are, and lets you know where the others are.
* Every time your color sensor reads a new color that color is sent to the server. The color is your position.

## Scoring points
When the game state on the server has the following state your team is awarded 1 point each second.
* One or more robots on your team is on the other teams circle
* No robots are on your own circle
* No robots from the other team are on their circle. 

## Winning
The team with the highest score when the round is over wins the round. 
