package main;

import character.NPC_Villager;
import monster.GreenVillain;

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

    /**
     * Instantiate NPC and provide location
     */
    public void setNPC() {

        gp.npc[0] = new NPC_Villager(gp);
        gp.npc[0].worldX = gp.tileSize * 21;
        gp.npc[0].worldY = gp.tileSize * 21;
    }

    /**
     * Instantiate monster and provide location
     */
    public void setMonster() {
        gp.monster[0] = new GreenVillain(gp);
        gp.monster[0].worldX = gp.tileSize*6;
        gp.monster[0].worldY = gp.tileSize*19;

        gp.monster[1] = new GreenVillain(gp);
        gp.monster[1].worldX = gp.tileSize*30;
        gp.monster[1].worldY = gp.tileSize*30;
    }
}