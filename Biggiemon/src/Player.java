
import javax.swing.ImageIcon;

public class Player {
    private String name;
    private int x;
    private int y;
    private int width;
    private int height;
    private int dx;
    private int dy;
    private String direction;


    private ImageIcon picP;

    public Player(String name, int x, int y, int width, int height, ImageIcon picP, String direction) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.picP = picP;

        this.dx = 0;
        this.dy = 0;

        this.direction = direction;

    }



    public String getName() {
        return name;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getDx() {
        return dx;
    }

    public void setDx(int dx) {
        this.dx = dx;
    }

    public int getDy() {
        return dy;
    }

    public void setDy(int dy) {
        this.dy = dy;
    }

    public ImageIcon getPicP() {
        return picP;
    }

    public void setPicP(ImageIcon picP) {
        this.picP = picP;
    }

    public void move() {
        x += dx;
        y += dy;
    }

}

