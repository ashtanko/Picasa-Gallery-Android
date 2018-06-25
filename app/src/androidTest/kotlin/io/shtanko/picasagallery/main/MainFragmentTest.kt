/*
 * Copyright 2018 Alexey Shtanko
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

package io.shtanko.picasagallery.main

import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import io.shtanko.picasagallery.R
import io.shtanko.picasagallery.ui.main.MainActivity
import io.shtanko.picasagallery.ui.main.MainFragment
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainFragmentTest {

    val mainActivityRule = ActivityTestRule(MainActivity::class.java, true, true)

    @Rule
    fun rule() = mainActivityRule

    @Before
    fun init() {
        rule().activity.supportFragmentManager.beginTransaction().add(R.id.content_frame,
                MainFragment()).commit()
    }

    @Test
    fun recyclerViewTest() {
        Assert.assertNotNull(mainActivityRule)
//        Espresso.onData(allOf(withId(R.id.album))).inAdapterView(
//                withId(R.id.main_content_grid)).atPosition(
//                0).perform(
//                click())
    }

}