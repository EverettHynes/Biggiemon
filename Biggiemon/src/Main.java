
import java.awt.*;
import javax.swing.*;

public class Main extends JFrame {
    public static final int WIDTH = 1200;
    public static final int HEIGHT = 800;

    public Main() {
        super("BIGGIE MON");
        setSize(WIDTH, HEIGHT);
        Game play = new Game();
        ((Component) play).setFocusable(true);

        Color RoyalBlue = new Color(22, 13, 193);

        setBackground(RoyalBlue);
        
        getContentPane().add(play);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        Main run = new Main();
    }
}
