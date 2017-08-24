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

import io.shtanko.picasagallery.data.AlbumDataSource
import io.shtanko.picasagallery.data.AlbumRepository
import io.shtanko.picasagallery.extensions.AlbumsList
import io.shtanko.picasagallery.util.ActivityScoped
import io.shtanko.picasagallery.view.main.MainContract.View
import javax.annotation.Nullable
import javax.inject.Inject

@ActivityScoped
class MainPresenter @Inject constructor(
    var repository: AlbumRepository
) : MainContract.Presenter {

  @Nullable
  private var view: MainContract.View? = null

  override fun takeView(view: View) {
    this.view = view
  }

  override fun dropView() {
    this.view = null
  }

  override fun getAlbums() {
    view?.setLoadingIndicator(true)
    repository.getAlbums(object : AlbumDataSource.LoadAlbumsCallback {
      override fun onAlbumsLoaded(list: AlbumsList) {
        view?.onShowAlbums(list)
        view?.setLoadingIndicator(false)
      }

      override fun onDataNotAvailable(message: String) {
        view?.onShowError(message)
        view?.setLoadingIndicator(false)
      }
    })
  }
}