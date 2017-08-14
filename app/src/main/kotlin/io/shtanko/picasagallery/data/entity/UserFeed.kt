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

package io.shtanko.picasagallery.data.entity

import com.google.gson.annotations.SerializedName


data class UserFeed(
    @SerializedName("subtitle") var subtitle: SingleStringElement,
    @SerializedName("gphoto'$'user") var gPhotoUser: SingleStringElement,
    @SerializedName("gphoto'$'maxPhotosPerAlbum") var gPhotoMaxPhotosPerAlbum: SingleIntegerElement,
    @SerializedName("openSearch'$'itemsPerPage") var openSearchItemsPerPage: SingleStringElement,
    @SerializedName("id") var id: SingleStringElement,
    @SerializedName("category") var category: List<Category>,
    @SerializedName("generator") var generator: Generator,
    @SerializedName("openSearch'$'startIndex") var openSearchStartIndex: SingleIntegerElement,
    @SerializedName("xmlns'$'media") var xmlnsMedia: String,
    @SerializedName("xmlns'$'app") var xmlnsApp: String,
    @SerializedName("gd'$'etag") var gdETag: String,
    @SerializedName("updated") var updated: SingleStringElement,
    @SerializedName("xmlns'$'gd") var xmlnsGd: String,
    @SerializedName("xmlns'$'gphoto") var xmlnsGPhoto: String,
    @SerializedName("gphoto'$'nickname") var gPhotoNickname: String,
    @SerializedName("link") var link: List<Link>,
    @SerializedName("xmlns'$'openSearch") var xmlnsOpenSearch: String,
    @SerializedName("gphoto'$'quotacurrent") var gPhotoQuotaCurrent: SingleStringElement,
    @SerializedName("icon") var icon: SingleStringElement,
    @SerializedName("xmlns") var xmlns: SingleStringElement,
    @SerializedName("openSearch'$'totalResults") var openSearchTotalResults: SingleIntegerElement,
    @SerializedName("author") var author: List<Author>,
    @SerializedName("gphoto'$'quotalimit") var gPhotoQuotalimit: SingleStringElement,
    @SerializedName("gphoto'$'thumbnail") var gPhotoThumbnail: SingleStringElement,
    @SerializedName("title") var title: String,
    @SerializedName("entry") var entry: List<AlbumEntry>
    )