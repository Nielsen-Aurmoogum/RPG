package character;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.InputHandler;
import main.UtilityTool;

/**
 * Class of main character
 */
public class Player extends Character {
    public final int screenX;
    public final int screenY;
    GamePanel gp;
    InputHandler inputH;
    int hasKey = 0;

    // Constructor
    public Player(GamePanel gp, InputHandler inputH) {
        this.gp = gp;
        this.inputH = inputH; // Key input

        setStartValues();
        getPlayerImage();

        // Center charater
        screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
        screenY = gp.screenHeight / 2 - (gp.tileSize / 2);

        // Collision Hit-Box
        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 16;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 32;
        solidArea.height = 32;
    }

    // Set default position, speed and direction
    public void setStartValues() {
        worldX = gp.tileSize * 18;
        worldY = gp.tileSize * 18;
        speed = 4;
        direction = "up";
    }

    // Read main character image
    public void getPlayerImage() {

        up1 = setup("up1");
        up2 = setup("up2");
        down1 = setup("down1");
        down2 = setup("down2");
        left1 = setup("left1");
        left2 = setup("left2");
        right1 = setup("right1");
        right2 = setup("right2");
    }

    // Scale player image
    public BufferedImage setup(String imageName) {
        UtilityTool utool = new UtilityTool();
        BufferedImage image = null;

        try {
            image = ImageIO.read(new File("resources/player/" + imageName + ".png"));
            image = utool.scaleImage(image, gp.tileSize, gp.tileSize);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return image;
    }

    /**
     * Update main character information
     */
    public void update() {

        if (inputH.upInput == true || inputH.downInput == true || inputH.leftInput == true
                || inputH.rightInput == true) { // When no keys are pressed, stay still

            // Check direction
            if (inputH.upInput == true) {
                direction = "up";
            }

            else if (inputH.downInput == true) {
                direction = "down";
            }

            else if (inputH.leftInput == true) {
                direction = "left";
            }

            else if (inputH.rightInput == true) {
                direction = "right";
            }

            // Check tile collision
            collisionOn = false;
            gp.collisionTest.checkTile(this);

            // Check object collision
            int objectIndex = gp.collisionTest.checkObject(this, true);
            pickupObject(objectIndex);

            // No collision, player moves
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
        }
    }

    // Player interacts with objects
    public void pickupObject(int i) {

        if (i != 999) {
            String objectName = gp.obj[i].name;

            switch (objectName) {
                case "Key":
                    hasKey++;
                    gp.obj[i] = null; // Remove from screen
                    break;

                case "Door":
                    if (hasKey > 0) {
                        gp.obj[i] = null;
                        hasKey--; // Use key to open door
                    }
                    break;
            }
        }
    }

    // Display main character on the screen
    public void draw(Graphics2D g2) {
        BufferedImage image = null;

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
        g2.drawImage(image, screenX, screenY, null);
    }
}