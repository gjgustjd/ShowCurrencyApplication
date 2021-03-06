package com.example.showcurrencyapplication.activity

import android.view.View
import android.widget.TextView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.example.showcurrencyapplication.R
import org.hamcrest.Matcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@LargeTest
@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @Rule
    @JvmField
    val mActivityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun showAlertMessageTest() {
        val edtMoneyAmount = onView(withId(R.id.edt_main_money_amount))
        edtMoneyAmount.perform(clearText(), typeText("0"), closeSoftKeyboard())

        val expectedText = "송금액이 바르지 않습니다."
        val textView = onView(withId(R.id.txt_main_exchanged_money))
        textView.check(matches(withText(expectedText)))
        textView.check(matches(hasTextColor(R.color.red)))

        edtMoneyAmount.perform(clearText(), typeText("10001"), closeSoftKeyboard())
        textView.check(matches(withText(expectedText)))
        textView.check(matches(hasTextColor(R.color.red)))
    }

    @Test
    fun showCalculatedMoneyTest() {
        onView(withId(R.id.edt_main_money_amount))
            .perform(typeText("10"), closeSoftKeyboard())

        val exchangeRate = withId(R.id.txt_main_exchange_rate).getTag().toString().replace(",", "")
        val sendAmount = withId(R.id.edt_main_money_amount).getText().toString()
        val exchangedMoneyText = (exchangeRate.toDouble() * sendAmount.toDouble())

        val txtExchangedMoney = onView(withId(R.id.txt_main_exchanged_money))
        txtExchangedMoney.check(
            matches(
                withText(
                    String.format(
                        "수취금액은 %,2.2f KRW입니다",
                        exchangedMoneyText
                    )
                )
            )
        )
        txtExchangedMoney.check(matches(hasTextColor(R.color.black)))

    }

    private fun Matcher<View?>.getText(): String? {
        val stringHolder = arrayOf<String?>(null)
        onView(this).perform(object : ViewAction {
            override fun getConstraints(): Matcher<View> {
                return isAssignableFrom(TextView::class.java)
            }

            override fun getDescription(): String {
                return "getting text from a TextView"
            }

            override fun perform(uiController: UiController?, view: View) {
                val tv = view as TextView
                stringHolder[0] = tv.text.toString()
            }
        })
        return stringHolder[0]
    }

    private fun Matcher<View?>.getTag(): String? {
        val stringHolder = arrayOf<String?>(null)
        onView(this).perform(object : ViewAction {
            override fun getConstraints(): Matcher<View> {
                return isAssignableFrom(TextView::class.java)
            }

            override fun getDescription(): String {
                return "getting text from a TextView"
            }

            override fun perform(uiController: UiController?, view: View) {
                val tv = view as TextView
                stringHolder[0] = tv.tag.toString()
            }
        })
        return stringHolder[0]
    }
}