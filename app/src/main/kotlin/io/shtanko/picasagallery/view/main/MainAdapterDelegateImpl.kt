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

import android.support.v7.widget.RecyclerView.ViewHolder
import android.view.LayoutInflater
import android.view.ViewGroup
import io.shtanko.picasagallery.R
import io.shtanko.picasagallery.view.delegate.ViewType
import io.shtanko.picasagallery.view.delegate.ViewTypeAdapterDelegate


class MainAdapterDelegateImpl : ViewTypeAdapterDelegate {
  override fun onCreateViewHolder(parent: ViewGroup?): ViewHolder {
    val view = LayoutInflater.from(parent!!.context).inflate(R.layout.album_item, parent, false)
    val viewHolder = MainViewHolder(view)
    return viewHolder
  }

  override fun onBindViewHolder(holder: ViewHolder, item: ViewType) {
    holder as MainViewHolder
    holder.bind(item)
  }

}