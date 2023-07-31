package tile;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;

public class TileManager {

    GamePanel gp;
    public Tile[] tile; // Array of tiles
    public int tileNumMap[][]; // Tiles on map

    // Constructor
    public TileManager(GamePanel gp) {
        this.gp = gp;

        tile = new Tile[10];
        tileNumMap = new int[gp.maxWorldCol][gp.maxWorldRow];

        getTileImage();
        loadMap("resources/maps/world01.txt");
    }

    // Read tile types images
    public void getTileImage() {
        try {

            tile[0] = new Tile();
            tile[0].image = ImageIO.read(new File("resources/tiles/beigebrick.png"));

            tile[1] = new Tile();
            tile[1].image = ImageIO.read(new File("resources/tiles/lava.png"));
            tile[1].collision = true;

            tile[2] = new Tile();
            tile[2].image = ImageIO.read(new File("resources/tiles/rock.png"));
            tile[2].collision = true;

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Build map
    public void loadMap(String filePath) {

        // Read text file containing map data
        File file = new File(filePath);

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {

            int col = 0;
            int row = 0;

            while (col < gp.maxWorldCol && row < gp.maxWorldRow) {
                String line = br.readLine();

                while (col < gp.maxWorldCol) {
                    String numbers[] = line.split(" "); // Do not take whitespace

                    int num = Integer.parseInt(numbers[col]); // Stores the tile type

                    tileNumMap[col][row] = num; // Map then knows what tile is of which type(water, bricks or rocks)
                    col++;
                }
                if (col == gp.maxWorldCol) {
                    col = 0;
                    row++;
                }
            }
            br.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Drawing tiles on the screen
     */
    public void draw(Graphics2D g2) {

        int worldCol = 0;
        int worldRow = 0;

        // While within window screen size
        while (worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) {

            // world is position on the map
            // screen is where we draw

            int tileNum = tileNumMap[worldCol][worldRow];
            int worldX = worldCol * gp.tileSize;
            int worldY = worldRow * gp.tileSize;
            int screenX = worldX - gp.player.worldX + gp.player.screenX;
            int screenY = worldY - gp.player.worldY + gp.player.screenY;

            // Draw only for our window, don't draw whole world from start
            if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX
                    && worldX - gp.tileSize < gp.player.worldX + gp.player.screenX
                    && worldY + gp.tileSize > gp.player.worldY - gp.player.screenY
                    && worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {
                g2.drawImage(tile[tileNum].image, screenX, screenY, gp.tileSize, gp.tileSize, null);
            }
            worldCol++;

            if (worldCol == gp.maxWorldCol) {
                worldCol = 0;
                worldRow++;
            }
        }
    }
}