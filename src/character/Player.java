package character;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import main.GamePanel;
import main.InputHandler;
import object.ObjectLightsaber;
import object.ObjectShield;

/**
 * Class of main character
 */
public class Player extends SuperCharacter {
    public final int screenX;
    public final int screenY;
    InputHandler inputH;
    public boolean attackCancel = false;
    public ArrayList<SuperCharacter> inventory = new ArrayList<>();
    public final int maxInventorySize = 10;

    // Constructor
    public Player(GamePanel gp, InputHandler inputH) {
        super(gp);
        this.inputH = inputH; // Key input

        setStartValues();
        getPlayerImage();
        getPlayerAttackImage();
        setItems();

        // Center charater
        screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
        screenY = gp.screenHeight / 2 - (gp.tileSize / 2);

        // Collision Hit-Box
        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 16;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 32;
        solidArea.height = 32;
    }

    // Set default position, speed and direction
    public void setStartValues() {
        worldX = gp.tileSize * 7;
        worldY = gp.tileSize * 2;
        speed = 4;
        direction = "right";

        // Player status
        level = 1;
        fullLife = 6;
        life = fullLife;
        strength = 1; // Influences attackPower
        agility = 1; // Influences defensePower
        exp = 0;
        nextLevelExp = 5;
        currentWeapon = new ObjectLightsaber(gp);
        currentShield = new ObjectShield(gp);
        attackPower = getAttackPower(); // Depends on weapon
        defensePower = getDefensePower(); // Depends on shield
    }

    // These items will be in inventory from the start
    public void setItems() {
        inventory.add(currentWeapon);
        inventory.add(currentShield);
    }

    // Getter method for attackPower
    public int getAttackPower() {
        attackArea = currentWeapon.attackArea;
        return attackPower = strength * currentWeapon.attackValue;
    }

    // Getter method for defensePower
    public int getDefensePower() {
        return defensePower = agility * currentShield.defenseValue;
    }

    // Read main character images
    public void getPlayerImage() {

        up1 = setup("resources/player/up1", gp.tileSize, gp.tileSize);
        up2 = setup("resources/player/up2", gp.tileSize, gp.tileSize);
        down1 = setup("resources/player/down1", gp.tileSize, gp.tileSize);
        down2 = setup("resources/player/down2", gp.tileSize, gp.tileSize);
        left1 = setup("resources/player/left1", gp.tileSize, gp.tileSize);
        left2 = setup("resources/player/left2", gp.tileSize, gp.tileSize);
        right1 = setup("resources/player/right1", gp.tileSize, gp.tileSize);
        right2 = setup("resources/player/right2", gp.tileSize, gp.tileSize);
    }

    // Read main character attack images
    public void getPlayerAttackImage() {

        if (currentWeapon.type == type_greenLightSaber) {
            attackUp1 = setup("resources/player/attackUp1", gp.tileSize, gp.tileSize * 2);
            attackUp2 = setup("resources/player/attackUp2", gp.tileSize, gp.tileSize * 2);
            attackDown1 = setup("resources/player/attackDown1", gp.tileSize, gp.tileSize * 2);
            attackDown2 = setup("resources/player/attackDown2", gp.tileSize, gp.tileSize * 2);
            attackLeft1 = setup("resources/player/attackLeft1", gp.tileSize * 2, gp.tileSize);
            attackLeft2 = setup("resources/player/attackLeft2", gp.tileSize * 2, gp.tileSize);
            attackRight1 = setup("resources/player/attackRight1", gp.tileSize * 2, gp.tileSize);
            attackRight2 = setup("resources/player/attackRight2", gp.tileSize * 2, gp.tileSize);
        }

        if (currentWeapon.type == type_redLightSaber) {
            attackUp1 = setup("resources/player/attackUp1red", gp.tileSize, gp.tileSize * 2);
            attackUp2 = setup("resources/player/attackUp2red", gp.tileSize, gp.tileSize * 2);
            attackDown1 = setup("resources/player/attackDown1red", gp.tileSize, gp.tileSize * 2);
            attackDown2 = setup("resources/player/attackDown2red", gp.tileSize, gp.tileSize * 2);
            attackLeft1 = setup("resources/player/attackLeft1red", gp.tileSize * 2, gp.tileSize);
            attackLeft2 = setup("resources/player/attackLeft2red", gp.tileSize * 2, gp.tileSize);
            attackRight1 = setup("resources/player/attackRight1red", gp.tileSize * 2, gp.tileSize);
            attackRight2 = setup("resources/player/attackRight2red", gp.tileSize * 2, gp.tileSize);
        }
    
    }

