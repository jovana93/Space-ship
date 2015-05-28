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
     private Paint obstacle;
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
        obstacle = new Paint((getPANEL_WIDTH()- 70)/2,getPANEL_HEIGHT()-20);
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
        
        g2d.draw(obstacle.getLowerRectangle());
    }

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
            obstacle.move();	
      if (e.getKeyCode() == KeyEvent.VK_RIGHT)	   
            obstacle.move1();	
 }

    @Override
    public void keyReleased(KeyEvent e) {
        
    }
    
}

