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

package io.shtanko.picasagallery.view.album

import android.os.Bundle
import dagger.Lazy
import io.shtanko.picasagallery.Config.ALBUM_ID_KEY
import io.shtanko.picasagallery.Config.PHOTO_ID_KEY
import io.shtanko.picasagallery.R
import io.shtanko.picasagallery.view.base.BaseActivity
import javax.inject.Inject

class InternalAlbumsActivity : BaseActivity() {

  // region injection
  @Inject lateinit var internalAlbumsPresenter: InternalAlbumsPresenter
  @Inject lateinit var internalAlbumsFragmentProvider: Lazy<InternalAlbumsFragment>
  // endregion

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.container_activity)

    val photoId = intent.getStringExtra(PHOTO_ID_KEY)
    val albumId = intent.getStringExtra(ALBUM_ID_KEY)
    val bundle = Bundle()
    bundle.putString(PHOTO_ID_KEY, photoId)
    bundle.putString(ALBUM_ID_KEY, albumId)
    internalAlbumsFragmentProvider.get()
        .arguments = bundle
    addFragment(R.id.content_frame, internalAlbumsFragmentProvider)
  }
}