package com.zhmyr.lab5

import android.content.pm.ActivityInfo
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @get:Rule var activityScenarioRule = ActivityTestRule<MainActivity>(MainActivity::class.java)
    @Test
    fun rotate() {
        onView(withId(R.id.ed)).perform(typeText("test"), closeSoftKeyboard())
        onView(withId(R.id.but)).perform(click())
            .check(matches(withText("Clicked")))
        activityScenarioRule.getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)
        onView(withId(R.id.but)).check(matches(withText("Button")))
        onView(withId(R.id.ed)).check(matches(withText("Nametest")))
    }
}