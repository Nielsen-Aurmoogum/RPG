package main;

import object.ObjectDoor;
import object.ObjectKey;

public class ObjectPlacer {
    GamePanel gp;

    public ObjectPlacer(GamePanel gp) {
        this.gp = gp;
    }

    /**
     * Instantiate object and provide location
     */
    public void setObject() {

        // Instantiate key and provide location
        gp.obj[0] = new ObjectKey(gp);
        gp.obj[0].worldX = 12 * gp.tileSize;
        gp.obj[0].worldY = 16 * gp.tileSize;

        gp.obj[1] = new ObjectKey(gp);
        gp.obj[1].worldX = 12 * gp.tileSize;
        gp.obj[1].worldY = 20 * gp.tileSize;

        // Instantiate door and provide location
        gp.obj[2] = new ObjectDoor(gp);
        gp.obj[2].worldX = 20 * gp.tileSize;
        gp.obj[2].worldY = 16 * gp.tileSize;
    }
}