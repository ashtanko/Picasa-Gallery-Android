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

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import io.shtanko.picasagallery.view.auth.SignInActivity
import io.shtanko.picasagallery.view.main.MainActivity
import javax.inject.Inject

class LaunchActivity : Activity(), LaunchContract.View {

  @Inject lateinit var presenter: LaunchPresenter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    //presenter?.isSignIn()
  }

  override fun onSignedIn() {
    showMainScreen()
  }

  override fun onSignedOut() {
    showSignInScreen()
  }

  private fun showMainScreen() {
    startActivity(Intent(this, MainActivity::class.java))
    finishAffinity()
  }

  private fun showSignInScreen() {
    startActivity(Intent(this, SignInActivity::class.java))
    finishAffinity()
  }
}