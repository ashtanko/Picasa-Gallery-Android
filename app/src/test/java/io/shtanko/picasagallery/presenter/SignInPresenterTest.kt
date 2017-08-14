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

package io.shtanko.picasagallery.presenter

import android.content.SharedPreferences
import com.nhaarman.mockito_kotlin.mock
import io.shtanko.picasagallery.data.PreferenceHelper
import io.shtanko.picasagallery.view.auth.SignInContract
import io.shtanko.picasagallery.view.auth.SignInPresenter
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class) class SignInPresenterTest {

  val view = mock<SignInContract.View>()
  val sharedPreferences = mock<SharedPreferences>()
  val preferenceHelper = PreferenceHelper(sharedPreferences)

  private lateinit var presenter: SignInPresenter

  @Before fun setUp() {
    MockitoAnnotations.initMocks(this)
    presenter = SignInPresenter(view, preferenceHelper)
  }

  @Test
  fun not_nullTest() {
    assertNotNull(presenter)
  }

}