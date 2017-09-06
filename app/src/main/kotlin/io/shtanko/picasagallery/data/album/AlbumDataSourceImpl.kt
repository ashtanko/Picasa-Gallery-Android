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

import io.reactivex.observers.DefaultObserver
import io.shtanko.picasagallery.core.prefs.PreferenceHelper
import io.shtanko.picasagallery.data.album.AlbumDataSource.LoadAlbumsCallback
import io.shtanko.picasagallery.data.api.ApiManager
import io.shtanko.picasagallery.data.entity.album.AlbumType
import io.shtanko.picasagallery.data.model.UserFeedResponseEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AlbumDataSourceImpl @Inject constructor(
    var apiManager: ApiManager,
    var preferencesHelper: PreferenceHelper,
    var albumEntityMapper: AlbumEntityMapper) : AlbumDataSource {

  override fun getAlbums(callback: LoadAlbumsCallback) {

    apiManager.getUser(preferencesHelper.getUserId()).subscribe(
        object : DefaultObserver<UserFeedResponseEntity>() {
          override fun onComplete() {
          }

          override fun onError(e: Throwable) {
            callback.onDataNotAvailable(e.localizedMessage)
          }

          override fun onNext(userFeedResponseEntity: UserFeedResponseEntity) {
            val list = ArrayList<AlbumType>()
            userFeedResponseEntity.feed.entry.forEach { it ->
              list.add(albumEntityMapper.transform(it))
            }
            callback.onAlbumsLoaded(list)
          }

        })
  }
}