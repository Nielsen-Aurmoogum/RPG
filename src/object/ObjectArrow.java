package object;

import character.Projectile;
import main.GamePanel;

public class ObjectArrow extends Projectile {

    GamePanel gp;

    public ObjectArrow(GamePanel gp) {
        super(gp);
        this.gp = gp;

        name = "Arrow";
        speed = 7;
        fullLife = 80;
        attackPower = 2;
        alive = false;

        getImage();
    }

    // Read projectile image
    public void getImage() {
        up1 = setup("resources/objects/key", gp.tileSize, gp.tileSize);
        up2 = setup("resources/objects/key", gp.tileSize, gp.tileSize);
        down1 = setup("resources/objects/key", gp.tileSize, gp.tileSize);
        down2 = setup("resources/objects/key", gp.tileSize, gp.tileSize);
        left1 = setup("resources/objects/key", gp.tileSize, gp.tileSize);
        left2 = setup("resources/objects/key", gp.tileSize, gp.tileSize);
        right1 = setup("resources/objects/key", gp.tileSize, gp.tileSize);
        right2 = setup("resources/objects/key", gp.tileSize, gp.tileSize);
    }
}