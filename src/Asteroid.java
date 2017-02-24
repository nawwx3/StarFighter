import java.awt.*;
import java.io.*;
import java.util.Random;

/*
    Created by Nathan Welch on 1/1/2017.
 */
public class Asteroid implements Updatable, Renderable {

    /*-----------------------

    put it back into arrays and make
    it work from there before trying
    to add the struct type stuff

     ----------------------------*/

    private Ship ship;
    private Menu menu;

    private final int numAst = 10;
    private final int numTrait = 4;
                        //x, y, dimensions, speed

    private float[][] astArr = new float[numAst][numTrait];
    private Color[] colorArr = new Color[numAst];

    private Font scoreFont = new Font("Arial", Font.BOLD, 20);
    private int score = 0;
    private int oldScore = 0;

    public Asteroid(Ship ship, Menu menu) {
        this.menu = menu;
        this.ship = ship;
        resetAsteroid();
    }

    private void resetAsteroid() {
        for(int i = 0; i < numAst; i++) {
            astArr[i][0] = randomX();
            astArr[i][2] = randomDim();
            astArr[i][1] = 0 - astArr[i][2] - (i * 90);
            astArr[i][3] = randomSpeed();
            colorArr[i] = randomColor();
        }
        ship.resetShip();
        oldScore = score;
        if(score > menu.getScore()) {
            menu.setScore(score);
        }
        score = 0;
    }

    //generates random start location
    private int randomX() {
        Random rand = new Random();
        return rand.nextInt(Game.WIDTH);
    }

    //generates random size of asteroid
    private int randomDim() {
        Random rand = new Random();
        return rand.nextInt(50) + 35;
    }

    //generates random color
    private Color randomColor() {
        Random rand = new Random();
        int color = rand.nextInt(3);
        switch(color) {
            case 0:
                return Color.WHITE;
            case 1:
                return Color.GRAY;
            default:
                return Color.LIGHT_GRAY;
        }
    }

    //generates random speed between 1 and 2
    private float randomSpeed() {
        Random rand = new Random();
        return rand.nextFloat()+ 1;
    }

    @Override
    public void update(Input input) {
        //moves the asteroids to their next location
        if(input.getStarted()) {
            for(int i = 0; i < numAst; i++) {
                astArr[i][1] += astArr[i][3];
            }
        }

        //collision check triangle
        for(int i = 0; i < numAst; i++) {
            //checks ast is in x-range of ship
            if((astArr[i][1] + astArr[i][2]) > ship.shipCoordsY[1] && astArr[i][1] < ship.shipCoordsY[0]) {
                //ast hits point of ship
                if( ship.shipCoordsX[1] > astArr[i][0] && ship.shipCoordsX[1] < (astArr[i][0]+astArr[i][2])    ) {
//                    System.out.println("top");
                    resetAsteroid();
                }
                //ast left of ship
                else if( astArr[i][0]+astArr[i][2] > ship.shipCoordsX[0] && astArr[i][0] < ship.shipCoordsX[0] &&
                        astArr[i][1]+astArr[i][2] > ship.shipCoordsY[0] && astArr[i][1] < ship.shipCoordsY[0])  {
//                    System.out.println("moving left");
                    resetAsteroid();
                }
                //ast right of ship
                else if( astArr[i][0]+astArr[i][2] > ship.shipCoordsX[2] && astArr[i][0] < ship.shipCoordsX[2] &&
                        astArr[i][1]+astArr[i][2] > ship.shipCoordsY[2] && astArr[i][1] < ship.shipCoordsY[2])  {
                    System.out.println("moving right");
                    resetAsteroid();
                }
                //if     y/x > 3  -> collision left of ship    AND    bottom of ast has not past bottom of ship
                else if(  ((astArr[i][1]+astArr[i][2]-ship.shipCoordsY[1])/(ship.shipCoordsX[1]-(astArr[i][0]+astArr[i][2])))>3 &&
                        (astArr[i][1]+astArr[i][2] < ship.shipCoordsY[0])  ) {
//                    System.out.println("left");
                    resetAsteroid();
                }
                else if(  ((astArr[i][1]+astArr[i][2]-ship.shipCoordsY[1])/((astArr[i][0]-ship.shipCoordsX[1])))>3 &&
                        (astArr[i][1]+astArr[i][2] < ship.shipCoordsY[0])  ) {
//                    System.out.println("right");
                    resetAsteroid();
                }
            }
        }
        //if at bottom of screen puts them back at top
        for(int i = 0; i < numAst; i ++) {
            if(astArr[i][1] > Game.HEIGHT && input.getStarted()) {
                astArr[i][0] = randomX();
                astArr[i][1] = -90;
                score++;
            }
        }
    }

    @Override
    public void render(Graphics2D g, float interpolation) {
        for(int i = 0; i < numAst; i++ ) {
            g.setColor(colorArr[i]);
            g.fillRect((int)astArr[i][0], (int) astArr[i][1], (int) astArr[i][2], (int) astArr[i][2]);
        }
//       render score
        g.setFont(scoreFont);
        g.setColor(Color.WHITE);
        g.drawString("Score: " + score, 25, 50);
        g.drawString("Old Score: " + oldScore, 25, 75);
    }
}
