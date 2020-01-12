package com.zhmyr.lab5

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.swipeRight
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.DrawerActions
import androidx.test.espresso.contrib.NavigationViewActions
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.uiautomator.UiDevice
import org.hamcrest.core.AllOf.allOf
import org.hamcrest.core.IsNot.not
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    lateinit var dev: UiDevice

    private fun isDisp(disp: Int) {
        fun isFirstActivity(exist: Boolean) {
            if (exist)
                onView(withId(R.id.but_first_2)).check(matches(isDisplayed()))
            else
                onView(withId(R.id.but_first_2)).check(doesNotExist())
        }


        fun isSecondActivity(exist: Boolean) {
            if (exist)
                onView(withId(R.id.but_second_1)).check(matches(isDisplayed()))
            else
                onView(withId(R.id.but_second_1)).check(doesNotExist())
        }

        fun isThirdActivity(exist: Boolean) {
            if (exist)
                onView(withId(R.id.but_third_1)).check(matches(isDisplayed()))
            else
                onView(withId(R.id.but_third_1)).check(doesNotExist())
        }

        when (disp) {
            1 -> {
                isFirstActivity(true)
                isSecondActivity(false)
                isThirdActivity(false)
                onView(withId(R.id.txt_about)).check(doesNotExist())
            }
            2 -> {
                isFirstActivity(false)
                isSecondActivity(true)
                isThirdActivity(false)
                onView(withId(R.id.txt_about)).check(doesNotExist())
            }
            3 -> {
                isFirstActivity(false)
                isSecondActivity(false)
                isThirdActivity(true)
                onView(withId(R.id.txt_about)).check(doesNotExist())
            }
            4 -> {
                isFirstActivity(false)
                isSecondActivity(false)
                isThirdActivity(false)
                onView(withId(R.id.txt_about)).check(matches(isDisplayed()))
            }
        }
    }

    @get:Rule
    var intentsTestRule = IntentsTestRule<First>(First::class.java)

    @Before
    fun init(){
        dev = UiDevice.getInstance(getInstrumentation())
    }

    @Test
    fun navigationSimple() {
        isDisp(1)
        onView(withId(R.id.but_first_2)).perform(click())
        onView(withId(R.id.but_second_3)).check(matches(isDisplayed()))
        isDisp(2)
        dev.pressBack()
        isDisp(1)
        onView(withId(R.id.but_first_2)).perform(click())
        onView(withId(R.id.but_second_3)).perform(click())
        isDisp(3)
        dev.pressBack()
        isDisp(2)
        dev.pressBack()
        isDisp(1)
        onView(withId(R.id.but_first_3)).perform(click())
        onView(withId(R.id.but_second_3)).perform(click())
        isDisp(3)
        dev.pressBack()
        isDisp(1)
    }

    @Test
    fun navigationAbout(){
        isDisp(1)
        onView(withId(R.id.drawer_layout_first)).perform(DrawerActions.open())
        onView(withId(R.id.nav_view_first)).perform(NavigationViewActions.navigateTo(R.id.about_item))
        isDisp(4)
        dev.pressBack()
        isDisp(1)
        onView(withId(R.id.but_first_2)).perform(click())
        isDisp(2)
        onView(withId(R.id.drawer_layout_second)).perform(DrawerActions.open())
        onView(withId(R.id.nav_view_second)).perform(NavigationViewActions.navigateTo(R.id.about_item))
        isDisp(4)
        dev.pressBack()
        isDisp(2)
        onView(withId(R.id.but_second_3)).perform(click())
        isDisp(3)
        onView(withId(R.id.drawer_layout_third)).perform(DrawerActions.open())
        onView(withId(R.id.nav_view_third)).perform(NavigationViewActions.navigateTo(R.id.about_item))
        isDisp(4)
        dev.pressBack()
        isDisp(3)
        dev.pressBack()
        dev.pressBack()
        isDisp(1)
    }

}
