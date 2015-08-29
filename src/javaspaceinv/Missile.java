package javaspaceinv;

import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Missile {

    private Rectangle2D.Double lowerRectangle;
    private int speedX = 1;
    static BufferedImage missileImage = null;

    private int x;
    private int y;
    private int WIDHT;
    private int HIGHT;
    private boolean hited;

    public Missile(int x, int y) {
        this.x = x;
        this.y = y;
        this.WIDHT = 5;
        this.HIGHT = 10;
        hited = false;
        lowerRectangle = new Rectangle2D.Double(x, y, WIDHT, HIGHT);
    }

    public Rectangle2D.Double getLowerRectangle() {
        lowerRectangle.x = x;
        return lowerRectangle;
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

    public boolean isHit() {
        return hited;
    }

    public static void loadImages() {
        try {
            missileImage = ImageIO.read(new File("src/images/invader.png"));
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public static BufferedImage getImage() {
        return missileImage;
    }

    public void move() {
        y -= 2;
    }

    public void moveDown() {
        y += 2;
    }
}
