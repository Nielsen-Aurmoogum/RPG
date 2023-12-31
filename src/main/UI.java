package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import character.SuperCharacter;
import object.ObjectLife;

/**
 * Used to display components on screen
 */
public class UI {

    // System
    GamePanel gp;
    Graphics2D g2;
    public boolean gameFinished = false;
    public int commandNum = 0;
    int subState = 0;

    // Fonts
    Font arial_40, arial_80B;

    // Health
    BufferedImage heartfull, hearthalf, heartempty;

    // Messages
    ArrayList<String> message = new ArrayList<>();
    ArrayList<Integer> messageCounter = new ArrayList<>();
    public boolean messageOn = false;
    public String currentDialogue = "";

    // Inventory management
    public int slotCol = 0;
    public int slotRow = 0;

    public UI(GamePanel gp) {

        this.gp = gp;

        arial_40 = new Font("Arial", Font.PLAIN, 40);
        arial_80B = new Font("Arial", Font.BOLD, 80);

        // Create life object
        SuperCharacter life = new ObjectLife(gp);
        heartfull = life.image;
        hearthalf = life.image2;
        heartempty = life.image3;
    }

    // Display text
    public void addMessage(String text) {
        message.add(text);
        messageCounter.add(0);
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
            drawMessage();
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

        // Game over state
        if (gp.gameState == gp.gameOverState) {
            drawGameOverScreen();
        }

        // Win state
        if (gp.gameState == gp.winState) {
            drawWinScreen();
        }
    }

