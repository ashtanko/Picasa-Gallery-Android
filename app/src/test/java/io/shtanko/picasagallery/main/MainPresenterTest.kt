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

package io.shtanko.picasagallery.main

import com.nhaarman.mockito_kotlin.mock
import io.shtanko.picasagallery.view.main.MainContract
import io.shtanko.picasagallery.view.main.MainPresenter
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class) class MainPresenterTest {
  val view = mock<MainContract.View>()

  private lateinit var presenter: MainPresenter

  @Before fun setUp() {
    MockitoAnnotations.initMocks(this)
    presenter = MainPresenter()
  }

  @Test
  fun not_nullTest() {
    assertNotNull(presenter)
  }

}