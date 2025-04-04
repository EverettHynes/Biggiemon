import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Timer;
import javax.swing.*;
import java.awt.Font;

public class Game extends JPanel implements Runnable, KeyListener, MouseListener {
    private BufferedImage back;
    private int key;

    private int time = 0;

    private String screen;
    private String message;

    private Timer animationTimer;

    private String name;
    private ImageIcon PlayerPic;

    private ImageIcon backgroundMain;
    private ImageIcon backgroundSoup;

    private ImageIcon WormsWorthMap;
    private ImageIcon WormsWorthProfile;
    public boolean wormtalk;

    private ImageIcon IceCubePic;

    private Player player;

    private biggie IceCube;

    private ArrayList<biggie> Inventory = new ArrayList<biggie>();

    public Game() {
        setFocusable(true);
        requestFocusInWindow();
        new Thread(this).start();
        this.addKeyListener(this);
        this.addMouseListener(this);

        key = -1;
        

        wormtalk = false; 

        screen = "GameArea";

        message = "hello welcome to the game. Glad to meet you";

        name = "Player";

        IceCube = new biggie("IceCubert", "GameArea", 100, 100, 100, 100, IceCubePic, IceCubePic, 100, 10, 5, true, false);

        PlayerPic = new ImageIcon("TK_DownFace.png");

        backgroundMain = new ImageIcon("BackgroundIceCream.png");
        backgroundSoup = new ImageIcon("BackgroundHotSprings.png");

        WormsWorthMap = new ImageIcon("Wormsworth house.png");
        WormsWorthProfile = new ImageIcon("Womrsworth.png");  


        IceCubePic = new ImageIcon("BackGroundIceCream.png");

        player = new Player(name, 540, 350, 58, 96, new ImageIcon("TK_DownFace.png"), "Down");
    }

    public void run() {
        try {
            while (true) {
                Thread.currentThread().sleep(5);
                repaint();
            }
        } catch (Exception e) {
        }
    }

    public void screen(Graphics g2d) {
        switch (screen) {
            case "PlayerMenu" -> {
                g2d.clearRect(0, 0, getWidth(), getHeight());

                g2d.drawString("Click to start", 100, 100);

                drawDialog(g2d);
            }
            case "GameArea" -> {
                g2d.clearRect(0, 0, getWidth(), getHeight());

                g2d.drawImage(backgroundMain.getImage(), 0, 0, getWidth(), getHeight(), this);

                checkCollision(g2d); 

                if(wormtalk){
                    drawWormsWorthProfile(g2d);
                }
                
                
                
                drawPlayer(g2d);

            }

            case "Soup" -> {
                g2d.clearRect(0, 0, getWidth(), getHeight());
                
                g2d.drawImage(backgroundSoup.getImage(), 0, 0, getWidth(), getHeight(), this);
                
                drawPlayer(g2d);

            }
            case "Inventory" -> {
                g2d.clearRect(0, 0, getWidth(), getHeight());
                for (biggie item : Inventory) {
                    g2d.drawImage(item.getPicP().getImage(), item.getX(), item.getY(), item.getWidth(),
                            item.getHeight(), null);
                    g2d.drawString(item.getName() + " - Health: " + item.getHealth() + ", Attack: " + item.getAttack()
                            + ", Defense: " + item.getDefense(), item.getX(), item.getY() - 10);
                }
            }
            case "Batlle" -> {
                g2d.clearRect(0, 0, getWidth(), getHeight());
                g2d.drawString("You are in battle", 100, 100);
                drawPlayer(g2d);
            }
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g); // Call the superclass's method to ensure proper painting

        if (back == null) {
            back = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
        }

        Graphics2D g2d = back.createGraphics();

        screen(g2d);

        g2d.dispose();
        g.drawImage(back, 0, 0, null);
    }

