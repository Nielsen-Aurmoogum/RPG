package character;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.InputHandler;

public class Player extends Character {
    GamePanel gp;
    InputHandler inputH;

    public Player(GamePanel gp, InputHandler inputH) {
        this.gp = gp;
        this.inputH = inputH;

        setStartValues();
        getPlayerImage();
    }

    public void setStartValues() {
        x = 100;
        y = 100;
        speed = 4;
        direction = "up";
    }

    public void getPlayerImage() {

        try {

            up1 = ImageIO.read(
                    new File("resources/player/up1.png"));
            up2 = ImageIO.read(
                    new File("resources/player/up2.png"));
            down1 = ImageIO.read(new File(
                    "resources/player/down1.png"));
            down2 = ImageIO.read(new File(
                    "resources/player/down2.png"));
            right1 = ImageIO.read(new File(
                    "resources/player/right1.png"));
            right2 = ImageIO.read(new File(
                    "resources/player/right2.png"));
            left1 = ImageIO.read(new File(
                    "resources/player/left1.png"));
            left2 = ImageIO.read(new File(
                    "resources/player/left2.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {

        if (inputH.upInput == true || inputH.downInput == true || inputH.leftInput == true
                || inputH.rightInput == true) {

            if (inputH.upInput == true) {
                direction = "up";
                y -= speed;
            }

            else if (inputH.downInput == true) {
                direction = "down";
                y += speed;
            }

            else if (inputH.leftInput == true) {
                direction = "left";
                x -= speed;
            }

            else if (inputH.rightInput == true) {
                direction = "right";
                x += speed;
            }

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

    public void draw(Graphics2D g2) {
        BufferedImage image = null;

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
        g2.drawImage(image, x, y, gp.tileSize, gp.tileSize, null);
    }
}