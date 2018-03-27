package com.example.qiweili.healthapp.Food

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.qiweili.healthapp.R
import com.example.qiweili.healthapp.pages.MealScreen
import kotlinx.android.synthetic.main.row_main_meals.view.*

/**
 * Adapter for meals
 */
class AdapatersForMeals(val meals : MutableList<MealEntry>) : RecyclerView.Adapter<MealScreen.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): MealScreen.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent?.context)
        val cellForRow = layoutInflater.inflate(R.layout.row_main_meals, parent, false)
        return MealScreen.ViewHolder(cellForRow, meals, this)
    }

    override fun getItemCount(): Int {
       return meals.size
    }

    override fun onBindViewHolder(holder: MealScreen.ViewHolder?, position: Int) {
        holder?.view?.description?.text = meals[position].description
        holder?.view?.data?.text = meals[position].getTotalCalories().toString()
        holder?.view?.time?.text = meals[position].time

    }
}

/**
 * Adapter for food
 */
class AdapatersForFoods(val foods : MutableList<FoodEntry>) : RecyclerView.Adapter<MealScreen.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): MealScreen.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent?.context)
        val cellForRow = layoutInflater.inflate(R.layout.row_main_foods, parent, false)
        return MealScreen.ViewHolder(cellForRow,null,null)
    }

    override fun getItemCount(): Int {
        return foods.size
    }

    override fun onBindViewHolder(holder: MealScreen.ViewHolder?, position: Int) {
        holder?.view?.description?.text = foods[position].name
        holder?.view?.data?.text = foods[position].calories.toString()

    }
}