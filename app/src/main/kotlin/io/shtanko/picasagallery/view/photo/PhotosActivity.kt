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

package io.shtanko.picasagallery.view.photo

import android.os.Bundle
import dagger.Lazy
import io.shtanko.picasagallery.R
import io.shtanko.picasagallery.view.base.BaseActivity
import io.shtanko.picasagallery.view.delegate.ViewType
import io.shtanko.picasagallery.view.photo.PhotosFragment.PhotoClickListener
import javax.inject.Inject

class PhotosActivity : BaseActivity(), PhotoClickListener {

  @Inject lateinit var presenter: PhotosPresenter
  @Inject lateinit var fragmentProvider: Lazy<PhotosFragment>
  private val viewer = PhotoViewer()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.container_activity)
    addFragment(R.id.content_frame, fragmentProvider)
  }

  override fun onPhotoClick(model: ViewType) {
    viewer.initActivity(this)
    viewer.openPhoto()
  }

  override fun onDestroy() {
    super.onDestroy()
    viewer.closePhoto()
  }
}