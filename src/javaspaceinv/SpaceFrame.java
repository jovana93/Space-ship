package javaspaceinv;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class SpaceFrame extends JFrame {

    SpacePanel panel = new SpacePanel();

    public SpaceFrame() {

        setResizable(false);
        setTitle("Space Invaders");
        setJMenuBar(initMenu());

        add(panel);
        pack();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

    }

    private JMenuBar initMenu() {
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Game");
        JMenuItem exit = new JMenuItem("Exit");
        JMenuItem game = new JMenuItem("New Game");

        JMenu help = new JMenu("Help");
        help.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
                Help helpFrame = new Help();
                helpFrame.setVisible(true);
                helpFrame.setLocationRelativeTo(null);
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int answer;
                answer = javax.swing.JOptionPane.showConfirmDialog(null, "Are you sure you want to exit ?", "QUESTION ?",
                        javax.swing.JOptionPane.YES_NO_OPTION, javax.swing.JOptionPane.WARNING_MESSAGE);
                if (answer == javax.swing.JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });
        game.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (SpacePanel.getInGame()) {
                    int answer;
                    answer = javax.swing.JOptionPane.showConfirmDialog(null, "Are you sure you want to cancel this game ?", "QUESTION ?",
                            javax.swing.JOptionPane.YES_NO_OPTION, javax.swing.JOptionPane.WARNING_MESSAGE);
                    if (answer == javax.swing.JOptionPane.YES_OPTION) {
                        panel.restart();
                    }
                }
            }
        });

        menu.add(game);
        menu.add(exit);

        menuBar.add(menu);
        menuBar.add(help);

        return menuBar;
    }
}
