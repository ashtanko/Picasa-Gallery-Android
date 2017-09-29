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

import android.accounts.Account
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ProgressBar
import com.google.android.gms.auth.GoogleAuthUtil
import com.google.android.gms.auth.api.Auth.GOOGLE_SIGN_IN_API
import com.google.android.gms.auth.api.Auth.GoogleSignInApi
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.GoogleSignInResult
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.SignInButton.SIZE_STANDARD
import com.google.android.gms.common.api.GoogleApiClient
import dagger.android.support.DaggerFragment
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.shtanko.picasagallery.Config
import io.shtanko.picasagallery.R
import io.shtanko.picasagallery.data.entity.user.User
import io.shtanko.picasagallery.extensions.close
import io.shtanko.picasagallery.extensions.getSafeContext
import io.shtanko.picasagallery.extensions.shortToast
import io.shtanko.picasagallery.view.main.MainActivity
import javax.inject.Inject


class SignInFragment @Inject constructor() : DaggerFragment(),
    SignInContract.View,
    GoogleApiClient.OnConnectionFailedListener,
    GoogleApiClient.ConnectionCallbacks {

  @Inject lateinit var presenter: SignInContract.Presenter
  @Inject lateinit var googleSignInOptions: GoogleSignInOptions
  lateinit var rootView: View
  private var googleApiClient: GoogleApiClient? = null

  companion object {
    private val SIGN_IN_RESULT = 1
  }

  var progressBar: ProgressBar? = null

  override fun onConnectionFailed(p0: ConnectionResult) {
    shortToast("Unable to connect to Google Play Services")
  }

  override fun onConnected(p0: Bundle?) =
      enableButton(true)

  override fun onConnectionSuspended(p0: Int) =
      enableButton(false)

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

    googleApiClient = GoogleApiClient.Builder(getSafeContext())
        .addApi(GOOGLE_SIGN_IN_API, googleSignInOptions)
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
    val signInIntent = GoogleSignInApi.getSignInIntent(googleApiClient)
    startActivityForResult(signInIntent, SIGN_IN_RESULT)
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    if (requestCode == SIGN_IN_RESULT) {
      val result = GoogleSignInApi.getSignInResultFromIntent(data)
      handleSignInResult(result)
    }
  }

  private fun handleSignInResult(result: GoogleSignInResult) {
    if (result.isSuccess) {
      val acct = result.signInAccount
      if (acct != null) {
        val account = Account(acct.email, "com.google")
        Single.create<String> { it ->
          val token = GoogleAuthUtil.getToken(activity, account,
              "oauth2:" + TextUtils.join(" ", Config.AUTH_SCOPES))
          if (token != null && !TextUtils.isEmpty(token)) {
            it.onSuccess(token)
          } else {
            it.onError(Throwable("Error get token"))
          }
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe { t1, t2 ->
          presenter.saveToken(t1)
        }

        val user = User(acct.displayName, acct.givenName,
            acct.familyName, acct.email, acct.id)
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
    progressBar?.visibility = if (active) VISIBLE else GONE
  }

  private fun addSignInButton() {
    with(rootView.findViewById<SignInButton>(R.id.sign_in_button)) {
      setSize(SIZE_STANDARD)
      setOnClickListener {
        login()
      }
    }
  }

  private fun enableButton(enable: Boolean) {
    with(rootView.findViewById<SignInButton>(R.id.sign_in_button)) {
      isEnabled = enable
    }
  }
}

