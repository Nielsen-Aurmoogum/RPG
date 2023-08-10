package object;

import character.SuperCharacter;
import main.GamePanel;

public class ObjectLightsaber extends SuperCharacter {

    public ObjectLightsaber(GamePanel gp) {
        super(gp);

        name = "Green Lightsaber";
        down1 = setup("resources/objects/key", gp.tileSize, gp.tileSize); // need real lightsaber image
        attackValue = 1;
        description = "[" + name + "]\nYoda's lightsaber.";
    }

}