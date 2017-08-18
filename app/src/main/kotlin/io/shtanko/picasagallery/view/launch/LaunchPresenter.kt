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

package io.shtanko.picasagallery.view.launch

import io.shtanko.picasagallery.data.UserDataSource
import io.shtanko.picasagallery.data.UserRepository
import io.shtanko.picasagallery.util.ActivityScoped
import io.shtanko.picasagallery.util.Logger
import io.shtanko.picasagallery.view.launch.LaunchContract.View
import javax.annotation.Nullable
import javax.inject.Inject

@ActivityScoped
class LaunchPresenter @Inject constructor(
    var repository: UserRepository) : LaunchContract.Presenter {

  @Nullable
  private var view: LaunchContract.View? = null

  override fun takeView(view: View) {
    this.view = view
  }

  override fun dropView() {
    this.view = null
  }

  override fun isSignIn() {
    repository.getSignIn(object : UserDataSource.SignInCallback {
      override fun onSuccess(value: Boolean) {
        Logger.verbose(this, "TESTTSTS: " + value + " VIEW: " + view)
        if (value) {
          view?.onSignedIn()
        } else {
          view?.onSignedOut()
        }
      }

      override fun onFailure() {
      }
    })
  }

}