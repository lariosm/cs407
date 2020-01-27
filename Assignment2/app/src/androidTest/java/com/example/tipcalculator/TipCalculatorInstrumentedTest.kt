package com.example.tipcalculator

import android.view.View
import android.widget.SeekBar
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import org.hamcrest.Matchers
import org.hamcrest.Matchers.anything
import org.hamcrest.Matchers.containsString
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
@LargeTest
class TipCalculatorInstrumentedTest {
    @get:Rule
    var activityRule: ActivityTestRule<MainActivity>
        = ActivityTestRule<MainActivity>(MainActivity::class.java)

    fun setProgress(progress: Int): ViewAction? {
        return object : ViewAction {
            override fun perform(uiController: UiController?, view: View) {
                val seekBar = view as SeekBar
                seekBar.progress = progress
            }

            override fun getDescription(): String {
                return "Set a progress on a SeekBar"
            }

            override fun getConstraints(): org.hamcrest.Matcher<View> {
                return isAssignableFrom(SeekBar::class.java)
            }
        }
    }

    @Test
    fun changeBillInput() {
        onView(withId(R.id.editBillInput))
            .perform(typeText("45.76"), closeSoftKeyboard())

        onView(withId(R.id.textTipValue))
            .check(matches(withText("$0.00")))

        onView(withId(R.id.textBillValue))
            .check(matches(withText("$45.76")))

        onView(withId(R.id.textPerPersonValue))
            .check(matches(withText("$45.76")))
    }

    @Test
    fun changeTipSpinner() {
        onView(withId(R.id.tipDropDown))
            .perform(click())

        onData(anything()).atPosition(3)
            .perform(click())

        onView(withId(R.id.tipDropDown))
            .check(matches((withSpinnerText(containsString("15")))))

        onView(withId(R.id.textTipValue))
            .check(matches(withText("$3.75")))

        onView(withId(R.id.textBillValue))
            .check(matches(withText("$28.73")))

        onView(withId(R.id.textPerPersonValue))
            .check(matches(withText("$28.73")))
    }

    @Test
    fun changeSeekBar() {
        onView(withId(R.id.peopleSlider))
            .perform(setProgress(6))

        onView(withId(R.id.textPeopleValue))
            .check(matches(withText("7")))

        onView(withId(R.id.textTipValue))
            .check(matches(withText("$0.00")))

        onView(withId(R.id.textBillValue))
            .check(matches(withText("$24.98")))

        onView(withId(R.id.textPerPersonValue))
            .check(matches(withText("$3.57")))
    }

    @Test
    fun changeAllInputs() {
        onView(withId(R.id.editBillInput))
            .perform(typeText("74.31"), closeSoftKeyboard())

        onView(withId(R.id.tipDropDown))
            .perform(click())

        onData(anything()).atPosition(14)
            .perform(click())

        onView(withId(R.id.tipDropDown))
            .check(matches((withSpinnerText(containsString("70")))))

        onView(withId(R.id.peopleSlider))
            .perform(setProgress(9))

        onView(withId(R.id.textPeopleValue))
            .check(matches(withText("10")))

        onView(withId(R.id.textTipValue))
            .check(matches(withText("$52.02")))

        onView(withId(R.id.textBillValue))
            .check(matches(withText("$126.33")))

        onView(withId(R.id.textPerPersonValue))
            .check(matches(withText("$12.63")))
    }
}
