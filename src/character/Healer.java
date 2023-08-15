package character;

import java.util.Random;

import main.GamePanel;

public class Healer extends SuperCharacter {

    public Healer(GamePanel gp) {
        super(gp);

        direction = "down";
        speed = 1;

        getCharacterImage();
        setDialogue();
    }

    // Read healer images
    public void getCharacterImage() {

        up1 = setup("resources/healer/angelup1", gp.tileSize, gp.tileSize);
        up2 = setup("resources/healer/angelup2", gp.tileSize, gp.tileSize);
        down1 = setup("resources/healer/angeldown1", gp.tileSize, gp.tileSize);
        down2 = setup("resources/healer/angeldown2", gp.tileSize, gp.tileSize);
        left1 = setup("resources/healer/angelleft1", gp.tileSize, gp.tileSize);
        left2 = setup("resources/healer/angelleft2", gp.tileSize, gp.tileSize);
        right1 = setup("resources/healer/angelright1", gp.tileSize, gp.tileSize);
        right2 = setup("resources/healer/angelright2", gp.tileSize, gp.tileSize);
    }

    // Set healer dialogues
    public void setDialogue() {
        dialogues[0] = "Thank you for releasing me from my\nprison ! I will help you on your\nadventure. Let me heal you.";
    }

    // Healer specific behaviours
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

    // Heal player to full life
    public void healPlayer() {

        super.healPlayer();
    }

}