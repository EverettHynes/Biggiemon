import javax.swing.ImageIcon;


    public class biggie{
        private String name;
        private String screen;

        private int x;
        private int y;
    
        private int width;
        private int height;

        private ImageIcon picP;
        private ImageIcon picF;
        
        
        private int health;
        private int attack;
        private int defense;

        private boolean isAlive;
        private boolean inBattle;

    
        public biggie(String name, String screen,int x, int y, int width, int height, ImageIcon picB, ImageIcon picF, int health, int attack, int defense, boolean isAlive, boolean inBattle) {
            this.name = name;
            this.screen = screen;
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            this.picP = picP;
            this.picF = picF;
            this.health = health;
            this.attack = attack;
            this.defense = defense;
            this.isAlive = isAlive;
            this.inBattle = inBattle;
     
        }
    
        public String getName() {
            return name;
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
    
        public ImageIcon getPicP() {
            return picP;
        }
    
        public void setPicP(ImageIcon picP) {
            this.picP = picP;
        }

    public ImageIcon getPicF() {
        return picF;
    }

    public void setPicF(ImageIcon picF) {
        this.picF = picF;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getDefense() {
        return defense;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public boolean isIsAlive() {
        return isAlive;
    }

    public void setIsAlive(boolean isAlive) {
        this.isAlive = isAlive;
    }

    public boolean isInBattle() {
        return inBattle;
    }

    public void setInBattle(boolean inBattle) {
        this.inBattle = inBattle;
    }
    
    
    
}

