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

package io.shtanko.picasagallery.data

import io.shtanko.picasagallery.data.UserDataSource.SignInCallback
import io.shtanko.picasagallery.data.entity.UserEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserDataSourceImpl @Inject constructor(
    var preferencesHelper: PreferenceHelper) : UserDataSource {

  override fun saveToken(token: String) {
    preferencesHelper.saveToken(token)
  }

  override fun saveUser(user: UserEntity) {
    preferencesHelper.saveUserData(user)
  }

  override fun getSignIn(callback: SignInCallback) {
    if (!preferencesHelper.getUserId().isEmpty()) {
      callback.onSuccess(true)
    } else {
      callback.onSuccess(false)
    }
  }
}