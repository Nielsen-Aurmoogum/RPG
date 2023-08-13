package object;

import character.SuperCharacter;
import main.GamePanel;

public class ObjectDoor extends SuperCharacter {

    GamePanel gp;

    public ObjectDoor(GamePanel gp) {
        super(gp);
        this.gp = gp;

        type = type_obstacle;
        name = "Door";
        down1 = setup("resources/objects/door", gp.tileSize, gp.tileSize);
        collision = true;
    }

    @Override
    // Player interaction with door
    public void interact() {

        gp.gameState = gp.dialogueState;
        gp.ui.currentDialogue = "You need a key to open this door.";
    }
}