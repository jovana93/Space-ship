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
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

public class SpacePanel extends JPanel implements ActionListener, KeyListener {

    int frame;
    private final Font mainFont;
    private final static int PANEL_HEIGHT = 400;
    private final static int PANEL_WIDTH = 600;
    private Ship ship;
    private Invaders[][] invaders;
    private Wall[] walls = new Wall[3];
    private ArrayList<Missile> missiles;
    private ArrayList<Missile> invaderMissiles;
    private int[][] listOfExplosions = new int[3][1];
    private int[] explosionY = new int[3];
    private int[] holdExplosion = new int[3];
    
    private final Timer timer;
    private static boolean inGame;
    private boolean finished;
    private int score;
    private final Random random;
    private int invaderMissileFrame;
    private boolean started = false;

    static Image backgroundImage;
    static Image startImage;
    static Image explosionImage;
    static Image gameOverImage;
    private final int BACKGROUND_WIDTH = 1000;
    private final int backgroundPosition;

    public static Dimension getPanelDimension(){
    
    return new Dimension(PANEL_WIDTH, PANEL_HEIGHT);
    }
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

        inGame = false;
        addKeyListener(this);
        holdExplosion[0] = holdExplosion[1] = holdExplosion[2] = 0;
    }

    public static boolean getInGame() {
        return inGame;
    }

    private void setMoveSide(boolean side) {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 6; j++) {
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
                for (int j = 0; j < 6; j++) {
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
                for (int j = 0; j < 6; j++) {
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
        if(started && !finished){
            drawBackground(g2d);

            g2d.draw(ship.getRectangle());
            drawShip(g2d);
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 6; j++) {
                    if (!invaders[i][j].isHit()) {
                        g2d.draw(invaders[i][j].getRectangle());
                        drawInvaders(g2d, i, j);
                    }
                }
            }

            for (int k = 0; k < 3; k++) {
                if (!walls[k].isFinished()) {
                    g2d.draw(walls[k].getRectangle());
                    drawWall(g2d, k);
                }
            }
            if (!missiles.isEmpty()) {
                for (Missile missile : missiles) {
                    g2d.drawImage(Missile.getImage(false), missile.getX(), missile.getY(), missile.getWIDHT(), missile.getHIGHT(), null);
                }
            }
            if (!invaderMissiles.isEmpty()) {
                for (Missile invaderMissile : invaderMissiles) {
                    g2d.drawImage(Missile.getImage(true), invaderMissile.getX(), invaderMissile.getY(), invaderMissile.getWIDHT(), invaderMissile.getHIGHT(), null);
                }
            }
            if(listOfExplosions[0][0] != -1){
                drawExplosion(g2d, listOfExplosions[0][0], explosionY[0], 0);   
            }
            if(listOfExplosions[1][0] != -1){
                drawExplosion(g2d, listOfExplosions[1][0], explosionY[1], 1);   
            }
            if(listOfExplosions[2][0] != -1){
                drawExplosion(g2d, listOfExplosions[2][0], explosionY[2], 2);
            }

            drawScore(g2d);
        }
        else
        {
            drawStart(g2d);
        }
        if (finished) {
            drawOver(g2d);
        }

    }

    private void drawScore(Graphics2D g2d) {
        g2d.setFont(mainFont);

        String message = "SCORE: " + score;

        FontMetrics fontMetrics = g2d.getFontMetrics(mainFont);

        int stringWidth = fontMetrics.stringWidth(message);
        g2d.setColor(Color.WHITE);
        g2d.drawString(message, 0, PANEL_HEIGHT / 2 - 180);
    }

    private void drawMessage(Graphics2D g2d) {
        g2d.setFont(mainFont);

        String message = "Game over: SCORE = " + score;

        FontMetrics fontMetrics = g2d.getFontMetrics(mainFont);
        int stringWidth = fontMetrics.stringWidth(message);
        g2d.setColor(Color.red);
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
    private void drawStart(Graphics2D g2d) {
        g2d.drawImage(startImage, backgroundPosition, 0, PANEL_WIDTH, PANEL_HEIGHT, null);
    }
    private void drawOver(Graphics2D g2d) {
        g2d.drawImage(gameOverImage, backgroundPosition, 0, PANEL_WIDTH, PANEL_HEIGHT, null);
    }
    
    private void drawExplosion(Graphics2D g2d, int x, int y, int n) {
        g2d.drawImage(explosionImage, x - 10, y, 20, 20, null);
        holdExplosion[n]++;
        if (holdExplosion[n] == 3) {
            listOfExplosions[n][0] = -1;
            holdExplosion[n] = 0;
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
        if (inGame) {
            frame++;
            moveMissile();
            if (frame % 10 == 0) {
                moveInvaders();
            }
            int k = hasMissileHitInvaderOrWal();
            if (k != -1) {
                explosionY[2] = missiles.get(k).getY();
                listOfExplosions[2][0] = missiles.get(k).getX();
                
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
                    j = random.nextInt(6);
                } while (invaders[i][j].isHit());

                invaderMissiles.add(new Missile(invaders[i][j].getX(), invaders[i][j].getY()));

            }
            areMissilesCollided();
            repaint();
            
        } 
    }

    @Override
    public void keyTyped(KeyEvent e) {
    
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(started){
            if (!inGame) {
                inGame = true;
                if(!finished)
                    timer.start();
            }
            if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                ship.moveLeft();
            } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                ship.moveRight();
            }
        }else{
                finished = false;
                repaint();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) 
        {
            if(started)
                missiles.add(new Missile(ship.getX() + ship.getWIDHT() / 2, ship.getY()));
            
        }
    }

    public static void loadImages() {

        try {
            Invaders.loadImages();
            Wall.loadImages();
            Ship.loadImages();
            Missile.loadImages();
            backgroundImage = ImageIO.read(new File("src/images/space.png"));
            startImage = ImageIO.read(new File("src/images/start.jpg"));
            explosionImage = ImageIO.read(new File("src/images/explosion.jpg"));
            gameOverImage = ImageIO.read(new File("src/images/GameOver.gif"));
        } catch (IOException e) {
            System.out.println(e);
        }
    }
    public static Image getImage() {
        return backgroundImage;
    }
   
    private int hasMissileHitInvaderOrWal() {
        double x, y;
        if (!missiles.isEmpty()) {
            for (int k = 0; k < missiles.size(); k++) {
                x = missiles.get(k).getX();
                y = missiles.get(k).getY();
                for (int i = 0; i < 10; i++) {
                    for (int j = 0; j < 6; j++) {
                        if (invaders[i][j].getRectangle().intersects(x, y, missiles.get(k).getWIDHT(), missiles.get(k).getHIGHT())) {

                            if (!invaders[i][j].isHit()) {
                                invaders[i][j].setHit(true);
                                updateScore(20);
                                return k;
                            }
                        }
                    }
                }
                for (int i = 0; i < 3; i++) {
                    if (walls[i].getRectangle().intersects(x, y, missiles.get(k).getWIDHT(), missiles.get(k).getHIGHT())) {

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
                    listOfExplosions[0][0] = missiles.get(i).getX();
                    explosionY[0] = 0;
                    missiles.remove(i);
                    
                }
            }
        }
        if (!invaderMissiles.isEmpty()) {
            for (int i = 0; i < invaderMissiles.size(); i++) {
                invaderMissiles.get(i).moveDown();
                if (invaderMissiles.get(i).getY() > PANEL_HEIGHT) {
                    listOfExplosions[1][0] = invaderMissiles.get(i).getX();
                    explosionY[1] = PANEL_HEIGHT - 20;
                    invaderMissiles.remove(i);
                }
            }
        }
    }

    private boolean aliensHit() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 6; j++) {
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
        ship = null;
        walls = null;
        invaders = null;
        finished = true;
        String playerName = JOptionPane.showInputDialog(null, "Please, enter your name:", "You scored " + getGameScore() + " points", JOptionPane.INFORMATION_MESSAGE);
        try {

            List<String> scores = load("src/score/results.txt");
            scores.add(playerName + " - " + getGameScore());
            save_file("src/score/results.txt", scores); //igra je sacuvana

        } catch (IOException ex) { //ako ne moze da bude prikazano, objavi gresku
            System.out.println("Error : " + ex);
        }
        inGame = false;
        started = false;
    }
    
    public int getGameScore() {
        return score;
    }
    public final void restart() {
        frame = 0;
        missiles = new ArrayList<>();
        finished = false;
        invaderMissiles = new ArrayList<>();
        score = 0;
        inGame = false;
        started = true;
        ship = new Ship((getPANEL_WIDTH() - 70) / 2, getPANEL_HEIGHT() - 20, 70, 20);

        invaders = new Invaders[10][6];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 6; j++) {
                invaders[i][j] = new Invaders(i * 30, j * 30 + 30, 20, 20);

            }
        }
        
        walls = new Wall[3];
        walls[0] = new Wall((getPANEL_WIDTH() - 410) / 2, getPANEL_HEIGHT() - 150, 70, 80);
        int x1 = walls[0].getX();
        for (int k = 0; k < 3; k++) {
            walls[k] = new Wall(x1 + k * (walls[0].getWIDHT() + 100), getPANEL_HEIGHT() - 150, 70, 40);
        }

        invaderMissileFrame = random.nextInt(165) + 1;
        listOfExplosions[0][0] = listOfExplosions[1][0] = -1;
        repaint();
    }
    private void save_file(String name_fale, List<String> scores) throws IOException {

        File file = new File(name_fale);
        if (!file.exists()) { //Ako ne postoji datoteka, kreirati je
            file.createNewFile();
        }
        try (PrintWriter writer = new PrintWriter(file, "UTF-8")) {
            for (String score : scores) {
                writer.println(score);
            }
        }
    }

    // postaviti u listu rezultate(punjenje liste)
    private List<String> load(String file_name) throws FileNotFoundException {
        File file = new File(file_name);

        if (!file.exists()) {
            //ako ne postoji datotetka, izbaci izuzetak
            throw new FileNotFoundException();
        }

        List<String> scores = new ArrayList<>();

        try (Scanner scanner = new Scanner(file)) { //kreiranje citaoca koji unosi podatke
            while (scanner.hasNextLine()) {
                scores.add(scanner.nextLine()); //postavljanje rezultata u listu

            }
        }

        return scores;
    }

    public static void readTextFileLineByLine() {
        FileReader in = null;
        //BufferedReader dozvoljava čitanje većeg "komada" datoteke odjednom.
        BufferedReader bin = null;

        try {

            File file = new File("src/score/results.txt");

            in = new FileReader(file);
            // Za inicijalizaciju, BufferedReader zahtjeva otvoren FileReader tok
            bin = new BufferedReader(in);

            String data;
            ArrayList<String> rijeci = new ArrayList<>();

            /*
             * Metoda readLine klase BufferedReader učitava jedan red teksta iz
             * datoteke. Vraća null ukoliko dođe do kraja datoteke.
             */
            while ((data = bin.readLine()) != null) {
                rijeci.add(data);
            }

            int d = rijeci.size();

            String strLine = "";

            for (int i = 0; i < d; i++) {
                strLine += (i + 1) + ". " + rijeci.get(i) + "\n";
            }
            JOptionPane.showMessageDialog(null, strLine, "Scores", JOptionPane.INFORMATION_MESSAGE);

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, ex.toString());
        } finally {
            if (bin != null) {
                try {
                    bin.close();
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, ex.toString());
                }
            }

            if (in != null) {
                try {
                    in.close();
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, ex.toString());
                }
            }
        }
    }
    private void areMissilesCollided() {
        if(!missiles.isEmpty() && !invaderMissiles.isEmpty()){
            for(int indexShip = 0; indexShip < missiles.size(); indexShip++)
            {
                for(int indexInvader = 0; indexInvader < invaderMissiles.size(); indexInvader++)
                {
                    if(missiles.get(indexShip).getRectangle().intersects(invaderMissiles.get(indexInvader).getRectangle())){
                        missiles.remove(indexShip);
                        invaderMissiles.remove(indexInvader);
                    }
                }
            }
        }
    }
}
