package object;

import character.SuperCharacter;
import main.GamePanel;

public class ObjectLife extends SuperCharacter {

    public ObjectLife(GamePanel gp) {
        super(gp);

        name = "Life";
        image = setup("resources/objects/heartfull",gp.tileSize,gp.tileSize);
        image2 = setup("resources/objects/hearthalf",gp.tileSize,gp.tileSize);
        image3 = setup("resources/objects/heartempty",gp.tileSize,gp.tileSize);
    }
}
