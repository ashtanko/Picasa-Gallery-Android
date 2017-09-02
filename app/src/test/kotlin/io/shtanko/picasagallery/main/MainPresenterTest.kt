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

import com.nhaarman.mockito_kotlin.argumentCaptor
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import io.shtanko.picasagallery.data.AlbumDataSource
import io.shtanko.picasagallery.data.AlbumRepository
import io.shtanko.picasagallery.data.entity.Album
import io.shtanko.picasagallery.extensions.AlbumsList
import io.shtanko.picasagallery.view.main.MainContract
import io.shtanko.picasagallery.view.main.MainPresenter
import io.shtanko.picasagallery.view.util.getImages
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MainPresenterTest {
  private val albumRepository = mock<AlbumRepository>()
  private val view = mock<MainContract.View>()
  private val albumCallbackCaptor = argumentCaptor<AlbumDataSource.LoadAlbumsCallback>()

  private lateinit var presenter: MainPresenter

  @Before
  fun setUp() {
    MockitoAnnotations.initMocks(this)
    presenter = MainPresenter(albumRepository)
    presenter.takeView(view)
  }

  @Test
  fun get_not_available_albumsTest() {
    presenter.getAlbums()
    verify(view).setLoadingIndicator(true)
    verify(albumRepository, times(1)).getAlbums(albumCallbackCaptor.capture())
    albumCallbackCaptor.firstValue.onDataNotAvailable("Error")
    verify(view).onShowError("Error")
    verify(view).setLoadingIndicator(false)
  }

  @Test
  fun get_available_albumsTest() {
    presenter.getAlbums()
    verify(view).setLoadingIndicator(true)
    verify(albumRepository, times(1)).getAlbums(albumCallbackCaptor.capture())
    albumCallbackCaptor.firstValue.onAlbumsLoaded(getDummyAlbumsList())
    verify(view).onShowAlbums(getDummyAlbumsList())
    verify(view).setLoadingIndicator(false)
  }

  private fun getDummyAlbumsList(): AlbumsList {
    val dummyList = ArrayList<Album>()
    getImages().mapTo(dummyList) { Album("Item", it) }
    return dummyList
  }
}