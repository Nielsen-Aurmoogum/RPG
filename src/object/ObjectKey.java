package object;

import character.SuperCharacter;
import main.GamePanel;

public class ObjectKey extends SuperCharacter {

    GamePanel gp;

    public ObjectKey(GamePanel gp) {
        super(gp);
        this.gp = gp;

        type = type_usable;
        name = "Key";
        down1 = setup("resources/objects/key", gp.tileSize, gp.tileSize);
        description = "[" + name + "]\nFind a door where \nyou can use it.";
    }

    @Override
    public boolean use(SuperCharacter character) {

        gp.gameState = gp.dialogueState;

        int objectIndex = getDetected(character, gp.obj, "Door");

        if (objectIndex != 999) {
            gp.ui.currentDialogue = "You have used the " + name + " to open the door.";
            gp.obj[objectIndex] = null;
            return true; // Has been used
        }

        else {
            gp.ui.currentDialogue = "You need to find a door.";
            return false; // Has not been used
        }
    }
}