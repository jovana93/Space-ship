package javaspaceinv;

import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Invaders {

    private Rectangle2D.Double Rectangle;
    private int speedX = 1;
    static BufferedImage invaderImage = null;

    private int x;
    private int y;
    private int WIDHT;
    private int HIGHT;
    private boolean side;
    private boolean hited;

    public Invaders(int x, int y, int WIDHT, int HIGHT) {
        this.x = x;
        this.y = y;
        this.WIDHT = WIDHT;
        this.HIGHT = HIGHT;
        side = true;
        hited = false;
        Rectangle = new Rectangle2D.Double(x, y, WIDHT, HIGHT);
    }

    public Rectangle2D.Double getRectangle() {
        Rectangle.x = x;
        return Rectangle;
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

    public boolean isSide() {
        return side;
    }

    public static void loadImages() {
        try {
            invaderImage = ImageIO.read(new File("src/images/alien.png"));
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public static BufferedImage getImage() {
        return invaderImage;
    }

    public void setSide(boolean side) {
        this.side = side;
    }

    public void setHit(boolean hited) {
        this.hited = hited;
    }

    public void move() {
        if (side) {
            if (getX() > 5) {
                x -= 20;
            } else {
                side = false;
            }
        }
        if (!side) {
            if (getX() + getWIDHT() < 595) {
                x += 20;
            } else {
                side = true;
            }
        }
    }
}
