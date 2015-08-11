package javaspaceinv;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;


public class Invaders {
    private Rectangle2D.Double lowerRectangle;
    private int speedX =1;
    
    private int x;
    private int y;
    private int WIDHT;
    private int HIGHT;
    private boolean side;
    private boolean hited;   
    public Invaders(int x, int y, int  WIDHT,int HIGHT ) {
        this.x = x;
        this.y = y;
        this.WIDHT = WIDHT;
        this.HIGHT = HIGHT;
        side = true;
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

    public boolean isHited() {
        return hited;
    }
    
    public void move()
    {
        if(side){
            if (getX() > 5)
                x -=20;
            else
                side = false;
        }
        if(!side)
        {
            if (getX() + getWIDHT() < 595)
            {
                x +=20;
            }else
                side = true;
        }
    }
}

