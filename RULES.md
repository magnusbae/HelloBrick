# Competition rules

## TLDR:

Two teams, four robots (each team gets two). Defend your own circle, "capture" the other teams'.
*Points are awarded only when you are on the other teams while also not having a robot on your own circle.*

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

