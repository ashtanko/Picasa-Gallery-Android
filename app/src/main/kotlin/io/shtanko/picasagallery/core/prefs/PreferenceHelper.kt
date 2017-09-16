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

package io.shtanko.picasagallery.core.prefs

import android.content.SharedPreferences
import io.shtanko.picasagallery.Config
import io.shtanko.picasagallery.Config.SAVED_PERSON_NAME_PREF
import io.shtanko.picasagallery.Config.USER_REFUSED_SIGN_IN_PREF
import io.shtanko.picasagallery.data.entity.user.User
import javax.inject.Singleton

@Singleton
class PreferenceHelper constructor(val sharedPreferences: SharedPreferences) {

  fun markUserRefusedSignIn(refused: Boolean) {
    sharedPreferences.edit().putBoolean(USER_REFUSED_SIGN_IN_PREF, refused).apply()
  }

  fun saveUserData(user: User) {
    sharedPreferences.run {
      edit().putString(SAVED_PERSON_NAME_PREF, user.personName).apply()
      edit().putString(Config.SAVED_PERSON_GIVEN_NAME_PREF,
          user.personGivenName).apply()
      edit().putString(Config.SAVED_PERSON_FAMILY_NAME_PREF,
          user.personFamilyName).apply()
      edit().putString(Config.SAVED_EMAIL_PREF, user.personEmail).apply()
      edit().putString(Config.SAVED_ID_PREF, user.personId).apply()
    }
  }


  fun getUser(): User {
    val personName = sharedPreferences.getString(Config.SAVED_PERSON_NAME_PREF, "")
    val personGivenName = sharedPreferences.getString(Config.SAVED_PERSON_GIVEN_NAME_PREF, "")
    val personFamilyName = sharedPreferences.getString(Config.SAVED_PERSON_FAMILY_NAME_PREF, "")
    val personEmail = sharedPreferences.getString(Config.SAVED_EMAIL_PREF, "")
    val personId = sharedPreferences.getString(Config.SAVED_ID_PREF, "")
    return User(personName, personGivenName,
        personFamilyName, personEmail, personId)
  }

  fun saveToken(token: String) = sharedPreferences.edit().putString(Config.SAVED_TOKEN_PREF,
      token).apply()

  fun getToken(): String = sharedPreferences.getString(Config.SAVED_TOKEN_PREF, "")

  fun getUserId(): String = sharedPreferences.getString(Config.SAVED_ID_PREF, "")

}