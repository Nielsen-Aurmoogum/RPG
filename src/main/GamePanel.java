package main;

import javax.swing.JPanel;
import character.SuperCharacter;
import character.Player;
import tile.TileManager;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class GamePanel extends JPanel implements Runnable {

    // Screen settings
    final int originalTileSize = 16; // 16x16 tile
    final int scale = 3;
    public final int tileSize = originalTileSize * scale; // 48x48 tile
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol; // 768 pixels
    public final int screenHeight = tileSize * maxScreenRow; // 576 pixels

    // World settings
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;
    public final int worldWidth = tileSize * maxWorldCol;
    public final int worldHeight = tileSize * maxWorldRow;

    // FPS
    int FPS = 60;

    // Instantiate classes

    // System
    TileManager tileM = new TileManager(this);
    public InputHandler inputH = new InputHandler(this);
    public VerifyCollision collisionTest = new VerifyCollision(this);
    public ObjectPlacer placer = new ObjectPlacer(this);
    public UI ui = new UI(this);
    public EventHandler eHandler = new EventHandler(this);
    Thread gameThread;

    // Characters and objects
    public Player player = new Player(this, inputH);
    public SuperCharacter obj[] = new SuperCharacter[15]; // Can display up to 15 objects at the same time
    public SuperCharacter npc[] = new SuperCharacter[10]; // Can display up to 10 npcs at the same time
    public SuperCharacter monster[] = new SuperCharacter[10]; // Can display up to 10 monsters at the same time
    public SuperCharacter healer[] = new SuperCharacter[2]; // Can display up to 2 healers at the same time
    public ArrayList<SuperCharacter> projectileList = new ArrayList<>(); // Projectiles stored in this array
    ArrayList<SuperCharacter> charactersList = new ArrayList<>(); // All characters and objects are stored in this array

    // Game state
    public int gameState;
    public int titleState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int dialogueState = 3;
    public final int characterInfoState = 4;
    public final int gameOverState = 5;
    public final int winState = 6;

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(inputH);
        this.setFocusable(true);
    }

    public void setupGame() {
        placer.setObject();
        placer.setNPC();
        placer.setMonster();
        placer.setHealer();
        gameState = titleState;
    }

    public void retry() {

        player.setDefaultPositions();
        player.lifeRestore();
        placer.setNPC();
        placer.setMonster();
        placer.setHealer();
    }

    public void restart() {

        player.setStartValues();
        player.setItems(); // Inventory cleared only default stays
        placer.setObject();
        placer.setNPC();
        placer.setMonster();
        placer.setHealer();
    }

    public void startGameThread() {

        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {

        // Frequency at which charater is repainted on screen
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
     * Update graphic component information
     */
    public void update() {

        if (gameState == playState) {

            // Player
            player.update();

            // NPC
            for (int i = 0; i < npc.length; i++) {
                if (npc[i] != null) {
                    npc[i].update();
                }
            }

            // Monster
            for (int i = 0; i < monster.length; i++) {
                if (monster[i] != null) {
                    if (monster[i].alive == true && monster[i].dying == false) {
                        monster[i].update();
                    }
                    if (monster[i].alive == false) {
                        monster[i] = null;
                    }
                }
            }

            // Healer
            for (int i = 0; i < healer.length; i++) {
                if (healer[i] != null) {
                    healer[i].update();
                }
            }

            // Projectile
            for (int i = 0; i < projectileList.size(); i++) {
                if (projectileList.get(i) != null) {
                    if (projectileList.get(i).alive == true) {
                        projectileList.get(i).update();
                    }
                    if (projectileList.get(i).alive == false) {
                        projectileList.remove(i);
                    }
                }
            }

        }

        if (gameState == pauseState) {
            // Do nothing
        }
    }

    /**
     * Draw updated information on screen
     */
    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        // TITLE SCREEN
        if (gameState == titleState) {
            ui.draw(g2);
        }

        // OTHERS
        else {

            // Tile
            tileM.draw(g2);

            // Populating list with characters and objects
            charactersList.add(player);

            for (int i = 0; i < npc.length; i++) {
                if (npc[i] != null) {
                    charactersList.add(npc[i]);
                }
            }

            for (int i = 0; i < obj.length; i++) {
                if (obj[i] != null) {
                    charactersList.add(obj[i]);
                }
            }

            for (int i = 0; i < monster.length; i++) {
                if (monster[i] != null) {
                    charactersList.add(monster[i]);
                }
            }

            for (int i = 0; i < healer.length; i++) {
                if (healer[i] != null) {
                    charactersList.add(healer[i]);
                }
            }

            for (int i = 0; i < projectileList.size(); i++) {
                if (projectileList.get(i) != null) {
                    charactersList.add(projectileList.get(i));
                }
            }

            // Sort render order
            // Sort list by using worldY as filter
            Collections.sort(charactersList, new Comparator<SuperCharacter>() {

                @Override
                public int compare(SuperCharacter c1, SuperCharacter c2) {
                    int result = Integer.compare(c1.worldY, c2.worldY);
                    return result;
                }

            });

            // Draw characters and objects
            for (int i = 0; i < charactersList.size(); i++) {
                charactersList.get(i).draw(g2);
            }

            // Empty charactersList
            charactersList.clear();

            // UI
            ui.draw(g2);

            g2.dispose();
        }
    }
}