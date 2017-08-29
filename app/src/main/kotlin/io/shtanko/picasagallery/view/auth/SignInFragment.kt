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
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.GoogleSignInResult
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.Scopes
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.common.api.Scope
import dagger.android.support.DaggerFragment
import io.shtanko.picasagallery.R
import io.shtanko.picasagallery.data.entity.User
import io.shtanko.picasagallery.extensions.close
import io.shtanko.picasagallery.extensions.getSafeContext
import io.shtanko.picasagallery.view.main.MainActivity
import java.util.ArrayList
import java.util.Arrays
import javax.inject.Inject

class SignInFragment @Inject constructor() : DaggerFragment(),
    SignInContract.View,
    GoogleApiClient.OnConnectionFailedListener,
    GoogleApiClient.ConnectionCallbacks {

  @Inject lateinit var presenter: SignInContract.Presenter
  lateinit var rootView: View

  private var googleApiClient: GoogleApiClient? = null

  companion object {
    private val SIGN_IN_RESULT = 1
    val SIGN_IN_REQUEST_CODE = 1111
  }

  var progressBar: ProgressBar? = null

  override fun onConnectionFailed(p0: ConnectionResult) {
    Toast.makeText(this.getSafeContext(), "Unable to connect to Google Play Services",
        Toast.LENGTH_SHORT).show()
  }

  override fun onConnected(p0: Bundle?) {
    with(rootView.findViewById<SignInButton>(R.id.sign_in_button)) {
      isEnabled = true
    }
  }

  override fun onConnectionSuspended(p0: Int) {
    with(rootView.findViewById<SignInButton>(R.id.sign_in_button)) {
      isEnabled = false
    }
  }

  override fun onResume() {
    super.onResume()
    presenter.takeView(this)
  }

  override fun onDestroy() {
    super.onDestroy()
    presenter.dropView()
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
      savedInstanceState: Bundle?): View? {

    val root = inflater.inflate(R.layout.fragment_siginin, container, false)

    rootView = root
    with(root) {
      addSignInButton()
      progressBar = rootView.findViewById<ProgressBar>(R.id.progress_bar)
    }

    // Auth scopes we need
    val AUTH_SCOPES = ArrayList(Arrays.asList(
        Scopes.PLUS_LOGIN,
        Scopes.DRIVE_APPFOLDER,
        "https://www.googleapis.com/auth/plus.profile.emails.read"))

    /** List of OAuth scopes to be requested from the Google sign-in API  */
    fun getAuthScopes(): List<String> = AUTH_SCOPES

    val gsoBuilder = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)

    for (scope in getAuthScopes()) {
      gsoBuilder.requestScopes(Scope(scope))
    }

    val gso = gsoBuilder.requestEmail()
        .build()

    googleApiClient = GoogleApiClient.Builder(getSafeContext())
        .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
        .addConnectionCallbacks(this)
        .addOnConnectionFailedListener(this)
        .build()

    googleApiClient?.connect()

    return root
  }

  override fun onDetach() {
    super.onDetach()
    googleApiClient?.disconnect()
  }

  private fun login() {
    val signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient)
    startActivityForResult(signInIntent, SIGN_IN_RESULT)
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    if (requestCode == SIGN_IN_RESULT) {
      val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
      handleSignInResult(result)
    }
  }

  private fun handleSignInResult(result: GoogleSignInResult) {
    if (result.isSuccess) {
      val acct = result.signInAccount
      if (acct != null) {
        val user = User(acct.displayName, acct.givenName, acct.familyName, acct.email, acct.id)
        presenter.saveUserData(user)
        openMainActivity()
      }
    }
  }

  private fun openMainActivity() {
    startActivity(Intent(activity, MainActivity::class.java)).also {
      activity.close()
    }
  }

  override fun setLoadingIndicator(active: Boolean) {
    progressBar?.visibility = if (active) View.VISIBLE else View.GONE
  }

  private fun addSignInButton() {
    with(rootView.findViewById<SignInButton>(R.id.sign_in_button)) {
      setSize(SignInButton.SIZE_STANDARD)
      setOnClickListener {
        login()
      }
    }
  }
}

