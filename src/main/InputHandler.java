package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class InputHandler implements KeyListener {
    public boolean upInput, downInput, leftInput, rightInput;

    // W A S D for character movements
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