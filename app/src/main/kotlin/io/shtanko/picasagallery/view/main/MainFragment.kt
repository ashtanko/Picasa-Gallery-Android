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

import android.content.Context
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ProgressBar
import io.shtanko.picasagallery.Config.TWO_COLUMNS_GRID
import io.shtanko.picasagallery.R
import io.shtanko.picasagallery.data.entity.album.AlbumType
import io.shtanko.picasagallery.extensions.AlbumsList
import io.shtanko.picasagallery.extensions.shortToast
import io.shtanko.picasagallery.util.ActivityScoped
import io.shtanko.picasagallery.util.ItemDividerDecoration
import io.shtanko.picasagallery.view.base.BaseFragment
import io.shtanko.picasagallery.view.delegate.ViewType
import io.shtanko.picasagallery.view.main.MainContract.Presenter
import io.shtanko.picasagallery.view.main.MainContract.View
import io.shtanko.picasagallery.view.util.OnItemClickListener
import javax.inject.Inject

@ActivityScoped
class MainFragment @Inject constructor() : BaseFragment(), View, OnItemClickListener {

	// region injection
	@Inject lateinit var presenter: Presenter
	@Inject lateinit var mainAdapter: MainAdapter
	// endregion

	private lateinit var progressBar: ProgressBar
	private var albumClickListener: AlbumClickListener? = null

	override fun onAttach(context: Context?) {
		super.onAttach(context)
		if (context is MainActivity) {
			this.albumClickListener = context
		}
	}

	override fun onDetach() {
		super.onDetach()
		this.albumClickListener = null
	}

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
			savedInstanceState: Bundle?): android.view.View? {
		val rootView = inflater.inflate(R.layout.container_list_fragment, container, false)
		presenter.takeView(this)
		presenter.getAlbums()

		val gridLayoutManager = GridLayoutManager(activity, TWO_COLUMNS_GRID)

		with(rootView) {
			progressBar = rootView.findViewById(R.id.progress_bar)

			rootView.findViewById<RecyclerView>(R.id.grid).apply {
				setHasFixedSize(true)
				layoutManager = gridLayoutManager
				adapter = mainAdapter

				addItemDecoration(ItemDividerDecoration(
						activity.resources.getDimensionPixelSize(R.dimen.divider_height),
						ContextCompat.getColor(activity, R.color.divider)))
			}
			mainAdapter.onItemClickListener = this@MainFragment
		}

		return rootView
	}

	override fun onDestroy() {
		super.onDestroy()
		presenter.dropView()
	}

	override fun showError(message: String) {
		shortToast(message)
	}

	override fun onShowAlbums(list: AlbumsList) {
		mainAdapter.items = list
	}

	override fun setLoadingIndicator(active: Boolean) {
		progressBar.visibility = if (active) VISIBLE else GONE
	}

	override fun <T> onItemClicked(model: T) {
		if (model is AlbumType) {
			presenter.onAlbumClick(model)
		}
	}

	override fun viewAlbum(model: ViewType) {
		albumClickListener?.onAlbumClick(model)
	}

	interface AlbumClickListener {
		fun onAlbumClick(model: ViewType)
	}
}