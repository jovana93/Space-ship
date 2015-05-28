
package javaspaceinv;

import javax.swing.JFrame;


public class SpaceFrame extends JFrame{
    public SpaceFrame() {
       SpacePanel panel = new SpacePanel();
        
        setResizable(false);
        
        add(panel);
        pack();
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
    }
}
