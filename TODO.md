TODO
====

* Remember: push (to GitHub) early, push often!
* Add short comments above each method, and sign them



Project
-------

* presentation on Thursday:
  - goals
  - current status
  - next two weekly milestones

GamePiece
---------

* public int getShips() // the number of ships belonging to the owner
* public void setShips()

Planet
------

* public int getResourceValue()
* public String getName()
* public int travelRadius()
* public boolean coordinateWithinTravelRadius(int x, int y)

* public Player dominantPlayer() // the player currently winning the planet
* public int percentCaptured()
* public Player[] getPlayers() // an array of all players with ships currently on the planet
* public int getPlayerShips(Player p) // the number of ships here that the given player has
* public void setPlayerShips(Player p, int n) // set the number of ships the given player has to be n
* public Color getColor() // Atanas, you decide what colors go here 

* public void update() // Atanas, I'll do these 3 (-Ben)
* public void performBattle() // split damage equally between players,
  simultaneously
* public int generateShips() // the number of new ships generated


Fleet
-----

* public void rotateImage(int degrees)
* public void resizeImage() // should exist in the GamePiece superclass,
  just check implementation

Graphics
--------

* a box displaying the number of ships for any GamePiece (Fleet/Planet)
* selection colours are the GamePiece's player's colour

