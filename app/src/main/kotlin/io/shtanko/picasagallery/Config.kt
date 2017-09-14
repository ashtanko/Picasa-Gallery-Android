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

package io.shtanko.picasagallery

import com.google.android.gms.common.Scopes
import java.util.ArrayList
import java.util.Arrays

object Config {

  val ACTIVE_ACCOUNT_PREF = "chosen_account_pref"

  val PREFIX_PREF_AUTH_TOKEN = "auth_token_"

  val SAVED_TOKEN_PREF = "token_pref"
  val USER_REFUSED_SIGN_IN_PREF = "user_refused_sign_in_pref"
  val SAVED_PERSON_NAME_PREF = "user_name_pref"
  val SAVED_PERSON_GIVEN_NAME_PREF = "user_given_name_pref"
  val SAVED_PERSON_FAMILY_NAME_PREF = "user_family_name_pref"
  val SAVED_EMAIL_PREF = "user_email_pref"
  val SAVED_ID_PREF = "user_id_pref"
  val PICASA_BASE_URL = "https://picasaweb.google.com/data"
  val PICASA_BASE_API_URL = "$PICASA_BASE_URL/feed/api"

  val LOGS_PATH = "/logs"
  val LOGS_DATE_FORMAT = "dd_MM_yyyy_HH_mm_ss"

  val JOB_THREAD_NAME = "android_"
  val APPLICATION_LOG_TAG = "PICASA_GALLERY"

  val TWO_COLUMNS_GRID = 2
  val THREE_COLUMNS_GRID = 3

  val MAIN_VIEW_TYPE_ID = 0
  val USER_VIEW_TYPE_ID = 1
  val PHOTO_VIEW_TYPE_ID = 2

  val LOG_QUEUE_NAME = "LOG_QUEUE"
  val LOG_FILE_FORMAT_NAME = ".log"
  val NET_LOG_FILENAME = "_net$LOG_FILE_FORMAT_NAME"

  val AUTH_SCOPES = ArrayList(Arrays.asList(
      Scopes.PLUS_ME,
      Scopes.PROFILE,
      Scopes.DRIVE_APPFOLDER,
      "https://www.googleapis.com/auth/plus.profile.emails.read"))

  //https://picasaweb.google.com/data/feed/api/user/113288841856843375771?alt=json&start-index=1&max-results=1

  val jsonParams = object : ArrayList<Pair<String, String>>() {
    init {
      add(Pair("alt", "json"))
    }
  }

  private val params = object : ArrayList<Pair<String, String>>() {
    init {
      add(Pair("alt", "json"))
      add(Pair("start-index", "json"))
      add(Pair("max-results", "json"))
    }
  }

  internal fun configureUserPath(id: String): String = "user/$id"

  internal fun configureAlbumsPath(userId: String,
      albumId: String) = "user/$userId/albumid/$albumId"

}
