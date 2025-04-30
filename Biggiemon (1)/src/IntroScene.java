import javax.swing.ImageIcon;

public class IntroScene {

    int Order;
    ImageIcon picture;
    String dialouge1, dialouge2, dialouge3;

    public IntroScene(int Order, ImageIcon picture ,String dialouge1, String dialouge2, String dialouge3){
        this.Order = Order;
        this.picture = picture;
        this.dialouge1 = dialouge1;
        this.dialouge2 = dialouge2;
        this.dialouge3 = dialouge3;
    }

    public int getOrder() {
        return Order;
    }

    public void setOrder(int order) {
        Order = order;
    }

    public ImageIcon getPicture() {
        return picture;
    }

    public void setPicture(ImageIcon picture) {
        this.picture = picture;
    }

    public String getDialouge1() {
        return dialouge1;
    }

    public void setDialouge1(String dialouge1) {
        this.dialouge1 = dialouge1;
    }

    public String getDialouge2() {
        return dialouge2;
    }

    public void setDialouge2(String dialouge2) {
        this.dialouge2 = dialouge2;
    }

    public String getDialouge3() {
        return dialouge3;
    }

    public void setDialouge3(String dialouge3) {
        this.dialouge3 = dialouge3;
    }

    
}
