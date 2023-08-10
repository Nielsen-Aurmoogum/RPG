package object;

import character.SuperCharacter;
import main.GamePanel;

public class ObjectRedLightSaber extends SuperCharacter {

    public ObjectRedLightSaber(GamePanel gp) {
        super(gp);

        type = type_redLightSaber;
        name = "Red Lightsaber";
        down1 = setup("resources/objects/lightsaberRed", gp.tileSize, gp.tileSize);
        attackValue = 2;
        attackArea.width = 38;
        attackArea.height = 38;
        description = "[" + name + "]\nDarth Vader's lightsaber";
    }

}