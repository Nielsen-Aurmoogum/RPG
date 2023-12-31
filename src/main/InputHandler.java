package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class InputHandler implements KeyListener {
    public boolean upInput, downInput, leftInput, rightInput, enterInput;
    GamePanel gp;

    public InputHandler(GamePanel gp) {
        this.gp = gp;
    }

    // W S for navigation in menus
    // W A S D for character movements
    // P pauses game
    // ENTER opens or quits dialogue with NPC(on collision only)
    // ENTER attacks monster
    @Override
    public void keyPressed(KeyEvent e) {

        int code = e.getKeyCode();

        // Title state
        if (gp.gameState == gp.titleState) {
            titleState(code);
        }

        // Play state
        else if (gp.gameState == gp.playState) {
            playState(code);
        }

        // Pause state
        else if (gp.gameState == gp.pauseState) {
            pauseState(code);
        }

        // Dialogue state
        else if (gp.gameState == gp.dialogueState) {
            dialogueState(code);
        }

        // Character info state
        else if (gp.gameState == gp.characterInfoState) {
            characterInfoState(code);
        }

        // Game over state
        else if (gp.gameState == gp.gameOverState) {
            gameOverState(code);
        }

        // Win state
        else if (gp.gameState == gp.winState) {
            winState(code);
        }

    }

    // When user is on title screen
    public void titleState(int code) {

        if (code == KeyEvent.VK_W) {
            gp.ui.commandNum--;
            if (gp.ui.commandNum < 0) {
                gp.ui.commandNum = 1;
            }
        }

        if (code == KeyEvent.VK_S) {
            gp.ui.commandNum++;
            if (gp.ui.commandNum > 1) {
                gp.ui.commandNum = 0;
            }
        }

        if (code == KeyEvent.VK_ENTER) {
            if (gp.ui.commandNum == 0) {
                gp.gameState = gp.playState;
            }

            if (gp.ui.commandNum == 1) {
                System.exit(0);
            }
        }
    }

    // When user is playing
    public void playState(int code) {

        if (code == KeyEvent.VK_W) {
            upInput = true;
        }

        if (code == KeyEvent.VK_A) {
            leftInput = true;
        }

        if (code == KeyEvent.VK_S) {
            downInput = true;
        }

        if (code == KeyEvent.VK_D) {
            rightInput = true;
        }

        if (code == KeyEvent.VK_P) {
            gp.gameState = gp.pauseState;
        }

        if (code == KeyEvent.VK_I) {
            gp.gameState = gp.characterInfoState;
        }

        if (code == KeyEvent.VK_ENTER) {
            enterInput = true;
        }
    }

    // When game is paused
    public void pauseState(int code) {

        if (code == KeyEvent.VK_P) {
            gp.gameState = gp.playState;
        }

        if (code == KeyEvent.VK_ENTER) {
            enterInput = true;
        }

        int maxCommandNum = 0;
        switch (gp.ui.subState) {

            case 0: // Pause screen 2 other options
                maxCommandNum = 2;
                break;

            case 2: // End game 1 other option
                maxCommandNum = 1;
                break;
        }

        if (code == KeyEvent.VK_W) {
            gp.ui.commandNum--;
            if (gp.ui.commandNum < 0) {
                gp.ui.commandNum = maxCommandNum;
            }
        }

        if (code == KeyEvent.VK_S) {
            gp.ui.commandNum++;
            if (gp.ui.commandNum > maxCommandNum) {
                gp.ui.commandNum = 0;
            }
        }
    }

    // When dialogues are happening
    public void dialogueState(int code) {

        if (code == KeyEvent.VK_ENTER) {
            gp.gameState = gp.playState;
        }
    }

    // When character info is being displayed
    public void characterInfoState(int code) {

        if (code == KeyEvent.VK_I) {
            gp.gameState = gp.playState;
        }

        if (code == KeyEvent.VK_W) {
            if (gp.ui.slotRow != 0) {
                gp.ui.slotRow--;
            }
        }

        if (code == KeyEvent.VK_A) {
            if (gp.ui.slotCol != 0) {
                gp.ui.slotCol--;
            }
        }

        if (code == KeyEvent.VK_S) {
            if (gp.ui.slotRow != 1) {
                gp.ui.slotRow++;
            }
        }

        if (code == KeyEvent.VK_D) {
            if (gp.ui.slotCol != 4) {
                gp.ui.slotCol++;
            }
        }

        if (code == KeyEvent.VK_ENTER) {
            gp.player.selectItem();
        }
    }

    // When game is over
    public void gameOverState(int code) {

        if (code == KeyEvent.VK_W) {
            gp.ui.commandNum--;
            if (gp.ui.commandNum < 0) {
                gp.ui.commandNum = 1;
            }
        }

        if (code == KeyEvent.VK_S) {
            gp.ui.commandNum++;
            if (gp.ui.commandNum > 1) {
                gp.ui.commandNum = 0;
            }
        }

        if (code == KeyEvent.VK_ENTER) {
            if (gp.ui.commandNum == 0) {
                gp.gameState = gp.playState;
                gp.retry();
            }

            if (gp.ui.commandNum == 1) {
                gp.gameState = gp.titleState;
                gp.restart();
            }
        }
    }

    // When game has been won
    public void winState(int code) {

        if (code == KeyEvent.VK_ENTER) {
            if (gp.ui.commandNum == 0) {
                gp.gameState = gp.titleState;
                gp.restart();
            }
        }
    }

    // To know when movement has to stop
    @Override
    public void keyReleased(KeyEvent e) {

        int code = e.getKeyCode();

        if (code == KeyEvent.VK_W) {
            upInput = false;
        }

        if (code == KeyEvent.VK_A) {
            leftInput = false;
        }

        if (code == KeyEvent.VK_S) {
            downInput = false;
        }

        if (code == KeyEvent.VK_D) {
            rightInput = false;
        }

    }

    // Not used for this game
    @Override
    public void keyTyped(KeyEvent e) {
    }

}