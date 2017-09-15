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

package io.shtanko.picasagallery.data.internal

import io.reactivex.Flowable
import io.shtanko.picasagallery.data.entity.internal.ContentType
import io.shtanko.picasagallery.extensions.ContentList
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class InternalAlbumsRepositoryImpl @Inject constructor() : InternalAlbumsRepository {
  override fun content(): Flowable<ContentList> {
    TODO(
        "not implemented") //To change body of created functions use File | Settings | File Templates.
  }

}