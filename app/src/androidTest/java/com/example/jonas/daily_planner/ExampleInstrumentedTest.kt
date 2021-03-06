package com.example.jonas.daily_planner

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

/**
 * Instrumented calculateWakeUpTime, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under calculateWakeUpTime.
        val appContext = InstrumentationRegistry.getTargetContext()
        assertEquals("com.example.jonas.daily_planner", appContext.packageName)
    }
}
