package co.edu.unal.un_trueque;

/**
 * Created by Jonathan on 9/22/2017.
 */

public class Product {

    private int img;
    private String name;
    private String type;

    public Product(int img, String name, String type) {
        this.img = img;
        this.name = name;
        this.type = type;
    }

    public int getId(){
        return name.hashCode();
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
