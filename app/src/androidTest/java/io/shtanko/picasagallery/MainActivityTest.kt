package io.shtanko.picasagallery

import android.support.test.InstrumentationRegistry
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import io.shtanko.picasagallery.ui.activities.MainActivity
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class) class MainActivityTest {

  @get:Rule val main: ActivityTestRule<MainActivity> = ActivityTestRule(
      MainActivity::class.java)

  val context = InstrumentationRegistry.getTargetContext()
  var mainActivity: MainActivity? = null

  @Before fun setUp() {
    mainActivity = main.activity
  }

  @Test fun run() {
    Assert.assertNotNull(mainActivity)
    Assert.assertNotNull(context)
  }

}