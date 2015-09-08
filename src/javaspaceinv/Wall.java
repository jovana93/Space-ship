package javaspaceinv;

import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Wall {

    private Rectangle2D.Double rectangle;
    static BufferedImage wallImage = null;
    private int x;
    private int y;
    private int WIDHT;
    private int HIGHT;
    private final int maxHits = 5;
    private int hitCount;
    private boolean finished;

    public Wall(int x, int y, int WIDHT, int HIGHT) {
        this.x = x;
        this.y = y;
        this.WIDHT = WIDHT;
        this.HIGHT = HIGHT;
        hitCount = 0;
        rectangle = new Rectangle2D.Double(x, y, WIDHT, HIGHT);
        finished = false;
    }

    public Rectangle2D.Double getRectangle() {
        rectangle.x = x;
        return rectangle;
    }

    public int getWIDHT() {
        return WIDHT;
    }

    public int getHIGHT() {
        return HIGHT;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void Hited() {
        hitCount++;
        if (hitCount == maxHits) {
            finished = true;
        }
    }

    public boolean isFinished() {
        return finished;
    }

    public static void loadImages() {
        try {
            wallImage = ImageIO.read(new File("src/images/wall.png"));
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public static BufferedImage getImage() {
        return wallImage;
    }
}