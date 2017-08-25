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

package io.shtanko.picasagallery

import io.shtanko.picasagallery.data.AlbumDataSource
import io.shtanko.picasagallery.data.AlbumDataSource.LoadAlbumsCallback
import io.shtanko.picasagallery.data.api.ApiManager
import io.shtanko.picasagallery.data.entity.AlbumEntity
import javax.inject.Inject
import javax.inject.Singleton
//TODO remove ApiManager injection
@Singleton
class MockAlbumDataSourceImpl @Inject constructor(var apiManager: ApiManager) : AlbumDataSource {

  override fun getAlbums(callback: LoadAlbumsCallback) {
    val dummyList = ArrayList<AlbumEntity>()
    for (i in 1..10000) {
      dummyList.add(AlbumEntity("Item: $i"))
    }
    callback.onAlbumsLoaded(dummyList)
  }
}