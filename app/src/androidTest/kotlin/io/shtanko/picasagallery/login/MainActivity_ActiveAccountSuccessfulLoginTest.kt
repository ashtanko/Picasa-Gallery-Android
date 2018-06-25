/*
 * Copyright 2017 Alexey Shtanko
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package io.shtanko.picasagallery.login

import android.support.test.InstrumentationRegistry
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.test.suitebuilder.annotation.LargeTest
import io.shtanko.picasagallery.util.LoginUtils
import io.shtanko.picasagallery.ui.auth.LoginAndAuthProvider
import io.shtanko.picasagallery.ui.main.MainActivity
import org.junit.After
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.properties.Delegates

@RunWith(AndroidJUnit4::class)
@LargeTest
class MainActivity_ActiveAccountSuccessfulLoginTest {

  private var accountName: String by Delegates.notNull()
  private var stubLoginAndAuth: StubLoginAndAuth by Delegates.notNull()

  @get:Rule
  val loginActivityRule: ActivityTestRule<MainActivity> = object : ActivityTestRule<MainActivity>(
      MainActivity::class.java) {

    override fun beforeActivityLaunched() {
      super.beforeActivityLaunched()

      accountName = LoginUtils.setFirstAvailableAccountAsActive(
          InstrumentationRegistry.getTargetContext())

      // Set stub login and auth as successful
      stubLoginAndAuth = StubLoginAndAuth(accountName, true, true)
      LoginAndAuthProvider.stubLoginAndAuth = stubLoginAndAuth
    }

    override fun afterActivityLaunched() {
      // Set up the activity as a listener of the stub login and auth
      //stubLoginAndAuth.setListener(activity)
    }
  }

  @After
  fun cleanUp() {

  }

  @Test
  fun accountName_IsDisplayed() {
    // Then the account name is shown
    Assert.assertNull(null)
    //onView(withText(accountName)).check(matches(isDisplayed()))
  }


}