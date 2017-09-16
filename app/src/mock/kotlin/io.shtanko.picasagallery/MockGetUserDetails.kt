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
import io.shtanko.picasagallery.MockGetUserDetails.Params
import io.shtanko.picasagallery.view.base.UseCase
import io.shtanko.picasagallery.core.executor.PostExecutionThread
import io.shtanko.picasagallery.core.executor.ThreadExecutor
import io.shtanko.picasagallery.data.entity.user.User
import io.shtanko.picasagallery.data.user.UserRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MockGetUserDetails @Inject constructor(
    val userRepository: UserRepository,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread) : UseCase<User, Params>(threadExecutor,
    postExecutionThread) {


  override fun buildUseCaseObservable(
      params: Params): Flowable<User> {
    val n = String::class.java.name
    val user = User(n, n, n, n, n)
    return Flowable.just(user)
  }

  class Params private constructor() {
    companion object {
      fun createQuery() = Params()
    }
  }

}