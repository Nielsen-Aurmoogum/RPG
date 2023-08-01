package main;

import character.NPC_Villager;

public class ObjectPlacer {
    GamePanel gp;

    public ObjectPlacer(GamePanel gp) {
        this.gp = gp;
    }

    /**
     * Instantiate object and provide location
     */
    public void setObject() {

    }

    public void setNPC() {

        gp.npc[0] = new NPC_Villager(gp);
        gp.npc[0].worldX = gp.tileSize * 21;
        gp.npc[0].worldY = gp.tileSize * 21;
    }
}