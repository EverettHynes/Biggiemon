import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import javax.swing.*;

public class Game extends JPanel implements Runnable, KeyListener, MouseListener {
    private BufferedImage back;
    private int key;
    
    private Random random = new Random();
    private int lastMove = 1;

    private int time = 0;
    private Timer animationTimer;



    //Strings / Messagse
    private String screen;
    private String message;


    //IntroScenes
    private IntroScene Chef1;
    private IntroScene Shelf2;
    private IntroScene Falling3;
    private IntroScene Black4;
    private IntroScene Eyes5;

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
    private ImageIcon backgroundBakery;
    private ImageIcon backgroundBattle;

    private ImageIcon healthImage;

    //NPCs
    private ImageIcon WormsWorthMap;
    private ImageIcon WormsWorthProfile;
    public boolean wormtalk;

    //Biggies
    private Biggie IceCube;
    private Biggie Hamburger;
    private ArrayList<Biggie> Biggies = new ArrayList<>();
    private ArrayList<Biggie> Inventory = new ArrayList<>();
    private boolean inInventory = false;
    private boolean inBattle = false;

    //Enemy
    private Enemy cat;
    private Enemy ice; 
    private ArrayList<Enemy> Enemies = new ArrayList<>();


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
        Chef1 = new IntroScene(1, new ImageIcon("Intros//EVILCHEF.png"), "There was an Evil Chef who used Dark Magic to create amazing dishes", 
        "One of these dishes was a pizza with an entire world contained apon it", 
        "It had crazy ingredients, dangerous dishes, and even monsters made of food.");

        Shelf2 = new IntroScene(2, new ImageIcon("Intros\\IntroSceneJar.png"), "This Evil Chef even used terrible ingredients such as humans he would keep trapped and shrunk down.",
         "You are one of these humans he has trapped on his shelf waiting to be used in some terrible magic dish", "But you try to escape ....");

        Falling3 = new IntroScene(3, new ImageIcon("Intros//IntroSceneFalling.png"), "You fall off the shelf and land in a world of food",
        "You are now in a world of food and you must escape the Evil Chef's evil clutches", "" );

        Black4 = new IntroScene(4, new ImageIcon("Intros//Black.png"), "You loose consciousness", "You are now in a world of food", "" );
        
        Eyes5 = new IntroScene(5, new ImageIcon("Intros//Eyes.gif"), "", "", "" );

        introScenes.add(Chef1);
        introScenes.add(Shelf2);
        introScenes.add(Falling3);
        introScenes.add(Black4);
        introScenes.add(Eyes5);
    


        //Enemies
        cat = new Enemy("Hampurrgur", 100, 100, 200, 200, new ImageIcon("Hampurrgur.png"), 50, 10, 0, false);
        ice = new Enemy("Icecubert", 100, 100, 200, 200, new ImageIcon("IceCubeFront.png"), 50, 10, 0, false);
        Enemies = new ArrayList<>();

        // Backgrounds
        backgroundMain = new ImageIcon("Backgrounds//BackgroundIceCream.png");
        backgroundSoup = new ImageIcon("Backgrounds//BackgroundHotSprings.png");
        backgroundSpaghetti = new ImageIcon("Backgrounds//BackgroundSpaghetti.png");
        backgroundSushi = new ImageIcon("Backgrounds//BackgroundSushi.png");
        backgroundPancakes = new ImageIcon("Backgrounds//BackgroundPancakes.png");
        backgroundBakery = new ImageIcon("Backgrounds//BackgroundBakery.png");
        backgroundBattle = new ImageIcon("Backgrounds//BackgroundBattle.png");
        healthImage = new ImageIcon("Health.png");


        //NPCs
        WormsWorthMap = new ImageIcon("Wormsworth house.png");
        WormsWorthProfile = new ImageIcon("Womrsworth.png");  
        wormtalk = false; 

        //Biggies
        IceCube = new Biggie("IceCubert", "HotSprings", 50, 300, 150, 150, new ImageIcon("IceCubeFront.png"), 100, 10, 10, true, false);
        Hamburger = new Biggie("Hampurrgur", "Spaghetti",500, 150, 150, 150, new ImageIcon("Hampurrgur.png"), 100, 10, 10, true, false);
        Biggies.add(Hamburger);
        Biggies.add(IceCube);

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
               g2d.clearRect(0,0, WIDTH, HEIGHT);
               
                introTime++;


