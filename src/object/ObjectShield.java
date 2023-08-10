package object;

import character.SuperCharacter;
import main.GamePanel;

public class ObjectShield extends SuperCharacter {

    public ObjectShield(GamePanel gp) {
        super(gp);

        type = type_shield;
        name = "Shield";
        down1 = setup("resources/objects/key", gp.tileSize, gp.tileSize); // need real image
        defenseValue = 1;
        description = "[" + name + "]\nOld shield";
    }

}