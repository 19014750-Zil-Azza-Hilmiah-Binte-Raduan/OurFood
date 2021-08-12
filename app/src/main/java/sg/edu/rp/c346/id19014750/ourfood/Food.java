package sg.edu.rp.c346.id19014750.ourfood;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Food implements Serializable {

    private int id;
    private String name;
    private String location;
    private float price;
    private int stars;

    public Food(int id, String name, String location, float price, int stars) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.price = price;
        this.stars = stars;

    }

    public int getId() {
        return id;
    }

    public Food setId(int id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Food setName(String name) {
        this.name = name;
        return this;
    }

    public String getLocation() {
        return location;
    }

    public Food setLocation(String location) {
        this.location = location;
        return this;
    }

    public float getPrice() {
        return price;
    }

    public Food setPrice(float price) {
        this.price = price;
        return this;
    }

    public int getStars() {
        return stars;
    }

    public Food setStars(int stars) {
        this.stars = stars;
        return this;
    }

    @NonNull
    @Override
    public String toString() {
        String starsString = "";
        for(int i = 0; i < stars; i++){
            starsString += "*";
        }
        return name + "\n" + location + " - " + price + "\n" + starsString;

    }
}