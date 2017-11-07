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

package io.shtanko.picasagallery.view.util

import io.shtanko.picasagallery.data.entity.internal.Content
import io.shtanko.picasagallery.data.entity.internal.ContentType
import io.shtanko.picasagallery.data.entity.photo.Photo
import io.shtanko.picasagallery.data.entity.photo.PhotoType

fun getContentData(): ArrayList<ContentType> {
	val array = ArrayList<ContentType>()
	(0 until getImages().size).mapTo(array) { Content("$it", getImages()[it]) }
	return array
}

fun getPhotosData(): ArrayList<PhotoType> {
	val array = ArrayList<PhotoType>()
	(0..11233).mapTo(array) { Photo(it) }
	return array
}

fun getImages(): Array<String> {
	val BASE_URL = "https://cdn.dribbble.com/users"
	return arrayOf(
			"https://cdn.dribbble.com/users/1158341/screenshots/3905987/illustration_for_landing_page-1.png",
			"$BASE_URL/334782/screenshots/3776132/gatefold-dribbs.jpg",
			"$BASE_URL/2738/screenshots/3778113/happy_moon.jpg",
			"$BASE_URL/1291181/screenshots/3775427/image.png",
			"https://assets.materialup.com/uploads/d383e8e9-1f77-49d9-8451-08d5d85a0bf0/preview.png",
			"$BASE_URL/179241/screenshots/3775619/chris-fernandez-cats-cats-cats-4.png",
			"$BASE_URL/230124/screenshots/3775406/kraken_v2a.jpg",
			"$BASE_URL/1133651/screenshots/3755524/___-3.jpg",
			"$BASE_URL/1133651/screenshots/3760766/__33.jpg",
			"$BASE_URL/1133651/screenshots/3776457/__3.jpg",
			"$BASE_URL/452270/screenshots/3776947/houstonstrong_dribbble.jpg",
			"$BASE_URL/104030/screenshots/3777869/purpleant.jpg",
			"$BASE_URL/12516/screenshots/3777524/kmc_illustration2_x-2.png",
			"$BASE_URL/1133651/screenshots/2730717/__.jpg",
			"$BASE_URL/1133651/screenshots/2733700/1026.jpg",
			"$BASE_URL/1133651/screenshots/2741019/1014_2_1.jpg",
			"$BASE_URL/1133651/screenshots/2745025/1024_2_.jpg",
			"$BASE_URL/1133651/screenshots/2747099/1020_3_.jpg",
			"$BASE_URL/1133651/screenshots/2749736/1028_2_.jpg",
			"$BASE_URL/1133651/screenshots/2751717/1013_2_.jpg",
			"$BASE_URL/1133651/screenshots/2754204/1016_2_.jpg",
			"$BASE_URL/1133651/screenshots/2772251/1017_2_.jpg",
			"$BASE_URL/1133651/screenshots/2773833/1025_2_.jpg",
			"$BASE_URL/1133651/screenshots/2792668/1021_2_.jpg",
			"$BASE_URL/1133651/screenshots/2795072/1027_2_.jpg",
			"$BASE_URL/1133651/screenshots/2797519/1015_2_.jpg",
			"$BASE_URL/1133651/screenshots/2811836/1029.jpg",
			"$BASE_URL/1133651/screenshots/2820498/1023.jpg",
			"$BASE_URL/1133651/screenshots/2823123/1018_2_.jpg",
			"$BASE_URL/12516/screenshots/3777524/kmc_illustration2_x-2.png",
			"$BASE_URL/179241/screenshots/3769909/chris-fernandez-the-mountain-4.png",
			"$BASE_URL/823181/screenshots/3776629/scenery_illustrations.png",
			"$BASE_URL/11867/screenshots/3775908/castle1.jpg",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			""
	)
}