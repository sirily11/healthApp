package com.example.qiweili.healthapp

import com.example.qiweili.healthapp.health.Calculate_Cals
import com.example.qiweili.healthapp.Food.FoodEntry
import com.example.qiweili.healthapp.Food.MealEntry
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    @Test
    fun mealTest(){
        val foods = mutableListOf<FoodEntry>()
        foods.add(FoodEntry("Apple", 100, 100, 100))
        foods.add(FoodEntry("Banana", 200, 100, 100))
        val meal = MealEntry("0", foods, "Chicago")
        assertEquals("Total calories should be 300",300,meal.getTotalCalories())
    }

    @Test
    fun mealTest2(){
        val foods = mutableListOf<FoodEntry>()
        foods.add(FoodEntry("Apple", 100, 100, 100))
        foods.add(FoodEntry("Apple", 200, 100, 100))
        foods.add(FoodEntry("Apple", 200, 100, 100))
        val meal = MealEntry("0", foods, "Chicago")
        val expected = arrayOf("Apple")
        val real = MealEntry.getArrayofFoodsName(mutableListOf(meal,meal))
        assertEquals("The array should only has one Apple",expected,real)
        assertEquals("Total calories should be 500",500,meal.getTotalCalories())
    }

    @Test
    fun cal_intakeTest(){
        val meals = mutableListOf<MealEntry>()
        val foods = mutableListOf<FoodEntry>()
        foods.add(FoodEntry("Apple", 100, 100, 100))
        foods.add(FoodEntry("Water", 200, 100, 100))
        foods.add(FoodEntry("Juice", 300, 100, 100))
        meals.add(MealEntry("27/3/2018", foods, "Meal1"))
        meals.add(MealEntry("27/3/2018", foods, "Meal2"))
        var cals = Calculate_Cals(meals).getCals_intake()
        assertEquals("Total cals inake at 27/3/2018 should be",1200,cals[0].data)
        meals.add(MealEntry("28/3/2018", foods, "Meal3"))
        cals = Calculate_Cals(meals).getCals_intake()
        assertEquals("Total cals intake at 28/3/2018 should be",600,cals[1].data)
        assertEquals("Total cals inake at 27/3/2018 should be",1200,cals[0].data)
    }


}
