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

import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.AccountPicker
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.GoogleApiClient
import dagger.android.support.DaggerFragment
import io.shtanko.picasagallery.R
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.EasyPermissions
import javax.inject.Inject


class SignInFragment @Inject constructor() : DaggerFragment(), SignInContract.View {


  companion object {
    val SIGN_IN_REQUEST_CODE = 1111
    val REQUEST_ACCOUNT_PICKER = 1000
    val REQUEST_RECOVER_PLAY_SERVICES_ERROR = 1024
    val REQUEST_GOOGLE_PLAY_SERVICES = 1002
    const val REQUEST_PERMISSION_GET_ACCOUNTS = 1003
    val ACCOUNT_TYPE_GOOGLE = "com.google"

  }

  @Inject lateinit var presenter: SignInContract.Presenter
  val ACCOUNT_TYPE_GOOGLE = "com.google"

  lateinit var rootView: View

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
    }

    return root
  }

  override fun setLoadingIndicator(active: Boolean) {
  }

  private fun addSignInButton() {
    with(rootView.findViewById<SignInButton>(R.id.sign_in_button)) {
      setSize(SignInButton.SIZE_STANDARD)
      setOnClickListener {
        signIn()
      }
    }

    with(rootView.findViewById<Button>(R.id.sign_in_button2)) {
      setOnClickListener {
        getResultsFromApi()
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

  @AfterPermissionGranted(REQUEST_PERMISSION_GET_ACCOUNTS)
  private fun pickAccount() {

    if (EasyPermissions.hasPermissions(
        activity, Manifest.permission.GET_ACCOUNTS)) {

      val accountTypes = arrayOf(ACCOUNT_TYPE_GOOGLE)
      val intent = AccountPicker.newChooseAccountIntent(null, null, accountTypes, false, null, null,
          null, null)
      activity.startActivityForResult(intent, REQUEST_ACCOUNT_PICKER)

    } else {
      EasyPermissions.requestPermissions(
          this,
          "This app needs to access your Google account (via Contacts).",
          REQUEST_PERMISSION_GET_ACCOUNTS,
          Manifest.permission.GET_ACCOUNTS);
    }
  }

  private fun getResultsFromApi() {
    if (!isGooglePlayServicesAvailable()) {
      acquireGooglePlayServices()
    } else {
      pickAccount()
    }
  }

  private fun isGooglePlayServicesAvailable(): Boolean {
    val apiAvailability =
        GoogleApiAvailability.getInstance();
    val connectionStatusCode =
        apiAvailability.isGooglePlayServicesAvailable(activity)
    return connectionStatusCode == ConnectionResult.SUCCESS;
  }

  private fun acquireGooglePlayServices() {
    val apiAvailability =
        GoogleApiAvailability.getInstance()
    val connectionStatusCode =
        apiAvailability.isGooglePlayServicesAvailable(activity)
    if (apiAvailability.isUserResolvableError(connectionStatusCode)) {
      showGooglePlayServicesAvailabilityErrorDialog(connectionStatusCode);
    }
  }

  private fun showGooglePlayServicesAvailabilityErrorDialog(connectionStatusCode: Int) {
    val apiAvailability = GoogleApiAvailability.getInstance()
    val dialog = apiAvailability.getErrorDialog(
        activity,
        connectionStatusCode,
        SignInFragment.REQUEST_GOOGLE_PLAY_SERVICES)
    dialog.show();
  }
}

