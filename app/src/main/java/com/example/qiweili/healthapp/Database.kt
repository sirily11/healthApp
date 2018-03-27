package com.example.qiweili.healthapp

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.graphics.Bitmap
import android.graphics.Bitmap.CompressFormat
import android.graphics.BitmapFactory
import com.example.qiweili.healthapp.Food.Meal
import com.example.qiweili.healthapp.Food.MealEntry
import com.example.qiweili.healthapp.friend.Friend
import com.example.qiweili.healthapp.health.HealthData
import com.example.qiweili.utils
import com.google.gson.GsonBuilder
import okhttp3.*
import java.io.ByteArrayOutputStream
import java.io.IOException


/**
 * Created by qiweili on 2018/3/7.
 */
class DatabaseHelper : SQLiteOpenHelper {


    constructor(Context: Context, DatabaseName: String) : super(Context, DatabaseName, null, 1)


    companion object {
        /**
         * Table name
         */
        val TABLE_NAME = "HEALTH_DATA"

        /**
         * Account id
         */
        val ACCONT_ID = "ACCOUNT_ID"

        /**
         * Profile name
         */
        val PROFILE_NAME = "PROFILE_NAME"

        /**
         * Steps
         */
        val STEPS = "STEPS"

        /**
         * Cal burned
         */
        val CAL_BURNED = "CAL_BURNED"

        /**
         * This should be a food-list
         */
        val CAL_INTAKE = "CAL_INTAKE"
        val FOOD_MAP = "FOOD_MAP"
        val ABOUTME_DESCRIBE = "ABOUTME_DESCRIBE"
        val FRIEND_LIST = "FRIEND_LIST"
        val PROFILE_PIC = "PROFILE_PIC"

        fun getBytes(bitmap: Bitmap): ByteArray {
            val stream = ByteArrayOutputStream()
            bitmap.compress(CompressFormat.PNG, 100, stream)
            return stream.toByteArray()
        }

        // convert from byte array to bitmap
        fun getImage(image: ByteArray): Bitmap {
            return BitmapFactory.decodeByteArray(image, 0, image.size)
        }
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("CREATE TABLE IF NOT EXISTS ${TABLE_NAME} (${ACCONT_ID} TEXT UNIQUE, " +
                "${PROFILE_NAME} TEXT, ${STEPS} TEXT, ${CAL_BURNED} TEXT, ${CAL_INTAKE} TEXT, " +
                "${FOOD_MAP} TEXT, ${ABOUTME_DESCRIBE} TEXT, ${FRIEND_LIST} TEXT, ${PROFILE_PIC} TEXT)")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
    }

    /**
     * Push the data to the remote server
     */
    fun push_to_server(selection: String) {
        val client = OkHttpClient()
        val gson = GsonBuilder().create()
        when (selection) {
            FOOD_MAP -> {
                val data = gson.toJson(getFood(utils.account_id!!))
                val request = Request.Builder()
                        .url("http://52.207.147.141/data/?${ACCONT_ID}=${utils.account_id}" +
                                "&${FOOD_MAP}=$data")
                        .get()
                        .build()

                val response = client.newCall(request).enqueue(object : Callback {
                    override fun onResponse(call: Call?, response: Response?) {
                        println()
                    }

                    override fun onFailure(call: Call?, e: IOException?) {
                        println()
                    }

                })
            }
            PROFILE_PIC -> {
                val data = gson.toJson(getBytes(getProfileImage(utils.account_id!!)!!))
                val request = Request.Builder()
                        .url("http://52.207.147.141/data/?${ACCONT_ID}=${utils.account_id}" +
                                "&${PROFILE_PIC}=$data")
                        .get()
                        .build()

                val response = client.newCall(request).enqueue(object : Callback {
                    override fun onResponse(call: Call?, response: Response?) {
                        println()
                    }

                    override fun onFailure(call: Call?, e: IOException?) {
                        println()
                    }

                })
            }
            PROFILE_NAME -> {
                val data = gson.toJson(getProfileName(utils.account_id!!))
                val request = Request.Builder()
                        .url("http://52.207.147.141/data/?${ACCONT_ID}=${utils.account_id}" +
                                "&${PROFILE_NAME}=$data")
                        .get()
                        .build()

                val response = client.newCall(request).enqueue(object : Callback {
                    override fun onResponse(call: Call?, response: Response?) {
                        println()
                    }

                    override fun onFailure(call: Call?, e: IOException?) {
                        println()
                    }

                })
            }

            ABOUTME_DESCRIBE -> {
                val data = gson.toJson(getAboutme(utils.account_id!!))
                val request = Request.Builder()
                        .url("http://52.207.147.141/data/?${ACCONT_ID}=${utils.account_id}" +
                                "&${ABOUTME_DESCRIBE}=$data")
                        .get()
                        .build()

                val response = client.newCall(request).enqueue(object : Callback {
                    override fun onResponse(call: Call?, response: Response?) {
                        println()
                    }

                    override fun onFailure(call: Call?, e: IOException?) {
                        println()
                    }

                })
            }
        }

    }

