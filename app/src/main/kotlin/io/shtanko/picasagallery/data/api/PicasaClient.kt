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

package io.shtanko.picasagallery.data.api

import android.accounts.Account
import android.content.Context
import android.text.TextUtils
import com.google.android.gms.auth.GoogleAuthUtil
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.SingleOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.shtanko.picasagallery.util.Logger

class PicasaClient(val context: Context) {

  private val ACCOUNT_TYPE_GOOGLE = "com.google"
  private lateinit var account: Account
  private lateinit var token: String


  fun setAccount(account: Account, additionalScopes: Array<String>): Completable {
    if (account.type == ACCOUNT_TYPE_GOOGLE) {
      this.account = account
      return retrieveTokenInitService(additionalScopes)
    }
    return Completable.error(RuntimeException("You may only set a Google account"))
  }

  private fun retrieveTokenInitService(additionalScopes: Array<String>): Completable {

    return Single.create(SingleOnSubscribe<String> { subscriber ->
      val scopes = ArrayList<String>()
      scopes.add("https://picasaweb.google.com/data/")
      scopes.addAll(additionalScopes)
      subscriber.onSuccess(
          GoogleAuthUtil.getToken(context, account, "oauth2:" + TextUtils.join(" ", scopes)))

    }).subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .doOnError { error ->
          Logger.verbose(this, error.message)
        }.doOnSuccess { it ->
      token = it
    }.toCompletable()
  }
}