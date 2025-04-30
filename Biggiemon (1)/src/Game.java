import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Timer;
import javax.swing.*;

public class Game extends JPanel implements Runnable, KeyListener, MouseListener {
    private BufferedImage back;
    private int key;

    private int time = 0;
    private Timer animationTimer;

    //Strings / Messagse

    private String screen;
    private String message;


    //IntroScenes
    private IntroScene Chef1;
    private ArrayList<IntroScene> introScenes;
    
    private int introTime = 0;
    private int sceneIndex = 1;
    private float alpha = 0.01f;


    //Backgrounds
    private ImageIcon backgroundMain;
    private ImageIcon backgroundSoup;
    private ImageIcon backgroundSpaghetti;
    private ImageIcon backgroundSushi;
    private ImageIcon backgroundPancakes;


    //NPCs
    private ImageIcon WormsWorthMap;
    private ImageIcon WormsWorthProfile;
    public boolean wormtalk;


    //Player Stuff
    private Player player;
    private ImageIcon PlayerPic;
    private String name;

    public Game() {
        setFocusable(true);
        requestFocusInWindow();
        new Thread(this).start();
        this.addKeyListener(this);
        this.addMouseListener(this);

        key = -1;
        
        screen = "Intro";

        //IntroScenes
        introScenes = new ArrayList<>();
        Chef1 = new IntroScene(1, new ImageIcon("EVILCHEF.png"), "There was an Evil Chef who used Dark Magic to create amazing dishes", 
        "One of these dishes was a pizza with an entire world contained apon it", 
        "It had crazy ingredients, dangerous dishes, and even monsters made of food.");

        introScenes.add(Chef1);



        // Backgrounds
        backgroundMain = new ImageIcon("Backgrounds//BackgroundIceCream.png");
        backgroundSoup = new ImageIcon("Backgrounds//BackgroundHotSprings.png");
        backgroundSpaghetti = new ImageIcon("Backgrounds//BackgroundSpaghetti.png");
        backgroundSushi = new ImageIcon("Backgrounds//BackgroundSushi.png");
        backgroundPancakes = new ImageIcon("Backgrounds//BackgroundPancakes.png");


        //NPCs
        WormsWorthMap = new ImageIcon("Wormsworth house.png");
        WormsWorthProfile = new ImageIcon("Womrsworth.png");  
        wormtalk = false; 

        //Biggies
        // will go here
        //Biggie1 = new Biggie("Biggie1", "GameArea", 100, 100, 50, 50, new ImageIcon("Biggie1.png"), new ImageIcon("Biggie1.png"), 100, 10, 10, true, false);


        //Player Stuff
        player = new Player(name, 540, 350, 58, 96, new ImageIcon("TK_DownFace.png"), "Down");
        PlayerPic = new ImageIcon("TK_DownFace.png");
        name = "Player";
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
            case "Intro" -> {
                introTime++;


                for(IntroScene scene : introScenes){
                    if(scene.getOrder() == sceneIndex){

                        ((Graphics2D) g2d).setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
                        
                        if(alpha < 1.0f){
                            alpha+= 0.001f;
                        } else if(alpha >= 1.0f){
                            alpha = 1.0f;
                        }

                        g2d.drawImage(scene.getPicture().getImage(), 0, 0, getWidth(), getHeight(), this);

                        if(introTime / 5 < scene.getDialouge1().length()){
                            g2d.drawString(scene.getDialouge1().substring(0, introTime / 5), 100, 100);
                        } else if(introTime / 5 < scene.getDialouge1().length() + scene.getDialouge2().length()){
                            g2d.drawString(scene.getDialouge1(), 100, 100);
                            g2d.drawString(scene.getDialouge2().substring(0, introTime / 5 - scene.getDialouge1().length()), 100, 150);
                        } else if(introTime / 5 < scene.getDialouge1().length() + scene.getDialouge2().length() + scene.getDialouge3().length()){
                            g2d.drawString(scene.getDialouge1(), 100, 100);
                            g2d.drawString(scene.getDialouge2(), 100, 150);
                            g2d.drawString(scene.getDialouge3().substring(0, introTime / 5 - scene.getDialouge1().length() - scene.getDialouge2().length()), 100, 200);
                        } else {
                            g2d.drawString(scene.getDialouge1(), 100, 100);
                            g2d.drawString(scene.getDialouge2(), 100, 150);
                            g2d.drawString(scene.getDialouge3(), 100, 200);
                            }
                        }
                    }

                    if(sceneIndex == introScenes.size()){
                        screen = "IceCream";
                    }
                }
            
            case "PlayerMenu" -> {
                g2d.clearRect(0, 0, getWidth(), getHeight());

                g2d.drawString("Click to start", 100, 100);


            }
            case "IceCream" -> {
                g2d.clearRect(0, 0, getWidth(), getHeight());

                g2d.drawImage(backgroundMain.getImage(), 0, 0, getWidth(), getHeight(), this);

                checkCollisionIceCream(g2d); 

                if(wormtalk){
                    drawWormsWorthProfile(g2d);
                }
                
                
                
                drawPlayer(g2d);

            }

            case "HotSprings" -> {
                g2d.clearRect(0, 0, getWidth(), getHeight());
                
                g2d.drawImage(backgroundSoup.getImage(), 0, 0, getWidth(), getHeight(), this);

                checkCollisionHotSprings(g2d);

                
                drawPlayer(g2d);

            }

            case "Spaghetti" -> {
                g2d.clearRect(0, 0, getWidth(), getHeight());

                g2d.drawImage(backgroundSpaghetti.getImage(), 0, 0, getWidth(), getHeight(), this);

                checkCollisionSpaghetti();

                drawPlayer(g2d);
            }

            case "Sushi" -> {
                g2d.clearRect(0, 0, getWidth(), getHeight());

                g2d.drawImage(backgroundSushi.getImage(), 0, 0, getWidth(), getHeight(), this);



                checkCollisionSushi(g2d);

                drawPlayer(g2d);
            
            }

            case "Pancakes" -> {
                g2d.clearRect(0, 0, getWidth(), getHeight());

                g2d.drawImage(backgroundPancakes.getImage(), 0, 0, getWidth(), getHeight(), this);


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
        g2d.drawImage(player.getPicP().getImage(), player.getX(), player.getY(), player.getWidth(), player.getHeight(),null);
        player.move();

        long currentTime = System.currentTimeMillis() / 200;

        if(player.getDirection().equals("Left")){
            player.setPicP(new ImageIcon(player.getDx() == 0 ? "TK sprites\\TK_LeftStill.png"
                    : (currentTime % 2 == 0
                            ? "TK sprites\\TK_LeftFrame1.png"
                            : "TK sprites\\TK_LeftFrame2.png")));
        }

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

    public void checkCollisionIceCream(Graphics g2d) {
        
        g2d.setColor(Color.red);
        g2d.drawRect(300,600,800,250);
        g2d.drawRect(900,500,800,250);
        g2d.drawRect(0,650,300,250);
        g2d.drawRect(0,500,50,200);

        Rectangle screenEdge1 = new Rectangle(0,650, 300, 250);
        Rectangle screenEdge2 = new Rectangle(300,600,600,250);
        Rectangle screenEdge3 = new Rectangle(900, 500, 300, 250); 
        Rectangle screenEdge4 = new Rectangle(0,500,50,200);
       
        Collision(screenEdge1);
        Collision(screenEdge2); 
        Collision(screenEdge3);
        Collision(screenEdge4);
        
        if(player.getX() < 10) { 
            player.setX(1000); 
            player.setDx(0); 
            player.setDy(0);
            screen = "HotSprings"; 
            time = 0;
        }

                
        if(player.getY() < 10) { 
            player.setY(700); 
            player.setDx(0); 
            player.setDy(0);
            screen = "Spaghetti"; 
            time = 0;
        }

    }

    public void checkCollisionHotSprings(Graphics g2d) {

        g2d.drawRect(0,0,500,200);
        g2d.drawRect(700,0,600,200);
        g2d.drawRect(0,485,getWidth(), 300);
        g2d.drawRect(0,0,10,800);

        Rectangle screenEdge1 = new Rectangle(0,0, 500, 200);
        Rectangle screenEdge2 = new Rectangle(700,0,600,200);
        Rectangle screenEdge3 = new Rectangle(0,480,getWidth(), 300);
        Rectangle screenEdge4 = new Rectangle(0,0,10,800);

        Collision(screenEdge1);
        Collision(screenEdge2);
        Collision(screenEdge3);
        Collision(screenEdge4);

        if(player.getX() + player.getWidth() > 1200-10) { 
            player.setX(50); 

            screen = "IceCream"; 
            time = 0;
        }

        if(player.getY() < 10) { 
            player.setY(700); 

            screen = "Sushi"; 
            time = 0;
        }
    }

    public void checkCollisionSushi(Graphics g2d) {

        g2d.drawRect(0,50,320,300);
        g2d.drawRect(310,200,600,10);
        g2d.drawRect(700,255,600,10);
        g2d.drawRect(0,0,80,800);

        Rectangle screenEdge1 = new Rectangle(0,50, 320, 300);
        Rectangle screenEdge2 = new Rectangle(310,200,600,10);
        Rectangle screenEdge3 = new Rectangle(700,255,600,10);
        Rectangle screenEdge4 = new Rectangle(0,0,80,800);

        Collision(screenEdge1);
        Collision(screenEdge2);
        Collision(screenEdge3);
        Collision(screenEdge4);

        if(player.getX() + player.getWidth() > getWidth()-10) { 
            player.setX(50); 
            player.setY(400);
            screen = "Spaghetti"; 
            time = 0;
        }

        if(player.getY() > getHeight()-10 ) { 
            player.setY(50); 
            player.setX(600);
            screen = "HotSprings"; 
            time = 0;
        }
    }

    public void checkCollisionSpaghetti(){
        
        if(player.getY() > getHeight()-10) { 
            player.setY(50); 
            player.setDx(0); 
            player.setDy(0);
            screen = "IceCream"; 
            time = 0;
        }

        if(player.getX() < 10) { 
            player.setX(1100); 
            player.setDx(0); 
            player.setDy(0);
            screen = "Sushi"; 
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

    
    }

    public void Collision(Rectangle rect){
        if (player.getX() + player.getWidth() > rect.getX() && player.getX() < rect.getX() + rect.getWidth()
                && player.getY() + player.getHeight() > rect.getY()
                && player.getY() < rect.getY() + rect.getHeight()) {
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
            player.setDy(-5);
            player.setDirection("Up");
        }

        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            player.setDy(5);
            player.setDirection("Down");
        }

        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            player.setDx(-5);
            player.setDirection("Left");
        }

        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            player.setDx(5);
            player.setDirection("Right");

        }

        Rectangle labRect = new Rectangle(100, 50, 100, 200);
        
        if(e.getKeyCode() == KeyEvent.VK_Z) {
            if (screen.equals("GameArea") && player.getX() + player.getWidth() >= labRect.getX() && player.getX() <= labRect.getX() + labRect.getWidth()
                && player.getY() + player.getHeight() >= labRect.getY()
                && player.getY() <= labRect.getY() + labRect.getHeight()) {
                    
                wormtalk = !wormtalk;
        }

   
    }


    if(e.getKeyCode() == KeyEvent.VK_Z) {
        if (screen.equals("Intro")) {
            if (introTime / 5 >= Chef1.getDialouge1().length() + Chef1.getDialouge2().length() + Chef1.getDialouge3().length()) {
                sceneIndex++;
                introTime = 0;
            } else{
                introTime = 2000;
            }
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