# PacManJavaFX
In this group project, we made the game Pacman using JavaFX.
The main classes that we had were Map and Character.
The player can choose the difficulty of the game (easy or hard). Easy will make the enemy move randomly. However, hard will make the enemy move in the shortest path to Pacman using the Breadth First Search algorithm. 

![1](https://github.com/RamizFaragalla/PacManJavaFX/blob/master/gameScreenshot.PNG)

Map class:
  - had a 2D char array that was read from a txt file
  - each character represents an item on the map (ex: W = wall)
  - using the 2D char array, we created the actual map on the screen
  - the actual map was a GridPane object that contained ImageView objects
  - Map has an update function to update the positions of the characters on the map

Character (abstract class):
  - has a position (row and column) 
  - has abstract controls() and stopControls()
  
  1. Player Class extends Character:
      - has keyevent handler for controls
      - has an inner class that implements AnimationTimer to allow for continous movement of the Pacman once a direction is choosen
      - has points counter to keep track of score
  
  2. Enemy Class extends Character:
      - has an inner class that implements AnimationTimer (enemies make a move every 0.3 of a sec)
      - easy enemy ai chooses a random valid direction (direction that doesn't have a wall or another enemy)
      - hard enemy ai will choose the direction that is in the shortest path to the player using BFS. Every 0.3 of a sec, each enemy      recalculates the current shortest path to the player.

Once the Pacman collides with an enemy or all the points are taken, then the controls of the characters stop and Game Over label appears.

Our game also has save/load feature:
  - made most of our classes implement the Serializable interface
  - used the FileChooser class to allow the user to save multiple files
  - once save is pressed, the map and player objects get saved to a binary file
  - once load is pressed, the map and player objects get read back from the binary file and the map is updated
  
Lastly, the Restart Game button restarts the application. The window is closed and a new window appears. 
