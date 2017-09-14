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
import com.nhaarman.mockito_kotlin.verify
import io.shtanko.picasagallery.data.entity.photo.Photo
import io.shtanko.picasagallery.data.photo.PhotosRepository
import io.shtanko.picasagallery.view.delegate.ViewType
import io.shtanko.picasagallery.view.photo.PhotosContract.View
import io.shtanko.picasagallery.view.photo.PhotosPresenter
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class PhotosPresenterTest {

  private val view = mock<View>()
  private var mockRepository = mock<PhotosRepository>()
  private lateinit var presenter: PhotosPresenter

  @Before
  fun setUp() {
    MockitoAnnotations.initMocks(this)
    presenter = PhotosPresenter(mockRepository)
    presenter.takeView(view)
  }

  @Test
  fun on_photo_clickTest() {
    presenter.onPhotoClick(getFakePhoto())
    verify(view).viewPhoto(getFakePhoto())
  }

  private fun getFakePhoto(): ViewType = Photo(0)

}