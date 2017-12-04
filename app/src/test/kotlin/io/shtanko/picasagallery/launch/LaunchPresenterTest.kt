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

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.never
import io.shtanko.picasagallery.data.DefaultObserver
import io.shtanko.picasagallery.data.entity.user.User
import io.shtanko.picasagallery.data.user.GetUserDetails
import io.shtanko.picasagallery.view.launch.LaunchContract.View
import io.shtanko.picasagallery.view.launch.LaunchPresenter
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations.initMocks
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner.Silent::class)
class LaunchPresenterTest {

  private val getUserDetails = mock<GetUserDetails>()
  private val view = mock<View>()
  private lateinit var presenter: LaunchPresenter

  @Before
  fun setUp() {
    initMocks(this)
    presenter = LaunchPresenter(getUserDetails)
    presenter.takeView(view)
  }

  @Test
  fun isSignedInTest() {
    presenter.isSignIn()
    verify(view, never()).onSignedIn()
  }

  @Test
  fun isSignedOutTest() {
    presenter.isSignIn()
    verify(view, never()).onSignedOut()
  }

  inner class UserListObserver : DefaultObserver<User>() {

  }
}
