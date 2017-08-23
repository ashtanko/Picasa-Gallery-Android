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

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.shtanko.picasagallery.R
import io.shtanko.picasagallery.extensions.AlbumsList
import io.shtanko.picasagallery.extensions.shortToast
import io.shtanko.picasagallery.util.ActivityScoped
import io.shtanko.picasagallery.view.base.BaseFragment
import javax.inject.Inject

@ActivityScoped
class MainFragment @Inject constructor() : BaseFragment(), MainContract.View {

  @Inject lateinit var presenter: MainContract.Presenter

  val mainAdapter = MainAdapter()

  override fun onResume() {
    super.onResume()

  }

  override fun onDestroy() {
    super.onDestroy()
    presenter.dropView()
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
      savedInstanceState: Bundle?): View? {
    val rootView = inflater.inflate(R.layout.fragment_main, container, false)
    presenter.takeView(this)
    presenter.getAlbums()

    val gridLayoutManager = GridLayoutManager(activity, 15)

    with(rootView) {

      rootView.findViewById<RecyclerView>(R.id.grid).apply {
        layoutManager = gridLayoutManager
        adapter = mainAdapter
      }
    }

    return rootView
  }

  override fun onShowAlbums(list: AlbumsList) {
    mainAdapter.addAlbums(list)
  }

  override fun onShowError(message: String) {
    shortToast(message)
  }
}