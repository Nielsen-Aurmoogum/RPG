# The Adventure of Slander man

<img width="880" alt="Screenshot 2023-08-15 at 20 36 40" src="https://github.com/Nielsen-Aurmoogum/RPG/assets/123058725/c8a1c1aa-9dc8-4c7c-99f8-5d932fb70111">

# Introduction: 
In this game, the aim is to be able to kill monsters to level up and to find the hidden real treasure. However, it is not so easy to find them without getting killed by the bombs lanched by the monsters. 

Be careful where you step on, you might get burnt or bombed !
## Group members: 
* Nielsen Aurmoogum - 2112446
* Shivangi Ramnial - 2115392
* Ghennah Poyroo - 2115920

## Game rules:
1. You initially have 6 lives (3 hearts = 6 lives) <br>

2. Upon starting the game, you have 0 experience (0 exp) then when you kill monsters you gain experience which leads to level up. 
3. As you level up, the difficulty to level up doubles, such as: <br>
level 1 -> level 2 (5 exp needed)
<br> level 2 -> level 3 (10 exp needed)
<br> level 3 -> level 4 (15 exp needed)

4. There are some tiles that cannot be walked on. <br>

5. As you level up, your life, attack power and defense power increase. 

6. You have an inventory size of 10 maximum. If an item is collected when maximum inventory capacity is already met, the newly collected item will not be stored in the inventory. 
7. Once you run out of lives and the game is over, you have two options : `Retry` or `Quit`. <br>
<br>**Retry** will save items collected and level reached but monsters are respawned and player's position on the map is reset.<br>
<br> **Quit** will bring you back to the title screen where you can choose to play again or to quit and you lose all items collected as well as level reached. 
8. When pausing the game, you have three options:
 <br> **Controls** - It shows the descriptions and keys of the controls of the game <br>
 <br> **End game** - It will end the game.<br>
 <br>**Resume** - It will take you back to the game on the same position, level and with the collected items in the inventory. 

## How to play the game:
1. To select : press `Enter `<br>
2. To move the character around, we need to make use of the following : 
<br> `W` - move up
<br> `A` - move left
<br> `S` - move down
<br> `D` - move right
3. To pause the game : press `P` <br>
4. To view inventory : press `I` <br>
To move around in inventory, use : `W`,`A`,`S`,`D`
<br> To equip/use : press `Enter`

5. To attack : press `Enter` <br>

6. To open door : press `Enter` <br>
7. To talk to NPC, we need to get close to them then press `Enter`
8. To pick up an item, walk through the item.<br>

## Items
Some items are normally picked up, they are automatically kept in the inventory.
* Shield : It diminishes damage you take from monsters.
* Yoda's Lightsaber: The lightsaber is green and it's attack power is less relatively to the Darth Vader's lightsaber.
* Purple potion : Upon selecting the purple potion from the inventory, it increases the character's speed.
* Boot : Upon picking up a boot, the character's speed decreases. 
* Key : Keys may open up chests and doors.
* Darth Vader's lightsaber : The lightsaber is red and it has more attack power. 


## How to run the project 

In order to run the project code, we use the terminal and we type the following : <br>
You need to clone the repository or download the compressed ZIP file then run the lines below on your terminal

```bash
// Using java 17.0.8 2023-07-18 LTS
cd /directory_where_src_folder_is_saved/ 

// Using javac to compile the code
javac character/*.java tile/*.java object/*.java monster/*.java main/*.java 


// To run the main
java main.Main

```
## Overview of code structure
For this project, we have made use of the object-oriented code structure using java language. The game components are encapsulated as objects, embodying both data and behavior, fostering modularity and reusability. The game's universe can be represented by classes, such as "Player," "GreenVillain," or "SuperCharacter," each instantiated with specific attributes and methods that define their interactions and dynamics. Inheritance allows the creation of specialized classes that inherit properties from a common ancestor, enabling the realization of diverse characters or entities with shared traits. Polymorphism permits the seamless interchangeability of objects, enabling streamlined code and enhancing extensibility. 
