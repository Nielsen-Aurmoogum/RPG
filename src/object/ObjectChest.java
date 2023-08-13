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
        image = setup("resources/objects/newBoot", gp.tileSize, gp.tileSize);
        image2 = setup("resources/objects/newBoot", gp.tileSize, gp.tileSize);
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
            
            StringBuilder sb = new StringBuilder();
            sb.append("You have found the treasure !");
            down1 = image2;
            opened = true;
            gp.ui.currentDialogue = sb.toString();
        }

        else {
            gp.ui.currentDialogue = "This treasure has already been found.";
        }
    }

}