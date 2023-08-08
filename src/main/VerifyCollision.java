package main;

import character.SuperCharacter;

public class VerifyCollision {
    GamePanel gp;

    public VerifyCollision(GamePanel gp) {
        this.gp = gp;
    }

    // Check for tile collision
    public void checkTile(SuperCharacter character) {
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
    public int checkObject(SuperCharacter character, boolean player) {
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
                        break;

                    case "down":
                        character.solidArea.y += character.speed;                        
                        break;

                    case "left":
                        character.solidArea.x -= character.speed;                        
                        break;

                    case "right":
                        character.solidArea.x += character.speed;                        
                        break;
                }

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
                
                // Reset
                character.solidArea.x = character.solidAreaDefaultX;
                character.solidArea.y = character.solidAreaDefaultY;
                gp.obj[i].solidArea.x = gp.obj[i].solidAreaDefaultX;
                gp.obj[i].solidArea.y = gp.obj[i].solidAreaDefaultY;
            }
        }

        return index;
    }

    // Player to NPC or Monster collision checker
    public int checkCharacter(SuperCharacter character, SuperCharacter[] target) {
        int index = 999;

        for (int i = 0; i < target.length; i++) {
            if (target[i] != null) {

                // Get character solid area position
                character.solidArea.x = character.worldX + character.solidArea.x;
                character.solidArea.y = character.worldY + character.solidArea.y;

                // Get target solid area position
                target[i].solidArea.x = target[i].worldX + target[i].solidArea.x;
                target[i].solidArea.y = target[i].worldY + target[i].solidArea.y;

                switch (character.direction) {
                    case "up":
                        character.solidArea.y -= character.speed;
                        break;

                    case "down":
                        character.solidArea.y += character.speed;
                        break;

                    case "left":
                        character.solidArea.x -= character.speed;                        
                        break;

                    case "right":
                        character.solidArea.x += character.speed;                        
                        break;
                }

                // intersects knows when NPC/Monster hit-box and player hit-box overlap
                if (character.solidArea.intersects(target[i].solidArea)) {
                    if (target[i] != character) {
                        character.collisionOn = true;
                        index = i;                        
                    }
                }

                // Reset
                character.solidArea.x = character.solidAreaDefaultX;
                character.solidArea.y = character.solidAreaDefaultY;
                target[i].solidArea.x = target[i].solidAreaDefaultX;
                target[i].solidArea.y = target[i].solidAreaDefaultY;
            }
        }

        return index;
    }

    // NPC or Monster to Player collision checker
    public void checkPlayer(SuperCharacter character) {
        // Get character solid area position
        character.solidArea.x = character.worldX + character.solidArea.x;
        character.solidArea.y = character.worldY + character.solidArea.y;

        // Get target solid area position
        gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
        gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;

        switch (character.direction) {
            case "up":
                character.solidArea.y -= character.speed;
                // intersects knows when NPC/Monster hit-box and player hit-box overlap
                if (character.solidArea.intersects(gp.player.solidArea)) {
                    character.collisionOn = true;
                }
                break;

            case "down":
                character.solidArea.y += character.speed;
                if (character.solidArea.intersects(gp.player.solidArea)) {
                    character.collisionOn = true;
                }
                break;

            case "left":
                character.solidArea.x -= character.speed;
                if (character.solidArea.intersects(gp.player.solidArea)) {
                    character.collisionOn = true;
                }
                break;

            case "right":
                character.solidArea.x += character.speed;
                if (character.solidArea.intersects(gp.player.solidArea)) {
                    character.collisionOn = true;
                }
                break;
        }

        // Reset
        character.solidArea.x = character.solidAreaDefaultX;
        character.solidArea.y = character.solidAreaDefaultY;
        gp.player.solidArea.x = gp.player.solidAreaDefaultX;
        gp.player.solidArea.y = gp.player.solidAreaDefaultY;

    }
}