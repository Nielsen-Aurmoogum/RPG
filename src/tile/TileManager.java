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
    Tile[] tile;
    int tileNumMap[][];

    public TileManager(GamePanel gp) {
        this.gp = gp;

        tile = new Tile[10];
        tileNumMap = new int[gp.maxScreenCol][gp.maxScreenRow];

        getTileImage();
        loadMap("resources/maps/map01.txt");
    }

    public void getTileImage() {
        try {

            tile[0] = new Tile();
            tile[0].image = ImageIO.read(new File("resources/tiles/bricks.png"));

            tile[1] = new Tile();
            tile[1].image = ImageIO.read(new File("resources/tiles/water.png"));

            tile[2] = new Tile();
            tile[2].image = ImageIO.read(new File("resources/tiles/rock.png"));
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadMap(String filePath) {
        File file = new File(filePath);

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {

            int col = 0;
            int row = 0;

            while (col < gp.maxScreenCol && row < gp.maxScreenRow) {
                String line = br.readLine();

                while (col < gp.maxScreenCol) {
                    String numbers[] = line.split(" ");

                    int num = Integer.parseInt(numbers[col]);

                    tileNumMap[col][row] = num;
                    col++;
                }
                if (col == gp.maxScreenCol) {
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
        
        int col = 0;
        int row = 0;
        int x = 0;
        int y = 0;

        while (col < gp.maxScreenCol && row < gp.maxScreenRow) {
            int tileNum = tileNumMap[col][row];

            g2.drawImage(tile[tileNum].image, x, y, gp.tileSize, gp.tileSize, null);
            col++;
            x += gp.tileSize;

            if (col == gp.maxScreenCol) {
                col = 0;
                x = 0;
                row++;
                y += gp.tileSize;
            }
        }
    }
}