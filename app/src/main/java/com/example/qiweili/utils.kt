package com.example.qiweili

import com.google.firebase.database.FirebaseDatabase

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





    }

}