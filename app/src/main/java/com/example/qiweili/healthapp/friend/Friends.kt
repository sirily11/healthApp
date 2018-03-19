package com.example.qiweili.healthapp.friend

import android.media.Image

/**
 * Created by qiweili on 2018/3/2.
 */
class Friends {
    val friends : MutableList<Person>? = null
    val friendsName : MutableList<String>? = null

    fun addFriend(name : String, profile : String, picture: ByteArray ){
        friends?.add(Person(name,profile,picture))
        friendsName?.add(name)
    }


    class Person(name : String, profile: String, picture : ByteArray){



    }

}
