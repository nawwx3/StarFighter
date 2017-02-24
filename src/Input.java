/*
    Created by Nathan Welch on 1/1/2017.
 */

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class Input implements KeyListener {
    private boolean pPressed = false;
    private boolean pReleased = true;
    private boolean rightPressed = false;
    private boolean rightReleased = true;
    private boolean leftPressed = false;
    private boolean leftReleased = true;

    public boolean isPPressed() {
        boolean s = pPressed;
        pPressed  = false;
        return s;
    }
    public boolean isRightPressed() {
        boolean s = rightPressed;
        rightPressed  = false;
        return s;
    }
    public boolean isLeftPressed() {
        boolean s = leftPressed;
        leftPressed  = false;
        return s;
    }

    private boolean started = false;
    public boolean getStarted() { return started; }


    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_P && pReleased) {
            pPressed = true;
            pReleased = false;
        }
        if(e.getKeyCode() == KeyEvent.VK_RIGHT && rightReleased) {
            rightPressed = true;
            rightReleased = false;
        }
        if(e.getKeyCode() == KeyEvent.VK_LEFT && leftReleased) {
            leftPressed = true;
            leftReleased = false;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_P) {
            pPressed = true;
            started = true;
        }
        if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
            rightPressed = true;
        }
        if(e.getKeyCode() == KeyEvent.VK_LEFT) {
            leftPressed = true;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

}
