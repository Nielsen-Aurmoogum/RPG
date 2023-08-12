package object;

import character.SuperCharacter;
import main.GamePanel;

public class ObjectShield extends SuperCharacter {

    public ObjectShield(GamePanel gp) {
        super(gp);

        type = type_shield;
        name = "Shield";
        down1 = setup("resources/objects/shield", gp.tileSize, gp.tileSize);
        defenseValue = 1;
        description = "[" + name + "]\nDark Knight's shield.";
    }

}