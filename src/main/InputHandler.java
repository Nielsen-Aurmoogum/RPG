package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class InputHandler implements KeyListener {
    public boolean upInput, downInput, leftInput, rightInput;
    GamePanel gp;

    public InputHandler(GamePanel gp) {
        this.gp = gp;
    }

    // W A S D for character movements
    // P pauses game
    @Override
    public void keyPressed(KeyEvent e) {

        int code = e.getKeyCode();

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
            if (gp.gameState == gp.playState) {
                gp.gameState = gp.pauseState;
            }
            else if (gp.gameState == gp.pauseState) {
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