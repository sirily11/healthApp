package com.example.qiweili

import com.example.qiweili.healthapp.health.HealthData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import java.util.*

/**
 * Created by qiweili on 2018/3/2.
 */
class utils {
    companion object {
        var mDatabase = FirebaseDatabase.getInstance()
        init {
            mDatabase.setPersistenceEnabled(true)
        }

        fun getDatabase(): FirebaseDatabase {
            return mDatabase
        }

        /**
         * This is a helper method which will generate a random double number
         * @param rangeMax Max bound
         * @param rangeMin Min bound
         */
        fun randomDoubleGenerator(rangeMin : Double, rangeMax : Double) : Double{
            val r = Random()
            val randomValue = rangeMin + (rangeMax - rangeMin) * r.nextDouble()
            return randomValue

        }

        /**
         * This is a test method for graphing the graph
         * This method will return a list of double numbers
         * @param rangeMin Min bound
         * @param rangeMax Max bound
         * @param number size of the list
         * @return a list with a bunch of double numbers
         */

        fun randomDoubleListGenerator(rangeMin: Double,rangeMax: Double,number : Int) : MutableList<Double>{
            val list = mutableListOf<Double>()
            for(i in 1..number){
                list.add(randomDoubleGenerator(rangeMin,rangeMax))
            }
            return list
        }

        /**
         * Helper method
         * Takes two days and tell if they are equal
         * @param dateOne first date
         * @param dateTwo second date
         * @return if the two dates are equal
         */

        fun isSameDay(dateOne : String, dateTwo : String) : Boolean{
            return dateOne == dateTwo
        }

        /**
         * Helper method
         * Get current date
         * @return string version of current date
         */
        fun getCurrentDate(): String {
            val currentTime = Calendar.getInstance()
            var day = currentTime.get(Calendar.DAY_OF_MONTH).toString()
            var month = currentTime.get(Calendar.MONTH).toString()
            var year = currentTime.get(Calendar.YEAR).toString()
            return "${day}/${month}/${year}"
        }

        /**
         * This is a helper method which would return the last object in the
         * Mutablelist of health data
         * @param list Mutablelist of HealthData
         * @return the last index of the mutable list of Healthdata
         */
        fun getLastHealthData(list : MutableList<HealthData>?) : HealthData? {
                return list?.get(list?.size - 1)
        }

        /**
         * Constant variable for the database's name
         */
        val databaseName = "Health.db"

        /**
         * variable for current login user's id
         */
        val account_id = FirebaseAuth.getInstance().currentUser?.uid

        /**
         * Step's index
         */
        val steps_index = 0

        /**
         * Cal's index
         */
        val cal_burned_index = 1

    }

}
