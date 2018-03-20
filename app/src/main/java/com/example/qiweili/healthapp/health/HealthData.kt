package com.example.qiweili.healthapp.health

import android.accounts.AuthenticatorDescription

/**
 * This is a data class which will store the user health data
 * which includes the calories and steps
 * It can store the time of the data.
 * @param time time of the data, this should be the date of year/month/day
 * @param data data
 * @param description the type of the data.
 * This should use the description text in Utils
 */
class HealthData(var time : String?, var data: Int?, var description: String?){

}
