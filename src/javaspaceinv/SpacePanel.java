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

    
     private Font mainFont;
     private final int PANEL_HEIGHT = 400;
     private final int PANEL_WIDTH = 600;
     private Paint obstacle1,obstacle2,obstacle3,obstacle4,obstacle5,obstacle6;
     private Timer timer;
     
     
     
    public SpacePanel() {
        mainFont = new Font("Ariel", Font.BOLD, 18);
        
        
        setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        setLayout(null);
        setBackground(Color.WHITE);
        setFocusable(true);
        timer = new Timer(30,this);
        timer.start();
        addKeyListener (this);
        obstacle1 = new Paint((getPANEL_WIDTH()- 70)/2,getPANEL_HEIGHT()-20,70,20);
        obstacle2 = new Paint((getPANEL_WIDTH()-380)/2,40,20,20);
        //obstacle4 = new Paint(((getPANEL_WIDTH()- 480)/2)+185,getPANEL_HEIGHT() -150,70,80);
        //obstacle5 = new Paint(((getPANEL_WIDTH()- 480))/2 + 360,getPANEL_HEIGHT() -150,70,80);
        obstacle6 = new Paint((getPANEL_WIDTH()- 410)/2,getPANEL_HEIGHT() -150,70,80);
       
        



    }
    /*private void moveObjects()
   
    { 
        obstacle.move();
    }
    */
    
    
    @Override
    public void paint(Graphics g) {
        super.paint(g); 
        Graphics2D g2d = (Graphics2D)g;
        
        g2d.draw(obstacle1.getLowerRectangle());
        g2d.draw(obstacle2.getLowerRectangle());
        //g2d.draw(obstacle4.getLowerRectangle());
        //g2d.draw(obstacle5.getLowerRectangle());
        //g2d.draw(obstacle6.getLowerRectangle());
        
    
        int x = obstacle2.getX();
        int y = obstacle2.getY();
        int x1 = obstacle6.getX();
        
        
        
          int i, j,k;
          
        for (k = 1; k <= 3; k++)
        {
            obstacle4 = new Paint(x1,getPANEL_HEIGHT() -150,70,40);
            g2d.draw(obstacle4.getLowerRectangle());
            x1+=(obstacle6.getWIDHT() +100);
        }

        for (i = 1; i <= 5; i++) {
            for (j = 1; j <= 10; j++) 
                {   obstacle3 = new Paint(x, y ,20,20);
                    g2d.draw(obstacle3.getLowerRectangle());
                    x += obstacle2.getWIDHT()+20;
                    
                }   
                    
                    y += obstacle2.getWIDHT()+20;
                    x = obstacle2.getX();
    }}

    public int getPANEL_HEIGHT() {
        return PANEL_HEIGHT;
    }

    public int getPANEL_WIDTH() {
        return PANEL_WIDTH;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //obstacle.move();
         repaint(); 
    }

    @Override
    public void keyTyped(KeyEvent e) {
        //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyPressed(KeyEvent e) {
         
      if (e.getKeyCode() == KeyEvent.VK_LEFT)	   
            obstacle1.move();	
      if (e.getKeyCode() == KeyEvent.VK_RIGHT)	   
            obstacle1.move1();	
 }

    @Override
    public void keyReleased(KeyEvent e) {
        
    }
    
}

