package javaspaceinv;

import static com.sun.java.accessibility.util.AWTEventMonitor.addKeyListener;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Rectangle2D;
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
     
    public SpacePanel() {
        mainFont = new Font("Ariel", Font.BOLD, 18);
        
        
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
    private void moveObjects(){ 
        for(int i=0; i<10; i++)
            for(int j = 0; j<5; j++)
                invaders[i][j].move();
    }
    
    
    @Override
    public void paint(Graphics g) {
        super.paint(g); 
        Graphics2D g2d = (Graphics2D)g;
        
        g2d.draw(ship.getLowerRectangle());
        for(int i = 0; i<10; i++)
            for(int j=0; j<5; j++)
                g2d.draw(invaders[i][j].getLowerRectangle());
        
        int x1 = walls[0].getX();
        
        for (int k = 0; k < 3; k++)
        {
            walls[k] = new Wall(x1,getPANEL_HEIGHT() -150,70,40);
            g2d.draw(walls[k].getLowerRectangle());
            x1+=(walls[0].getWIDHT() +100);
        }
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
        if(frame%3 == 0)
        {
     //       moveObjects();
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
    
}

