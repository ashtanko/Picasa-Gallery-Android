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
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import io.reactivex.Flowable
import io.shtanko.picasagallery.data.album.AlbumRepository
import io.shtanko.picasagallery.data.entity.album.Album
import io.shtanko.picasagallery.data.entity.album.AlbumType
import io.shtanko.picasagallery.extensions.AlbumsList
import io.shtanko.picasagallery.utils.TrampolineSchedulerRule
import io.shtanko.picasagallery.ui.main.MainContract.View
import io.shtanko.picasagallery.ui.main.MainPresenter
import io.shtanko.picasagallery.ui.util.getImages
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
class MainPresenterTest {

  private val view = mock<View>()
  private lateinit var presenter: MainPresenter
  private val exception = RuntimeException("Error")

  @Rule @JvmField
  var trampolineSchedulerRule = TrampolineSchedulerRule()

  @Before
  fun setUp() {
    initMocks(this)
    presenter = MainPresenter(FakeAlbumRepository())
    presenter.takeView(view)
  }

  @Test
  fun `on dummy album click`() {
    val album = getDummyAlbumsList()[0]
    presenter.onAlbumClick(album)
    verify(view).viewAlbum(album)
  }

  @Test
  fun `on custom album click`() {
    val dummyModel = Album("", "", "", "")
    presenter.onAlbumClick(dummyModel)
    verify(view, times(1)).viewAlbum(dummyModel)
  }

  @Test
  fun `when get albums successful`() {
    presenter.getAlbums()
    verify(view, times(1)).onShowAlbums(getDummyAlbumsList())
    verify(view, times(1)).setLoadingIndicator(false)
  }

  @Test
  fun `when get albums failure`() {
    presenter = MainPresenter(FakeAlbumRepositoryWithException())
    presenter.takeView(view)
    presenter.getAlbums()
    verify(view, times(1)).showError(exception.localizedMessage)
    verify(view, times(1)).setLoadingIndicator(false)
  }

  inner class FakeAlbumRepository : AlbumRepository {
    override fun albums(): Flowable<AlbumsList> = Flowable.just(getDummyAlbumsList())
  }

  inner class FakeAlbumRepositoryWithException : AlbumRepository {
    override fun albums(): Flowable<AlbumsList> = Flowable.fromIterable(
        getDummyAlbumsList()).concatWith(
        Flowable.error { exception }).toList().toFlowable()
  }

  private fun getDummyAlbumsList(): AlbumsList {
    val dummyList = ArrayList<AlbumType>()
    getImages().mapTo(dummyList) { Album("", "Item", it, "") }
    return dummyList
  }
}