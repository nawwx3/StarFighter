/*
   Created by Nathan Welch on 1/19/2017.
 */
import java.awt.*;
import java.io.*;

public class Menu implements Renderable{

    private final int PLAY_X_START = 100;
    private final int PLAY_Y_START = 225;
    private final int SCORE_X_START = 125;
    private final int SCORE_Y_START = 450;

    private final int PLAY_HEIGHT = 200;
    private final int PLAY_WIDTH = 300;
    private final int SCORE_HEIGHT = 100;
    private final int SCORE_WIDTH = 250;

    private Font menuFont = new Font("Arial", Font.BOLD, 24);
    private Font headingFont = new Font("Arial", Font.BOLD, 42);
    private Font scoreFont = new Font("Arial", Font.BOLD, 18);

    public int getScore() {
        File fileName = new File("score.txt");
        try {
            fileName.createNewFile();
        }
        catch(IOException ex) {
            System.out.println("Error reading file '" + fileName + "'");
        }
        int highscore = 0;
        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String line = bufferedReader.readLine();
            while(line != null) {
                try{
                    int score = Integer.parseInt(line.trim());
                    if(score > highscore) {
                        highscore = score;
                    }
                } catch (NumberFormatException ex) {
//                    System.out.println("catch exception code 1");
                }
                line = bufferedReader.readLine();
            }
            bufferedReader.close();
        }
        catch(IOException ex) {
            System.out.println("Error reading file '" + fileName + "'");
        }
        return highscore;

    }

    public void setScore(int score) {
        String fileName = "score.txt";
        try {
            FileWriter fileWriter = new FileWriter(fileName);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.newLine();
            bufferedWriter.append(" " + score);
            bufferedWriter.close();
        }
        catch(IOException ex) {
            System.out.println(
                    "Error writing to file '" + fileName + "'");
        }
    }

    @Override
    public void render(Graphics2D g, float interpolation) {
        g.setColor(Color.RED);
        g.fillRect(PLAY_X_START, PLAY_Y_START, PLAY_WIDTH, PLAY_HEIGHT);

        g.setColor(Color.BLUE);
        g.fillRect(SCORE_X_START, SCORE_Y_START, SCORE_WIDTH, SCORE_HEIGHT);

        g.setColor(Color.WHITE);
        g.setFont(menuFont);
        g.drawString("Play (p)", 200, 320);
        g.setFont(scoreFont);
        g.drawString("Highscore: " + getScore(), 175, 505);

        g.setFont(headingFont);
        g.setColor(Color.ORANGE);
        g.drawString("Star Fighter!", 120, 120);


//        g.fillOval(0, 0, 45, 45);  //makes a circle
//        g.fillRoundRect(5,5, 45, 45, 45, 45);


    }
}




