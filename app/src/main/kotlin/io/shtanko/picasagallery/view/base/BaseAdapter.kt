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


import android.support.annotation.LayoutRes
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

abstract class BaseAdapter : RecyclerView.Adapter<BaseViewHolder>() {

  private var items: MutableList<Any> = ArrayList<Any>()

  fun items(): List<Any> {
    return items
  }

  fun clear() {
    this.items.clear()
  }

  fun <T> addItems(items: List<T>) {
    this.items.add(items)
  }

  override fun onBindViewHolder(holder: BaseViewHolder?, position: Int) {

  }

  protected fun objectFromSectionRow(index: Int): Any {
    return items[index]
  }

  protected abstract fun viewHolder(@LayoutRes layout: Int, view: View): BaseViewHolder

  override fun onCreateViewHolder(viewGroup: ViewGroup, layout: Int): BaseViewHolder {
    val view = inflateView(viewGroup, layout)
    val viewHolder = viewHolder(layout, view)
    return viewHolder
  }

  private fun inflateView(viewGroup: ViewGroup, viewType: Int): View {
    val layoutInflater = LayoutInflater.from(viewGroup.context)
    return layoutInflater.inflate(viewType, viewGroup, false)
  }
}