    fun insertData(account_id: String?, profile_name: String?, steps: Int?, cal_burned: Int?,
                   friends: Friend?, cal_intake: Meal?, about_me: String?,
                   profile_pic: ByteArray?): Boolean {

        val db = this.writableDatabase
        val contentValue = ContentValues()
        val gson = GsonBuilder().create()
        val total_Cal = gson.toJson(cal_intake?.getCalIntake())
        //val friendsStr = gson.toJson(friends?.friendsName)

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
        val success = db.execSQL("INSERT OR IGNORE INTO $TABLE_NAME($ACCONT_ID) VALUES('$account_id')")

    }

    /**
     * This method will update the steps to the database
     * @param steps the steps number you want to update for
     * the account id
     * @param account_id the account you want to update
     */

    fun updateSteps(steps: MutableList<HealthData>, account_id: String) {
        val db = this.writableDatabase
        val gson = GsonBuilder().create()
        val data = gson.toJson(steps)
        db.execSQL("UPDATE $TABLE_NAME SET $STEPS = '$data' WHERE $ACCONT_ID = '$account_id'")
    }

    /**
     * This method will update the cal to the database
     * @param cal the cal number you want to update for
     * the account id
     * @param account_id the account you want to update
     */

    fun updateCal_Burned(cal: MutableList<HealthData>, account_id: String) {
        val db = this.writableDatabase
        val gson = GsonBuilder().create()
        val data = gson.toJson(cal)
        db.execSQL("UPDATE $TABLE_NAME SET $CAL_BURNED = '$data' WHERE $ACCONT_ID = '$account_id'")
    }

    /**
     * This method will update the food to the database
     * @param food the cal number you want to update for
     * the account id
     * @param account_id the account you want to update
     */

    fun updateFood(food: MutableList<MealEntry>, account_id: String) {
        val db = this.writableDatabase
        val gson = GsonBuilder().create()
        val data = gson.toJson(food)
        db.execSQL("UPDATE $TABLE_NAME SET $FOOD_MAP = '$data' WHERE $ACCONT_ID = '$account_id'")
    }

    /**
     * This method will update the about me to the database
     * @param about_me the about me description you want to update for
     * the account id
     * @param account_id the account you want to update
     */

    fun updateAboutme(about_me: String?, account_id: String) {
        val db = this.writableDatabase
        db.execSQL("UPDATE $TABLE_NAME SET $ABOUTME_DESCRIBE = '$about_me' WHERE $ACCONT_ID = '$account_id'")
    }

    /**
     * This method will update the about me to the database
     * @param image the image you want to update for
     * the account id
     * @param account_id the account you want to update
     */

    fun updateProfileImage(image: ByteArray?, account_id: String) {
        val gson = GsonBuilder().create()
        val data = gson.toJson(image)
        val db = this.writableDatabase
        db.execSQL("UPDATE $TABLE_NAME SET $PROFILE_PIC = '${data}' WHERE $ACCONT_ID = '$account_id'")
    }

    /**
     * This method will update the about me to the database
     * @param image the image you want to update for
     * the account id
     * @param account_id the account you want to update
     */

    fun updateFriendList(friends: MutableList<Friend>, account_id: String) {
        val gson = GsonBuilder().create()
        val data = gson.toJson(friends)
        val db = this.writableDatabase
        db.execSQL("UPDATE $TABLE_NAME SET $FRIEND_LIST = '${data}' WHERE $ACCONT_ID = '$account_id'")
    }

    /**
     * This method will update the profile name to the database
     * @param steps the steps number you want to update for
     * the account id
     * @param account_id the account you want to update
     */

    fun updateProfileName(profile_name: String?, account_id: String) {
        val db = this.writableDatabase
        db.execSQL("UPDATE $TABLE_NAME SET $PROFILE_NAME = '$profile_name' WHERE $ACCONT_ID = '$account_id'")
    }

    /**
     * This method will get the profile name from the database
     * @param account_id the account you want to update
     * @return  profile name from the database,
     *          null if the profile name doesn't exist
     */

