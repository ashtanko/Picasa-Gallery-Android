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

import android.accounts.Account

interface Account {

	/**
	 * Specify whether the app has an active account set.
	 *
	 */
	abstract fun hasActiveAccount(): Boolean

	/**
	 * Return the accountName the app is using as the active Google Account.
	 *
	 */
	abstract fun getActiveAccountName(): String

	/**
	 * Return the {@code Account} the app is using as the active Google Account.
	 *
	 */
	abstract fun getActiveAccount(): Account

	abstract fun setActiveAccount(accountName: String)

	abstract fun clearActiveAccount()

	abstract fun makeAccountSpecificPrefKey(prefix: String): String

	abstract fun makeAccountSpecificPrefKey(accountName: String, prefix: String): String

	abstract fun getAuthToken(): String

	abstract fun setAuthToken(accountName: String, authToken: String?)

	abstract fun setAuthToken(authToken: String?)

	fun invalidateAuthToken() = setAuthToken(null)

}