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

package io.shtanko.picasagallery.view.profile

import android.os.Bundle
import io.shtanko.picasagallery.R
import io.shtanko.picasagallery.util.ActivityUtils
import io.shtanko.picasagallery.view.base.BaseActivity


class ProfileActivity : BaseActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.container_activity)

    val fragment = getFragment()
  }

  private fun getFragment(): ProfileFragment {
    val fragment = supportFragmentManager.findFragmentById(
        R.id.content_frame) as ProfileFragment? ?: ProfileFragment().also {
      ActivityUtils.addFragmentToActivity(supportFragmentManager, it, R.id.content_frame)
    }
    return fragment
  }

}