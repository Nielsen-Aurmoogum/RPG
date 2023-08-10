package object;

import character.SuperCharacter;
import main.GamePanel;

public class ObjectLightsaber extends SuperCharacter {

    public ObjectLightsaber(GamePanel gp) {
        super(gp);

        type = type_greenLightSaber;
        name = "Green Lightsaber";
        down1 = setup("resources/objects/key", gp.tileSize, gp.tileSize); // need real lightsaber image
        attackValue = 1;
        attackArea.width = 35;
        attackArea.height = 35;
        description = "[" + name + "]\nYoda's lightsaber.";
    }

}