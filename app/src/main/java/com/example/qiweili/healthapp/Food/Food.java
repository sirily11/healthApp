package com.example.qiweili.healthapp.Food;

/**
 * Created by Lucas on 2/13/2018.
 * Class for food items
 */

public class Food {


    private String name;
    private int calories;
    private int protein;
    private int fats;

    protected Food(String name, int calories, int protein, int fats) {
        this.name = name;
        this.calories = calories;
        this.protein = protein;
        this.fats = fats;
    }

    public String getName(){
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public int getProtein() {
        return protein;
    }

    public void setProtein(int protein) {
        this.protein = protein;
    }

    public int getFats() {
        return fats;
    }

    public void setFats(int fats) {
        this.fats = fats;
    }
}