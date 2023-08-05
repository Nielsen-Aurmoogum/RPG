package main;

public class EventHandler {

    GamePanel gp;
    EventRect eventRect[][];
    int eventRectDefaultX, eventRectDefaultY;

    int previousEventX, previousEventY;
    boolean canTouchEvent;

    public EventHandler(GamePanel gp) {
        this.gp = gp;

        eventRect = new EventRect[gp.maxWorldCol][gp.maxWorldRow];

        int col = 0;
        int row = 0;
        while (col < gp.maxWorldCol && row < gp.maxScreenRow) {
            eventRect[col][row] = new EventRect();
            eventRect[col][row].x = 23;
            eventRect[col][row].y = 23;
            eventRect[col][row].width = 2;
            eventRect[col][row].height = 2;
            eventRect[col][row].eventRectDefaultX = eventRect[col][row].x;
            eventRect[col][row].eventRectDefaultY = eventRect[col][row].y;
        }

    }

    // What kind of event and where are the events
    // on the map
    public void checkEvent() {

        // check if player is more than one tile away from previous event
        int xDistance = Math.abs(gp.player.worldX = previousEventX);
        int yDistance = Math.abs(gp.player.worldY = previousEventY);
        int distance = Math.max(xDistance, yDistance);

        if (distance > gp.tileSize) {
            canTouchEvent = true;
        }

        if (canTouchEvent = true) {
            if (hit(4, 4, "left") == true) {
                fire(gp.dialogueState);
            }
        }

    }

    // Check for event collision
    public boolean hit(int col, int row, String reqDirection) {
        boolean hit = false;

        gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
        gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;
        eventRect[col][row].x = col * gp.tileSize + eventRect[col][row].x;
        eventRect[col][row].y = row * gp.tileSize + eventRect[col][row].y;

        if (gp.player.solidArea.intersects(eventRect[col][row]) && eventRect[col][row].eventDone == false) {
            if (gp.player.direction.contains(reqDirection) || reqDirection.contentEquals("any")) {
                hit = true;

                previousEventX = gp.player.worldX;
                previousEventY = gp.player.worldY;
            }
        }

        gp.player.solidArea.x = gp.player.solidAreaDefaultX;
        gp.player.solidArea.y = gp.player.solidAreaDefaultY;
        eventRect[col][row].x = eventRect[col][row].eventRectDefaultX;
        eventRect[col][row].y = eventRect[col][row].eventRectDefaultY;

        return hit;
    }

    // Damage-based event
    public void fire(int gameState) {
        gp.gameState = gameState;
        gp.ui.currentDialogue = "You are on fire but in a bad way";
        gp.player.life -= 1;

    }

}