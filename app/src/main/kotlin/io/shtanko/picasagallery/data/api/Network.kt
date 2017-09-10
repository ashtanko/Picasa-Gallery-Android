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

import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.rx.rx_object
import com.github.kittinunf.result.Result
import com.google.gson.Gson
import io.reactivex.Maybe
import io.reactivex.Observable
import io.shtanko.picasagallery.Config
import io.shtanko.picasagallery.data.model.AlbumsResponseEntity
import io.shtanko.picasagallery.data.model.UserFeedResponseEntity
import io.shtanko.picasagallery.data.user.UserException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Network @Inject constructor() : PicasaService {

  init {
    FuelManager.instance.basePath = Config.PICASA_BASE_API_URL
  }

  override fun getUser(userId: String): Observable<UserFeedResponseEntity> {
    return Config.configureUserPath(userId).httpGet().rx_object(
        UserFeedResponseEntity.Deserializer)
        .flatMapMaybe {
          when (it) {
            is Result.Success -> {
              if (!it.value.feed.id.body.isEmpty()) {
                Maybe.just(it.value)
              } else {
                Maybe.empty<UserFeedResponseEntity>()
              }
            }
            is Result.Failure -> {
              try {
                Maybe.error<UserFeedResponseEntity>(
                    Gson().fromJson(it.error.response.data.toString(Charsets.UTF_8),
                        UserException::class.java))
              } catch (e: Throwable) {
                Maybe.error<UserFeedResponseEntity>(UserException(
                    it.error.message ?: it.error.exception.message ?: it.error.response.responseMessage))
              }
            }
          }
        }.toObservable()
  }

  override fun getAlbums(userId: String, albumId: String): Observable<AlbumsResponseEntity> {

    Config.configureAlbumsPath(userId, albumId).httpGet()

    return Observable.empty()
  }
}