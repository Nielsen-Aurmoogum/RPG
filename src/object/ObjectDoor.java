package object;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;

public class ObjectDoor extends SuperObject {
    GamePanel gp;

    public ObjectDoor(GamePanel gp) {

        this.gp = gp;
        name = "Door";

        try {
            image = ImageIO.read(new File("resources/tiles/bricks.png")); // needs the real door image
            uTool.scaleImage(image, gp.tileSize, gp.tileSize);
        } catch (IOException e) {
            e.printStackTrace();
        }

        collision = true;
    }
}