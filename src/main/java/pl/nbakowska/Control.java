package pl.nbakowska;


import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Control extends KeyAdapter {
    @Override
    public void keyPressed(KeyEvent e) {

        int key = e.getKeyCode();

        if ((key == KeyEvent.VK_LEFT) && (!Parameter.turnRight)) {
            Parameter.turnLeft = true;
            Parameter.turnUp = false;
            Parameter.turnDown = false;
        }

        if ((key == KeyEvent.VK_RIGHT) && (!Parameter.turnLeft)) {
            Parameter.turnRight = true;
            Parameter.turnUp = false;
            Parameter.turnDown = false;
        }

        if ((key == KeyEvent.VK_UP) && (!Parameter.turnDown)) {
            Parameter.turnUp = true;
            Parameter.turnRight = false;
            Parameter.turnLeft = false;
        }

        if ((key == KeyEvent.VK_DOWN) && (!Parameter.turnUp)) {
            Parameter.turnDown = true;
            Parameter.turnRight = false;
            Parameter.turnLeft = false;
        }

    }

}


