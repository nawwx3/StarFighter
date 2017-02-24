/*
    update, render, the arrays, add/remove updatable/renderable functions, everything labeled, are not my original code
    It was taken from a Java class on StackSkills.com that I need to insert the name of in here

       course and instructor name/place of work
 */

/* ----------   Ideas as to how this should work   ----------------
    - can have differing asteroids be different point values based on size
    - once gone past ~30 asteroids can speed everything up


 ------------------------------ */

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;

/*
 * Created by Nathan Welch on 1/1/2017.
 */
public class Game {
    //final is a constant
    //static is a member variable
    public final static int HEIGHT = 650, WIDTH = 500;

    private String gameName = "Star Fighter";
    //creates a background
    private Canvas game = new Canvas();
    private Input input;

    //array of renderables/updatables
    private ArrayList<Updatable> updatables = new ArrayList<>();
    private ArrayList<Renderable> renderables = new ArrayList<>();

    public void addUpdatable(Updatable u) {
        updatables.add(u);
    }
    public void removeUpdatble(Updatable u) {
        updatables.remove(u);
    }
    public void addRenderable(Renderable r) {
        renderables.add(r);
    }
    public void removeRenderable(Renderable r) {
        renderables.remove(r);
    }

    public void start() {

    //not originally mine
        Dimension gameSize = new Dimension(Game.WIDTH, Game.HEIGHT);
        JFrame gameWindow = new JFrame(gameName);
        gameWindow.setSize(gameSize);
        gameWindow.setResizable(false);
        gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameWindow.setVisible(true);

        game.setSize(gameSize);
        game.setMaximumSize(gameSize);
        game.setMinimumSize(gameSize);
        game.setPreferredSize(gameSize);

        gameWindow.add(game);
        gameWindow.setLocationRelativeTo(null);

        //init input
        input = new Input();
        game.addKeyListener(input);

        //game loop
        final int TICKS_PER_SECOND = 60; //60 frames per second
        final int SKIP_TICKS = 1000 / TICKS_PER_SECOND; //gives amount of milliseconds it should take for 60 FPS
        //max amounts of updates per render
        final int MAX_FRAMESKIP = 5;

        long nextGameTick = System.currentTimeMillis();
        int loops;
        float interpolation;

        long timeAtLastFPSCheck = 0;
        int ticks = 0; //amount of updates per second

        //creates and stays in the menu until 'P' is pressed on keyboard
        Menu m = new Menu();
        addRenderable(m);
        while(!input.isPPressed()){
            render(0);
        }
        removeRenderable(m);
        //menu stops and actual game starts

        Ship s = new Ship();
        Asteroid a = new Asteroid(s, m);

        //adds the ship into render/update
        addRenderable(s);
        addUpdatable(s);
        //adds asteroids into render/update
        addRenderable(a);
        addUpdatable(a);

        s.resetShip();
        //a.resetAsteroid();

        boolean running = true;
        while(running) {
            loops = 0;
            while(System.currentTimeMillis() > nextGameTick && loops < MAX_FRAMESKIP) {
                update();
                ticks++;
                nextGameTick += SKIP_TICKS;
                loops++;
            }
            interpolation = (float) (System.currentTimeMillis() + SKIP_TICKS - nextGameTick)
                    / (float) SKIP_TICKS;
            render(interpolation);
            if(System.currentTimeMillis() - timeAtLastFPSCheck >= 1000) {
                gameWindow.setTitle(gameName + " - FPS: " + ticks);
                ticks = 0;
                timeAtLastFPSCheck = System.currentTimeMillis();
            }
        }
    }

    private void update() {
        for(Updatable u : updatables) {
            u.update(input);
        }
    }
    private void render(float interpolation) {
        BufferStrategy bs = game.getBufferStrategy();
        if(bs == null) {
            game.createBufferStrategy(2);
            return;
        }
        Graphics2D g = (Graphics2D) bs.getDrawGraphics();
        g.clearRect(0, 0, game.getWidth(), game.getHeight());

    //can put some function for a cool background here eventually
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, game.getWidth(), game.getHeight());

        for(Renderable r : renderables) {
            r.render(g, interpolation);
        }
        g.dispose();
        bs.show();
    }
}
