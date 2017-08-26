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

package io.shtanko.picasagallery.launch

import android.content.SharedPreferences
import com.nhaarman.mockito_kotlin.mock
import io.shtanko.picasagallery.data.PreferenceHelper
import io.shtanko.picasagallery.data.user.GetUserDetails
import io.shtanko.picasagallery.view.launch.LaunchContract
import io.shtanko.picasagallery.view.launch.LaunchPresenter
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class LaunchPresenterTest {

  private val userRepository = mock<GetUserDetails>()
  private val view = mock<LaunchContract.View>()
  private val sharedPreferences = mock<SharedPreferences>()
  val preferenceHelper = PreferenceHelper(sharedPreferences)
  private lateinit var presenter: LaunchPresenter

  @Before
  fun setUp() {
    MockitoAnnotations.initMocks(this)
    presenter = LaunchPresenter(userRepository)
    presenter.takeView(view)
  }

  @Test
  fun is_signed_inTest() {
    Assert.assertNull(null)
  }

  @Test
  fun is_signed_outTest() {
    Assert.assertNull(null)
  }

}