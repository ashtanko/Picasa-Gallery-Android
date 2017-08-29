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

package io.shtanko.picasagallery.data.account

import android.content.SharedPreferences
import android.text.TextUtils
import com.google.android.gms.auth.GoogleAuthUtil
import io.shtanko.picasagallery.Config
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AccountHelper @Inject constructor(val sharedPreferences: SharedPreferences) : Account {

  override fun hasActiveAccount() = !TextUtils.isEmpty(getActiveAccountName())

  override fun getActiveAccountName(): String =
      sharedPreferences.getString(Config.ACTIVE_ACCOUNT_PREF, null)

  override fun getActiveAccount(): android.accounts.Account {
    val account = getActiveAccountName()
    return android.accounts.Account(account, GoogleAuthUtil.GOOGLE_ACCOUNT_TYPE)
  }

  override fun setActiveAccount(accountName: String) {
    sharedPreferences.edit().putString(Config.ACTIVE_ACCOUNT_PREF, accountName).apply()
  }

  override fun clearActiveAccount() {
    sharedPreferences.edit().remove(Config.ACTIVE_ACCOUNT_PREF).apply()
  }

  override fun makeAccountSpecificPrefKey(prefix: String): String {
    if (hasActiveAccount()) {
      return makeAccountSpecificPrefKey(getActiveAccountName(),
          prefix)
    } else {
      return ""
    }
  }

  override fun makeAccountSpecificPrefKey(accountName: String,
      prefix: String): String = prefix + accountName

  override fun getAuthToken(): String = if (hasActiveAccount())
    sharedPreferences.getString(makeAccountSpecificPrefKey(Config.PREFIX_PREF_AUTH_TOKEN),
        null) else ""

  override fun setAuthToken(accountName: String, authToken: String?) {
    sharedPreferences.edit().putString(
        makeAccountSpecificPrefKey(accountName, Config.PREFIX_PREF_AUTH_TOKEN),
        authToken).apply()
  }

  override fun setAuthToken(authToken: String?) {
    if (hasActiveAccount()) {
      setAuthToken(getActiveAccountName(), authToken)
    }
  }
}