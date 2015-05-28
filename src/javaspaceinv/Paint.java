package javaspaceinv;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;


public class Paint {
    private Rectangle2D.Double lowerRectangle;
    private int speedX =1;
    
    private int x;
    private int y;
    private int WIDHT = 70;
    private int HIGHT = 20;
    
       
    public Paint(int x, int y) {
        this.x = x;
        this.y = y;
        
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
}

