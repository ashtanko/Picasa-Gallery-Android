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
import io.shtanko.picasagallery.base.UseCase
import io.shtanko.picasagallery.core.executor.PostExecutionThread
import io.shtanko.picasagallery.core.executor.ThreadExecutor
import io.shtanko.picasagallery.data.entity.User
import io.shtanko.picasagallery.data.user.GetUserDetails.Params
import javax.inject.Inject

class GetUserDetails @Inject constructor(
    var userRepository: UserRepository,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread) : UseCase<User, Params>(threadExecutor,
    postExecutionThread) {


  override fun buildUseCaseObservable(params: Params): Flowable<User> = userRepository.getUserData()

  class Params private constructor() {
    companion object {
      fun createQuery() = Params()
    }
  }

}