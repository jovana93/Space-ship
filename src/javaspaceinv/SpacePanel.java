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
import java.io.File;
import java.io.IOException;
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
     private Timer timer;
     
      static Image backgroundImage;
      private final int BACKGROUND_WIDTH = 1000;
    private int backgroundPosition;
     
    public SpacePanel() {
        mainFont = new Font("Ariel", Font.BOLD, 18);
        
        backgroundPosition = 0;
        loadImages();
        
        setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        setLayout(null);
        setBackground(Color.WHITE);
        setFocusable(true);
        frame = 0;
        timer = new Timer(30,this);
        timer.start();
        addKeyListener (this);
        ship = new Ship((getPANEL_WIDTH()- 70)/2,getPANEL_HEIGHT()-20,70,20);
        for(int i = 0; i<10; i++)
            for(int j = 0; j<5; j++)
                invaders[i][j] = new Invaders(i*30, j*30, 20, 20);
         walls[0] = new Wall((getPANEL_WIDTH()- 410)/2,getPANEL_HEIGHT() -150,70,80);
       }
    
    private void setMoveSide(boolean side){
        for(int i=0; i<10; i++)
            for(int j = 0; j<5; j++)
                invaders[i][j].setSide(side);
    }
    private void moveObjects(){
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
        int x1 = walls[0].getX();
        
        for (int k = 0; k < 3; k++)
        {
            walls[k] = new Wall(x1+k*(walls[0].getWIDHT() +100),getPANEL_HEIGHT() -150,70,40);
            g2d.draw(walls[k].getLowerRectangle());
            drawWall(g2d, k);
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
        frame++;
        if(frame%10 == 0)
        {
            moveObjects();
        }
         repaint(); 
    }

    @Override
    public void keyTyped(KeyEvent e) {
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
         
      if (e.getKeyCode() == KeyEvent.VK_LEFT)	   
            ship.move();	
      if (e.getKeyCode() == KeyEvent.VK_RIGHT)	   
            ship.move1();	
 }

    @Override
    public void keyReleased(KeyEvent e) {
        
    }
    public static void loadImages() {
        try {
            Invaders.loadImages();
            Wall.loadImages();
            Ship.loadImages();
            backgroundImage = ImageIO.read(new File("src/images/pozadina.jpg"));
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public static Image getImage() {
        return backgroundImage;
    }
}
