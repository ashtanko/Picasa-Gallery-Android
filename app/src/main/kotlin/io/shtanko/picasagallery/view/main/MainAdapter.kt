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

import android.support.v4.util.SparseArrayCompat
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.ViewHolder
import android.view.ViewGroup
import io.shtanko.picasagallery.Config
import io.shtanko.picasagallery.data.entity.AlbumEntity
import io.shtanko.picasagallery.extensions.AlbumsList
import io.shtanko.picasagallery.view.delegate.ViewType
import io.shtanko.picasagallery.view.delegate.ViewTypeAdapterDelegate

class MainAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

  private var items: ArrayList<ViewType> = ArrayList()
  private var delegateAdapters = SparseArrayCompat<ViewTypeAdapterDelegate>()

  init {
    delegateAdapters.put(Config.MAIN, MainAdapterDelegateImpl())
  }

  fun addAlbums(list: AlbumsList) {
    items.addAll(list)
  }

  fun addAlbum(item: AlbumEntity) {
    items.add(item)
  }

  override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
    return delegateAdapters.get(viewType).onCreateViewHolder(parent)
  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    delegateAdapters.get(getItemViewType(position)).onBindViewHolder(holder, this.items[position])
  }

  override fun getItemViewType(position: Int): Int {
    return this.items[position].getViewType()
  }

  override fun getItemCount(): Int {
    val count = items.count()
    return count
  }

}