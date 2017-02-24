/*
   Created by Nathan Welch on 1/1/2017.
   Handles all the sprites for the game
 */
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Sprite {

    public static BufferedImage getSprite(String fileName) throws IOException {
        return ImageIO.read(Sprite.class.getResourceAsStream(fileName));
    }

}