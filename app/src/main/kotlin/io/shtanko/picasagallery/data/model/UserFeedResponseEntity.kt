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

package io.shtanko.picasagallery.data.model

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import java.io.Reader

data class UserFeedResponseEntity(
    @SerializedName("feed") var feed: UserFeedEntity,
    @SerializedName("version") var version: String,
    @SerializedName("encoding") var encoding: String,
    @SerializedName("gphoto" + "$" + "id") val albumId: SingleStringElementEntity
) {
  fun asJson() = Gson().toJson(this)

  object Deserializer : ResponseDeserializable<UserFeedResponseEntity> {
    override fun deserialize(reader: Reader): UserFeedResponseEntity = Gson().fromJson(reader,
        UserFeedResponseEntity::class.java)
  }
}