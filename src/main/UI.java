package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import character.SuperCharacter;
import object.ObjectLife;

/**
 * Used to display components on screen
 */
public class UI {

    GamePanel gp;
    Graphics2D g2;
    Font arial_40, arial_80B;
    BufferedImage heartfull, hearthalf, heartempty;
    public boolean messageOn = false;
    public String message = "";
    int messageCounter = 0;
    public boolean gameFinished = false;
    public String currentDialogue;
    public int commandNum = 0;
    public int slotCol = 0;
    public int slotRow = 0; 

    public UI(GamePanel gp) {

        this.gp = gp;

        arial_40 = new Font("Arial", Font.PLAIN, 40);
        arial_80B = new Font("Arial", Font.BOLD, 80);

        // Create object
        SuperCharacter life = new ObjectLife(gp);
        heartfull = life.image;
        hearthalf = life.image2;
        heartempty = life.image3;
    }

    // Display text
    public void showMessage(String text) {
        message = text;
        messageOn = true;
    }

    // Write on screen
    public void draw(Graphics2D g2) {
        this.g2 = g2;

        g2.setFont(arial_40);
        g2.setColor(Color.RED);

        // TITLE STATE
        if (gp.gameState == gp.titleState) {
            drawTitleScreen();
        }

        // Play state
        if (gp.gameState == gp.playState) {
            drawPlayerLife();
        }

        // Pause state
        if (gp.gameState == gp.pauseState) {
            drawPlayerLife();
            drawPauseScreen();
        }

        // Dialogue state
        if (gp.gameState == gp.dialogueState) {
            drawPlayerLife();
            drawDialogueScreen();
        }

        // Character info state
        if (gp.gameState == gp.characterInfoState) {
            drawCharacterInfoScreen();
            drawInventory();
        }
    }