    // In-game messages
    public void drawMessage() {
        int messageX = gp.tileSize;
        int messageY = gp.tileSize * 4;
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 28F));

        for (int i = 0; i < message.size(); i++) {
            if (message.get(i) != null) {
                g2.setColor(Color.black);
                g2.drawString(message.get(i), messageX + 2, messageY + 2);
                g2.setColor(Color.white);
                g2.drawString(message.get(i), messageX, messageY);
                int counter = messageCounter.get(i) + 1; // messageCounter++
                messageCounter.set(i, counter); // set the counter to array
                messageY += 50;

                if (messageCounter.get(i) > 180) {
                    message.remove(i);
                    messageCounter.remove(i);
                }
            }
        }
    }

    // Title screen
    public void drawTitleScreen() {

        g2.setColor(new Color(0, 0, 0));
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

        // TITLE NAME
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 45F));
        String text = "The Adventure of Slander Man";
        int x = xCenter(text);
        int y = gp.tileSize * 2;

        // SHADOW
        g2.setColor(Color.darkGray);
        g2.drawString(text, x + 5, y + 5);

        // MAIN COLOR
        g2.setColor(Color.red);
        g2.drawString(text, x, y);

        // SLANDER MAN IMAGE
        x = gp.screenWidth / 2 - (gp.tileSize * 2) / 2;
        y += gp.tileSize * 2 - 20;
        g2.drawImage(gp.player.down1, x - 30, y, gp.tileSize * 3, gp.tileSize * 3, null);

        // MENU
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 38F));

        text = "PLAY GAME";
        x = xCenter(text);
        y += gp.tileSize * 5;
        g2.drawString(text, x, y);
        if (commandNum == 0) {
            g2.drawString(">", x - gp.tileSize, y);
        }

        text = "QUIT GAME";
        x = xCenter(text);
        y += gp.tileSize;
        g2.drawString(text, x, y);
        if (commandNum == 1) {
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

    // Handles where pause menu will be
    // and how it looks like
    // Different selectable options
    public void drawPauseScreen() {

        // Frame
        g2.setColor(Color.WHITE);
        g2.setFont(g2.getFont().deriveFont(28F));

        // Sub window
        int frameX = (gp.tileSize * 4) + 20;
        int frameY = gp.tileSize;
        int frameWidth = gp.tileSize * 8;
        int frameHeight = gp.tileSize * 10;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        // Different options of pause menu
        switch (subState) {
            case 0:
                pause_top(frameX, frameY);
                break;

            case 1:
                optionsControl(frameX, frameY);
                break;

            case 2:
                confirmEndGame(frameX, frameY);
        }
        gp.inputH.enterInput = false;

    }

    // Pause screen components
    public void pause_top(int frameX, int frameY) {
        int textX;
        int textY;

        // Title
        String text = "Pause";
        textX = xCenter(text) + 15;
        textY = frameY + gp.tileSize;
        g2.drawString(text, textX, textY);

        // Controls
        textX = frameX + gp.tileSize;
        textY += gp.tileSize * 2;
        g2.drawString("Controls", textX, textY);
        if (commandNum == 0) {
            g2.drawString(">", textX - 25, textY);
            if (gp.inputH.enterInput == true) {
                subState = 1;
                commandNum = 0;
            }
        }

        // End game
        textY += gp.tileSize * 2;
        g2.drawString("End game", textX, textY);
        if (commandNum == 1) {
            g2.drawString(">", textX - 25, textY);
            if (gp.inputH.enterInput == true) {
                subState = 2;
                commandNum = 1;
            }
        }

        // Resume
        textY += gp.tileSize * 2;
        g2.drawString("Resume", textX, textY);
        if (commandNum == 2) {
            g2.drawString(">", textX - 25, textY);
            if (gp.inputH.enterInput == true) {
                gp.gameState = gp.playState;
                commandNum = 0;
            }
        }
    }

    // Options Control components
    public void optionsControl(int frameX, int frameY) {
        int textX;
        int textY;

        // Title
        String text = "Controls";
        textX = xCenter(text) + 15;
        textY = frameY + gp.tileSize;
        g2.drawString(text, textX, textY);

        // Left side
        textX = frameX + gp.tileSize;
        textY += gp.tileSize + 20;
        g2.drawString("Move", textX, textY);
        textY += gp.tileSize + 20;
        g2.drawString("Confirm/Attack", textX, textY);
        textY += gp.tileSize + 20;
        g2.drawString("Character info", textX, textY);
        textY += gp.tileSize + 20;
        g2.drawString("Pause", textX, textY);
        textY += gp.tileSize + 20;

        // Right side
        textX = frameX + gp.tileSize * 5 + 20;
        textY = frameY + gp.tileSize * 2 + 20;
        g2.drawString("WASD", textX, textY);
        textY += gp.tileSize + 20;
        g2.drawString("ENTER", textX, textY);
        textY += gp.tileSize + 20;
        g2.drawString("I", textX, textY);
        textY += gp.tileSize + 20;
        g2.drawString("P", textX, textY);
        textY += gp.tileSize + 20;

        // Back
        textX = frameX + gp.tileSize;
        textY = frameY + gp.tileSize * 9;
        g2.drawString("Back", textX, textY);
        if (commandNum == 0) {
            g2.drawString(">", textX - 25, textY);
            if (gp.inputH.enterInput == true) {
                subState = 0;
                commandNum = 0;
            }
        }
    }

    // End game confirmation message
    public void confirmEndGame(int frameX, int frameY) {

        int textX = frameX + gp.tileSize - 10;
        int textY = frameY + gp.tileSize + 15;

        currentDialogue = "Quit the game and return \nto the title screen ?";

        for (String line : currentDialogue.split("\n")) {
            g2.drawString(line, textX, textY);
            textY += 40;
        }

        // Yes
        String text = "YES";
        textX = xCenter(text) + 15;
        textY += gp.tileSize * 2;
        g2.drawString(text, textX, textY);
        if (commandNum == 0) {
            g2.drawString(">", textX - 25, textY);
            if (gp.inputH.enterInput == true) {
                subState = 0;
                gp.gameState = gp.titleState;
            }
        }

        // No
        text = "NO";
        textX = xCenter(text) + 15;
        textY += gp.tileSize * 2;
        g2.drawString(text, textX, textY);
        if (commandNum == 1) {
            g2.drawString(">", textX - 25, textY);
            if (gp.inputH.enterInput == true) {
                subState = 0;
                commandNum = 1;
            }
        }
    }

    // Game over screen
    public void drawGameOverScreen() {

        // Make screen darker
        g2.setColor(new Color(0, 0, 0, 150));
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

        int x;
        int y;
        String text;
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 100f));

        text = "Game Over";

        // Shadow
        g2.setColor(Color.BLACK);
        x = xCenter(text);
        y = gp.tileSize * 4;
        g2.drawString(text, x, y);

        // Main
        g2.setColor(Color.WHITE);
        g2.drawString(text, x - 4, y - 4);

        // Retry
        g2.setFont(g2.getFont().deriveFont(40f));
        text = "Retry";
        x = xCenter(text);
        y += gp.tileSize * 4;
        g2.drawString(text, x, y);
        if (commandNum == 0) {
            g2.drawString(">", x - 40, y);
        }

        // Back to title screen
        text = "Quit";
        x = xCenter(text);
        y += 55;
        g2.drawString(text, x, y);
        if (commandNum == 1) {
            g2.drawString(">", x - 40, y);
        }
    }

    // Win screen
    public void drawWinScreen() {

        // Make screen darker
        g2.setColor(new Color(0, 0, 0, 150));
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

        int x;
        int y;
        String text;
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 100f));

        text = "You Won !";

        // Shadow
        g2.setColor(Color.BLACK);
        x = xCenter(text);
        y = gp.tileSize * 4;
        g2.drawString(text, x, y);

        // Main
        g2.setColor(Color.WHITE);
        g2.drawString(text, x - 4, y - 4);

        // Back to title screen
        g2.setFont(g2.getFont().deriveFont(40f));
        text = "Quit";
        x = xCenter(text);
        y += gp.tileSize * 4;
        g2.drawString(text, x, y);
        if (commandNum == 0) {
            g2.drawString(">", x - 40, y);
        }
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
        final int frameHeight = gp.tileSize * 10;
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
        textY += lineHeight + 20;
        g2.drawString("Weapon", textX, textY);
        textY += lineHeight + 15;
        g2.drawString("Shield", textX, textY);
        textY += lineHeight;

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

        // Image for weapon and shield
        g2.drawImage(gp.player.currentWeapon.down1, tailX - gp.tileSize, textY - 14, null);
        textY += gp.tileSize;
        g2.drawImage(gp.player.currentShield.down1, tailX - gp.tileSize, textY - 7, null);
    }

    // Display inventory
    public void drawInventory() {

        // Frame
        int frameX = gp.tileSize * 9;
        int frameY = gp.tileSize;
        int frameWidth = gp.tileSize * 6;
        int frameHeight = gp.tileSize * 3;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        // Slots
        final int slotXStart = frameX + 20;
        final int slotYStart = frameY + 20;
        int slotX = slotXStart;
        int slotY = slotYStart;
        int slotSize = gp.tileSize + 3;

        // Draw player items
        for (int i = 0; i < gp.player.inventory.size(); i++) {

            // Equip cursor
            if (gp.player.inventory.get(i) == gp.player.currentWeapon
                    || gp.player.inventory.get(i) == gp.player.currentShield) {
                g2.setColor(new Color(240, 190, 90));
                g2.fillRoundRect(slotX, slotY, gp.tileSize, gp.tileSize, 10, 10);
            }

            g2.drawImage(gp.player.inventory.get(i).down1, slotX, slotY, null);

            slotX += slotSize;
            if (i == 4) {
                slotX = slotXStart;
                slotY += slotSize;
            }
        }

        // Cursor for selection
        int cursorX = slotXStart + (slotSize * slotCol);
        int cursorY = slotYStart + (slotSize * slotRow);
        int cursorWidth = gp.tileSize;
        int cursorHeight = gp.tileSize;

        // Draw cursor
        g2.setColor(Color.WHITE);
        g2.setStroke(new BasicStroke(3));
        g2.drawRoundRect(cursorX, cursorY, cursorWidth, cursorHeight, 10, 10);

        // Description frame
        int dFrameX = frameX;
        int dFrameY = frameY + frameHeight;
        int dFrameWidth = frameWidth;
        int dFrameHeight = gp.tileSize * 3;

        // Description text
        int textX = dFrameX + 20;
        int textY = dFrameY + gp.tileSize;
        g2.setFont(g2.getFont().deriveFont(22F));

        int itemIndex = getItemIndexInSlot();

        if (itemIndex < gp.player.inventory.size()) {

            drawSubWindow(dFrameX, dFrameY, dFrameWidth, dFrameHeight);

            for (String line : gp.player.inventory.get(itemIndex).description.split("\n")) {
                g2.drawString(line, textX, textY);
                textY += 30;
            }
        }
    }

    // Index of an item in inventory
    public int getItemIndexInSlot() {
        int itemIndex = slotCol + (slotRow * 5);
        return itemIndex;
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