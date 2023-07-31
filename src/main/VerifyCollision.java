package main;

import character.Character;

public class VerifyCollision {
    GamePanel gp;

    public VerifyCollision(GamePanel gp) {
        this.gp = gp;
    }

    public void checkTile(Character character) {
        int characterLeftWorldX = character.worldX + character.solidArea.x;
        int characterRightWorldX = character.worldX + character.solidArea.x + character.solidArea.width;
        int characterTopWorldY = character.worldY + character.solidArea.y;
        int characterBottomWorldY = character.worldY + character.solidArea.y + character.solidArea.height;

        int characterLeftCol = characterLeftWorldX / gp.tileSize;
        int characterRightCol = characterRightWorldX / gp.tileSize;
        int characterTopRow = characterTopWorldY / gp.tileSize;
        int characterBottomRow = characterBottomWorldY / gp.tileSize;

        int tileNum1, tileNum2; // Only two tiles will be checked

        switch (character.direction) {
            case "up":
                characterTopRow = (characterTopWorldY - character.speed) / gp.tileSize;
                tileNum1 = gp.tileM.tileNumMap[characterLeftCol][characterTopRow]; // top left
                tileNum2 = gp.tileM.tileNumMap[characterRightCol][characterTopRow]; // top right
                if (gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
                    character.collisionOn = true;
                }
                break;

            case "down":
                characterBottomRow = (characterBottomWorldY - character.speed) / gp.tileSize;
                tileNum1 = gp.tileM.tileNumMap[characterLeftCol][characterBottomRow]; // bottom left
                tileNum2 = gp.tileM.tileNumMap[characterRightCol][characterBottomRow]; // bottom right
                if (gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
                    character.collisionOn = true;
                }
                break;

            case "left":
                characterLeftCol = (characterLeftWorldX - character.speed) / gp.tileSize;
                tileNum1 = gp.tileM.tileNumMap[characterLeftCol][characterTopRow]; // top left
                tileNum2 = gp.tileM.tileNumMap[characterLeftCol][characterBottomRow]; // bottom left
                if (gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
                    character.collisionOn = true;
                }
                break;

            case "right":
                characterRightCol = (characterRightWorldX + character.speed) / gp.tileSize;
                tileNum1 = gp.tileM.tileNumMap[characterRightCol][characterTopRow]; // top right
                tileNum2 = gp.tileM.tileNumMap[characterRightCol][characterBottomRow]; // bottom right
                if (gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
                    character.collisionOn = true;
                }
                break;
        }

    }

    /**
     * Check if player hits object
     */
    public int checkObject(Character character, boolean player) {
        // When index not 999
        // player found object
        int index = 999;

        for (int i = 0; i < gp.obj.length; i++) {
            if (gp.obj[i] != null) {

                // Get character solid area position
                character.solidArea.x = character.worldX + character.solidArea.x;
                character.solidArea.y = character.worldY + character.solidArea.y;

                // Get object solid area position
                gp.obj[i].solidArea.x = gp.obj[i].worldX + gp.obj[i].solidArea.x;
                gp.obj[i].solidArea.y = gp.obj[i].worldY + gp.obj[i].solidArea.y;

                switch (character.direction) {
                    case "up":
                        character.solidArea.y -= character.speed;
                        // intersects knows when object hit-box and player hit-box overlap
                        if (character.solidArea.intersects(gp.obj[i].solidArea)) {
                            if (gp.obj[i].collision == true) {
                                character.collisionOn = true;
                            }

                            // Non-player characters cannot pick up object
                            if (player == true) {
                                index = i;
                            }
                        }
                        break;

                    case "down":
                        character.solidArea.y += character.speed;
                        if (character.solidArea.intersects(gp.obj[i].solidArea)) {
                            if (gp.obj[i].collision == true) {
                                character.collisionOn = true;
                            }

                            if (player == true) {
                                index = i;
                            }
                        }
                        break;

                    case "left":
                        character.solidArea.x -= character.speed;
                        if (character.solidArea.intersects(gp.obj[i].solidArea)) {
                            if (gp.obj[i].collision == true) {
                                character.collisionOn = true;
                            }

                            if (player == true) {
                                index = i;
                            }
                        }
                        break;

                    case "right":
                        character.solidArea.x += character.speed;
                        if (character.solidArea.intersects(gp.obj[i].solidArea)) {
                            if (gp.obj[i].collision == true) {
                                character.collisionOn = true;
                            }

                            if (player == true) {
                                index = i;
                            }
                        }
                        break;
                }

                // Reset
                character.solidArea.x = character.solidAreaDefaultX;
                character.solidArea.y = character.solidAreaDefaultY;
                gp.obj[i].solidArea.x = gp.obj[i].solidAreaDefaultX;
                gp.obj[i].solidArea.y = gp.obj[i].solidAreaDefaultY;
            }
        }

        return index;
    }
}