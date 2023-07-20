package character;

import java.awt.Color;
import java.awt.Graphics2D;

import main.GamePanel;
import main.InputHandler;

public class Player extends Character{
    GamePanel gp;
    InputHandler inputH;

    public Player(GamePanel gp, InputHandler inputH) {
        this.gp = gp;
        this.inputH = inputH;

        setStartValues();
    }

    public void setStartValues() {
        x = 100;
        y = 100;
        speed = 4;
    }

    public void update() {

        if (inputH.upInput == true) {
            y -= speed;
        }

        else if (inputH.downInput == true) {
            y += speed;
        }

        else if (inputH.leftInput == true) {
            x -= speed;
        }

        else if (inputH.rightInput == true) {
            x += speed;

        }
    }
    
    public void draw(Graphics2D g2) {
        g2.setColor(Color.RED);
        g2.fillRect(x, y, gp.tileSize, gp.tileSize);
    }
}