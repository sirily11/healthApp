package com.example.qiweili.healthapp

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.qiweili.healthapp.Food.Meal
import com.example.qiweili.healthapp.friend.Friends
import com.example.qiweili.healthapp.health.HealthData
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*


/**
 * Created by qiweili on 2018/3/7.
 */
class DatabaseHelper : SQLiteOpenHelper {


    constructor(Context: Context, DatabaseName: String) : super(Context, DatabaseName, null, 1)


    companion object {
        val TABLE_NAME = "HEALTH_DATA"
        val ACCONT_ID = "ACCOUNT_ID"
        val PROFILE_NAME = "PROFILE_NAME"
        val STEPS = "STEPS"
        val CAL_BURNED = "CAL_BURNED"
        /**
         * This should be a food-list
         */
        val CAL_INTAKE = "CAL_INTAKE"
        val FOOD_MAP = "FOOD_MAP"
        val ABOUTME_DESCRIBE = "ABOUTME_DESCRIBE"
        val FRIEND_LIST = "FRIEND_LIST"
        val PROFILE_PIC = "PROFILE_PIC"
        var push_to_server = false



    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("CREATE TABLE IF NOT EXISTS ${TABLE_NAME} ( ${ACCONT_ID} TEXT, " +
                "${PROFILE_NAME} TEXT, ${STEPS} REAL, ${CAL_BURNED} REAL, ${CAL_INTAKE} TEXT, " +
                "${FOOD_MAP} TEXT, ${ABOUTME_DESCRIBE} TEXT, ${FRIEND_LIST} TEXT, ${PROFILE_PIC} BLOB " +
                "UNIQUE $ACCONT_ID)")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
    }

    fun push_to_server(setting: Boolean) {
        push_to_server = setting
    }

    fun insertData(account_id: String?, profile_name: String?, steps: Int?, cal_burned: Int?,
                   friends: Friends?, cal_intake: Meal?, about_me: String?,
                   profile_pic: ByteArray?): Boolean {

        val db = this.writableDatabase
        val contentValue = ContentValues()
        val gson = GsonBuilder().create()
        val total_Cal = gson.toJson(cal_intake?.getCalIntake())
        val friendsStr = gson.toJson(friends?.friendsName)

        contentValue.put(ACCONT_ID, account_id)
        contentValue.put(PROFILE_NAME, profile_name)
        contentValue.put(STEPS, steps)
        //contentValue.put(CAL_BURNED,cal_burned)
        //contentValue.put(CAL_INTAKE,total_Cal)
        contentValue.put(ABOUTME_DESCRIBE, about_me)
        //contentValue.put(FRIEND_LIST,friendsStr)
        //contentValue.put(PROFILE_PIC,profile_pic)
        var success = 0
        if (!hasAccount(account_id!!)) {
            success = db.insert(TABLE_NAME, null, contentValue).toInt()
        } else {
            db.execSQL("UPDATE $TABLE_NAME SET $STEPS = $steps WHERE $ACCONT_ID = '$account_id'")

            success = 0
        }
        return success != 1
    }

    /**
     * This method will insert the account ID at everytime when user login.
     * This method will only insert the same account ID once
     * @param account_id The account id you want to insert
     */
    fun insertAccountID(account_id: String) {
        val db = this.writableDatabase
        val success = db.execSQL("INSERT OR IGNORE INTO $TABLE_NAME($ACCONT_ID) VALUES($account_id)")
    }

    /**
     * This method will update the steps in the database
     * @param steps the steps number you want to update for
     * the account id
     * @param account_id the account you want to update
     */

    fun updateSteps(steps: Int?, account_id: String) {
        val db = this.writableDatabase
        db.execSQL("UPDATE $TABLE_NAME SET $STEPS = '$steps' WHERE $ACCONT_ID = '$account_id'")
    }

