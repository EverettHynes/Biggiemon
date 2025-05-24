import javax.swing.ImageIcon;


    public class Biggie{
        private String name;
        private String Screen;



        private int x;
        private int y;
    
        private int width;
        private int height;

        private ImageIcon pic;

        
        
        private int health;
        private int attack;
        private int defense;

        private boolean isAlive;
        private boolean inBattle;

    
        public Biggie(String name, String Screen,int x, int y, int width, int height, ImageIcon pic, int health, int attack, int defense, boolean isAlive, boolean inBattle) {
            this.name = name;
            this.Screen = Screen;
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            this.pic = pic;
            this.health = health;
            this.attack = attack;
            this.defense = defense;
            this.isAlive = isAlive;
            this.inBattle = inBattle;
     
        }

        public String getScreen() {
            return Screen;
        }

        public void setScreen(String Screen) {
            this.Screen = Screen;
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
    
        public ImageIcon getPic() {
            return pic;
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

