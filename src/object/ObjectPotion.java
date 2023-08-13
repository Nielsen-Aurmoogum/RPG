package object;

import character.SuperCharacter;
import main.GamePanel;

public class ObjectPotion extends SuperCharacter {

    GamePanel gp;

    public ObjectPotion(GamePanel gp) {
        super(gp);
        this.gp = gp;

        type = type_consumable;
        name = "Purple Potion";
        down1 = setup("resources/objects/potion", gp.tileSize, gp.tileSize); // need real image
        description = "[" + name + "]\nSpeed potion";
    }

    @Override
    public void use(SuperCharacter character) {
        gp.gameState = gp.dialogueState;
        gp.ui.currentDialogue = "You are drinking the " + name + " !\n" + "You are now faster.";
        character.speed += 2;
    }

}