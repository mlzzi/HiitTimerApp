package com.example.hiit_timer_app

import android.app.Activity
import android.widget.TextView
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.hiit_timer_app.model.TimerType
import com.example.hiit_timer_app.ui.TimerApp
import com.example.hiit_timer_app.ui.TimerUiState
import org.junit.Assert.*
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
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.example.hiit_timer_app", appContext.packageName)
    }
}

class TimerUITests {

    @get:Rule
    val rule = createComposeRule()

    @Test
    fun set_timer_values() {
        rule.setContent {
            TimerApp(
                TimerUiState(
                    currentTimerType = TimerType.ACTIVE,
                    timeActive = 5,
                    timeRest = 6,
                    progress = 1f,
                    initial = 5,
                    current = 5,
                    rounds = 1,
                    currentRound = 2,
                    sound = true,
                    vibrate = false,
                    countdown = false
                )
            )
        }

//        rule.onNodeWithContentDescription("Active Button").performClick()
        rule.onAllNodesWithText("Active Button")[24].assertIsDisplayed()
        rule.onAllNodesWithText("Active Button")[29].assertIsDisplayed()
    }

}