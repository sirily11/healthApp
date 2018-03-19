package com.example.qiweili.healthapp

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import com.example.qiweili.healthapp.Food.Meal
import com.example.qiweili.healthapp.friend.Friends
import com.google.gson.GsonBuilder

/**
 * Created by qiweili on 2018/3/7.
 */
class DatabaseHelper : SQLiteOpenHelper{


    constructor(Context :Context,DatabaseName : String) : super(Context,DatabaseName,null,1)


    companion object {
        private val TABLE_NAME = "HEALTH_DATA"
        private val ACCONT_ID = "ACCOUNT_ID"
        private val PROFILE_NAME = "PROFILE_NAME"
        private val STEPS = "STEPS"
        private val CAL_BURNED = "CAL_BURNED"
        /**
         * This should be a food-list
         */
        private val CAL_INTAKE = "CAL_INTAKE"
        private val FOOD_MAP = "FOOD_MAP"
        private val ABOUTME_DESCRIBE = "ABOUTME_DESCRIBE"
        private val FRIEND_LIST = "FRIEND_LIST"
        private val PROFILE_PIC = "PROFILE_PIC"
        var push_to_server = false

    }


    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("CREATE TABLE IF NOT EXISTS ${TABLE_NAME} ( ${ACCONT_ID} TEXT, " +
                "${PROFILE_NAME} TEXT, ${STEPS} REAL, ${CAL_BURNED} REAL, ${CAL_INTAKE} TEXT, " +
                "${FOOD_MAP} TEXT, ${ABOUTME_DESCRIBE} TEXT, ${FRIEND_LIST} TEXT, ${PROFILE_PIC} BLOB)" )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
    }

    fun push_to_server(setting : Boolean){
        push_to_server = setting
    }

    fun insertData(account_id :String?, profile_name : String?, steps: Int?, cal_burned:Int?,
                   friends : Friends?, cal_intake : Meal?, about_me : String?,
                   profile_pic : ByteArray?) : Boolean{

        val db = this.writableDatabase
        val contentValue = ContentValues()
        val gson = GsonBuilder().create()
        val total_Cal = gson.toJson(cal_intake?.getCalIntake())
        val friendsStr = gson.toJson(friends?.friendsName)

        contentValue.put(ACCONT_ID,account_id)
        //contentValue.put(PROFILE_NAME,profile_name)
        contentValue.put(STEPS,steps)
        //contentValue.put(CAL_BURNED,cal_burned)
        //contentValue.put(CAL_INTAKE,total_Cal)
        //contentValue.put(ABOUTME_DESCRIBE,about_me)
        //contentValue.put(FRIEND_LIST,friendsStr)
        //contentValue.put(PROFILE_PIC,profile_pic)
        var success = 0
        if(!hasData(account_id!!)) {
            success = db.insert(TABLE_NAME, null, contentValue).toInt()
        }else{
            db.execSQL("UPDATE $TABLE_NAME SET $STEPS = $steps WHERE $ACCONT_ID = '$account_id'")

            success = 0
        }
        return success != 1
    }

    fun getSteps(account_id: String) : Int{
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT $STEPS FROM $TABLE_NAME 'WHERE $ACCONT_ID = $account_id'",null)
        val steps = mutableListOf<Int>()
        with(cursor){
           while (moveToNext()){
               val step = getInt(0)
               steps.add(step)
           }
       }
        if(steps.size > 0) {
            return steps[0]
        }
        else{
            return 0
        }
    }

    fun hasData(account_id: String) : Boolean{
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_NAME 'WHERE $ACCONT_ID = $account_id'",null)
        println(cursor.count)
        return cursor.count > 0
    }

}