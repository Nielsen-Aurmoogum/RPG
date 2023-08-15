package character;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import javax.imageio.ImageIO;
import main.GamePanel;
import main.UtilityTool;

/**
 * Parent class for all
 * characters and objects
 * that will be in the game
 */
public class SuperCharacter {
    GamePanel gp;
    public int worldX, worldY; // Position on map
    public Rectangle attackArea = new Rectangle(0, 0, 0, 0);

    // Dialogue
    String dialogues[] = new String[20];
    int dialogueIndex = 0;

    // Character orientation and associated behaviour attributes
    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    public BufferedImage attackUp1, attackUp2, attackRight1, attackRight2, attackLeft1, attackLeft2, attackDown1,
            attackDown2;
    public String direction = "down";
    public int actionLockCounter = 0;
    public boolean invincible = false;
    public int invincibleCounter = 0;
    boolean attack = false;
    public boolean alive = true;
    public boolean dying = false;
    boolean hpBarOn = false;

    // Character attributes
    public String name;
    public int speed;
    public int fullLife;
    public int life;
    public int level;
    public int strength;
    public int agility;
    public int attackPower;
    public int defensePower;
    public int exp;
    public int nextLevelExp;
    public SuperCharacter currentWeapon;
    public SuperCharacter currentShield;

    // Item attributes
    public int attackValue;
    public int defenseValue;
    public String description = "";

    // Types
    public int type; // 0 - Player, 1 - NPC, 2 - Monster ...
    public final int type_player = 0;
    public final int type_npc = 1;
    public final int type_monster = 2;
    public final int type_greenLightSaber = 3;
    public final int type_redLightSaber = 4;
    public final int type_shield = 5;
    public final int type_usable = 6;
    public final int type_obstacle = 7;

    // Used to decide when to cycle through different orientations
    public int spriteCounter = 0;
    public int spriteNum = 1;

    // Monster utilities
    int dyingCounter = 0;
    int hpBarCounter = 0;
    public Projectile projectile;
    public int shotAvailableCounter = 0;

    // Collision region
    public Rectangle solidArea = new Rectangle(0, 0, 48, 48);
    public boolean collisionOn = false;
    public int solidAreaDefaultX, solidAreaDefaultY;

    // Objects like keys, doors and hearts
    public BufferedImage image, image2, image3;
    public boolean collision = false;

    // Constructor
    public SuperCharacter(GamePanel gp) {
        this.gp = gp;
    }

    // Some useful getter methods
    public int getLeftX() {
        return worldX + solidArea.x;
    }

    public int getRightX() {
        return worldX + solidArea.x + solidArea.width;
    }

    public int getTopY() {
        return worldY + solidArea.y;
    }

    public int getBottomY() {
        return worldY + solidArea.y + solidArea.height;
    }

    public int getCol() {
        return (worldX + solidArea.x) / gp.tileSize;
    }

    public int getRow() {
        return (worldY + solidArea.y) / gp.tileSize;
    }

    // Will be overridden
    public void setAction() {
    }

    // Will be overridden
    public void damageReact() {
    }

    // Will be overridden
    public void interact() {
    }

    // Store the dialogue in currentDialogue in UI class
    public void speak() {

        // When end of dialogue array is reached
        // Go back to first index
        if (dialogues[dialogueIndex] == null) {
            dialogueIndex = 0;
        }
        gp.ui.currentDialogue = dialogues[dialogueIndex];
        dialogueIndex++;

        // When conversation happening,
        // make NPC face player
        switch (gp.player.direction) {
            case "up":
                direction = "down";
                break;

            case "down":
                direction = "up";
                break;

            case "left":
                direction = "right";
                break;

            case "right":
                direction = "left";
                break;
        }
    }

    // Will be overridden
    public boolean use(SuperCharacter character) {
        return false;
    }

    public void update() {
        setAction();

        collisionOn = false;

        // Checking collision for NPC or Monster or Tile or Object
        gp.collisionTest.checkTile(this);
        gp.collisionTest.checkObject(this, false);
        gp.collisionTest.checkCharacter(this, gp.npc);
        gp.collisionTest.checkCharacter(this, gp.monster);
        boolean contactPlayer = gp.collisionTest.checkPlayer(this);

        if (this.type == type_monster && contactPlayer == true) {
            damagePlayer(attackPower);
        }

        // No collision, character moves
        if (collisionOn == false) {
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
        }

        // Dictates when a variation of an
        // orientation is used
        spriteCounter++;
        if (spriteCounter > 15) {

            if (spriteNum == 1) {
                spriteNum = 2;
            }

            else if (spriteNum == 2) {
                spriteNum = 1;
            }
        }

        if (invincible == true) {
            invincibleCounter++;
            if (invincibleCounter > 40) {
                invincible = false;
                invincibleCounter = 0;
            }
        }

        // Restrict number of projectiles shot
        if (shotAvailableCounter < 30) {
            shotAvailableCounter++;
        }
    }