                for(IntroScene scene : introScenes){
                    if(scene.getOrder() == sceneIndex){

                        ((Graphics2D) g2d).setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
                        
                        Font customFont = new Font("Serif", Font.BOLD, 30);
                        g2d.setFont(customFont);
                        g2d.setColor(Color.cyan);

                        alpha = Math.min(alpha + 0.001f, 1.0f);

                        g2d.drawImage(scene.getPicture().getImage(), 0, 0, getWidth(), getHeight(), this);

                        if(introTime / 5 < scene.getDialouge1().length()){
                            g2d.drawString(scene.getDialouge1().substring(0, introTime / 5), 10, 620);
                        } else if(introTime / 5 < scene.getDialouge1().length() + scene.getDialouge2().length()){
                            g2d.drawString(scene.getDialouge1(), 100, 100);
                            g2d.drawString(scene.getDialouge2().substring(0, introTime / 5 - scene.getDialouge1().length()), 10, 670);
                        } else if(introTime / 5 < scene.getDialouge1().length() + scene.getDialouge2().length() + scene.getDialouge3().length()){
                            g2d.drawString(scene.getDialouge1(), 100, 100);
                            g2d.drawString(scene.getDialouge2(), 100, 150);
                            g2d.drawString(scene.getDialouge3().substring(0, introTime / 5 - scene.getDialouge1().length() - scene.getDialouge2().length()), 100, 720);
                        } else {
                            g2d.drawString(scene.getDialouge1(), 10, 620);
                            g2d.drawString(scene.getDialouge2(), 10, 670);
                            g2d.drawString(scene.getDialouge3(), 10, 720);
                            }
                        }
                    }

                    if(sceneIndex == introScenes.size()+1){
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
                checkCollisionPancakes(g2d);

                drawPlayer(g2d);
            
            }
        
            case "Bakery" -> {
                g2d.clearRect(0, 0, getWidth(), getHeight());

                g2d.drawImage(backgroundBakery.getImage(), 0, 0, getWidth(), getHeight(), this);
                checkCollisionBakery(g2d);

                drawPlayer(g2d);
            }

            case "Battle" -> {
                g2d.clearRect(0, 0, getWidth(), getHeight());
                
                g2d.drawImage(backgroundBattle.getImage(), 0, 0, getWidth(), getHeight(), this);    
            
                //draws biggeis
                g2d.drawImage(Inventory.get(0).getPic().getImage(), 120, 200, 200 ,200, this);
                g2d.drawImage(Enemies.get(0).getPic().getImage(), 765, 30, 200 ,200, this);

                //draws your Inventory
                g2d.drawImage(healthImage.getImage(),25, 125,Inventory.get(0).getHealth()*5 , 50, this);
                g2d.drawString("Attack: " + Inventory.get(0).getAttack(), 25, 20);
                g2d.drawString("Defence: " + Inventory.get(0).getDefense(), 25, 40);
                g2d.drawString("Health: " + Inventory.get(0).getHealth(), 25, 60);
    
                //draws enemy stats
                g2d.drawImage(healthImage.getImage(), 650, 350, Enemies.get(0).getHealth()*5 , 50, this);
                g2d.drawString("Attack: " + Enemies.get(0).getAttack(), 650, 420);
                g2d.drawString("Defence: " + Enemies.get(0).getDefense(), 650, 440);
                g2d.drawString("Health: " + Enemies.get(0).getHealth(), 650, 460);

                switch (lastMove) {
                    case 1 -> g2d.drawString("Attack", 40, 620);
                    case 2 -> g2d.drawString("Raise Defense", 110, 620);
                    case 3 -> g2d.drawString("Raise Attack", 260, 620);
                }



                
                
                if(Enemies.get(0).isTurn==true){
                    Random localRandom = new Random();
                    int randomChoice = localRandom.nextInt(3); // Generates a new random number each time
                    



                    //Handels Enemy Turn
                    if(randomChoice == 0){
                        Inventory.get(0).setHealth(Inventory.get(0).getHealth() - (Enemies.get(0).getAttack() - Inventory.get(0).getDefense()));
                        Enemies.get(0).setIsTurn(!Enemies.get(0).isIsTurn());
                    }
                    if(randomChoice == 1){
                        Enemies.get(0).setDefense(Enemies.get(0).getDefense()+5);
                        Enemies.get(0).setIsTurn(!Enemies.get(0).isIsTurn());
                    } 
                    if(randomChoice == 2){
                        Enemies.get(0).setAttack(Enemies.get(0).getAttack()+5);
                        Enemies.get(0).setIsTurn(!Enemies.get(0).isIsTurn());
                    }
                }

                if(Enemies.get(0).getHealth() <= 0){
                    if(Enemies.get(0).getName().equals("Hampurrgur")){
                        screen = "Spaghetti";
                    } 
                }

                if(Inventory.get(0).getHealth() <= 0){

                    screen = "GameOver";
                }
      


                g2d.drawRect(30, 600, 100, 50);
                g2d.drawRect(140, 600, 100, 50);
                g2d.drawRect(250, 600, 100, 50);


          
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

        drawBiggies(g2d);

        drawInventory(g2d);

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
            screen = "HotSprings"; 
            time = 0;
        }

        if(player.getX() > 1190) { 
            player.setX(50);
            player.setY(400); 
            screen = "Bakery"; 
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

        if(player.getX() > getWidth()-10) { 
            player.setX(50); 
            player.setDx(0); 
            player.setDy(0);
            screen = "Pancakes"; 
            time = 0;
        }
    }

    public void checkCollisionPancakes(Graphics g2d) {
        if(player.getX() < 10) { 
            player.setX(1100); 
            player.setY(400);
            screen = "Spaghetti"; 
            time = 0;
        }

        if(player.getY() > getHeight()-10) { 
            player.setY(50);
            player.setX(600);
            screen = "Bakery"; 
            time = 0;
        }

    }

