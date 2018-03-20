package com.example.qiweili.healthapp

import android.arch.persistence.room.Database
import android.content.Context
import android.test.mock.MockContext
import com.example.qiweili.utils
import org.junit.Test

import org.junit.Assert.*
import org.mockito.Mock

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    @Test
    fun isSameDayTest(){
        val day = DatabaseHelper.getCurrentDate()
        assertTrue("This should be the same day",DatabaseHelper.isSameDay(day,day))
    }
    @Test
    fun isSameDayTest2(){
        val day = DatabaseHelper.getCurrentDate()
        assertFalse("This should not be the same day",DatabaseHelper.isSameDay(day,(day.toInt()+1).toString()))
    }
}
