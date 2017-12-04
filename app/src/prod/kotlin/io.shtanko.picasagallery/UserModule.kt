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

import dagger.Module
import dagger.Provides
import io.shtanko.picasagallery.core.executor.PostExecutionThread
import io.shtanko.picasagallery.core.executor.ThreadExecutor
import io.shtanko.picasagallery.data.entity.user.User
import io.shtanko.picasagallery.data.user.GetUserDetails
import io.shtanko.picasagallery.data.user.GetUserDetails.Params
import io.shtanko.picasagallery.data.user.GetUserRepositoryImpl
import io.shtanko.picasagallery.data.user.UserDataSourceImpl
import io.shtanko.picasagallery.data.user.UserRepository
import io.shtanko.picasagallery.view.base.UseCase
import javax.inject.Named
import javax.inject.Singleton

@Module
class UserModule {

  @Provides
  @Singleton
  fun provideUserRepository(dataSourceImpl: UserDataSourceImpl): UserRepository =
      GetUserRepositoryImpl(dataSourceImpl)

  @Provides
  @Singleton
  @Named("UserDetails")
  fun provideUserDetails(userRepository: UserRepository,
      threadExecutor: ThreadExecutor,
      postExecutionThread: PostExecutionThread): UseCase<User, Params> =
      GetUserDetails(userRepository, threadExecutor, postExecutionThread)

}