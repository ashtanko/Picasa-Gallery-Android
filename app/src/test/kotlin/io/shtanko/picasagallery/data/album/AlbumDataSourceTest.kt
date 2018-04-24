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

package io.shtanko.picasagallery.data.album

import com.nhaarman.mockito_kotlin.given
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import io.reactivex.Observable
import io.shtanko.picasagallery.data.api.ApiManager
import io.shtanko.picasagallery.data.model.UserFeedResponseEntity
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.exceptions.misusing.UnnecessaryStubbingException
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner.Silent::class)
class AlbumDataSourceTest {

  private val FAKE_USER_ID = "123"

  private val mockApiManager = mock<ApiManager>()
  private val albumEntityMapper = mock<AlbumEntityMapper>()

  @Before
  fun setUp() {
  }

  @Test
  @Throws(UnnecessaryStubbingException::class)
  fun `when get albums successfully`() {
    val fakeUserEntity = mock<UserFeedResponseEntity>()
    val fakeUserObservable = Observable.just(fakeUserEntity)
    given(mockApiManager.getUser(FAKE_USER_ID)).willReturn(fakeUserObservable)
    verify(mockApiManager, times(0)).getUser(FAKE_USER_ID)
  }
}