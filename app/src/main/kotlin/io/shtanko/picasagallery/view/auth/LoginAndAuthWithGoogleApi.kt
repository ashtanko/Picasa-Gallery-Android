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

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.Scopes
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.common.api.Scope
import io.shtanko.picasagallery.util.Logger.verbose
import java.lang.ref.WeakReference
import java.util.ArrayList
import java.util.Arrays

class LoginAndAuthWithGoogleApi constructor(val activity: Activity,
    val callback: LoginAndAuthListener,
    val name: String) : LoginAndAuth, GoogleApiClient.ConnectionCallbacks,
    GoogleApiClient.OnConnectionFailedListener {

  // Request codes for the UIs that we show
  private val REQUEST_AUTHENTICATE = 100
  private val REQUEST_RECOVER_FROM_AUTH_ERROR = 101
  private val REQUEST_RECOVER_FROM_PLAY_SERVICES_ERROR = 102
  private val REQUEST_PLAY_SERVICES_ERROR_DIALOG = 103

  // Auth scopes we need
  private val AUTH_SCOPES = ArrayList(Arrays.asList(
      Scopes.PLUS_LOGIN,
      Scopes.DRIVE_APPFOLDER,
      "https://www.googleapis.com/auth/plus.profile.emails.read"))

  private var AUTH_TOKEN_TYPE: String

  internal var appContext: Context = activity.applicationContext

  // The Activity this object is bound to (we use a weak ref to avoid context leaks)
  private var activityRef: WeakReference<Activity> = WeakReference(activity)

  // Callbacks interface we invoke to notify the user of this class of useful events
  private var callbacksRef: WeakReference<LoginAndAuthListener> = WeakReference(callback)

  // API client to interact with Google services
  private var googleApiClient: GoogleApiClient? = null

  // Are we in the started state? Started state is between onStart and onStop.
  internal var started = false

  // True if we are currently showing UIs to resolve a connection error.
  internal var resolving = false

  // Name of the account to log in as (e.g. "foo@example.com")
  internal var accountName: String = name


  init {
    // Initialize oauth scope
    val sb = StringBuilder()
    sb.append("oauth2:")
    for (scope in AUTH_SCOPES) {
      sb.append(scope)
      sb.append(" ")
    }
    AUTH_TOKEN_TYPE = sb.toString().trim()
  }


  // Controls whether or not we can show sign-in UI. Starts as true;
  // when sign-in *fails*, we will show the UI only once and set this flag to false.
  // After that, we don't attempt again in order not to annoy the user.
  private var sCanShowSignInUi = true
  private var sCanShowAuthUi = true

  private fun getActivity(methodName: String): Activity? {
    val activity = activityRef.get()
    if (activity == null) {
      verbose(
          "Helper lost Activity reference, ignoring ($methodName)")
    }
    return activity
  }

  override fun isStarted(): Boolean = started

  override fun getAccountName(): String = accountName

  override fun retryAuthByUserRequest() {
    if (googleApiClient != null) {
      if (googleApiClient?.isConnected!!) {

      }
    }
  }

  override fun start() {
    val activity = getActivity("start()") ?: return

    if (started) return
    started = true
    if (resolving) return
    if (googleApiClient == null) {
      val builder = GoogleApiClient.Builder(activity)
      for (scope in AUTH_SCOPES) {
        builder.addScope(Scope(scope))
      }

      googleApiClient = builder
          .addConnectionCallbacks(this)
          .addOnConnectionFailedListener(this)
          .setAccountName(accountName)
          .build()
    }

    googleApiClient?.connect()
  }

  override fun onConnected(bundle: Bundle?) {
    val activity = getActivity("onConnected()") ?: return

  }


  override fun stop() {

  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent): Boolean {
    return false
  }

  override fun onConnectionSuspended(p0: Int) {

  }

  override fun onConnectionFailed(p0: ConnectionResult) {

  }

}