    fun getProfileName(account_id: String): String? {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT $PROFILE_NAME FROM $TABLE_NAME WHERE $ACCONT_ID = '$account_id'", null)
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

    /**
     * This method will get the about me from the database
     * @param account_id the account you want to update
     * @return  about me from the database,
     *          null if the about me doesn't exist
     */

    fun getAboutme(account_id: String): String? {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT $ABOUTME_DESCRIBE FROM $TABLE_NAME WHERE $ACCONT_ID = '$account_id'", null)
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


    /**
     * Get the list of steps data from sql database
     * @param account_id the account you want to get
     * @return mutable list of health data,
     *  null if not exist
     */
    fun getSteps(account_id: String): MutableList<HealthData>? {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT $STEPS FROM $TABLE_NAME WHERE $ACCONT_ID = '$account_id'", null)
        val stepsJson = mutableListOf<String>()
        val gson = GsonBuilder().create()
        with(cursor) {
            while (moveToNext()) {
                val step: String? = getString(0)
                if (step == null) {
                    return null
                }
                stepsJson.add(step)
            }
        }
        if (stepsJson.size < 1) {
            return null
        }
        val steps = gson.fromJson(stepsJson[0], Array<HealthData>::class.java).toMutableList()
        if (steps.size > 0) {
            return steps
        } else {
            return null
        }
    }

    /**
     * Get the list of steps data from sql database
     * @param account_id the account you want to get
     * @return mutable list of meal data,
     *  null if not exist
     */
    fun getFood(account_id: String): MutableList<MealEntry>? {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT $FOOD_MAP FROM $TABLE_NAME WHERE $ACCONT_ID = '$account_id'", null)
        val mealsJson = mutableListOf<String>()
        val gson = GsonBuilder().create()
        with(cursor) {
            while (moveToNext()) {
                val meal: String? = getString(getColumnIndexOrThrow(FOOD_MAP))
                if (meal == null) {
                    return null
                }
                mealsJson.add(meal)
            }
        }
        if (mealsJson.size < 1) {
            return null
        }
        val meals = gson.fromJson(mealsJson[0], Array<MealEntry>::class.java).toMutableList()
        return meals
    }

    /**
     * Get the list of cal_burned data from sql database
     * @param account_id the account you want to get
     * @return mutable list of health data,
     * null if not exist
     */

    fun getCalsBurned(account_id: String): MutableList<HealthData>? {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT $CAL_BURNED FROM $TABLE_NAME WHERE $ACCONT_ID = '$account_id'", null)
        val calJson = mutableListOf<String>()
        val gson = GsonBuilder().create()
        with(cursor) {
            while (moveToNext()) {
                val cal = getString(0)
                if (cal == null) {
                    return null
                }
                calJson.add(cal)
            }
        }
        if (calJson.size < 1) {
            return null
        }
        val cals = gson.fromJson(calJson[0], Array<HealthData>::class.java).toMutableList()
        if (cals.size > 0) {
            return cals
        } else {
            return null
        }
    }

    /**
     * Get the list of cal_burned data from sql database
     * @param account_id the account you want to get
     * @return mutable list of health data,
     * null if not exist
     */

    fun getFriends(account_id: String): MutableList<Friend>? {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT $FRIEND_LIST FROM $TABLE_NAME WHERE $ACCONT_ID = '$account_id'", null)
        val friendJson = mutableListOf<String>()
        val gson = GsonBuilder().create()
        with(cursor) {
            while (moveToNext()) {
                val friend = getString(0)
                if (friend == null) {
                    return null
                }
                friendJson.add(friend)
            }
        }
        if (friendJson.size < 1) {
            return null
        }
        val friends = gson.fromJson(friendJson[0], Array<Friend>::class.java).toMutableList()
        if (friends.size > 0) {
            return friends
        } else {
            return null
        }
    }

    /**
     * Get the list of image data from sql database
     * @param account_id the account you want to get
     * @return image,
     * null if not exist
     */

    fun getProfileImage(account_id: String): Bitmap? {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT $PROFILE_PIC FROM $TABLE_NAME WHERE $ACCONT_ID = '$account_id'", null)
        val imagesJson = mutableListOf<String>()
        with(cursor) {
            while (moveToNext()) {
                val image = getString(0)
                if (image == null) {
                    return null
                }
                imagesJson.add(image)
            }
        }
        if (imagesJson.size < 1) {
            return null
        }
        val gson = GsonBuilder().create()
        val images = gson.fromJson(imagesJson[0], ByteArray::class.java)
        return getImage(images)
    }

    private fun hasAccount(account_id: String): Boolean {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_NAME WHERE $ACCONT_ID = '$account_id'", null)
        println(cursor.count)
        return cursor.count > 0
    }

    private fun hasData(account_id: String, data: String): Boolean {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT $data FROM $TABLE_NAME WHERE $ACCONT_ID = '$account_id AND $data IS NOT NULL'", null)
        return cursor.count > 0
    }

}