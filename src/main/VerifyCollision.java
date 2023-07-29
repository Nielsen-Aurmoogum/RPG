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
}