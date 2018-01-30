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

package io.shtanko.picasagallery.internal

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import io.reactivex.Flowable
import io.shtanko.picasagallery.data.internal.InternalAlbumsRepository
import io.shtanko.picasagallery.extensions.ContentList
import io.shtanko.picasagallery.extensions.applyComputationScheduler
import io.shtanko.picasagallery.utils.TrampolineSchedulerRule
import io.shtanko.picasagallery.view.album.InternalAlbumsContract.View
import io.shtanko.picasagallery.view.album.InternalAlbumsPresenter
import io.shtanko.picasagallery.view.util.getContentData
import org.junit.Before
import org.junit.FixMethodOrder
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters.JVM
import org.mockito.MockitoAnnotations.initMocks
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
@FixMethodOrder(JVM)
class InternalAlbumsPresenterTest {

  private val view = mock<View>()
  private lateinit var presenter: InternalAlbumsPresenter
  private val exception = RuntimeException("Error")

  @Rule
  @JvmField
  var trampolineSchedulerRule = TrampolineSchedulerRule()

  @Before
  fun setUp() {
    initMocks(this)
    presenter = InternalAlbumsPresenter(FakeInternalAlbumsRepository())
    presenter.takeView(view)
  }

  @Test
  fun onSuccessLoadedContentTest() {
    presenter.getContent()
    verify(view, times(1)).setLoadingIndicator(true)
    verify(view, times(1)).showData(fakeContent)
    verify(view, times(1)).setLoadingIndicator(false)
  }

  @Test
  fun onFailureLoadedContentTest() {
    presenter = InternalAlbumsPresenter(FakeInternalAlbumsRepositoryWithError())
    presenter.takeView(view)
    presenter.getContent()
    verify(view, times(1)).setLoadingIndicator(true)
    verify(view, times(1)).showError(exception.localizedMessage)
    verify(view, times(1)).setLoadingIndicator(false)
  }

  inner class FakeInternalAlbumsRepository : InternalAlbumsRepository {
    override fun content(): Flowable<ContentList> =
        Flowable.fromIterable(fakeContent).toList().toFlowable()

  }

  inner class FakeInternalAlbumsRepositoryWithError : InternalAlbumsRepository {
    override fun content(): Flowable<ContentList> =
        Flowable.fromIterable(fakeContent).concatWith(
            Flowable.error { exception }).toList().toFlowable()

  }

  private val fakeContent = getContentData()

}