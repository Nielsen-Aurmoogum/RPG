package object;

import character.SuperCharacter;
import main.GamePanel;

public class ObjectLightsaber extends SuperCharacter {

    public ObjectLightsaber(GamePanel gp) {
        super(gp);

        type = type_greenLightSaber;
        name = "Green Lightsaber";
        down1 = setup("resources/objects/lightsaber", gp.tileSize, gp.tileSize);
        attackValue = 1;
        attackArea.width = 35;
        attackArea.height = 35;
        description = "[" + name + "]\nYoda's lightsaber.";
    }

}