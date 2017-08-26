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

package io.shtanko.picasagallery.data.user

import io.reactivex.Flowable
import io.shtanko.picasagallery.core.FileManager
import io.shtanko.picasagallery.core.executor.ThreadExecutor
import io.shtanko.picasagallery.data.Serializer
import io.shtanko.picasagallery.data.entity.User
import javax.inject.Inject
import javax.inject.Singleton

/**
 * {@link io.shtanko.picasagallery.data.user.UserCache} implementation.
 */
@Singleton
class UserCacheImpl @Inject constructor(
    serializer: Serializer,
    fileManager: FileManager,
    threadExecutor: ThreadExecutor) : UserCache {

  override fun get(): Flowable<User> {
    Flowable.unsafeCreate<User> {

    }
    TODO(
        "not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun put(user: User?) {
    TODO(
        "not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun isCached(): Boolean {
    TODO(
        "not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun isExpired(): Boolean {
    TODO(
        "not implemented") //To change body of created functions use File | Settings | File Templates.
  }
}