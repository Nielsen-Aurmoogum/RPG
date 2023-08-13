package main;

import character.NPC_Villager;
import monster.GreenVillain;
import object.ObjectBoots;
import object.ObjectChest;
import object.ObjectDoor;
import object.ObjectKey;
import object.ObjectPotion;
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

        // Keys
        gp.obj[i] = new ObjectKey(gp);
        gp.obj[i].worldX = gp.tileSize * 33;
        gp.obj[i].worldY = gp.tileSize * 7;
        i++;

        gp.obj[i] = new ObjectKey(gp);
        gp.obj[i].worldX = gp.tileSize * 29;
        gp.obj[i].worldY = gp.tileSize * 31;
        i++;

        // Weapon
        gp.obj[i] = new ObjectRedLightSaber(gp);
        gp.obj[i].worldX = gp.tileSize * 44;
        gp.obj[i].worldY = gp.tileSize * 39;
        i++;

        // Potion
        gp.obj[i] = new ObjectPotion(gp);
        gp.obj[i].worldX = gp.tileSize * 20;
        gp.obj[i].worldY = gp.tileSize * 21;
        i++;

        // Boots
        gp.obj[i] = new ObjectBoots(gp);
        gp.obj[i].worldX = gp.tileSize * 46;
        gp.obj[i].worldY = gp.tileSize * 28;
        i++;

        // Healer prison
        gp.obj[i] = new ObjectDoor(gp);
        gp.obj[i].worldX = gp.tileSize * 41;
        gp.obj[i].worldY = gp.tileSize * 36;
        i++;

        // Treasure room
        gp.obj[i] = new ObjectDoor(gp);
        gp.obj[i].worldX = gp.tileSize * 43;
        gp.obj[i].worldY = gp.tileSize * 47;
        i++;

        gp.obj[i] = new ObjectChest(gp);
        gp.obj[i].worldX = gp.tileSize * 46;
        gp.obj[i].worldY = gp.tileSize * 47;

    }

    /**
     * Instantiate NPC and provide location
     */
    public void setNPC() {

        int i = 0;
        gp.npc[i] = new NPC_Villager(gp);
        gp.npc[i].worldX = gp.tileSize * 21;
        gp.npc[i].worldY = gp.tileSize * 21;
    }

    /**
     * Instantiate monster and provide location
     */
    public void setMonster() {
        int i = 0;

        // Monster trap
        gp.monster[i] = new GreenVillain(gp);
        gp.monster[i].worldX = gp.tileSize * 31;
        gp.monster[i].worldY = gp.tileSize * 8;
        i++;

        gp.monster[i] = new GreenVillain(gp);
        gp.monster[i].worldX = gp.tileSize * 32;
        gp.monster[i].worldY = gp.tileSize * 8;
        i++;

        // After bridge
        gp.monster[i] = new GreenVillain(gp);
        gp.monster[i].worldX = gp.tileSize * 30;
        gp.monster[i].worldY = gp.tileSize * 30;
        i++;

        gp.monster[i] = new GreenVillain(gp);
        gp.monster[i].worldX = gp.tileSize * 30;
        gp.monster[i].worldY = gp.tileSize * 31;
        i++;

        gp.monster[i] = new GreenVillain(gp);
        gp.monster[i].worldX = gp.tileSize * 23;
        gp.monster[i].worldY = gp.tileSize * 37;
        i++;

        // Second water pond
        gp.monster[i] = new GreenVillain(gp);
        gp.monster[i].worldX = gp.tileSize * 6;
        gp.monster[i].worldY = gp.tileSize * 19;
        i++;

        gp.monster[i] = new GreenVillain(gp);
        gp.monster[i].worldX = gp.tileSize * 9;
        gp.monster[i].worldY = gp.tileSize * 31;
        i++;

        gp.monster[i] = new GreenVillain(gp);
        gp.monster[i].worldX = gp.tileSize * 9;
        gp.monster[i].worldY = gp.tileSize * 32;
        i++;

        gp.monster[i] = new GreenVillain(gp);
        gp.monster[i].worldX = gp.tileSize * 8;
        gp.monster[i].worldY = gp.tileSize * 31;
        i++;

        gp.monster[i] = new GreenVillain(gp);
        gp.monster[i].worldX = gp.tileSize * 9;
        gp.monster[i].worldY = gp.tileSize * 28;
    }
}