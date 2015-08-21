package javaspaceinv;

import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Ship {
private Rectangle2D.Double lowerRectangle;
    private int speedX =1;
    static BufferedImage shipImage = null;
    private int x;
    private int y;
    private int WIDHT;
    private int HIGHT;
    
       
    public Ship(int x, int y, int  WIDHT,int HIGHT ) {
        this.x = x;
        this.y = y;
        this.WIDHT = WIDHT;
        this.HIGHT = HIGHT;
        
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
    
    public void move()
    {
        if (getX() > 5)
        x  -=20;
    }
    public void move1()
    { if (getX() + getWIDHT() < 595)
        x  +=20;
    }    
    public static void loadImages() {
        try {
            shipImage = ImageIO.read(new File("src/images/ship.jpg"));
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public static BufferedImage getImage() {
        return shipImage;
    }
}