    /**
     * Update main character information
     */
    public void update() {

        if (attack == true) {
            attack();
        }

        else if (inputH.upInput == true || inputH.downInput == true || inputH.leftInput == true
                || inputH.rightInput == true || inputH.enterInput == true) { // When no keys are pressed, stay still

            // Check direction
            if (inputH.upInput == true) {
                direction = "up";
            }

            else if (inputH.downInput == true) {
                direction = "down";
            }

            else if (inputH.leftInput == true) {
                direction = "left";
            }

            else if (inputH.rightInput == true) {
                direction = "right";
            }

            // Check tile collision
            collisionOn = false;
            gp.collisionTest.checkTile(this);

            // Check object collision
            int objectIndex = gp.collisionTest.checkObject(this, true);
            pickupObject(objectIndex);

            // Check NPC collision
            int npcIndex = gp.collisionTest.checkCharacter(this, gp.npc);
            interactNPC(npcIndex);

            // Check monster collision
            int monsterIndex = gp.collisionTest.checkCharacter(this, gp.monster);
            contactMonster(monsterIndex);

            // Check event collision
            gp.eHandler.checkEvent();

            // No collision, player moves
            if (collisionOn == false && inputH.enterInput == false) {
                switch (direction) {
                    case "up":
                        worldY -= speed;
                        break;

                    case "down":
                        worldY += speed;
                        break;

                    case "left":
                        worldX -= speed;
                        break;

                    case "right":
                        worldX += speed;
                        break;
                }
            }

            if (inputH.enterInput == true && attackCancel == false) {
                attack = true;
                spriteCounter = 0;
            }

            attackCancel = false;
            gp.inputH.enterInput = false;

            // Dictates when a variation of an
            // orientation is used
            spriteCounter++;
            if (spriteCounter > 15) {

                if (spriteNum == 1) {
                    spriteNum = 2;
                }

                else if (spriteNum == 2) {
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }
        }

        // Receive damage every 60 frames(1s)
        if (invincible == true) {
            invincibleCounter++;
            if (invincibleCounter > 60) {
                invincible = false;
                invincibleCounter = 0;
            }
        }

    }

    // Sprites when attacking monster
    public void attack() {
        spriteCounter++;

        if (spriteCounter <= 5) {
            spriteNum = 1;
        }

        if (spriteCounter > 5 && spriteCounter <= 25) {
            spriteNum = 2;

            int currentWorldX = worldX;
            int currentWorldY = worldY;
            int solidAreaWidth = solidArea.width;
            int solidAreaHeight = solidArea.height;

            switch (direction) {
                case "up":
                    worldY -= attackArea.height;
                    break;
                case "down":
                    worldY += attackArea.height;
                    break;
                case "left":
                    worldX -= attackArea.width;
                    break;
                case "right":
                    worldX += attackArea.width;
                    break;
            }

            solidArea.width = attackArea.width;
            solidArea.height = attackArea.height;

            int monsterIndex = gp.collisionTest.checkCharacter(this, gp.monster);
            manageMonster(monsterIndex);

            worldX = currentWorldX;
            worldY = currentWorldY;
            solidArea.width = solidAreaWidth;
            solidArea.height = solidAreaHeight;
        }

        if (spriteCounter > 25) {
            spriteNum = 1;
            spriteCounter = 0;
            attack = false;
        }

    }

    // Player interacts with objects
    public void pickupObject(int i) {

        // Collision with object
        if (i != 999) {

            String text;

            // OBSTACLES
            if (gp.obj[i].type == type_obstacle) {
                if (inputH.enterInput == true) {
                    attackCancel = true;
                    gp.obj[i].interact();
                }
            }

            // INVENTORY ITEMS
            else {

                // Check if inventory is not full
                if (inventory.size() != maxInventorySize) {

                    inventory.add(gp.obj[i]);
                    text = "Got a " + gp.obj[i].name + " !";
                }

                else {
                    text = "Inventory is already full !";
                }
                gp.ui.addMessage(text);
                gp.obj[i] = null;
            }
        }
    }

    // Player to NPC collision
    public void interactNPC(int i) {
        if (gp.inputH.enterInput == true) {
            if (i != 999) {
                attackCancel = true;
                gp.gameState = gp.dialogueState;
                gp.npc[i].speak();
            }
        }
    }

    // Player to monster collision
    public void contactMonster(int i) {
        if (i != 999) {

            // Receive damage once
            if (invincible == false && gp.monster[i].dying == false) {
                int damage = gp.monster[i].attackPower - defensePower;

                // Standard minimum damage
                if (damage <= 0) {
                    life -= 1;
                    invincible = true;
                }

                else {
                    life -= damage;
                    invincible = true;
                }
            }
        }
    }

    // When monster has been hit by player
    public void manageMonster(int i) {

        if (i != 999) {

            if (gp.monster[i].invincible == false) {
                int damage = attackPower - gp.monster[i].defensePower;
                if (damage < 0) {
                    damage = 0;
                }

                gp.monster[i].life -= damage;
                gp.ui.addMessage("Damage x" + damage);
                gp.monster[i].invincible = true;
                gp.monster[i].damageReact();
                if (gp.monster[i].life <= 0) {
                    gp.monster[i].dying = true;
                    gp.ui.addMessage("You killed the " + gp.monster[i].name + " !");
                    gp.ui.addMessage("Exp +" + gp.monster[i].exp);
                    exp += gp.monster[i].exp;
                    checkLevelUp();
                }
            }
        }
    }

    // Check exp when player killed monster
    public void checkLevelUp() {
        if (exp >= nextLevelExp) {
            level++;
            nextLevelExp = nextLevelExp * 2;
            fullLife += 1;
            strength++;
            agility++;
            attackPower = getAttackPower();
            defensePower = getDefensePower();

            gp.gameState = gp.dialogueState;
            gp.ui.currentDialogue = "You are level " + level + " !\n" + "Well done.";
        }
    }

    // Select item found in inventory
    public void selectItem() {

        int itemIndex = gp.ui.getItemIndexInSlot();

        if (itemIndex < inventory.size()) {

            SuperCharacter selectedItem = inventory.get(itemIndex);

            if (selectedItem.type == type_greenLightSaber || selectedItem.type == type_redLightSaber) {

                currentWeapon = selectedItem;
                attackPower = getAttackPower();
                getPlayerAttackImage();
            }

            if (selectedItem.type == type_shield) {

                currentShield = selectedItem;
                defensePower = getDefensePower();
            }

            if (selectedItem.type == type_usable) {

                // Remove item from inventory only if used
                if (selectedItem.use(this) == true) {
                    inventory.remove(itemIndex);
                }
            }
        }
    }

    // Display main character on the screen
    @Override
    public void draw(Graphics2D g2) {
        BufferedImage image = null;
        int tempScreenX = screenX;
        int tempScreenY = screenY;

        // Different variations of orientations
        // are used
        switch (direction) {
            case "up":
                if (attack == false) {
                    if (spriteNum == 1) {
                        image = up1;
                    } else if (spriteNum == 2) {
                        image = up2;
                    }
                }
                if (attack == true) { // Different sprites when attacking
                    tempScreenY = screenY - gp.tileSize;
                    if (spriteNum == 1) {
                        image = attackUp1;
                    } else if (spriteNum == 2) {
                        image = attackUp2;
                    }
                }
                break;
            case "down":
                if (attack == false) {
                    if (spriteNum == 1) {
                        image = down1;
                    } else if (spriteNum == 2) {
                        image = down2;
                    }
                }
                if (attack == true) {
                    if (spriteNum == 1) {
                        image = attackDown1;
                    } else if (spriteNum == 2) {
                        image = attackDown2;
                    }
                }
                break;
            case "left":
                if (attack == false) {
                    if (spriteNum == 1) {
                        image = left1;
                    } else if (spriteNum == 2) {
                        image = left2;
                    }
                }
                if (attack == true) {
                    tempScreenX = screenX - gp.tileSize;
                    if (spriteNum == 1) {
                        image = attackLeft1;
                    } else if (spriteNum == 2) {
                        image = attackLeft2;
                    }
                }
                break;
            case "right":
                if (attack == false) {

                    if (spriteNum == 1) {
                        image = right1;
                    } else if (spriteNum == 2) {
                        image = right2;
                    }
                }
                if (attack == true) {
                    if (spriteNum == 1) {
                        image = attackRight1;
                    } else if (spriteNum == 2) {
                        image = attackRight2;
                    }
                }
                break;
        }

        // Player changes visually when invincible
        if (invincible == true) {
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
        }
        g2.drawImage(image, tempScreenX, tempScreenY, null);

        // Reset alpha
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
    }
}