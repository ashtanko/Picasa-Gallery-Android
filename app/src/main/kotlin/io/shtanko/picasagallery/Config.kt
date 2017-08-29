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
  val PICASA_BASE_API_URL = "$PICASA_BASE_URL/feed/api/"

  val LOGS_PATH = "/logs"
  val LOGS_DATE_FORMAT = "dd_MM_yyyy_HH_mm_ss"

  val JOB_THREAD_NAME = "android_"
  val APPLICATION_LOG_TAG = "PICASA_GALLERY"

  val MAIN = 0

}
