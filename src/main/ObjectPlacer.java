package main;

import character.NPC_Villager;
import monster.GreenVillain;
import object.ObjectKey;
import object.ObjectRedLightSaber;

public class ObjectPlacer {
    GamePanel gp;

    public ObjectPlacer(GamePanel gp) {
        this.gp = gp;
    }

    /**
     * Instantiate object and provide location
     */
    public void setObject() {

        int i = 0;
        gp.obj[i] = new ObjectKey(gp);
        gp.obj[i].worldX = gp.tileSize * 33;
        gp.obj[i].worldY = gp.tileSize * 7;
        i++;

        gp.obj[i] = new ObjectKey(gp);
        gp.obj[i].worldX = gp.tileSize * 29;
        gp.obj[i].worldY = gp.tileSize * 31;
        i++;

        gp.obj[i] = new ObjectRedLightSaber(gp);
        gp.obj[i].worldX = gp.tileSize * 9;
        gp.obj[i].worldY = gp.tileSize * 2;
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
        // Monster trap
        gp.monster[0] = new GreenVillain(gp);
        gp.monster[0].worldX = gp.tileSize*31;
        gp.monster[0].worldY = gp.tileSize*8;

        gp.monster[1] = new GreenVillain(gp);
        gp.monster[1].worldX = gp.tileSize*32;
        gp.monster[1].worldY = gp.tileSize*8;

        // After bridge
        gp.monster[2] = new GreenVillain(gp);
        gp.monster[2].worldX = gp.tileSize*30;
        gp.monster[2].worldY = gp.tileSize*30;

        gp.monster[3] = new GreenVillain(gp);
        gp.monster[3].worldX = gp.tileSize*30;
        gp.monster[3].worldY = gp.tileSize*31;

        gp.monster[4] = new GreenVillain(gp);
        gp.monster[4].worldX = gp.tileSize*31;
        gp.monster[4].worldY = gp.tileSize*30;

        // Second water pond
        gp.monster[5] = new GreenVillain(gp);
        gp.monster[5].worldX = gp.tileSize*6;
        gp.monster[5].worldY = gp.tileSize*19;

        gp.monster[6] = new GreenVillain(gp);
        gp.monster[6].worldX = gp.tileSize*9;
        gp.monster[6].worldY = gp.tileSize*31;

        gp.monster[7] = new GreenVillain(gp);
        gp.monster[7].worldX = gp.tileSize*9;
        gp.monster[7].worldY = gp.tileSize*32;

        gp.monster[8] = new GreenVillain(gp);
        gp.monster[8].worldX = gp.tileSize*8;
        gp.monster[8].worldY = gp.tileSize*31;

        gp.monster[9] = new GreenVillain(gp);
        gp.monster[9].worldX = gp.tileSize*9;
        gp.monster[9].worldY = gp.tileSize*28;
    }
}