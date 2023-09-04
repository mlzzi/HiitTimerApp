package com.example.hiit_timer_app

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.hiit_timer_app.ui.TimerConfiguration
import com.example.hiit_timer_app.ui.TimerUiState
import com.example.hiit_timer_app.ui.TimerViewModel
import com.example.hiit_timer_app.util.TimerUtil.calculateSpinProgress
import com.example.hiit_timer_app.util.TimerUtil.formatTime
import org.junit.Test

import org.junit.Assert.*
import org.junit.Rule
import org.junit.runner.RunWith

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun testFormatTimer() {
        val timeInSeconds = 30
        val expectedOutput = "00:30"
        val actualValue = formatTime(timeInSeconds)
        assertEquals(expectedOutput, actualValue)
    }

    @Test
    fun testFormatTimer2() {
        val timeInSeconds = 60
        val expectedOutput = "01:00"
        val actualValue = formatTime(timeInSeconds)
        assertEquals(expectedOutput, actualValue)
    }

    @Test
    fun testCalculateSpinProgress() {
        val remainingTime = 15
        val initialTime = 30
        val expectedOutput = 0.5f
        val actualValue = calculateSpinProgress(remainingTime, initialTime)
        assertEquals(expectedOutput, actualValue)
    }

    @Test
    fun testCalculateSpinProgress2() {
        val remainingTime = 10
        val initialTime = 30
        val expectedOutput = 0.6666666f
        val actualValue = calculateSpinProgress(remainingTime, initialTime)
        assertEquals(expectedOutput, actualValue)
    }
    @Test
    fun testCalculateSpinProgress3() {
        val remainingTime = 5
        val initialTime = 30
        val expectedOutput = 0.8333333f
        val actualValue = calculateSpinProgress(remainingTime, initialTime)
        assertEquals(expectedOutput, actualValue)
    }
}