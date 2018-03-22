package com.example.qiweili.healthapp

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.action.ViewActions.typeText
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.view.Gravity
import com.example.qiweili.healthapp.pages.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LoginTest {
    @get:Rule
    public var mActivityRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun signUpTest(){
        onView(withId(R.id.LoginBtn))
                .perform(click())
        onView(withId(R.id.SignUp))
                .perform(click())
        onView(withId(R.id.signin_username))
                .perform(typeText("abcd@abcd.com"))
        onView(withId(R.id.signin_password))
                .perform(typeText("123456"))
        onView(withId(R.id.signin_signup))
                .perform(click())
    }

    @Test
    fun loginTest() {
        onView(withId(R.id.LoginBtn))
                .perform(click())
        onView(withId(R.id.userName))
                .perform(typeText("abc@abc.com"))
        onView(withId(R.id.password))
                .perform(typeText("123456"))
        onView(withId(R.id.signInBtn))
                .perform(click())
    }

}
