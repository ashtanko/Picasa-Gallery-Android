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

package io.shtanko.picasagallery.view.auth

import io.shtanko.picasagallery.data.entity.user.User
import io.shtanko.picasagallery.data.user.UserRepositoryImpl
import io.shtanko.picasagallery.util.ActivityScoped
import io.shtanko.picasagallery.view.auth.SignInContract.View
import javax.annotation.Nullable
import javax.inject.Inject

@ActivityScoped
class SignInPresenter @Inject constructor(
    private val repository: UserRepositoryImpl) : SignInContract.Presenter {

  @Nullable
  private var view: SignInContract.View? = null

  override fun takeView(view: View) {
    this.view = view
  }

  override fun dropView() {
    this.view = null
  }

  override fun signIn() {
    this.view?.setLoadingIndicator(true)
  }

  override fun saveUserData(user: User) {
    this.view?.setLoadingIndicator(false)
    repository.saveUser(user)
  }

  override fun saveToken(token: String) {
    this.view?.setLoadingIndicator(false)
    repository.saveToken(token)
  }
}