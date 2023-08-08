package object;

import character.SuperCharacter;
import main.GamePanel;

public class ObjectKey extends SuperCharacter {

    public ObjectKey(GamePanel gp) {
        super(gp);

        name = "Key";
        down1 = setup("resources/objects/key",gp.tileSize,gp.tileSize);

    }
}