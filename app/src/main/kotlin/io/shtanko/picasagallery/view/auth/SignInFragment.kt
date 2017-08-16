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

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.GoogleApiClient
import io.shtanko.picasagallery.R

class SignInFragment : Fragment(), SignInContract.View {

  companion object {
    val SIGN_IN_REQUEST_CODE = 1111
  }

  override var presenter: SignInContract.Presenter? = null
  val ACCOUNT_TYPE_GOOGLE = "com.google"

  lateinit var rootView: View

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
      savedInstanceState: Bundle?): View? {
    val root = inflater.inflate(R.layout.fragment_siginin, container, false)
    rootView = root
    with(root) {
      addSignInButton()
    }

    return root
  }

  override fun setLoadingIndicator(active: Boolean) {
    with(rootView.findViewById<ProgressBar>(R.id.progress_bar)) {
      post {
        visibility = View.GONE
      }
    }
  }

  private fun addSignInButton() {
    with(rootView.findViewById<SignInButton>(R.id.sign_in_button)) {
      setSize(SignInButton.SIZE_STANDARD)
      setOnClickListener {
        signIn()
      }
    }
  }

  private fun signIn() {
    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(getString(R.string.default_web_client_id))
        .requestEmail()
        .build()

    val googleApiClient = GoogleApiClient.Builder(activity.applicationContext)
        .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
        .build()

    googleApiClient.registerConnectionFailedListener {
    }

    val signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient)

    activity.startActivityForResult(signInIntent, SignInFragment.SIGN_IN_REQUEST_CODE)
  }

}

