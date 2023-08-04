package object;

import character.SuperCharacter;
import main.GamePanel;

public class ObjectDoor extends SuperCharacter {

    public ObjectDoor(GamePanel gp) {
        super(gp);

        name = "Door";
        down1 = setup("resources/objects/door");
        collision = true;
    }
}