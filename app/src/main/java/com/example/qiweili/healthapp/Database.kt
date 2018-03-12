package com.example.qiweili.healthapp

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.qiweili.healthapp.Food.Meal
import com.example.qiweili.healthapp.friend.Friends
import com.example.qiweili.healthapp.friend.Profile_image
import com.google.gson.GsonBuilder

/**
 * Created by qiweili on 2018/3/7.
 */
class DatabaseHelper : SQLiteOpenHelper{


    constructor(Context :Context,DatabaseName : String) : super(Context,DatabaseName,null,1)

    private val TABLE_NAME = "HEALTH_DATA"
    private val ACCONT_ID = "ACCOUNT_ID"
    private val PROFILE_NAME = "PROFILE_NAME"
    private val STEPS = "PROFILE_NAME"
    private val CAL_BURNED = "PROFILE_NAME"
    /**
     * This should be a food-list
     */
    private val CAL_INTAKE = "PROFILE_NAME"
    private val FOOD_MAP = "FOOD_MAP"
    private val ABOUTME_DESCRIBE = "PROFILE_NAME"
    private val FRIEND_LIST = "PROFILE_NAME"
    private val PROFILE_PIC = "PROFILE_NAME"
    var push_to_server = false



    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("CREATE TABLE IF NOT EXISTS ${TABLE_NAME} ( ${ACCONT_ID} TEXT " +
                "${PROFILE_NAME} TEXT ${STEPS} REAL ${CAL_BURNED} REAL ${CAL_INTAKE} TEXT " +
                "${FOOD_MAP} TEXT ${ABOUTME_DESCRIBE} TEXT ${FRIEND_LIST} TEXT ${PROFILE_PIC} BLOB)" )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
    }

    fun push_to_server(setting : Boolean){
        push_to_server = setting
    }

    fun insertData(account_id :String, profile_name : String,steps: Int, cal_burned:Int,
                   friends : Friends, cal_intake : Meal, about_me : String,
                   profile_pic : Profile_image,time : String){

        val db = this.writableDatabase
        val contentValue = ContentValues()
        val gson = GsonBuilder().create()
        val total_Cal = gson.toJson(cal_intake.getCalIntake())
        contentValue.put(ACCONT_ID,account_id)
        contentValue.put(PROFILE_NAME,profile_name)
        contentValue.put(STEPS,steps)
        contentValue.put(CAL_BURNED,cal_burned)
        contentValue.put(CAL_INTAKE,total_Cal)
        contentValue.put(ABOUTME_DESCRIBE,about_me)
        contentValue.put(PROFILE_PIC,profile_pic)
        contentValue.put(time,time)
        contentValue.put(FRIEND_LIST,friends)


    }

}