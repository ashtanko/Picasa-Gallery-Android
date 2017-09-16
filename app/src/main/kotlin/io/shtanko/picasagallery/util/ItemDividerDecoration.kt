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

package io.shtanko.picasagallery.util

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Paint.Style.FILL
import android.support.annotation.ColorInt
import android.support.annotation.Dimension
import android.support.annotation.NonNull
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.State
import io.shtanko.picasagallery.view.util.Divided
import kotlin.properties.Delegates

class ItemDividerDecoration constructor(
    @Dimension var dividerSize: Int,
    @ColorInt dividerColor: Int
) : RecyclerView.ItemDecoration() {

  private var paint: Paint by Delegates.notNull()

  init {
    paint = Paint()
    paint.color = dividerColor
    paint.style = FILL
  }

  override fun onDrawOver(canvas: Canvas, parent: RecyclerView, state: State?) {
    if (parent.isAnimating) return

    val childCount = parent.childCount

    val lm = parent.layoutManager

    for (i in 0 until childCount) {
      val child = parent.getChildAt(i)
      val viewHolder = parent.getChildViewHolder(child)
      if (viewHolder is Divided) {
        val right = lm.getDecoratedRight(child)
        val bottom = lm.getDecoratedBottom(child)
        // draw the bottom divider
        canvas.drawRect(lm.getDecoratedLeft(child).toFloat(),
            (bottom - dividerSize).toFloat(),
            right.toFloat(),
            bottom.toFloat(),
            paint)
        // draw the right edge divider
        canvas.drawRect((right - dividerSize).toFloat(),
            lm.getDecoratedTop(child).toFloat(),
            right.toFloat(),
            (bottom - dividerSize).toFloat(),
            paint)
      }
    }
  }

}