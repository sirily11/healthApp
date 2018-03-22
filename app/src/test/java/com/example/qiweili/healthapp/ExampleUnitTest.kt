package com.example.qiweili.healthapp

import android.arch.persistence.room.Database
import android.content.Context
import android.test.mock.MockContext
import com.amitshekhar.utils.Utils
import com.example.qiweili.healthapp.Food.Food
import com.example.qiweili.healthapp.profile.FoodEntry
import com.example.qiweili.healthapp.profile.MealEntry
import com.example.qiweili.utils
import org.junit.Test

import org.junit.Assert.*
import org.mockito.Mock

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    @Test
    fun mealTest(){
        val foods = mutableListOf<FoodEntry>()
        foods.add(FoodEntry("Apple",100,100,100))
        foods.add(FoodEntry("Banana",200,100,100))
        val meal = MealEntry("0",foods,"Chicago")
        assertEquals("Total calories should be 300",300,meal.getTotalCalories())
    }

    @Test
    fun mealTest2(){
        val foods = mutableListOf<FoodEntry>()
        foods.add(FoodEntry("Apple",100,100,100))
        foods.add(FoodEntry("Apple",200,100,100))
        foods.add(FoodEntry("Apple",200,100,100))
        val meal = MealEntry("0",foods,"Chicago")
        val expected = arrayOf("Apple")
        val real = MealEntry.getArrayofFoodsName(mutableListOf(meal,meal))
        assertEquals("The array should only has one Apple",expected,real)
        assertEquals("Total calories should be 500",500,meal.getTotalCalories())
    }
}
