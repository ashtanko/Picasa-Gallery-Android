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

package io.shtanko.picasagallery.data.api

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.shtanko.picasagallery.data.model.AlbumsResponse
import io.shtanko.picasagallery.data.model.UserFeedResponse

class ApiManagerImpl constructor(var apiService: PicasaService) : ApiManager {

  override fun getUser(userId: String): Observable<UserFeedResponse> {
    return apiService.getUser(userId).subscribeOn(AndroidSchedulers.mainThread()).observeOn(
        Schedulers.io())
  }

  override fun getAlbums(userId: String, albumId: String): Observable<AlbumsResponse> {
    return apiService.getAlbums(userId, albumId).subscribeOn(
        AndroidSchedulers.mainThread()).observeOn(
        Schedulers.io())
  }


}