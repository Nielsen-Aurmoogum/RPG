package object;

import character.SuperCharacter;
import main.GamePanel;

public class ObjectBoots extends SuperCharacter {

    GamePanel gp;

    public ObjectBoots(GamePanel gp) {
        super(gp);
        this.gp = gp;

        type = type_consumable;
        name = "Heavy Boot";
        down1 = setup("resources/objects/newBoot", gp.tileSize, gp.tileSize); // need real image
        description = "[" + name + "]\nWear at your own risk.";
    }

    @Override
    public void use(SuperCharacter character) {
        gp.gameState = gp.dialogueState;
        gp.ui.currentDialogue = "You are wearing the " + name + " !\n" + "You are now slower :(";
        character.speed -= 1;
    }

}