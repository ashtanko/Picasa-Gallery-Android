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

package io.shtanko.picasagallery.data.photo

import io.reactivex.Flowable
import io.shtanko.picasagallery.data.entity.photo.Photo
import io.shtanko.picasagallery.extensions.PhotosList
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Interface that represents a Repository for getting [Photo] related data.
 */
interface PhotosRepository {

  /**
   * Get an [rx.Observable] which will emit a List of [Photo].
   */
  fun photos(): Flowable<PhotosList>

}

@Singleton
class PhotosRepositoryImpl @Inject constructor(
  private val dataSource: PhotosDataSource
) : PhotosRepository {

  override fun photos(): Flowable<PhotosList> = dataSource.getPhotos()
}