    public void drawPlayer(Graphics g2d) {
        g2d.drawImage(player.getPicP().getImage(), player.getX(), player.getY(), player.getWidth(), player.getHeight(),
                null);

        player.move();

        long currentTime = System.currentTimeMillis() / 200;

        // Left walking animation
        if (player.getDirection().equals("Left")) {
            if (player.getDx() == 0) {
                player.setPicP(new ImageIcon("TK sprites\\TK_LeftStill.png"));
            } else if (currentTime % 2 == 0) {
                player.setPicP(new ImageIcon("TK sprites\\TK_LeftFrame1.png"));
            } else {
                player.setPicP(new ImageIcon("TK sprites\\TK_LeftFrame2.png"));
            }
        }

        // Right walking animation
        if (player.getDirection().equals("Right")) {
            player.setPicP(new ImageIcon(player.getDx() == 0 ? "TK sprites\\TK_RightStill.png"
                    : (currentTime % 2 == 0
                            ? "TK sprites\\TK_RightFrame1.png"
                            : "TK sprites\\TK_RightFrame2.png")));
        }

        if (player.getDirection().equals("Down")) {
            player.setPicP(new ImageIcon(player.getDy() == 0 ? "TK sprites\\TK_DownStill.png"
                    : (currentTime % 2 == 0
                            ? "TK sprites\\TK_DownFrame1.png"
                            : "TK sprites\\TK_DownFrame2.png")));
        }

        if (player.getDirection().equals("Up")) {
            player.setPicP(new ImageIcon(player.getDy() == 0 ? "TK sprites\\TK_UpStill.png"
                    : (currentTime % 2 == 0
                            ? "TK sprites\\TK_UpFrame1.png"
                            : "TK sprites\\TK_UpFrame2.png")));
        }
    }

