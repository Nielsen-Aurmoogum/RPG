package object;

import character.Projectile;
import main.GamePanel;

public class ObjectBomb extends Projectile {

    GamePanel gp;

    public ObjectBomb(GamePanel gp) {
        super(gp);
        this.gp = gp;

        name = "Bomb";
        speed = 7;
        fullLife = 60;
        attackPower = 3;
        alive = false;

        getImage();
    }

    // Read projectile image
    public void getImage() {
        up1 = setup("resources/objects/bomb", gp.tileSize, gp.tileSize);
        up2 = setup("resources/objects/bomb", gp.tileSize, gp.tileSize);
        down1 = setup("resources/objects/bomb", gp.tileSize, gp.tileSize);
        down2 = setup("resources/objects/bomb", gp.tileSize, gp.tileSize);
        left1 = setup("resources/objects/bomb", gp.tileSize, gp.tileSize);
        left2 = setup("resources/objects/bomb", gp.tileSize, gp.tileSize);
        right1 = setup("resources/objects/bomb", gp.tileSize, gp.tileSize);
        right2 = setup("resources/objects/bomb", gp.tileSize, gp.tileSize);
    }
}