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

import io.reactivex.Flowable
import io.shtanko.picasagallery.data.album.AlbumDataSource
import io.shtanko.picasagallery.data.entity.album.Album
import io.shtanko.picasagallery.data.entity.album.AlbumType
import io.shtanko.picasagallery.extensions.AlbumsList
import io.shtanko.picasagallery.view.util.getImages
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MockAlbumDataSourceImpl @Inject constructor() : AlbumDataSource {

	override fun getAlbums(): Flowable<AlbumsList> {
		val dummyList = ArrayList<AlbumType>()
		return Flowable.just(getImages().mapTo(dummyList) { Album("Item", it, "", "") })
	}
}