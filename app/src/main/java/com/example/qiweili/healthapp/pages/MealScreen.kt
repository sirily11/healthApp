package com.example.qiweili.healthapp.pages

import android.app.AlertDialog
import android.os.Bundle
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.PopupMenu
import android.support.v7.widget.RecyclerView
import android.view.*
import android.widget.Toast
import com.example.qiweili.healthapp.DatabaseHelper
import com.example.qiweili.healthapp.Drawer_menu
import com.example.qiweili.healthapp.Food.AdapatersForFoods
import com.example.qiweili.healthapp.Food.AdapatersForMeals
import com.example.qiweili.healthapp.R
import com.example.qiweili.healthapp.profile.FoodEntry
import com.example.qiweili.healthapp.profile.MealEntry
import com.example.qiweili.utils
import kotlinx.android.synthetic.main.activity_meal.*
import kotlinx.android.synthetic.main.alert_dialog_window_meal.view.*
import kotlinx.android.synthetic.main.collapsing_toolbar.*
import kotlinx.android.synthetic.main.row_main_meals.view.*

class MealScreen : AppCompatActivity() {
    val foods = mutableListOf<FoodEntry>()
    var meals = mutableListOf<MealEntry>()
    var db = DatabaseHelper(this, utils.databaseName)
    var myDrawerToggle: ActionBarDrawerToggle? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meal)
        setSupportActionBar(toolbar)
        val drawer_layout = Drawer_menu(this,
                this@MealScreen, drawer_layout_home, nav_Home)

        drawer_layout.setListener()
        meal_list.layoutManager = LinearLayoutManager(meal_list.context)
        myDrawerToggle = drawer_layout.mDrawerToggle
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        if (db.getFood(utils.account_id!!) != null) {
            meals = db.getFood(utils.account_id)!!
        }
        meal_list.adapter = AdapatersForMeals(meals)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val mMenuInflater = menuInflater
        mMenuInflater.inflate(R.menu.add_meal_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (myDrawerToggle?.onOptionsItemSelected(item)!!) {
            return true
        }
        when (item?.itemId) {
            R.id.add_meal -> {
                showAddMealWindows()
                return true
            }
            else -> {
                return super.onOptionsItemSelected(item)
            }
        }
    }

    fun showAddMealWindows() {
        val mBuilder = AlertDialog.Builder(this)
        val inflater = LayoutInflater.from(this)
        val view = inflater.inflate(R.layout.alert_dialog_window_meal, null)
        view.food_list.layoutManager = LinearLayoutManager(view.food_list.context)
        view.food_list.adapter = AdapatersForFoods(foods)
        mBuilder.setView(view)
        val dialog = mBuilder.create()
        /**
         * Get array adapter for autocomplete
         */
        val mealNameArray = MealEntry.getArrayofMealsName(meals)
        val foodNameArray = MealEntry.getArrayofFoodsName(meals)
        val mealNameAdapter = MealEntry.getStringArrayAdapter(mealNameArray, this, R.layout.autocomplete_layout)
        val foodNameAdapter = MealEntry.getStringArrayAdapter(foodNameArray, this, R.layout.autocomplete_layout)
        /**
         * Set the auto complete for the input text
         */
        view.meal_name.threshold = 2
        view.meal_name.setAdapter(mealNameAdapter)
        view.food_name.threshold = 2
        view.food_name.setAdapter(foodNameAdapter)

        /**
         * Add foods
         */
        view.add_button.setOnClickListener {
            var foodName = view.food_name.text.toString()
            var calories = view.cal_intake.text.toString().toIntOrNull()

            if (calories == null) {
                calories = 0
            }
            if (foodName != null) {
                foods.add(FoodEntry(calories = calories, name = foodName, fats = 0, protein = 0))
            }
            if (foods.size > 0) {
                view.remove_button.visibility = View.VISIBLE
            }
            view.food_name.text.clear()
            view.cal_intake.text.clear()
            view.food_list.adapter.notifyDataSetChanged()

        }
        /**
         * Delete foods
         */
        view.remove_button.setOnClickListener {
            foods.removeAt(foods.size - 1)
            view.food_name.text.clear()
            view.cal_intake.text.clear()
            view.food_list.adapter.notifyDataSetChanged()
        }
        /**
         * Add meals
         */
        view.set.setOnClickListener {
            val mealName = view.meal_name.text.toString()
            meals.add(MealEntry(utils.getCurrentDate(), foods, mealName))
            db.updateFood(meals, utils.account_id!!)
            db.push_to_server(DatabaseHelper.FOOD_MAP)
            Toast.makeText(this,"Total Cal is ${meals.get(meals.size - 1).getTotalCalories()}",Toast.LENGTH_SHORT).show()

            foods.clear()
            view.food_list.adapter.notifyDataSetChanged()
            dialog.dismiss()
            recreate()
        }


        /**
         * Close the window
         */
        view.cancel.setOnClickListener {

            foods.clear()
            view.food_list.adapter.notifyDataSetChanged()

            dialog.dismiss()
        }

        dialog.show()
    }

    class ViewHolder(val view: View?, val meals: MutableList<MealEntry>?, val adapatersForMeal: AdapatersForMeals?) : RecyclerView.ViewHolder(view) {


        init {
            view?.edit_meals_button?.setOnClickListener {
                showMenu(view)
            }
        }

        fun showMenu(v: View) {
            val popup = PopupMenu(view?.context!!, view, Gravity.NO_GRAVITY)
            popup.inflate(R.menu.menu_edit)
            popup.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.delete -> {
                        meals?.removeAt(adapterPosition)
                        adapatersForMeal?.notifyDataSetChanged()
                        var db = DatabaseHelper(view.context, utils.databaseName)
                        db.updateFood(meals!!, utils.account_id!!)
                        true
                    }
                    else -> {
                        true
                    }
                }
            }

            popup.show()
        }

    }
}
