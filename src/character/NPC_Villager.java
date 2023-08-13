package character;

import java.util.Random;
import main.GamePanel;

public class NPC_Villager extends SuperCharacter {

    public NPC_Villager(GamePanel gp) {
        super(gp);
        direction = "down";
        speed = 1;

        getCharacterImage();
        setDialogue();
    }

    // Read villager character image
    public void getCharacterImage() {

        up1 = setup("resources/npc/villager-up1", gp.tileSize, gp.tileSize);
        up2 = setup("resources/npc/villager-up2", gp.tileSize, gp.tileSize);
        down1 = setup("resources/npc/villager-down1", gp.tileSize, gp.tileSize);
        down2 = setup("resources/npc/villager-down2", gp.tileSize, gp.tileSize);
        left1 = setup("resources/npc/villager-left1", gp.tileSize, gp.tileSize);
        left2 = setup("resources/npc/villager-left2", gp.tileSize, gp.tileSize);
        right1 = setup("resources/npc/villager-right1", gp.tileSize, gp.tileSize);
        right2 = setup("resources/npc/villager-right2", gp.tileSize, gp.tileSize);
    }

    // What dialogues are stored in
    // dialogues array
    public void setDialogue() {
        dialogues[0] = "Hello, warrior.";
        dialogues[1] = "This island has many surprises\n for you.";
        dialogues[2] = "Good luck on your adventure !";
    }

    // Villager specific behaviours
    @Override
    public void setAction() {
        actionLockCounter++;

        // Every 2s(120 frames) possibility of direction change
        if (actionLockCounter == 120) {
            Random random = new Random();
            int i = random.nextInt(100) + 1; // number from 1 to 100

            if (i <= 25) {
                direction = "up";
            }

            if (i > 25 && i <= 50) {
                direction = "down";
            }

            if (i > 50 && i <= 75) {
                direction = "left";
            }

            if (i > 75 && i <= 100) {
                direction = "right";
            }

            actionLockCounter = 0;
        }

    }

    // Store the dialogue in currentDialogue in UI class
    public void speak() {
        super.speak();
    }

}