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
import io.shtanko.picasagallery.data.entity.user.User
import io.shtanko.picasagallery.data.user.UserDataSource
import io.shtanko.picasagallery.data.user.UserRepository
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MockGetUserRepositoryImpl @Inject constructor(
  var dataSourceImpl: UserDataSource
) : UserRepository {

  override fun getUserData(): Flowable<User> = Flowable.just(
      User(
          "Mock Name", "Mock Given Name",
          "Mock Family Name", "mock@mock.com",
          "${UUID.randomUUID()}"
      )
  )

}