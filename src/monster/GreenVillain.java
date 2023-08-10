package monster;

import java.util.Random;

import character.SuperCharacter;
import main.GamePanel;

public class GreenVillain extends SuperCharacter {

    GamePanel gp;

    public GreenVillain(GamePanel gp) {
        super(gp);

        this.gp = gp;

        name = "Green Villain";
        type = type_monster;
        speed = 1;
        fullLife = 20;

        solidArea.x = 8;
        solidArea.y = 16;
        solidArea.width = 32;
        solidArea.height = 32;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        getImage();
    }

    public void getImage() {

        up1 = setup("resources/monster/monsterup1", gp.tileSize, gp.tileSize);
        up2 = setup("resources/monster/monsterup2", gp.tileSize, gp.tileSize);
        down1 = setup("resources/monster/monsterdown1", gp.tileSize, gp.tileSize);
        down2 = setup("resources/monster/monsterdown2", gp.tileSize, gp.tileSize);
        left1 = setup("resources/monster/monsterleft1", gp.tileSize, gp.tileSize);
        left2 = setup("resources/monster/monsterleft2", gp.tileSize, gp.tileSize);
        right1 = setup("resources/monster/monsterright1", gp.tileSize, gp.tileSize);
        right2 = setup("resources/monster/monsterright2", gp.tileSize, gp.tileSize);

    }

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

    public void damageReact() {

        actionLockCounter = 0;
        direction = gp.player.direction;
    }

}