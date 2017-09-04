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
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import io.shtanko.picasagallery.Config.THREE_COLUMNS_GRID
import io.shtanko.picasagallery.R
import io.shtanko.picasagallery.util.ActivityScoped
import io.shtanko.picasagallery.view.base.BaseFragment
import javax.inject.Inject

@ActivityScoped
class PhotosFragment @Inject constructor() : BaseFragment(), PhotosContract.View {

  private var progressBar: ProgressBar? = null

  // region injection
  @Inject lateinit var presenter: PhotosContract.Presenter
  // endregion

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
      savedInstanceState: Bundle?): View? {
    val rootView = inflater.inflate(R.layout.container_list_fragment, container, false)
    presenter.takeView(this)

    val gridLayoutManager = GridLayoutManager(activity, THREE_COLUMNS_GRID)

    with(rootView) {
      progressBar = rootView.findViewById<ProgressBar>(R.id.progress_bar)

      rootView.findViewById<RecyclerView>(R.id.grid).apply {
        layoutManager = gridLayoutManager
      }
    }

    return rootView
  }

  override fun onDestroy() {
    super.onDestroy()
    presenter.dropView()
  }

  override fun setLoadingIndicator(active: Boolean) {
  }


}