    // Make player receive damage
    public void damagePlayer(int attackPower) {
        if (gp.player.invincible == false) {
            int damage = attackPower - gp.player.defensePower;

            // Standard minimum damage
            if (damage <= 0) {
                gp.player.life -= 2;
                gp.player.invincible = true;
            }

            else {
                gp.player.life -= damage;
                gp.player.invincible = true;
            }
        }
    }

    // Draw npc or monster on screen
    public void draw(Graphics2D g2) {
        BufferedImage image = null;
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        // Draw only for our window, don't draw all characters from start
        if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX
                && worldX - gp.tileSize < gp.player.worldX + gp.player.screenX
                && worldY + gp.tileSize > gp.player.worldY - gp.player.screenY
                && worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {

            // Different variations of orientations
            // are used
            switch (direction) {
                case "up":
                    if (spriteNum == 1) {
                        image = up1;
                    } else if (spriteNum == 2) {
                        image = up2;
                    }
                    break;
                case "down":
                    if (spriteNum == 1) {
                        image = down1;
                    } else if (spriteNum == 2) {
                        image = down2;
                    }
                    break;
                case "left":
                    if (spriteNum == 1) {
                        image = left1;
                    } else if (spriteNum == 2) {
                        image = left2;
                    }
                    break;
                case "right":
                    if (spriteNum == 1) {
                        image = right1;
                    } else if (spriteNum == 2) {
                        image = right2;
                    }
                    break;
            }

            // health
            if (type == type_monster && hpBarOn == true) {

                double oneScale = (double) gp.tileSize / fullLife;
                double hpBarValue = oneScale * life;

                g2.setColor(new Color(35, 35, 35));
                g2.fillRect(screenX - 1, screenY - 16, gp.tileSize + 2, 12);
                g2.setColor(new Color(255, 0, 30));
                g2.fillRect(screenX, screenY - 15, (int) hpBarValue, 10);

                hpBarCounter++;

                if (hpBarCounter > 600) {
                    hpBarCounter = 0;
                    hpBarOn = false;
                }
            }

            if (invincible == true) {
                hpBarOn = true;
                hpBarCounter = 0;
                changeAlpha(g2, 0.4F);

            }
            if (dying == true) {
                dyingAnimation(g2);
            }
            g2.drawImage(image, screenX, screenY, null);
            changeAlpha(g2, 1F);
        }
    }

    public void dyingAnimation(Graphics2D g2) {

        dyingCounter++;

        int i = 5;

        if (dyingCounter <= i) {
            changeAlpha(g2, 0f);
        }
        if (dyingCounter > i && dyingCounter <= i * 2) {
            changeAlpha(g2, 0f);
        }
        if (dyingCounter > i * 2 && dyingCounter <= i * 3) {
            changeAlpha(g2, 1f);
        }
        if (dyingCounter > i * 3 && dyingCounter <= i * 4) {
            changeAlpha(g2, 0f);
        }
        if (dyingCounter > i * 4 && dyingCounter <= i * 5) {
            changeAlpha(g2, 1f);
        }
        if (dyingCounter > i * 5 && dyingCounter <= i * 6) {
            changeAlpha(g2, 0f);
        }
        if (dyingCounter > i * 6 && dyingCounter <= i * 7) {
            changeAlpha(g2, 1f);
        }
        if (dyingCounter > i * 7 && dyingCounter <= i * 8) {
            changeAlpha(g2, 0f);
        }
        if (dyingCounter > i * 8) {
            alive = false;
        }
    }

    // Utility method to set alpha value
    public void changeAlpha(Graphics2D g2, float alphaValue) {
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alphaValue));
    }

    // Scale character image
    public BufferedImage setup(String imagePath, int width, int height) {
        UtilityTool utool = new UtilityTool();
        BufferedImage image = null;

        try {
            image = ImageIO.read(new File(imagePath + ".png"));
            image = utool.scaleImage(image, width, height);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return image;
    }

    public int getDetected(SuperCharacter user, SuperCharacter target[], String targetName) {

        int index = 999;

        // Check the surrounding objects
        int nextWorldX = user.getLeftX();
        int nextWorldY = user.getTopY();

        switch (user.direction) {
            case "up":
                nextWorldY = user.getTopY() - user.speed;
                break;

            case "down":
                nextWorldY = user.getBottomY() + user.speed;
                break;

            case "left":
                nextWorldX = user.getLeftX() - user.speed;
                break;

            case "right":
                nextWorldX = user.getRightX() + user.speed;
                break;
        }

        int col = nextWorldX / gp.tileSize;
        int row = nextWorldY / gp.tileSize;

        // Check if player is in front of object
        // and object is a door
        for (int i = 0; i < target.length; i++) {
            if (target[i] != null) {
                if (target[i].getCol() == col && target[i].getRow() == row && target[i].name.equals(targetName)) {
                    index = i;
                    break;
                }
            }
        }
        return index;
    }
}