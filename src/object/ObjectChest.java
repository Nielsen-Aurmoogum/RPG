package object;

import character.SuperCharacter;
import main.GamePanel;

public class ObjectChest extends SuperCharacter {

    GamePanel gp;
    boolean opened = false;

    public ObjectChest(GamePanel gp) {
        super(gp);
        this.gp = gp;

        type = type_obstacle;
        name = "Treasure Chest";
        image = setup("resources/objects/chestClosed", gp.tileSize, gp.tileSize);
        image2 = setup("resources/objects/chestOpened", gp.tileSize, gp.tileSize);
        down1 = image;
        collision = true;

        solidArea.x = 4;
        solidArea.y = 16;
        solidArea.width = 40;
        solidArea.height = 32;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

    }

    @Override
    // Player interaction with chest
    public void interact() {

        gp.gameState = gp.dialogueState;

        if (opened == false) {

            down1 = image2;
            opened = true;
            gp.gameState = gp.winState;
        }

        else {
            gp.ui.currentDialogue = "This treasure has already been found.";
        }
    }

}