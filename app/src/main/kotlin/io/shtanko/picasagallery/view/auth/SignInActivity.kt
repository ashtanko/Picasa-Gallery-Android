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

package io.shtanko.picasagallery.view.auth

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import io.shtanko.picasagallery.PicasaApplication
import io.shtanko.picasagallery.R
import io.shtanko.picasagallery.util.ActivityUtils
import javax.inject.Inject


class SignInActivity : AppCompatActivity() {

  @Inject lateinit var presenter: SignInPresenter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.container_activity)
    initDagger()
  }

  private fun getFragment(): SignInFragment {
    val fragment = supportFragmentManager.findFragmentById(
        R.id.content_frame) as SignInFragment? ?: SignInFragment().also {
      ActivityUtils.addFragmentToActivity(supportFragmentManager, it, R.id.content_frame)
    }
    return fragment
  }


  private fun initDagger() {
    DaggerSignInComponent.builder()
        .mainComponent(PicasaApplication.graph).signInModule(SignInModule(getFragment()))
        .build()
        .inject(this)
  }
}