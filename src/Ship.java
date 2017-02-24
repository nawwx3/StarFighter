import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/*
  Created by Nathan Welch on 1/1/2017.


      HEIGHT = 650, WIDTH = 500;


 */
public class Ship implements Updatable, Renderable {

    private float moveVel = 10;
    private int xTBase = 250, xBLBase = 225, xBRBase = 275;
    public int yTBase = 500, yBBase = 575;

    public int shipCoordsX[] = new int[3];
    public int shipCoordsY[] = new int [3];

    private BufferedImage spaceship;

    public Ship() {
        resetShip();
        try {
            spaceship = Sprite.getSprite("ship.png");
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
            System.exit(1);
        }
    }

    public void resetShip() {
        shipCoordsX[0] = xBLBase;
        shipCoordsX[1] = xTBase;
        shipCoordsX[2] = xBRBase;

        shipCoordsY[0] = yBBase;
        shipCoordsY[1] = yTBase;
        shipCoordsY[2] = yBBase;
    }

    @Override
    public void render(Graphics2D g, float interpolation) {
        //triangle
        //thought I'd keep this here just in case
//        g.setColor(Color.BLACK);
//        g.fillPolygon(shipCoordsX, shipCoordsY, 3);
        //ship
        g.drawImage(spaceship, shipCoordsX[0], shipCoordsY[1], null);
    }

    @Override
    public void update(Input input) {
        //if right arrow pressed, shift right
        if(input.isRightPressed()) {
            if(shipCoordsX[2] + moveVel < Game.WIDTH) {
                shipCoordsX[0] += moveVel;
                shipCoordsX[1] += moveVel;
                shipCoordsX[2] += moveVel;
            }
        }
        //if left arrow pressed, shift left
        if(input.isLeftPressed()) {
            if(shipCoordsX[0] - moveVel > 0) {
                shipCoordsX[0] -= moveVel;
                shipCoordsX[1] -= moveVel;
                shipCoordsX[2] -= moveVel;
            }
        }
    }
}
