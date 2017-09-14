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
import io.shtanko.picasagallery.data.album.AlbumRepositoryImpl
import io.shtanko.picasagallery.data.entity.album.Album
import io.shtanko.picasagallery.data.entity.album.AlbumType
import io.shtanko.picasagallery.extensions.AlbumsList
import io.shtanko.picasagallery.view.main.MainContract.View
import io.shtanko.picasagallery.view.main.MainPresenter
import io.shtanko.picasagallery.view.util.getImages
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MainPresenterTest {

  private val albumRepository = mock<AlbumRepositoryImpl>()
  private val view = mock<View>()

  private lateinit var presenter: MainPresenter

  @Before
  fun setUp() {
    MockitoAnnotations.initMocks(this)
    presenter = MainPresenter(albumRepository)
    presenter.takeView(view)
  }

  @Test
  fun on_album_clickTest() {
    val album = getDummyAlbumsList()[0]
    presenter.onAlbumClick(album)
    verify(view).viewAlbum(album)
  }

  @Test
  fun album_clickTest() {
    val dummyModel = Album("", "")
    presenter.onAlbumClick(dummyModel)
    verify(view, times(1)).viewAlbum(dummyModel)
  }

  private fun getDummyAlbumsList(): AlbumsList {
    val dummyList = ArrayList<AlbumType>()
    getImages().mapTo(dummyList) { Album("Item", it) }
    return dummyList
  }
}