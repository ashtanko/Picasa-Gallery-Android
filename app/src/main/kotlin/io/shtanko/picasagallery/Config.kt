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
  val SAVED_TOKEN_PREF = "token_pref"
  val SAVED_PERSON_NAME_PREF = "user_name_pref"
  val SAVED_PERSON_GIVEN_NAME_PREF = "user_given_name_pref"
  val SAVED_PERSON_FAMILY_NAME_PREF = "user_family_name_pref"
  val SAVED_EMAIL_PREF = "user_email_pref"
  val SAVED_ID_PREF = "user_id_pref"
  val PICASA_BASE_URL = "https://picasaweb.google.com/data/"
  val PICASA_BASE_API_URL = "$PICASA_BASE_URL/data/feed/api/"
}
