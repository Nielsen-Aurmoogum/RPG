package object;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;

public class ObjectLife extends SuperObject {
    
    GamePanel gp;

    public ObjectLife(GamePanel gp) {

        this.gp = gp;
        name = "life";

        try {
            image = ImageIO.read(new File("resources/objects/heartfull.png"));
            image2 = ImageIO.read(new File("resources/objects/hearthalf.png"));
            image3 = ImageIO.read(new File("resources/objects/heartempty.png")); // needs the real key image
            image = uTool.scaleImage(image, gp.tileSize, gp.tileSize);
            image2 = uTool.scaleImage(image, gp.tileSize, gp.tileSize);
            image3 = uTool.scaleImage(image, gp.tileSize, gp.tileSize);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
