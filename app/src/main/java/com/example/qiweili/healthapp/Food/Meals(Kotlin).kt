package com.example.qiweili.healthapp.Food

import android.content.Context
import android.widget.ArrayAdapter

class MealEntry(var time: String, var foods: MutableList<FoodEntry>, var description: String) {

    /**
     * Helper functions for Meal
     */
    companion object {
        /**
         * Get a string version of string adapter
         * @param array The array which needs a adapter
         * @param context Context
         * @param resourceID The ID for Autocomplete Layout
         * @return a Array Adapter
         */
        fun getStringArrayAdapter(array: Array<String>, context: Context, resourceID: Int): ArrayAdapter<String> {
            val arrayAdapter = ArrayAdapter<String>(context, resourceID, array)
            return arrayAdapter
        }

        /**
         * Get Array of food's name
         * @param meals Meal list
         * @return All the food's name in that Meal list
         */
        fun getArrayofFoodsName(meals: MutableList<MealEntry>): Array<String> {
            val strings = mutableListOf<String>()
            for (i in meals) {
                for (j in i.foods) {
                    if(containsBefore(strings, j.name)){
                        continue
                    }
                    strings.add(j.name)
                }
            }
            return strings.toTypedArray()
        }
        /**
         * Get Array of Meal's name
         * @param meals Meal list
         * @return All the Meal's name in that Meal list
         */
        fun getArrayofMealsName(meals: MutableList<MealEntry>): Array<String> {
            val strings = mutableListOf<String>()
            for (i in meals) {
                if(containsBefore(strings, i.description)){
                    continue
                }
                strings.add(i.description)
            }
            return strings.toTypedArray()
        }

        /**
         * Get Array of food's Cal
         * @param meals Meal list
         * @return All the food's Cal in that Meal list
         */
        fun getArrayofFoodCal(meals: MutableList<MealEntry>): Array<String> {
            val strings = mutableListOf<String>()
            for (i in meals) {
                for (j in i.foods) {
                    if(containsBefore(strings, j.calories.toString())){
                        continue
                    }
                    strings.add(j.calories.toString())
                }
            }
            return strings.toTypedArray()
        }

        /**
         * Helper method
         */
        private fun containsBefore(stringList: MutableList<String>, string: String): Boolean {
            return stringList.contains(string)
        }
    }

    /**
     * Get the total calories for one meal
     * @return total calories
     */
    fun getTotalCalories(): Int {
        var cals = 0
        for (f in foods) {
            cals += f.calories
        }
        return cals
    }

    /**
     * Get the total protein for one meal
     * @return total protein
     */
    fun getTotalProtein(): Int {
        var protein = 0
        for (f in foods) {
            protein += f.protein!!
        }
        return protein
    }

    /**
     * Get the total fats for one meal
     * @return total fats
     */
    fun getTotalFats(): Int {
        var fats = 0
        for (f in foods) {
            fats += f.fats
        }
        return fats
    }

}

class FoodEntry(var name: String, var calories: Int, var protein: Int, var fats: Int) {


}