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
import android.support.v4.content.ContextCompat
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import io.shtanko.picasagallery.Config
import io.shtanko.picasagallery.R
import io.shtanko.picasagallery.R.color
import io.shtanko.picasagallery.R.dimen
import io.shtanko.picasagallery.extensions.ContentList
import io.shtanko.picasagallery.util.ActivityScoped
import io.shtanko.picasagallery.util.ItemDividerDecoration
import io.shtanko.picasagallery.view.album.InternalAlbumsContract.View
import io.shtanko.picasagallery.view.base.BaseFragment
import javax.inject.Inject

@ActivityScoped
class InternalAlbumsFragment @Inject constructor() : BaseFragment(), View {

  // region injection
  @Inject lateinit var presenter: InternalAlbumsContract.Presenter
  @Inject lateinit var internalAlbumsAdapter: InternalAlbumsAdapter
  // endregion

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
      savedInstanceState: Bundle?): android.view.View? {
    val rootView = inflater.inflate(R.layout.container_list_fragment, container, false)
    presenter.takeView(this)
    presenter.getContent()

    val gridLayoutManager = GridLayoutManager(activity, Config.TWO_COLUMNS_GRID)

    with(rootView) {
      rootView.findViewById<RecyclerView>(R.id.grid).apply {
        setHasFixedSize(true)
        layoutManager = gridLayoutManager
        adapter = internalAlbumsAdapter

        addItemDecoration(ItemDividerDecoration(
            activity.resources.getDimensionPixelSize(dimen.divider_height),
            ContextCompat.getColor(activity, color.divider)))
      }
    }

    return rootView

  }

  override fun showError(message: String) {
  }

  override fun showData(data: ContentList) {
  }

  override fun setLoadingIndicator(active: Boolean) {

  }
}