package object;

import character.SuperCharacter;
import main.GamePanel;

public class ObjectLife extends SuperCharacter {

    public ObjectLife(GamePanel gp) {
        super(gp);

        name = "Life";
        image = setup("resources/objects/heartfull");
        image2 = setup("resources/objects/hearthalf");
        image3 = setup("resources/objects/heartempty");
    }
}
