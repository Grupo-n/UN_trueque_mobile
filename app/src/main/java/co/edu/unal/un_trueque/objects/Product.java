package co.edu.unal.un_trueque.objects;

import java.io.Serializable;

/**
 * Created by Jonathan on 9/22/2017.
 */

public class Product implements Serializable{

    private int img;
    private String id;
    private String name;
    private String type;
    private String description;

    public Product(int img, String name, String type, String description) {
        this.img = img;
        this.name = name;
        this.type = type;
        this.description = description;
    }

    public Product(int img, String name, String type, String description, String id) {
        this.img = img;
        this.name = name;
        this.type = type;
        this.description = description;
        this.id = id;
    }


    public String getId(){
        return this.id;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
