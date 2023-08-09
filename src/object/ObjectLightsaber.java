package object;

import character.SuperCharacter;
import main.GamePanel;

public class ObjectLightsaber extends SuperCharacter {

    public ObjectLightsaber(GamePanel gp) {
        super(gp);

        name = "Green Lightsaber";
        down1 = setup("imagepath", gp.tileSize, gp.tileSize);
        attackValue = 1;
    }

}