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

package io.shtanko.picasagallery.view.main

import android.content.Intent
import android.os.Bundle
import dagger.Lazy
import io.shtanko.picasagallery.Config
import io.shtanko.picasagallery.R
import io.shtanko.picasagallery.data.entity.album.Album
import io.shtanko.picasagallery.data.entity.album.AlbumType
import io.shtanko.picasagallery.view.album.InternalAlbumsActivity
import io.shtanko.picasagallery.view.base.BaseActivity
import io.shtanko.picasagallery.view.delegate.ViewType
import io.shtanko.picasagallery.view.main.MainFragment.AlbumClickListener
import javax.inject.Inject

class MainActivity : BaseActivity(), AlbumClickListener {

  @Inject lateinit var presenter: MainPresenter
  @Inject lateinit var mainFragmentProvider: Lazy<MainFragment>

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.container_activity)
    addFragment(R.id.content_frame, mainFragmentProvider)
  }

  override fun onAlbumClick(model: ViewType) {
    if (model is AlbumType) {
      val album = model as Album
      val intent = Intent(this, InternalAlbumsActivity::class.java)
      intent.putExtra(Config.PHOTO_ID_KEY, album.id)
      intent.putExtra(Config.ALBUM_ID_KEY, album.albumId)
      startActivity(intent)
    }
  }
}