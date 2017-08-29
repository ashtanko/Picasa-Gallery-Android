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

package io.shtanko.picasagallery.util

import android.accounts.AccountManager
import android.content.Context
import android.preference.PreferenceManager
import android.support.test.InstrumentationRegistry
import com.google.android.gms.auth.GoogleAuthUtil
import io.shtanko.picasagallery.data.account.AccountHelper

/**
 * Methods to help mock login/account status
 */
class LoginUtils {
  companion object {
    val DUMMY_ACCOUNT_NAME = "testieso"

    fun setFirstAvailableAccountAsActive(context: Context): String {
      val account: String
      val am = AccountManager.get(InstrumentationRegistry.getTargetContext())
      val accountArray = am.getAccountsByType(GoogleAuthUtil.GOOGLE_ACCOUNT_TYPE)
      if (accountArray.isNotEmpty()) {
        account = accountArray[0].name
      } else {
        account = DUMMY_ACCOUNT_NAME
      }
      AccountHelper(
          PreferenceManager.getDefaultSharedPreferences(InstrumentationRegistry.getTargetContext()))
          .setActiveAccount(account)
      return account
    }
  }
}