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

package io.shtanko.picasagallery.core.album

import com.nhaarman.mockito_kotlin.mock
import io.shtanko.picasagallery.data.album.AlbumEntityMapper
import io.shtanko.picasagallery.data.entity.album.Album
import io.shtanko.picasagallery.data.model.AlbumEntity
import io.shtanko.picasagallery.data.model.MediaAndGroups
import io.shtanko.picasagallery.data.model.MediaContent
import io.shtanko.picasagallery.data.model.SingleIntegerElementEntity
import io.shtanko.picasagallery.data.model.SingleStringElementEntity
import io.shtanko.picasagallery.data.model.TitleTypeEntity
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.instanceOf
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class AlbumEntityDataMapperTest {

	private val FAKE_ALBUM_ID = "0"
	private val FAKE_ALBUM_TITLE = "My awesome summer"
	private val FAKE_ALBUM_PUBLISHED = "0"
	private val FAKE_ALBUM_UPDATED = "0"
	private val FAKE_ALBUM_SUMMARY = "0"
	private val FAKE_ALBUM_RIGHTS = "0"

	private val FAKE_ALBUM_URL = "0"
	private val FAKE_ALBUM_TYPE = "0"
	private val FAKE_ALBUM_MEDIUM = "0"
	private val FAKE_ID = "0"
	private var albumEntityDataMapper: AlbumEntityMapper? = null

	@Before
	@Throws(Exception::class)
	fun setUp() {
		albumEntityDataMapper = AlbumEntityMapper()
	}

	@Test
	fun test_transform_album_EntityTest() {
		val entity = createFakeAlbumEntity()
		val album = albumEntityDataMapper?.transform(entity)
		assertThat(album, `is`(instanceOf(Album::class.java)))
		assertThat(album?.title, `is`(FAKE_ALBUM_TITLE))
	}

	@Test
	fun test_transform_album_entity_CollectionTest() {
		val mockAlbumEntityOne = mock<AlbumEntity>()
		val mockAlbumEntityTwo = mock<AlbumEntity>()

		val albumEntityList = ArrayList<AlbumEntity>()
		albumEntityList.add(mockAlbumEntityOne)
		albumEntityList.add(mockAlbumEntityTwo)

		val collection = albumEntityDataMapper?.transform(albumEntityList)
		assertThat(collection?.first(), `is`(Album::class.java))
		assertThat(collection?.last(), `is`(Album::class.java))
		assertThat(collection?.size, `is`(2))
	}


	private fun createFakeAlbumEntity(): AlbumEntity {
		val fakeAlbumId = SingleStringElementEntity(FAKE_ALBUM_ID)
		val fakeTitle = TitleTypeEntity(FAKE_ALBUM_TITLE, "")
		val fakePublished = SingleStringElementEntity(FAKE_ALBUM_PUBLISHED)
		val fakeUpdated = SingleStringElementEntity(FAKE_ALBUM_UPDATED)
		val fakeSummary = TitleTypeEntity(FAKE_ALBUM_SUMMARY, "")
		val fakeRights = TitleTypeEntity(FAKE_ALBUM_RIGHTS, "")
		val fakePhotoId = SingleStringElementEntity(FAKE_ID)
		val fakeId = SingleStringElementEntity(FAKE_ID)

		val mediaContent = listOf(MediaContent(FAKE_ALBUM_URL, FAKE_ALBUM_TYPE, FAKE_ALBUM_MEDIUM))
		val credit = listOf(SingleIntegerElementEntity(0))
		val media = MediaAndGroups(mediaContent, credit)
		return AlbumEntity(fakeAlbumId, fakePublished, fakeUpdated, fakeTitle, fakeSummary, fakeRights,
				media, fakePhotoId, fakeId)
	}
}