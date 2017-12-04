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

import com.google.android.gms.common.Scopes.DRIVE_APPFOLDER
import com.google.android.gms.common.Scopes.PLUS_ME
import com.google.android.gms.common.Scopes.PROFILE
import java.io.File
import java.lang.String.format
import java.util.ArrayList
import java.util.Arrays.asList

object Config {

  const val AUTHORIZATION_HEADER = "Authorization"
  const val BEARER_PREFIX = "Bearer"
  const val ACTIVE_ACCOUNT_PREF = "chosen_account_pref"
  const val PREFIX_PREF_AUTH_TOKEN = "auth_token_"
  const val SAVED_TOKEN_PREF = "token_pref"
  const val USER_REFUSED_SIGN_IN_PREF = "user_refused_sign_in_pref"
  const val SAVED_PERSON_NAME_PREF = "user_name_pref"
  const val SAVED_PERSON_GIVEN_NAME_PREF = "user_given_name_pref"
  const val SAVED_PERSON_FAMILY_NAME_PREF = "user_family_name_pref"
  const val SAVED_EMAIL_PREF = "user_email_pref"
  const val SAVED_ID_PREF = "user_id_pref"
  const private val PICASA_BASE_URL = "https://picasaweb.google.com/data"
  const val PICASA_BASE_API_URL = "$PICASA_BASE_URL/feed/api"
  const val PICASA_BASE_USER_API_URL = "$PICASA_BASE_URL/feed/api/user/default"
  const val LOGS_PATH = "/logs"
  const val LOGS_DATE_FORMAT = "dd_MM_yyyy_HH_mm_ss"
  const val JOB_THREAD_NAME = "android_"
  const val APPLICATION_LOG_TAG = "PICASA_GALLERY"
  const val PHOTO_ID_KEY = "PHOTO_ID_KEY"
  const val ALBUM_ID_KEY = "ALBUM_ID_KEY"
  const val TWO_COLUMNS_GRID = 2
  const val THREE_COLUMNS_GRID = 3
  const val MAIN_VIEW_TYPE_ID = 0
  const val USER_VIEW_TYPE_ID = 1
  const val PHOTO_VIEW_TYPE_ID = 2
  const val CONTENT_VIEW_TYPE_ID = 3
  const val LOG_QUEUE_NAME = "LOG_QUEUE"
  const val LOG_FILE_FORMAT_NAME = ".log"
  const val NET_LOG_FILENAME = "_net$LOG_FILE_FORMAT_NAME"
  const val NATIVE_LIBRARY_NAME = "picasa-lib"

  val AUTH_SCOPES = ArrayList(asList(
      PLUS_ME,
      PROFILE,
      DRIVE_APPFOLDER,
      "https://www.googleapis.com/auth/plus.profile.emails.read"))

  val JSON_PARAMS = object : ArrayList<Pair<String, String>>() {
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

  internal fun configureUserPath(id: String): String = format("user/%s", id)

  internal fun configureAlbumsPath(userId: String,
      albumId: String) = format("user/%s/alubumid/%s", userId, albumId)

  external fun stringFromJNI(): String

  external fun initProperties(file: File)
  external fun initProperties(path: String)
}
