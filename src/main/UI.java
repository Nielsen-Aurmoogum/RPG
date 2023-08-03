package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import object.ObjectLife;
import object.SuperObject;

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

    public UI(GamePanel gp) {
        this.gp = gp;

        arial_40 = new Font("Arial", Font.PLAIN, 40);
        arial_80B = new Font("Arial", Font.BOLD, 80);
     //Create object
     SuperObject life = new ObjectLife(gp);
     heartfull = life.image;
     hearthalf = life.image2;
     heartempty= life.image3;
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
    }
    public void drawPlayerLife(){
        gp.player.Life = 5;

        int x = gp.tileSize/2;
        int y = gp.tileSize/2;
        int i = 0 ;

        //Draw full life
        while(i < gp.player.fullLife/2){
            g2.drawImage(heartempty,x,y,null);
            i++ ;
            x += gp.tileSize;
        }

        x = gp.tileSize/2;
        y = gp.tileSize/2;
        i = 0 ;

        while(i < gp.player.Life){
            g2.drawImage(hearthalf,x,y,null);
            i++;
            if(i<gp.player.Life)
            {
                g2.drawImage(heartfull,x,y,null);
            }
            i++;
            x+= gp.tileSize ;
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

    // Setup dialogue window
    public void drawSubWindow(int x, int y, int width, int height) {
        Color c = new Color(0, 0, 0, 200);
        g2.setColor(c);
        g2.fillRoundRect(x, y, width, height, 35, 35);

        // White frame
        c = new Color(255, 255, 255);
        g2.setColor(c);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x+5, y+5, width - 10, height - 10, 25, 25);
    }

    // Center of screen x
    public int xCenter(String text) {
        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = gp.screenWidth / 2 - length / 2;

        return x;
    }
}