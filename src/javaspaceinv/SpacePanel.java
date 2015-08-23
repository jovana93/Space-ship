package javaspaceinv;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;


public class SpacePanel extends JPanel implements  ActionListener, KeyListener {	 

    int frame;
     private Font mainFont;
     private final int PANEL_HEIGHT = 400;
     private final int PANEL_WIDTH = 600;
     private Ship ship;
     private Invaders[][] invaders = new Invaders[10][5];
     private Wall[] walls = new Wall[3];
     private ArrayList<Missile> missiles;
     private Timer timer;
     private boolean inGame;
     
      static Image backgroundImage;
      private final int BACKGROUND_WIDTH = 1000;
    private int backgroundPosition;
     
    public SpacePanel() {
        mainFont = new Font("Ariel", Font.BOLD, 18);
        inGame = false;
        
        backgroundPosition = 0;
        loadImages();
        
        missiles = new ArrayList<>();
        
        setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        setLayout(null);
        setBackground(Color.WHITE);
        setFocusable(true);
        frame = 0;
        timer = new Timer(30,this);
        
        addKeyListener (this);
        ship = new Ship((getPANEL_WIDTH()- 70)/2,getPANEL_HEIGHT()-20,70,20);
        for(int i = 0; i<10; i++)
            for(int j = 0; j<5; j++)
                invaders[i][j] = new Invaders(i*30, j*30, 20, 20);
         walls[0] = new Wall((getPANEL_WIDTH()- 410)/2,getPANEL_HEIGHT() -150,70,80);
         int x1 = walls[0].getX();
         for(int k = 0; k<3; k++)
         walls[k] = new Wall(x1+k*(walls[0].getWIDHT() +100),getPANEL_HEIGHT() -150,70,40);
       }
    
    private void setMoveSide(boolean side){
        for(int i=0; i<10; i++)
            for(int j = 0; j<5; j++)
                invaders[i][j].setSide(side);
    }
    private void moveInvaders(){
        boolean moveSide = invaders[0][0].isSide();
        boolean temp;
        if(moveSide)
        {
            invaders[0][0].move();
            setMoveSide(invaders[0][0].isSide());
            for(int i=0; i<10; i++)
                for(int j = 0; j<5; j++)
                    if(i==0 && j == 0)
                        continue;
                    else
                        invaders[i][j].move();
        }
        else
        {
            invaders[9][0].move();
            temp = invaders[9][0].isSide();
            setMoveSide(invaders[9][0].isSide());
            for(int i=0; i<10; i++)
                for(int j = 0; j<5; j++)
                    if(i==9 && j == 0)
                        continue;
                    else
                        invaders[i][j].move();
            if(temp!=moveSide)
                invaders[9][0].move();
        }
    }
     
    @Override
    public void paint(Graphics g) {
        super.paint(g); 
        Graphics2D g2d = (Graphics2D)g;
        
        drawBackground(g2d);
        
        g2d.draw(ship.getLowerRectangle());
        drawShip(g2d);
        for(int i = 0; i<10; i++)
            for(int j=0; j<5; j++)
                if(!invaders[i][j].isHited())
                {
                    g2d.draw(invaders[i][j].getLowerRectangle());
                    drawInvaders(g2d, i,j);
                }
        
        
        for (int k = 0; k < 3; k++)
        {
            if(!walls[k].isFinished()){
            
            g2d.draw(walls[k].getLowerRectangle());
            drawWall(g2d, k);
        }}
        if(!missiles.isEmpty())
             for(int i = 0; i< missiles.size(); i++)
             {
                 //g2d.draw(missiles.get(i).getLowerRectangle());
                 g2d.drawImage(Wall.getImage(),missiles.get(i).getX(),missiles.get(i).getY(), missiles.get(i).getWIDHT(), missiles.get(i).getHIGHT(), null);
             }
            
    }
    private void drawInvaders(Graphics2D g2d, int i, int j){
        g2d.drawImage(Invaders.getImage(),invaders[i][j].getX(),invaders[i][j].getY(), invaders[i][j].getWIDHT(), invaders[i][j].getHIGHT(), null);
    }
    private void drawWall(Graphics2D g2d, int i){
        g2d.drawImage(Wall.getImage(),walls[i].getX(),walls[i].getY(), walls[i].getWIDHT(), walls[i].getHIGHT(), null);
    }
    private void drawShip(Graphics2D g2d){
        g2d.drawImage(Ship.getImage(),ship.getX(),ship.getY(), ship.getWIDHT(), ship.getHIGHT(), null);
    }
    private void drawBackground(Graphics2D g2d){
        g2d.drawImage(backgroundImage, backgroundPosition, 0, BACKGROUND_WIDTH, PANEL_HEIGHT, null);
    }
    
    public int getPANEL_HEIGHT() {
        return PANEL_HEIGHT;
    }

    public int getPANEL_WIDTH() {
        return PANEL_WIDTH;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(inGame)
        {
            frame++;
            moveMissile();
            if(frame%10 == 0)
            {
                moveInvaders();
                int k = hasMissileHitObstacle();
                if(k!=-1)
                    missiles.remove(k);
            }
        if(gameOver())
        {    
            inGame = false;
            if(!missiles.isEmpty())
                missiles.clear();
            timer.stop();
        }
        repaint();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
      if(!inGame){
          inGame = true;
          timer.start();
      }
          if (e.getKeyCode() == KeyEvent.VK_LEFT)	   
            ship.move();	
      else if(e.getKeyCode() == KeyEvent.VK_RIGHT)	   
            ship.move1();	
      else if(e.getKeyCode() == KeyEvent.VK_SPACE)
            missiles.add(new Missile(ship.getX() + ship.getWIDHT()/2, ship.getY()));
      else
          e.consume();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        
    }
    public static void loadImages() {
        try {
            Invaders.loadImages();
            Wall.loadImages();
            Ship.loadImages();
            backgroundImage = ImageIO.read(new File("src/images/space.jpg"));
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public static Image getImage() {
        return backgroundImage;
    }
    
    private int hasMissileHitObstacle() {
        double x, y;
        if(!missiles.isEmpty())
            for(int k=0; k< missiles.size(); k++)
            {
                x = missiles.get(k).getX();
                y = missiles.get(k).getY();
                for(int i=0; i<10; i++)
                    for(int j = 0; j<5; j++)
                        if (invaders[i][j].getLowerRectangle().intersects(x, y, missiles.get(k).getWIDHT(), missiles.get(k).getHIGHT()))
                        {
                            if(!invaders[i][j].isHited()){
                                invaders[i][j].setHited(true);
                                return k;
                            }
                        }
                for(int i = 0; i< 3; i++)
                    if (walls[i].getLowerRectangle().intersects(x, y, missiles.get(k).getWIDHT(), missiles.get(k).getHIGHT()))
                    {
                        if(!walls[i].isFinished())
                        {    walls[i].Hited();
                            return k;
                        }
                    }
            }
    return -1;
    }

    private void moveMissile() {
        if(!missiles.isEmpty()){
        for(int i = 0; i< missiles.size(); i++)
        {
            missiles.get(i).move();
            if(missiles.get(i).getY() < 0)
                missiles.remove(i);
        }
    }
    }
    
    private boolean gameOver(){
        for(int i = 0; i<10; i++)
            for(int j = 0; j<5; j++)
                if(!invaders[i][j].isHited())
                    return false;
        return true;
    }
}