    /**
     * This method will update the cal in the database
     * @param cal the cal number you want to update for
     * the account id
     * @param account_id the account you want to update
     */

    fun updateCal_Burned(cal: Int?, account_id: String) {
        val db = this.writableDatabase
        db.execSQL("UPDATE $TABLE_NAME SET $CAL_BURNED = '$cal' WHERE $ACCONT_ID = '$account_id'")
    }

    /**
     * This method will update the about me in the database
     * @param about_me the about me description you want to update for
     * the account id
     * @param account_id the account you want to update
     */

    fun updateAboutme(about_me: String?, account_id: String) {
        val db = this.writableDatabase
        db.execSQL("UPDATE $TABLE_NAME SET $ABOUTME_DESCRIBE = '$about_me' WHERE $ACCONT_ID = '$account_id'")
    }

    /**
     * This method will update the profile name in the database
     * @param steps the steps number you want to update for
     * the account id
     * @param account_id the account you want to update
     */

    fun updateProfileName(profile_name: String?, account_id: String) {
        val db = this.writableDatabase
        if (hasData(account_id = account_id, data = PROFILE_NAME)) {
            db.execSQL("UPDATE $TABLE_NAME SET $PROFILE_NAME = $profile_name WHERE $ACCONT_ID = '$account_id'")
        } else {

        }
    }

    fun getProfileName(account_id: String): String? {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT $PROFILE_NAME FROM $TABLE_NAME 'WHERE $ACCONT_ID = $account_id'", null)
        val profile_name = mutableListOf<String>()
        with(cursor) {
            while (moveToNext()) {
                val name = getString(0)
                profile_name.add(name)
            }
        }
        if (profile_name.size > 0) {
            return profile_name[0]
        } else {
            return null
        }
    }

    fun getAboutme(account_id: String): String? {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT $ABOUTME_DESCRIBE FROM $TABLE_NAME 'WHERE $ACCONT_ID = $account_id'", null)
        val about_me = mutableListOf<String>()
        with(cursor) {
            while (moveToNext()) {
                val name = getString(0)
                about_me.add(name)
            }
        }
        if (about_me.size > 0) {
            return about_me[0]
        } else {
            return null
        }
    }

    fun getSteps(account_id: String): List<HealthData>? {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT $STEPS FROM $TABLE_NAME 'WHERE $ACCONT_ID = $account_id'", null)
        val stepsJson = mutableListOf<String>()
        val gson = GsonBuilder().create()
        with(cursor) {
            while (moveToNext()) {
                val step = getString(0)
                stepsJson.add(step)
            }
        }
        val steps = gson.fromJson(stepsJson[0],Array<HealthData>::class.java).toMutableList()
        if (steps.size > 0) {
            return steps
        } else {
            return null
        }
    }

    fun getCalsBurned(account_id: String): List<Int>? {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT $CAL_BURNED FROM $TABLE_NAME 'WHERE $ACCONT_ID = $account_id'", null)
        val cals = mutableListOf<Int>()
        with(cursor) {
            while (moveToNext()) {
                val cal = getInt(0)
                cals.add(cal)
            }
        }
        if (cals.size > 0) {
            return cals
        } else {
            return null
        }
    }

    fun isSameDay(dateOne : String, dateTwo : String) : Boolean{
        return dateOne == dateTwo
    }

    fun getCurrentDate(): String {
        val currentTime = Calendar.getInstance()
        var date = currentTime.get(Calendar.DAY_OF_MONTH).toString()
        return date
    }

    private fun hasAccount(account_id: String): Boolean {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_NAME 'WHERE $ACCONT_ID = $account_id'", null)
        println(cursor.count)
        return cursor.count > 0
    }

    private fun hasData(account_id: String, data: String): Boolean {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT $data FROM $TABLE_NAME 'WHERE $ACCONT_ID = $account_id AND $data IS NOT NULL'", null)
        println(cursor.count)
        return cursor.count > 0
    }

}