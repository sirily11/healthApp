package com.example.qiweili.healthapp.health

import com.example.qiweili.healthapp.DatabaseHelper

import com.example.qiweili.healthapp.Food.MealEntry

/**
 * Created by qiweili on 2018/3/26.
 */
class Calculate_Cals(val meals : MutableList<MealEntry>){


    /**
     * Get the total cal intake
     * @return MutableList of HealthData
     */
    fun getCals_intake(): MutableList<HealthData> {
        val data = mutableListOf<HealthData>()
        var totalCals = 0
        var time = ""
        val des = DatabaseHelper.CAL_INTAKE

        for(m in getAllData()){
            if(m.time != time) {
                m.description = des
                data.add(m)
                time = m.time!!
                totalCals += m.data!!
            }else{
                totalCals += m.data!!
                data.set(data.size - 1,HealthData(time,totalCals,des))
            }
        }
        return data
    }

    fun getAllData(): MutableList<HealthData>{
        val data = mutableListOf<HealthData>()
        for(m in meals){
            data.add(HealthData(m.time,m.getTotalCalories(),m.description))
        }
        return data
    }
}