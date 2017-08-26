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
import android.accounts.Account
import android.accounts.AccountManager
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.google.android.gms.auth.GoogleAuthUtil
import com.google.android.gms.auth.UserRecoverableAuthException
import com.google.android.gms.auth.api.Auth
import dagger.Lazy
import io.reactivex.Completable
import io.reactivex.CompletableObserver
import io.reactivex.Single
import io.reactivex.SingleOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.shtanko.picasagallery.Config
import io.shtanko.picasagallery.R
import io.shtanko.picasagallery.data.entity.User
import io.shtanko.picasagallery.extensions.close
import io.shtanko.picasagallery.util.ActivityUtils
import io.shtanko.picasagallery.util.Logger
import io.shtanko.picasagallery.view.base.BaseActivity
import io.shtanko.picasagallery.view.main.MainActivity
import pub.devrel.easypermissions.EasyPermissions
import javax.inject.Inject

class SignInActivity : BaseActivity() {

  @Inject lateinit var presenter: SignInPresenter
  @Inject lateinit var fragmentProvider: Lazy<SignInFragment>

  private var account: Account? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.container_activity)
    addFragment()
  }

  override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>,
      grantResults: IntArray) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    EasyPermissions.onRequestPermissionsResult(
        requestCode, permissions, grantResults, this)
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
    super.onActivityResult(requestCode, resultCode, data)

    onActivityResult(requestCode, resultCode, data,
        arrayOf(""))
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(object : CompletableObserver {
          override fun onComplete() {
            println()
          }

          override fun onSubscribe(d: Disposable) {
            println()
          }

          override fun onError(e: Throwable) {
            println()
          }
        })

    if (requestCode == SignInFragment.SIGN_IN_REQUEST_CODE) {
      val errorString = getString(R.string.error)
      val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
      if (result != null) {
        val acct = result.signInAccount
        if (acct != null) {
          val personName = acct.displayName
          val personGivenName = acct.givenName
          val personFamilyName = acct.familyName
          val personEmail = acct.email
          val personId = acct.id

          val userEntity = User(personName, personGivenName, personFamilyName, personEmail,
              personId)
          presenter.saveUserData(userEntity)
          openMainActivity()
        } else {
          Toast.makeText(this, errorString, Toast.LENGTH_LONG).show()
        }
      } else {
        Toast.makeText(this, errorString, Toast.LENGTH_SHORT).show()
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


  private fun setAccount(account: Account, additionalScopes: Array<String>): Completable {
    val errorString = getString(R.string.google_account_error)
    if (account.type == SignInFragment.ACCOUNT_TYPE_GOOGLE) {
      this.account = account
      return retrieveTokenInitService(additionalScopes)
    }
    return Completable.error(RuntimeException(errorString))
  }

  fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent,
      additionalScopes: Array<String>): Completable {
    val errorString = getString(R.string.user_cancelled_error)
    when (requestCode) {
      SignInFragment.REQUEST_ACCOUNT_PICKER -> {
        if (resultCode == Activity.RESULT_OK) {
          val accountEmail = data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME)
          account = Account(accountEmail, SignInFragment.ACCOUNT_TYPE_GOOGLE)
          return retrieveTokenInitService(additionalScopes)
        } else if (resultCode == Activity.RESULT_CANCELED) {
          return Completable.error(Exception(errorString))
        }
      }
      SignInFragment.REQUEST_RECOVER_PLAY_SERVICES_ERROR -> {
        if (resultCode == Activity.RESULT_OK) {
          return retrieveTokenInitService(additionalScopes)
        }
      }
    }
    return Completable.never()
  }

  private fun retrieveTokenInitService(additionalScopes: Array<String>): Completable {


    return Single.create(SingleOnSubscribe<String> { subscriber ->
      val scopes = ArrayList<String>()
      scopes.add(Config.PICASA_BASE_URL)
      scopes.addAll(additionalScopes)
      subscriber.onSuccess(
          GoogleAuthUtil.getToken(this, account, "oauth2:" + TextUtils.join(" ", scopes)))

    }).subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .doOnError { error ->
          if (error is UserRecoverableAuthException) {
            EasyPermissions.requestPermissions(
                this,
                getString(R.string.access_goole_account_alert_title),
                SignInFragment.REQUEST_PERMISSION_GET_ACCOUNTS,
                Manifest.permission.GET_ACCOUNTS)

            startActivityForResult(error.intent, SignInFragment.REQUEST_ACCOUNT_PICKER)
          }

          Logger.verbose(this, error.message)
        }
        .doOnSuccess { token ->
          presenter.saveToken(token)
        }
        .toCompletable()
  }

}