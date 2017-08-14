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
import android.support.v7.app.AppCompatActivity
import com.google.android.gms.auth.api.Auth
import io.shtanko.picasagallery.PicasaApplication
import io.shtanko.picasagallery.R
import io.shtanko.picasagallery.util.ActivityUtils
import javax.inject.Inject


class SignInActivity : AppCompatActivity() {

  @Inject lateinit var presenter: SignInPresenter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.container_activity)
    val fragment = getFragment()
    initDagger(fragment)
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
          val personPhoto = acct.photoUrl
          presenter.saveUserData(personName, personGivenName, personFamilyName, personEmail,
              personId)
        }
      }
    }
  }

  private fun getFragment(): SignInFragment {
    val fragment = supportFragmentManager.findFragmentById(
        R.id.content_frame) as SignInFragment? ?: SignInFragment().also {
      ActivityUtils.addFragmentToActivity(supportFragmentManager, it, R.id.content_frame)
    }
    return fragment
  }


  private fun initDagger(view: SignInContract.View) {
    DaggerSignInComponent.builder()
        .baseComponent(PicasaApplication.graph).signInModule(SignInModule(view))
        .build()
        .inject(this)
  }
}