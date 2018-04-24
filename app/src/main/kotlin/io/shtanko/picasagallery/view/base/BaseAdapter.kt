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

package io.shtanko.picasagallery.view.base

import android.support.v4.util.SparseArrayCompat
import android.support.v7.widget.RecyclerView.Adapter
import android.support.v7.widget.RecyclerView.ViewHolder
import android.view.ViewGroup
import io.shtanko.picasagallery.view.delegate.ViewType
import io.shtanko.picasagallery.view.delegate.ViewTypeAdapterDelegate
import io.shtanko.picasagallery.view.util.OnItemClickListener
import kotlin.properties.Delegates

abstract class BaseAdapter<T : ViewType> : Adapter<ViewHolder>() {

  var onItemClickListener: OnItemClickListener? = null

  var items: List<T> by Delegates.observable(
      emptyList()
  ) { _, _, _ -> notifyDataSetChanged() }

  override fun getItemCount() = items.size

  protected var delegateAdapters = SparseArrayCompat<ViewTypeAdapterDelegate>()

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
    delegateAdapters.get(viewType).onCreateViewHolder(parent)

  override fun onBindViewHolder(
    holder: ViewHolder,
    position: Int
  ) {
    val model = this.items[position]
    holder.itemView.setOnClickListener {
      onItemClickListener?.onItemClicked(model)
    }
    delegateAdapters.get(getItemViewType(position))
        .onBindViewHolder(holder, model)
  }

  override fun getItemViewType(position: Int): Int = this.items[position].getViewType()
}