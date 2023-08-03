package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class InputHandler implements KeyListener {
    public boolean upInput, downInput, leftInput, rightInput, enterInput;
    GamePanel gp;

    public InputHandler(GamePanel gp) {
        this.gp = gp;
    }

    // W A S D for character movements
    // P pauses game
    // ENTER opens or quits dialogue with NPC(on collision only)
    @Override
    public void keyPressed(KeyEvent e) {

        int code = e.getKeyCode();

        // Play state
        if (gp.gameState == gp.playState) {
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

            if (code == KeyEvent.VK_ENTER) {
                enterInput = true;
            }
        }

        // Pause state
        else if (gp.gameState == gp.pauseState) {
            if (code == KeyEvent.VK_P) {
                gp.gameState = gp.playState;
            }
        }

        // Dialogue state
        else if (gp.gameState == gp.dialogueState) {
            if (code == KeyEvent.VK_ENTER) {
                gp.gameState = gp.playState;
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