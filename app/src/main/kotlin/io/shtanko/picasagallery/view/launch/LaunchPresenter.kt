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

import android.text.TextUtils
import io.shtanko.picasagallery.core.log.FileLog
import io.shtanko.picasagallery.data.DefaultObserver
import io.shtanko.picasagallery.data.entity.User
import io.shtanko.picasagallery.data.user.GetUserDetails
import io.shtanko.picasagallery.util.ActivityScoped
import io.shtanko.picasagallery.view.launch.LaunchContract.View
import javax.annotation.Nullable
import javax.inject.Inject

@ActivityScoped
class LaunchPresenter @Inject constructor(
    var getUserDetails: GetUserDetails) : LaunchContract.Presenter {

  @Nullable
  private var view: LaunchContract.View? = null

  override fun takeView(view: View) {
    this.view = view
  }

  override fun dropView() {
    this.view = null
    getUserDetails.unSubscribe()
  }

  override fun isSignIn() {
    getUserDetails.execute(UserListObserver(), GetUserDetails.Params.createQuery())
  }

  inner class UserListObserver : DefaultObserver<User>() {
    override fun onComplete() {
    }

    override fun onNext(t: User) {
      if (!TextUtils.isEmpty(t.personId)) {
        this@LaunchPresenter.view?.onSignedIn()
      } else {
        this@LaunchPresenter.view?.onSignedOut()
      }
    }

    override fun onError(exception: Throwable) {
      FileLog.e(exception)
    }
  }
}