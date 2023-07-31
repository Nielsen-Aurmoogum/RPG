package object;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ObjectKey extends SuperObject{

    public ObjectKey() {

        name = "Key";

        try {
            image = ImageIO.read(new File("resources/tiles/rock.png")); // needs the real key image
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}