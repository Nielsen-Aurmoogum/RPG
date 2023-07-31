package character;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

/**
 * Parent class for all
 * characters that will be in the game
 */
public class Character {
    public int worldX, worldY; // Position on map
    public int speed;

    // Character orientation
    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    public String direction;

    // Used to decide when to cycle through different orientations
    public int spriteCounter = 0;
    public int spriteNum = 1;

    // Collision region
    public Rectangle solidArea;
    public boolean collisionOn = false;
    public int solidAreaDefaultX, solidAreaDefaultY;
}