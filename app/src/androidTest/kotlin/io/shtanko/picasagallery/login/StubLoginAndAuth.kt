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

package io.shtanko.picasagallery.login

import android.content.Intent
import android.os.Handler
import io.shtanko.picasagallery.ui.auth.LoginAndAuth
import io.shtanko.picasagallery.ui.auth.LoginAndAuthListener

class StubLoginAndAuth constructor(val name: String, val success: Boolean,
    val newAuthentication: Boolean) : LoginAndAuth {


  private var listener: LoginAndAuthListener? = null

  private var isStarted: Boolean = false

  fun setListener(listener: LoginAndAuthListener) {
    this.listener = listener
  }

  override fun getAccountName(): String = name

  override fun start() {
    isStarted = true
    val h = Handler()

    val r = object : Runnable {
      override fun run() {
        if (listener == null) {
          h.postDelayed(this, 50)
        } else {
          if (success) {
            listener!!.onAuthSuccess(name, newAuthentication)
          } else {
            listener!!.onAuthFailure(name)
          }
        }
      }
    }

    h.postDelayed(r, 0)
  }

  override fun isStarted(): Boolean = isStarted

  override fun stop() {
    isStarted = false
  }

  override fun retryAuthByUserRequest() {
    start()
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent): Boolean = false
}