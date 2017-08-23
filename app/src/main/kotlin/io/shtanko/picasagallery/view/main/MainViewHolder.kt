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

import android.view.View
import io.shtanko.picasagallery.data.entity.AlbumEntity
import io.shtanko.picasagallery.view.base.BaseViewHolder
import io.shtanko.picasagallery.view.delegate.ViewType


class MainViewHolder(itemView: View) : BaseViewHolder(itemView) {

  override fun bind(item: ViewType) {
    if (item is AlbumEntity) {
      item.title
    }
  }
}