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

import android.content.Intent
import android.os.Bundle
import com.google.android.gms.auth.api.Auth
import dagger.Lazy
import io.shtanko.picasagallery.R
import io.shtanko.picasagallery.data.entity.UserEntity
import io.shtanko.picasagallery.extensions.close
import io.shtanko.picasagallery.util.ActivityUtils
import io.shtanko.picasagallery.view.base.BaseActivity
import io.shtanko.picasagallery.view.main.MainActivity
import javax.inject.Inject

class SignInActivity : BaseActivity() {

  @Inject lateinit var presenter: SignInPresenter
  @Inject lateinit var fragmentProvider: Lazy<SignInFragment>

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.container_activity)
    addFragment()
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    if (requestCode == SignInFragment.SIGN_IN_REQUEST_CODE) {
      val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
      if (result != null) {
        val acct = result.signInAccount
        if (acct != null) {
          val personName = acct.displayName
          val personGivenName = acct.givenName
          val personFamilyName = acct.familyName
          val personEmail = acct.email
          val personId = acct.id

          val userEntity = UserEntity(personName, personGivenName, personFamilyName, personEmail,
              personId)
          //val personPhoto = acct.photoUrl
          presenter.saveUserData(userEntity)
          openMainActivity()
        }
      }
    }
  }

  private fun openMainActivity() {
    startActivity(Intent(this, MainActivity::class.java)).also {
      close()
    }
  }

  private fun addFragment() {
    var fragment = supportFragmentManager.findFragmentById(
        R.id.content_frame) as SignInFragment?
    if (fragment == null) {
      fragment = fragmentProvider.get()
      ActivityUtils.addFragmentToActivity(supportFragmentManager, fragment, R.id.content_frame)
    }
  }
}