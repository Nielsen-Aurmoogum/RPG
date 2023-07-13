package main;

import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class GamePanel extends JPanel implements Runnable {

    // Screen settings
    final int originalTileSize = 16; // 16x16 tile
    final int scale = 3;
    final int tileSize = originalTileSize * scale; // 48x48 tile
    final int maxScreenCol = 16;
    final int maxScreenRow = 12;
    final int screenWidth = tileSize * maxScreenCol; // 768 pixels
    final int screenHeight = tileSize * maxScreenRow; // 576 pixels

    // FPS
    int FPS = 60;

    InputHandler inputH = new InputHandler();
    Thread gameThread;

    // Start player position
    int playerX = 100;
    int playerY = 100;
    int playerSpeed = 4;

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(inputH);
        this.setFocusable(true);
    }

    public void startGameThread() {

        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        double paintInterval = 1000000000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while (gameThread != null) {

            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / paintInterval;
            lastTime = currentTime;

            if (delta >= 1) {
                update();
                repaint(); // Calling paintComponent method
                delta--;
            }

        }

    }

    /**
     * Updata charater information
     */
    public void update() {

        if (inputH.upInput == true) {
            playerY -= playerSpeed;
        }

        else if (inputH.downInput == true) {
            playerY += playerSpeed;
        }

        else if (inputH.leftInput == true) {
            playerX -= playerSpeed;
        }

        else if (inputH.rightInput == true) {
            playerX += playerSpeed;

        }
    }

    /**
     * Draw updated information on screen
     */
    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        g2.setColor(Color.PINK);
        g2.fillRect(playerX, playerY, tileSize, tileSize);

        g2.dispose();
    }
}