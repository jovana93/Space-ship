package javaspaceinv;

import java.awt.Color;
import java.awt.HeadlessException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;

public class Help extends JFrame {

    private JLabel createdBy = new JLabel();
    private JTextArea rulesOfGame = new JTextArea();

    public Help() throws HeadlessException {
        this.setTitle("Help");
        this.setSize(262, 300);
        this.setResizable(false);
        this.setAlwaysOnTop(true);
        createdBy.setText("Created by: Jovana Bundalo");
        rulesOfGame.setBackground(this.getBackground());
        rulesOfGame.setText("\n\nUse the [SPACE] bar to fire your gun at them\n"
                + " and blow each ship to pieces.Be careful \n"
                + "though,because these ships fire back and as \n"
                + "you deplete their numbers,the space invaders \n"
                + "get faster and faster until the last remaining\n "
                + "ship appears to move at near warp speed.To \n"
                + "ensure you stay alive as long as possible,hide\n"
                + " behind the bluewalls and develop and a \n"
                + "run-and-gun mentality.Try to blast out whole \n"
                + "rows of space invaders at once as it makes it\n"
                + " easier to shoot into a crowd rather than \n"
                + "pinpoint each individual ship.");
        rulesOfGame.setBounds(0, 0, 262, 248);
        rulesOfGame.setEditable(false);

        createdBy.setVerticalAlignment(JLabel.BOTTOM);
        createdBy.setHorizontalAlignment(JLabel.RIGHT);
        add(rulesOfGame);
        add(createdBy);
    }

}
