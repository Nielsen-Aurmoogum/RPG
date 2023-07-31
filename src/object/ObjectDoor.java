package object;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ObjectDoor extends SuperObject{

    public ObjectDoor() {

        name = "Door";

        try {
            image = ImageIO.read(new File("resources/tiles/bricks.png")); // needs the real door image
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}