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

package io.shtanko.picasagallery.extensions

import android.app.Activity
import android.content.Context
import android.os.Build.VERSION.SDK_INT
import android.os.Build.VERSION_CODES.JELLY_BEAN
import android.os.Build.VERSION_CODES.M
import android.support.v4.app.Fragment
import android.widget.Toast.LENGTH_SHORT
import android.widget.Toast.makeText
import dagger.android.DaggerFragment

fun Fragment.getSafeContext(): Context = if (SDK_INT >= M) this.context else activity
fun DaggerFragment.getSafeContext(): Context = if (SDK_INT >= M) this.context else activity

fun Activity.close() {
  if (SDK_INT >= JELLY_BEAN) {
    finishAffinity()
  } else {
    finish()
  }
}

fun Activity.shortToast(message: String) {
  makeText(this, message, LENGTH_SHORT).show()
}

fun Fragment.shortToast(message: String) {
  makeText(activity, message, LENGTH_SHORT).show()
}