    public void drawTitleScreen() {

        g2.setColor(new Color(0, 0, 0));
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

        // TITLE NAME
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 52F));
        String text = "The Adventure of Slander Man";
        int x = xCenter(text);
        int y = gp.tileSize * 3;

        // SHADOW
        g2.setColor(Color.darkGray);
        g2.drawString(text, x + 5, y + 5);

        // MAIN COLOR
        g2.setColor(Color.red);
        g2.drawString(text, x, y);

        // SLANDER MAN IMAGE
        x = gp.screenWidth / 2 - (gp.tileSize * 2) / 2;
        y += gp.tileSize * 2;
        g2.drawImage(gp.player.down1, x, y, gp.tileSize * 2, gp.tileSize * 2, null);

        // MENU
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 38F));

        text = "NEW GAME";
        x = xCenter(text);
        y += gp.tileSize * 3;
        g2.drawString(text, x, y);
        if (commandNum == 0) {
            g2.drawString(">", x - gp.tileSize, y);
        }

        text = "LOAD GAME";
        x = xCenter(text);
        y += gp.tileSize;
        g2.drawString(text, x, y);
        if (commandNum == 1) {
            g2.drawString(">", x - gp.tileSize, y);
        }

        text = "QUIT GAME";
        x = xCenter(text);
        y += gp.tileSize;
        g2.drawString(text, x, y);
        if (commandNum == 2) {
            g2.drawString(">", x - gp.tileSize, y);
        }

    }

    // Draws player lifeline
    public void drawPlayerLife() {

        int x = gp.tileSize / 2;
        int y = gp.tileSize / 2;
        int i = 0;

        // Draw full life
        while (i < gp.player.fullLife / 2) {
            g2.drawImage(heartempty, x, y, null);
            i++;
            x += gp.tileSize;
        }

        // Reset
        x = gp.tileSize / 2;
        y = gp.tileSize / 2;
        i = 0;

        // Draw current life
        while (i < gp.player.life) {
            g2.drawImage(hearthalf, x, y, null);
            i++;
            if (i < gp.player.life) {
                g2.drawImage(heartfull, x, y, null);
            }
            i++;
            x += gp.tileSize;
        }

    }

    // Handles where pause text will be
    public void drawPauseScreen() {

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 75F));
        String text = "PAUSED";
        int x = xCenter(text); // Center x
        int y = gp.screenHeight / 2; // Center y
        g2.drawString(text, x, y);

    }

    // Handles where dialogue screen will be
    public void drawDialogueScreen() {

        // Window
        int x = gp.tileSize * 2;
        int y = gp.tileSize / 2;
        int width = gp.screenWidth - (gp.tileSize * 4);
        int height = gp.tileSize * 4;

        drawSubWindow(x, y, width, height);

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 28F));
        x += gp.tileSize;
        y += gp.tileSize;

        // Line break at '\n'
        for (String line : currentDialogue.split("\n")) {
            g2.drawString(line, x, y);
            y += 40; // display next line below
        }
    }

    // Display window to show character info
    public void drawCharacterInfoScreen() {

        // Create a frame
        final int frameX = gp.tileSize;
        final int frameY = gp.tileSize;
        final int frameWidth = gp.tileSize * 5;
        final int frameHeight = gp.tileSize * 9;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        // Text
        g2.setColor(Color.WHITE);
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 22F));

        // Text placement
        int textX = frameX + 20;
        int textY = frameY + gp.tileSize;
        final int lineHeight = 40;

        // Attribute names
        g2.drawString("Level", textX, textY);
        textY += lineHeight; // Next attribute name will be on next line in frame
        g2.drawString("Life", textX, textY);
        textY += lineHeight;
        g2.drawString("Strength", textX, textY);
        textY += lineHeight;
        g2.drawString("Agility", textX, textY);
        textY += lineHeight;
        g2.drawString("Attack Power", textX, textY);
        textY += lineHeight;
        g2.drawString("Defense Power", textX, textY);
        textY += lineHeight;
        g2.drawString("Exp", textX, textY);
        textY += lineHeight;
        g2.drawString("Next level exp", textX, textY);
        textY += lineHeight + 15;
        g2.drawString("Weapon", textX, textY);

        // Attribute values
        int tailX = (frameX + frameWidth) - 30;
        textY = frameY + gp.tileSize; // Reset
        String value;

        value = String.valueOf(gp.player.level);
        textX = xRight(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.life + "/" + gp.player.fullLife);
        textX = xRight(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.strength);
        textX = xRight(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.agility);
        textX = xRight(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.attackPower);
        textX = xRight(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.defensePower);
        textX = xRight(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.exp);
        textX = xRight(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.nextLevelExp);
        textX = xRight(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        // Image for weapon
        g2.drawImage(gp.player.currentWeapon.down1, tailX - gp.tileSize, textY - 15, null);
    }


    // Display inventory
    public void drawInventory() {

        // Frame
        int frameX = gp.tileSize * 9;
        int frameY = gp.tileSize;
        int frameWidth = gp.tileSize * 6;
        int frameHeight = gp.tileSize * 5;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        // Slots
        final int slotXStart = frameX + 20;
        final int slotYStart = frameY + 20;
        int slotX = slotXStart;
        int slotY = slotYStart;

        // Cursor for selection
        int cursorX = slotXStart + (gp.tileSize * slotCol);
        int cursorY = slotYStart + (gp.tileSize * slotRow);
        int cursorWidth = gp.tileSize;
        int cursorHeight = gp.tileSize;

        // Draw cursor
        g2.setColor(Color.WHITE);
        g2.setStroke(new BasicStroke(3));
        g2.drawRoundRect(cursorX, cursorY, cursorWidth, cursorHeight, 10, 10);
    }

    // Setup dialogue window
    public void drawSubWindow(int x, int y, int width, int height) {
        Color c = new Color(0, 0, 0, 200);
        g2.setColor(c);
        g2.fillRoundRect(x, y, width, height, 35, 35);

        // White frame
        c = new Color(255, 255, 255);
        g2.setColor(c);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x + 5, y + 5, width - 10, height - 10, 25, 25);
    }

    // Center of screen x
    public int xCenter(String text) {
        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = gp.screenWidth / 2 - length / 2;

        return x;
    }

    // Align text to the right
    public int xRight(String text, int tailX) {
        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = tailX - length;
        return x;
    }
}