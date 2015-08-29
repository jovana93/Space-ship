package javaspaceinv;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;

public class SpacePanel extends JPanel implements ActionListener, KeyListener {

    int frame;
    private final Font mainFont;
    private final int PANEL_HEIGHT = 400;
    private final int PANEL_WIDTH = 600;
    private Ship ship;
    private final Invaders[][] invaders = new Invaders[10][5];
    private final Wall[] walls = new Wall[3];
    private ArrayList<Missile> missiles;
    private ArrayList<Missile> invaderMissiles;

    private final Timer timer;
    private boolean inGame;
    private boolean finished;
    private int score;
    private final Random random;
    private int invaderMissileFrame;

    static Image backgroundImage;
    private final int BACKGROUND_WIDTH = 1000;
    private final int backgroundPosition;

    public SpacePanel() {
        mainFont = new Font("Ariel", Font.BOLD, 18);

        backgroundPosition = 0;
        loadImages();

        setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        setLayout(null);
        setBackground(Color.WHITE);
        setFocusable(true);

        timer = new Timer(30, this);
        random = new Random();

        addKeyListener(this);
        restart();
    }

    private void setMoveSide(boolean side) {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 5; j++) {
                invaders[i][j].setSide(side);
            }
        }
    }

    private void moveInvaders() {
        boolean moveSide = invaders[0][0].isSide();
        boolean temp;
        if (moveSide) {
            invaders[0][0].move();
            setMoveSide(invaders[0][0].isSide());
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 5; j++) {
                    if (i == 0 && j == 0) {
                    } else {
                        invaders[i][j].move();
                    }
                }
            }
        } else {
            invaders[9][0].move();
            temp = invaders[9][0].isSide();
            setMoveSide(invaders[9][0].isSide());
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 5; j++) {
                    if (i == 9 && j == 0) {
                    } else {
                        invaders[i][j].move();
                    }
                }
            }
            if (temp != moveSide) {
                invaders[9][0].move();
            }
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;

        drawBackground(g2d);

        g2d.draw(ship.getLowerRectangle());
        drawShip(g2d);
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 5; j++) {
                if (!invaders[i][j].isHit()) {
                    g2d.draw(invaders[i][j].getLowerRectangle());
                    drawInvaders(g2d, i, j);
                }
            }
        }

        for (int k = 0; k < 3; k++) {
            if (!walls[k].isFinished()) {

                g2d.draw(walls[k].getLowerRectangle());
                drawWall(g2d, k);
            }
        }
        if (!missiles.isEmpty()) {
            for (Missile missile : missiles) {
                g2d.drawImage(Wall.getImage(), missile.getX(), missile.getY(), missile.getWIDHT(), missile.getHIGHT(), null);
            }
        }
        if (!invaderMissiles.isEmpty()) {
            for (Missile invaderMissile : invaderMissiles) {
                g2d.drawImage(Wall.getImage(), invaderMissile.getX(), invaderMissile.getY(), invaderMissile.getWIDHT(), invaderMissile.getHIGHT(), null);
            }
        }
        drawScore(g2d);
        if (finished) {
            drawMessage(g2d);
        }

    }

    private void drawScore(Graphics2D g2d) {
        g2d.setFont(mainFont);

        String message = "SCORE: " + score;

        FontMetrics fontMetrics = g2d.getFontMetrics(mainFont);
        int stringWidth = fontMetrics.stringWidth(message);

        g2d.drawString(message, 0, PANEL_HEIGHT);
    }

    private void drawMessage(Graphics2D g2d) {
        g2d.setFont(mainFont);

        String message = "Game over: SCORE = " + score;

        FontMetrics fontMetrics = g2d.getFontMetrics(mainFont);
        int stringWidth = fontMetrics.stringWidth(message);

        g2d.drawString(message, (PANEL_WIDTH - stringWidth) / 2, PANEL_HEIGHT / 2);
    }

    private void drawInvaders(Graphics2D g2d, int i, int j) {
        g2d.drawImage(Invaders.getImage(), invaders[i][j].getX(), invaders[i][j].getY(), invaders[i][j].getWIDHT(), invaders[i][j].getHIGHT(), null);
    }

    private void drawWall(Graphics2D g2d, int i) {
        g2d.drawImage(Wall.getImage(), walls[i].getX(), walls[i].getY(), walls[i].getWIDHT(), walls[i].getHIGHT(), null);
    }

    private void drawShip(Graphics2D g2d) {
        g2d.drawImage(Ship.getImage(), ship.getX(), ship.getY(), ship.getWIDHT(), ship.getHIGHT(), null);
    }

    private void drawBackground(Graphics2D g2d) {
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
        if (inGame) {
            frame++;
            moveMissile();
            if (frame % 10 == 0) {
                moveInvaders();
            }
            int k = hasMissileHitObstacle();
            if (k != -1) {
                missiles.remove(k);
            }
            if (hasMissileHitShip() || aliensHit()) {
                gameOver();
            }

            if (frame == invaderMissileFrame && inGame) {
                invaderMissileFrame = random.nextInt(100) + 100 + frame;
                int i, j;
                do {
                    i = random.nextInt(10);
                    j = random.nextInt(5);
                } while (invaders[i][j].isHit());
                invaderMissiles.add(new Missile(invaders[i][j].getX(), invaders[i][j].getY()));

            }

            repaint();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (!inGame) {
            inGame = true;
            timer.start();
            if (finished) {
                restart();
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            ship.move();
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            ship.move1();
        } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            missiles.add(new Missile(ship.getX() + ship.getWIDHT() / 2, ship.getY()));
        } else {
            e.consume();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    public static void loadImages() {
        try {
            Invaders.loadImages();
            Wall.loadImages();
            Ship.loadImages();
            backgroundImage = ImageIO.read(new File("src/images/space.png"));
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public static Image getImage() {
        return backgroundImage;
    }

    private int hasMissileHitObstacle() {
        double x, y;
        if (!missiles.isEmpty()) {
            for (int k = 0; k < missiles.size(); k++) {
                x = missiles.get(k).getX();
                y = missiles.get(k).getY();
                for (int i = 0; i < 10; i++) {
                    for (int j = 0; j < 5; j++) {
                        if (invaders[i][j].getLowerRectangle().intersects(x, y, missiles.get(k).getWIDHT(), missiles.get(k).getHIGHT())) {
                            if (!invaders[i][j].isHit()) {
                                invaders[i][j].setHit(true);
                                updateScore(20);
                                return k;
                            }
                        }
                    }
                }
                for (int i = 0; i < 3; i++) {
                    if (walls[i].getLowerRectangle().intersects(x, y, missiles.get(k).getWIDHT(), missiles.get(k).getHIGHT())) {
                        if (!walls[i].isFinished()) {
                            walls[i].Hited();
                            updateScore(2);
                            return k;
                        }
                    }
                }
            }
        }
        return -1;
    }

    private void moveMissile() {
        if (!missiles.isEmpty()) {
            for (int i = 0; i < missiles.size(); i++) {
                missiles.get(i).move();
                if (missiles.get(i).getY() < 0) {
                    missiles.remove(i);
                }
            }
        }
        if (!invaderMissiles.isEmpty()) {
            for (int i = 0; i < invaderMissiles.size(); i++) {
                invaderMissiles.get(i).moveDown();
                if (invaderMissiles.get(i).getY() > PANEL_HEIGHT) {
                    invaderMissiles.remove(i);
                }
            }
        }
    }

    private boolean aliensHit() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 5; j++) {
                if (!invaders[i][j].isHit()) {
                    return false;
                }
            }
        }
        return true;
    }

    private void updateScore(int point) {
        score += point;
    }

    private boolean hasMissileHitShip() {

        if (!invaderMissiles.isEmpty()) {
            for (Missile invaderMissile : invaderMissiles) {
                if (invaderMissile.getY() >= PANEL_HEIGHT - ship.getHIGHT() && invaderMissile.getY() <= PANEL_HEIGHT) {
                    if (invaderMissile.getX() <= ship.getX() + ship.getWIDHT() && invaderMissile.getX() >= ship.getX()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void gameOver() {
        if (!missiles.isEmpty()) {
            missiles.clear();
        }
        if (!invaderMissiles.isEmpty()) {
            invaderMissiles.clear();
        }
        timer.stop();
        finished = true;
        inGame = false;
    }

    public final void restart() {
        frame = 0;
        missiles = new ArrayList<>();
        finished = false;
        invaderMissiles = new ArrayList<>();
        score = 0;
        inGame = false;
        ship = new Ship((getPANEL_WIDTH() - 70) / 2, getPANEL_HEIGHT() - 20, 70, 20);
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 5; j++) {
                invaders[i][j] = new Invaders(i * 30, j * 30, 20, 20);
            }
        }
        walls[0] = new Wall((getPANEL_WIDTH() - 410) / 2, getPANEL_HEIGHT() - 150, 70, 80);
        int x1 = walls[0].getX();
        for (int k = 0; k < 3; k++) {
            walls[k] = new Wall(x1 + k * (walls[0].getWIDHT() + 100), getPANEL_HEIGHT() - 150, 70, 40);
        }
        invaderMissileFrame = random.nextInt(165) + 330;
        repaint();
    }
}
