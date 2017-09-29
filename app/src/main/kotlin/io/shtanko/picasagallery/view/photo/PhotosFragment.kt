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

import android.content.Context
import android.os.Bundle
import android.support.v4.content.ContextCompat.getColor
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import io.shtanko.picasagallery.Config.THREE_COLUMNS_GRID
import io.shtanko.picasagallery.R
import io.shtanko.picasagallery.R.color
import io.shtanko.picasagallery.R.dimen
import io.shtanko.picasagallery.R.layout
import io.shtanko.picasagallery.data.entity.photo.PhotoType
import io.shtanko.picasagallery.extensions.PhotosList
import io.shtanko.picasagallery.util.ActivityScoped
import io.shtanko.picasagallery.util.ItemDividerDecoration
import io.shtanko.picasagallery.view.base.BaseFragment
import io.shtanko.picasagallery.view.delegate.ViewType
import io.shtanko.picasagallery.view.util.OnItemClickListener
import javax.inject.Inject

@ActivityScoped
class PhotosFragment @Inject constructor() : BaseFragment(), PhotosContract.View, OnItemClickListener {

	// region injection
	@Inject lateinit var presenter: PhotosContract.Presenter
	@Inject lateinit var photosAdapter: PhotosAdapter
	// endregion

	private var progressBar: ProgressBar? = null
	private var photoClickListener: PhotoClickListener? = null

	override fun onAttach(context: Context?) {
		super.onAttach(context)
		if (context is PhotosActivity) {
			this.photoClickListener = context
		}
	}

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
			savedInstanceState: Bundle?): View? {
		val rootView = inflater.inflate(layout.container_list_fragment, container, false)
		presenterActions()
		viewActions(rootView)
		return rootView
	}

	override fun onDetach() {
		super.onDetach()
		this.photoClickListener = null
	}

	override fun onDestroy() {
		super.onDestroy()
		presenter.dropView()
	}

	override fun showError(message: String) {
	}

	override fun setLoadingIndicator(active: Boolean) {
	}

	override fun <T> onItemClicked(model: T) {
		if (model is PhotoType) {
			presenter.onPhotoClick(model)
		}
	}

	override fun showPhotos(photos: PhotosList) {
		photosAdapter.items = photos
	}

	override fun viewPhoto(model: ViewType) {
		photoClickListener?.onPhotoClick(model)
	}

	private fun presenterActions() {
		presenter.takeView(this)
		presenter.getPhotos()
	}

	private fun viewActions(rootView: View) {
		val gridLayoutManager = GridLayoutManager(activity, THREE_COLUMNS_GRID)

		with(rootView) {
			progressBar = rootView.findViewById(R.id.progress_bar)

			rootView.findViewById<RecyclerView>(R.id.grid).apply {
				layoutManager = gridLayoutManager
				adapter = photosAdapter
				addItemDecoration(
						ItemDividerDecoration(
								activity.resources.getDimensionPixelSize(dimen.divider_height),
								getColor(activity, color.divider)
						))
			}

			photosAdapter.onItemClickListener = this@PhotosFragment
		}
	}

	interface PhotoClickListener {
		fun onPhotoClick(model: ViewType)
	}
}