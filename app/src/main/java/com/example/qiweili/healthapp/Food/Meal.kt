package com.example.qiweili.healthapp.Food

import java.util.ArrayList

/**
 * Class for meal object. Superclass of Food.java
 * Created by Lucas on 2/13/2018.
 */

class Meal {

    //array list of foods that makes up the current meal
    private var foodList: MutableList<Food>? = null
    val foodMap : MutableMap<String,MutableList<Food>>? = null
    var mealName : String? = null
    var description : String? = null
    var timeStamp : String? = null

    //default constructor method
    constructor(time : String, mealName: String) {
        this.mealName = mealName
        this.timeStamp = time
        foodList = mutableListOf()
        foodMap?.put(timeStamp!!,foodList!!)
    }

    //constructor with a list of foods already given
    constructor(mealName: String, foodList: MutableList<Food>, time : String) {
        this.mealName = mealName
        this.foodList = foodList
        this.timeStamp = time
        foodMap?.put(timeStamp!!,foodList)
        makeDescription()
    }

    fun addFood(name: String, calories: Int, protein: Int, fats: Int) {
        val food = Food(name, calories, protein, fats)
        foodList!!.add(food)
        foodMap?.set(timeStamp!!,foodList!!)
        makeDescription()
    }

    fun deleteFood(food: Food) {
        foodList!!.remove(food)
        foodMap?.set(timeStamp!!,foodList!!)
        makeDescription()
    }

    fun getFoodList(): MutableList<Food>? {
        return foodList
    }

    fun setFoodList(foodList: ArrayList<Food>) {
        this.foodList = foodList
        makeDescription()
    }

    fun getDailyCal(time : String): Int{
        val todaysFood = foodMap?.get(timeStamp)
        var totalCal = 0
        for(i in todaysFood!!.iterator()){
            totalCal += i.calories
        }
        return totalCal
    }

    fun getCalIntake() : MutableList<Int>{
        val food = foodMap?.values
        val time = foodMap?.keys?.iterator()
        val calories = mutableListOf<Int>()
        for(i in food!!) {
            calories.add(getDailyCal(time?.next()!!))

        }
        return calories
    }

    //create a list of foods in the current meal
    private fun makeDescription() {
        for (i in 0 until foodList!!.size - 1) {
            description = description + foodList!![i].name + ", "
        }
        description = description + foodList!![foodList!!.size - 1].name
    }

    fun clearDescription() {
        description = ""
    }
}