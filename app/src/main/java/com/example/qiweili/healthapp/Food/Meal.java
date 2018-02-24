package com.example.qiweili.healthapp.Food;

import java.util.ArrayList;

/**
 * Class for meal object. Superclass of Food.java
 * Created by Lucas on 2/13/2018.
 */

public class Meal {

    //array list of foods that makes up the current meal
    private ArrayList<Food> foodList;
    private String mealName = "";
    private String description = "";

    //default constructor method
    public Meal(String mealName) {
        this.mealName = mealName;
        foodList = new ArrayList<>();
    }

    //constructor with a list of foods already given
    public Meal(String mealName, ArrayList<Food> foodList) {
        this.mealName = mealName;
        this.foodList = foodList;
        makeDescription();
    }

    public void addFood(String name, int calories, int protein, int fats) {
        Food food = new Food(name, calories, protein, fats);
        foodList.add(food);
        makeDescription();
    }

    public void deleteFood(Food food) {
        foodList.remove(food);
        makeDescription();
    }

    public ArrayList<Food> getFoodList() {
        return foodList;
    }

    public void setFoodList(ArrayList<Food> foodList) {
        this.foodList = foodList;
        makeDescription();
    }

    //create a list of foods in the current meal
    private void makeDescription() {
        for (int i = 0; i < foodList.size() - 1; i++) {
            description = description + foodList.get(i).getName() + ", ";
        }
        description = description + foodList.get(foodList.size() - 1).getName();
    }

    public String getDescription() {
        return description;
    }

    public void clearDescription() {
        description = "";
    }

    public String getMealName() {
        return mealName;
    }

    public void setMealName(String mealName) {
        this.mealName = mealName;
    }
}