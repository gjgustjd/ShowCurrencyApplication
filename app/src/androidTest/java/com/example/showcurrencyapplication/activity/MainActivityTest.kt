package com.example.showcurrencyapplication.activity

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.example.showcurrencyapplication.R
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun showAlertMessageTest() {
        onView(withId(R.id.edt_main_money_amount))
            .perform(typeText("0"), closeSoftKeyboard())

        val textView = onView(withId(R.id.txt_main_exchanged_money))
        textView.check(matches(withText("송금액이 바르지 않습니다.")))
    }
}