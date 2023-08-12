package character;

import main.GamePanel;

public class Projectile extends SuperCharacter {

    public Projectile(GamePanel gp) {
        super(gp);
    }

    public void set(int worldX, int worldY, String direction, boolean alive) {
        this.worldX = worldX;
        this.worldY = worldY;
        this.direction = direction;
        this.alive = alive;
        this.life = this.fullLife;
    }

    public void update() {

        boolean contactPlayer = gp.collisionTest.checkPlayer(this);
        if (gp.player.invincible == false && contactPlayer == true) {
            damagePlayer(attackPower);
            alive = false;
        }

        // Moving projectile
        switch (direction) {
            case "up":
                worldY -= speed;
                break;

            case "down":
                worldY += speed;
                break;

            case "left":
                worldX -= speed;
                break;

            case "right":
                worldX += speed;
                break;
        }

        // Every frame life of projectile decreases
        life--;
        if (life <= 0) {
            alive = false;
        }

        spriteCounter++;
        if (spriteCounter > 12) {
            if (spriteNum == 1) {
                spriteNum = 2;
            } else if (spriteNum == 2) {
                spriteNum = 1;
            }
            spriteCounter = 0;
        }
    }
}