    public void checkCollisionBakery(Graphics g2d) {
        if(player.getX() < 10) { 
            player.setX(1100); 
            player.setY(400);
            screen = "IceCream"; 
            time = 0;
        }

        if(player.getY() < 10) { 
            player.setY(50); 
            screen = "Pancakes"; 
            time = 0;
        }
    }   

    //this isnt drawn in screen its drawn in the canvas directly
    public void drawBiggies(Graphics g2d) {
        for(Biggie b : Biggies){
            if(screen.equals(b.getScreen())){
                g2d.drawImage(b.getPic().getImage(), b.getX(), b.getY(), b.getWidth(), b.getHeight(), this);

                Rectangle collision = new Rectangle(b.getX(), b.getY(), b.getWidth(), b.getHeight());
                Collision(collision);
            }
        }
    }

    public void drawInventory(Graphics g2d) {
        int xOffset = 0;
        g2d.clearRect(0, 0, WIDTH, HEIGHT);
        for (Biggie i : Inventory) {
            if (inInventory) {
                int yOffset = 0;
                if (100 + xOffset + 50 > getWidth()) { // Check if the next item exceeds the panel's width
                    xOffset = 0; // Reset xOffset to start a new row
                    yOffset += 150; // Move to the next row
                }
                g2d.drawImage(i.getPic().getImage(), 100 + xOffset, 100 + yOffset, i.getWidth(), i.getHeight(), this);
                g2d.drawString(i.getName(), 100 + xOffset, 100 + yOffset + 50);
                g2d.drawString("Health: " + i.getHealth(), 100 + xOffset, 100 + yOffset + 70);
                g2d.drawString("Attack: " + i.getAttack(), 100 + xOffset, 100 + yOffset + 90);
                g2d.drawString("Defense: " + i.getDefense(), 100 + xOffset, 100 + yOffset + 110);

                xOffset += 200;
            }
        }
    }

    public void Collision(Rectangle rect){
        if (player.getX() + player.getWidth() > rect.getX() && player.getX() < rect.getX() + rect.getWidth()
                && player.getY() + player.getHeight() > rect.getY()
                && player.getY() < rect.getY() + rect.getHeight()) {
            player.setDx(0);
            player.setDy(0);
            if (player.getDirection().equals("Right")) {
                player.setX(player.getX() - 4);
            }
            if (player.getDirection().equals("Left")) {
                player.setX(player.getX() + 4);
            }
            if (player.getDirection().equals("Up")) {
                player.setY(player.getY() + 4);
            }
            if (player.getDirection().equals("Down")) {
                player.setY(player.getY() - 4);
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
        for(Biggie b : Biggies){
            if(screen.equals(b.getScreen())){
                if (player.getX() + player.getWidth() >= b.getX()-10 && player.getX() <= b.getX()-10 + b.getWidth()+20
                    && player.getY() + player.getHeight() >= b.getY()-10
                    && player.getY() <= b.getY()-10 + b.getHeight()+20) {
                    
                        Inventory.add(b);


                        if(screen.equals("Spaghetti")){
                            screen = "Battle";
                            Enemies.add(cat);
                        }




                }
            }
        }
    }

    if(e.getKeyCode() == KeyEvent.VK_I) {
        inInventory = !inInventory;
    }

    if(e.getKeyCode() == KeyEvent.VK_Z) {
        if (screen.equals("Intro")) {
            if (introTime / 5 >= Chef1.getDialouge1().length() + Chef1.getDialouge2().length() + Chef1.getDialouge3().length()) {
                sceneIndex++;
                introTime = 0;
            } else{
                introTime = 5000;
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
        if(screen.equals("Battle")){
            if(Enemies.get(0).isTurn == false){


                Rectangle box1= new Rectangle(30, 600, 100, 50);
                Rectangle box2 = new Rectangle(140, 600, 100, 50);
                Rectangle box3= new Rectangle(250, 600, 100, 50);

                if(box1.contains(e.getPoint()) && e.getButton() == MouseEvent.BUTTON1){
                    Enemies.get(0).setHealth(Enemies.get(0).getHealth() - (Inventory.get(0).getAttack() - Enemies.get(0).getDefense()));
                    Enemies.get(0).setIsTurn(!Enemies.get(0).isIsTurn());
                    lastMove = 1;
                } 
                if(box2.contains(e.getPoint()) && e.getButton() == MouseEvent.BUTTON1){
                    Inventory.get(0).setDefense(Inventory.get(0).getDefense()+5);
                    Enemies.get(0).setIsTurn(!Enemies.get(0).isIsTurn());
                    lastMove = 2;
                }  
                if(box3.contains(e.getPoint()) && e.getButton() == MouseEvent.BUTTON1){
                    Inventory.get(0).setAttack(Inventory.get(0).getAttack()+5);
                    Enemies.get(0).setIsTurn(!Enemies.get(0).isIsTurn());
                    lastMove = 3;
                }
            }
        }
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