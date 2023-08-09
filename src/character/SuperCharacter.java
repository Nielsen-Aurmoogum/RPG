package character;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import javax.imageio.ImageIO;
import main.GamePanel;
import main.UtilityTool;

/**
 * Parent class for all
 * characters that will be in the game
 */
public class SuperCharacter {
    GamePanel gp;
    public int worldX, worldY; // Position on map
    public Rectangle attackArea = new Rectangle(0,0,0,0);
    // Dialogue
    String dialogues[] = new String[20];
    int dialogueIndex = 0;

    // Character orientation and associated behaviour attributes
    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    public BufferedImage attackUp1, attackUp2, attackRight1, attackRight2, attackLeft1, attackLeft2, attackDown1, attackDown2;
    public int speed;
    public String direction = "down";
    public int actionLockCounter = 0;
    public boolean invincible = false;
    public int invincibleCounter = 0;
    public int type;
    boolean attack = false;
    // Used to decide when to cycle through different orientations
    public int spriteCounter = 0;
    public int spriteNum = 1;

    // Collision region
    public Rectangle solidArea = new Rectangle(0, 0, 48, 48);
    public boolean collisionOn = false;
    public int solidAreaDefaultX, solidAreaDefaultY;

    // Character Status
    public int fullLife;
    public int life;

    // Objects like keys and doors
    public BufferedImage image, image2, image3;
    public String name;
    public boolean collision = false;

    // Constructor
    public SuperCharacter(GamePanel gp) {
        this.gp = gp;
    }

    public void setAction() {
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

    public void update() {
        setAction();

        collisionOn = false;

        // Checking collision for NPC or Monster
        gp.collisionTest.checkTile(this);
        gp.collisionTest.checkObject(this, false);
        gp.collisionTest.checkCharacter(this, gp.npc);
        gp.collisionTest.checkCharacter(this, gp.monster);
        boolean contactPlayer = gp.collisionTest.checkPlayer(this);

        if (this.type == 2 && contactPlayer == true) {
            if (gp.player.invincible == false) {
                gp.player.life -= 1;
                gp.player.invincible = true;
            }
        }

        // No collision, npc moves
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
    }

    // Draw npc on screen
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
                    if (spriteNum == 1) {image = up1; }
                    else if (spriteNum == 2) { image = up2 ;}
                    break;
                case "down":
                    if (spriteNum == 1) {image = down1;} 
                    else if (spriteNum == 2) {image = down2;}
                    break;
                case "left":
                    if (spriteNum == 1) {image = left1;} 
                    else if (spriteNum == 2) {image = left2;}
                    break;
                case "right":
                    if (spriteNum == 1) {image = right1;} 
                    else if (spriteNum == 2) {image = right2;}
                    break;
            }

            if (invincible == true) {
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
            }

            g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);

            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        }
    }

    // Scale character image
    public BufferedImage setup(String imagePath, int width, int height) {
        UtilityTool utool = new UtilityTool();
        BufferedImage image = null;

        try {
            image = ImageIO.read(new File(imagePath + ".png"));
            image = utool.scaleImage(image,width, height);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return image;
    }
}