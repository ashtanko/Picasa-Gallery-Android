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

import io.shtanko.picasagallery.data.entity.album.Album
import io.shtanko.picasagallery.data.mapper.SimpleMapper
import io.shtanko.picasagallery.data.model.AlbumEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AlbumEntityMapper @Inject constructor() : SimpleMapper<AlbumEntity, Album> {

  override fun transform(from: AlbumEntity?): Album = Album(from?.photoId?.body,
      from?.title?.body, from?.media?.content?.get(0)?.url, from?.albumId?.body)

  override fun transform(fromCollection: Collection<AlbumEntity>): List<Album> =
      fromCollection.mapTo(ArrayList(20)) { transform(it) }
}