    public void drawDialog(Graphics g2d) {
        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, 800, 800);
        g2d.setColor(Color.WHITE);

    }

    public void checkCollision(Graphics g2d) {
        
        g2d.setColor(Color.red);
        g2d.drawRect(300,600,800,250);
        g2d.drawRect(900,500,800,250);

        // Collision detection
        Rectangle labRect = new Rectangle(100, 50, 100, 200);
        Rectangle screenEdge1 = new Rectangle(0,550, 300, 250);
        Rectangle screenEdge2 = new Rectangle(300,600,600,250);
        Rectangle screenEdge3 = new Rectangle(900, 500, 300, 250); // Right edge of the screen
       
       
        if (player.getX() + player.getWidth() > screenEdge3.getX() && player.getX() < screenEdge3.getX() + screenEdge3.getWidth()
                && player.getY() + player.getHeight() > screenEdge3.getY()
                && player.getY() < screenEdge3.getY() + screenEdge3.getHeight()) {
            player.setDx(0);
            player.setDy(0);
            if (player.getDirection().equals("Right")) {
                player.setX(player.getX() - 2);
            }
            if (player.getDirection().equals("Left")) {
                player.setX(player.getX() + 2);
            }
            if (player.getDirection().equals("Up")) {
                player.setY(player.getY() + 2);
            }
            if (player.getDirection().equals("Down")) {
                player.setY(player.getY() - 2);
            }
        }
   

        Rectangle ScreenLeaveLeft = new Rectangle(0, 0, 50, getHeight()); // Left edge of the screen

        if(player.getX() + player.getWidth() > screenEdge2 .getX() && player.getX() < screenEdge2.getX() + screenEdge2.getWidth()
                && player.getY() + player.getHeight() > screenEdge2.getY()
                && player.getY() < screenEdge2.getY() + screenEdge2.getHeight()) {
            player.setDx(0);
            player.setDy(0);
            if (player.getDirection().equals("Right")) {
                player.setX(player.getX() - 2);
            }
            if (player.getDirection().equals("Left")) {
                player.setX(player.getX() + 2);
            }
            if (player.getDirection().equals("Up")) {
                player.setY(player.getY() + 2);
            }
            if (player.getDirection().equals("Down")) {
                player.setY(player.getY() - 2);
            }
        }

         // Collision with labRect

        if (player.getX() + player.getWidth() > screenEdge1 .getX() && player.getX() < screenEdge1.getX() + screenEdge1.getWidth()
                && player.getY() + player.getHeight() > screenEdge1.getY()
                && player.getY() < screenEdge1.getY() + screenEdge1.getHeight()) {
            player.setDx(0);
            player.setDy(0);
            if (player.getDirection().equals("Right")) {
                player.setX(player.getX() - 2);
            }
            if (player.getDirection().equals("Left")) {
                player.setX(player.getX() + 2);
            }
            if (player.getDirection().equals("Up")) {
                player.setY(player.getY() + 2);
            }
            if (player.getDirection().equals("Down")) {
                player.setY(player.getY() - 2);
            }
        }

        if (player.getX() + player.getWidth() > labRect.getX() && player.getX() < labRect.getX() + labRect.getWidth()
                && player.getY() + player.getHeight() > labRect.getY()
                && player.getY() < labRect.getY() + labRect.getHeight()) {
            player.setDx(0);
            player.setDy(0);
            if (player.getDirection().equals("Right")) {
                player.setX(player.getX() - 2);
            }
            if (player.getDirection().equals("Left")) {
                player.setX(player.getX() + 2);
            }
            if (player.getDirection().equals("Up")) {
                player.setY(player.getY() + 2);
            }
            if (player.getDirection().equals("Down")) {
                player.setY(player.getY() - 2);
            }
        }
        
        if(player.getX() + player.getWidth() < ScreenLeaveLeft.getWidth()) { 
            player.setX(getWidth()-50); 
            player.setDx(0); 
            player.setDy(0);
            screen = "Soup"; 
            time = 0;
        }



    }

    private void drawWormsWorthProfile(Graphics g2d) {
        g2d.drawImage(WormsWorthMap.getImage(), 130, 220, 20, 20, this);

        g2d.setFont(new Font("Sans_serif", Font.BOLD, 32)); // Set a big, bold font for readability
        g2d.drawImage(new ImageIcon("border.png").getImage(), 0, 565, getWidth(), 200, this);

        g2d.drawImage(WormsWorthProfile.getImage(), 0, 555, 200, 200, this);

        message = "Erm .......... What The Sigma ?!?!?!";
        time++;
        if (time / 8 < message.length()) {
            g2d.drawString(message.substring(0, time / 5), 250, 610);
        } else {
            g2d.drawString(message, 250, 610);
        }
    
    }


    



    // DO NOT DELETE
    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub
    }

    // DO NOT DELETE
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            player.setDy(-2);
            player.setDirection("Up");
        }

        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            player.setDy(2);
            player.setDirection("Down");
        }

        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            player.setDx(-2);
            player.setDirection("Left");
        }

        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            player.setDx(2);
            player.setDirection("Right");

        }

        Rectangle labRect = new Rectangle(100, 50, 100, 200);
        
        if(e.getKeyCode() == KeyEvent.VK_Z) {
            if (screen.equals("GameArea") && player.getX() + player.getWidth() >= labRect.getX() && player.getX() <= labRect.getX() + labRect.getWidth()
                && player.getY() + player.getHeight() >= labRect.getY()
                && player.getY() <= labRect.getY() + labRect.getHeight()) {
                    
                wormtalk = true;
        }
    }

        key = e.getKeyCode();
    }

    // DO NOT DELETE
    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            player.setDy(0);

        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            player.setDy(0);

        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            player.setDx(0);

        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            player.setDx(0);

        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (screen.equals("PlayerMenu")) {
            if (e.getX() >= 200 && e.getX() <= 600 && e.getY() >= 200 && e.getY() <= 600
                    && e.getButton() == MouseEvent.BUTTON1) {
                screen = "GameArea";
                name = "TK";
            }
        }
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

}