package javaspaceinv;

import java.awt.geom.Rectangle2D;

public class Wall {
    private Rectangle2D.Double lowerRectangle;
    
    private int x;
    private int y;
    private int WIDHT;
    private int HIGHT;
    private final int maxHits = 5;
    private int hitCount;
    public Wall(int x, int y, int  WIDHT,int HIGHT ) {
        this.x = x;
        this.y = y;
        this.WIDHT = WIDHT;
        this.HIGHT = HIGHT;
        hitCount = 0;
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

    public  boolean Hited(){
            hitCount++;
        if(hitCount == maxHits)
            return true;
        return false;
    }
    
   }


