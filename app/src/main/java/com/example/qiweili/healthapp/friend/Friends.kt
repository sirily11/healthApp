package com.example.qiweili.healthapp.friend

import android.graphics.Bitmap
import android.media.Image
import com.example.qiweili.healthapp.DatabaseHelper
import com.google.gson.GsonBuilder

/**
 * Created by qiweili on 2018/3/2.
 */
class Friend(val name : String, val profilePic : String?, val aboutme : String?, val account_id : String) {

    fun getProfilePic() : Bitmap?{
        if(profilePic == null){
            return null
        }
        val gson = GsonBuilder().create()
        val images = gson.fromJson(profilePic,ByteArray ::class.java)
        return DatabaseHelper.getImage(images)
    }

}
