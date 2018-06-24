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

package io.shtanko.picasagallery.photo

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import io.reactivex.Flowable
import io.shtanko.picasagallery.data.entity.photo.Photo
import io.shtanko.picasagallery.data.photo.PhotosRepository
import io.shtanko.picasagallery.extensions.PhotosList
import io.shtanko.picasagallery.ui.delegate.ViewType
import io.shtanko.picasagallery.ui.photo.PhotosContract.View
import io.shtanko.picasagallery.ui.photo.PhotosPresenter
import io.shtanko.picasagallery.ui.util.getPhotosData
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.MockitoAnnotations.initMocks
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class PhotosPresenterTest {

  private val view = mock<View>()
  private lateinit var presenter: PhotosPresenter
  private val exception = RuntimeException("Error")

  @Before
  fun setUp() {
    initMocks(this)
    presenter = PhotosPresenter(FakePhotosRepository())
    presenter.takeView(view)
  }

  @Test
  fun `on photo item click`() {
    presenter.onPhotoClick(getFakePhoto())
    verify(view, times(1)).viewPhoto(getFakePhoto())
  }

  @Test
  fun `on success get photos`() {
    presenter.getPhotos()
    verify(view, times(1)).showPhotos(getFakePhotos())
  }

  @Test
  fun `on error get photos`() {
    presenter = PhotosPresenter(FakePhotosRepositoryWithException())
    presenter.takeView(view)
    presenter.getPhotos()
    verify(view, times(1)).showError(exception.localizedMessage)
  }

  inner class FakePhotosRepository : PhotosRepository {
    override fun photos(): Flowable<PhotosList> =
        Flowable.fromIterable(getFakePhotos()).toList().toFlowable()
  }

  inner class FakePhotosRepositoryWithException : PhotosRepository {
    override fun photos(): Flowable<PhotosList> =
        Flowable.fromIterable(getFakePhotos()).concatWith(
            Flowable.error { exception }).toList().toFlowable()
  }

  private fun getFakePhoto(): ViewType = Photo(0)
  private fun getFakePhotos() = getPhotosData()

}