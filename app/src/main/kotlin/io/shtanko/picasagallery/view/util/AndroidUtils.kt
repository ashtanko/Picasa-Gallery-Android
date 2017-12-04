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

package io.shtanko.picasagallery.view.util

import android.graphics.Matrix
import android.graphics.Matrix.ScaleToFit.FILL
import android.graphics.Point
import android.graphics.RectF
import io.shtanko.picasagallery.PicasaApplication

object AndroidUtils {
  var statusBarHeight = 0
  var displaySize = Point()
  var density = 1f

  fun setRectToRect(matrix: Matrix, src: RectF, dst: RectF, rotation: Int,
      align: Matrix.ScaleToFit) {
    val tx: Float
    var sx: Float
    val ty: Float
    var sy: Float
    if (rotation == 90 || rotation == 270) {
      sx = dst.height() / src.width()
      sy = dst.width() / src.height()
    } else {
      sx = dst.width() / src.width()
      sy = dst.height() / src.height()
    }
    if (align != FILL) {
      if (sx > sy) {
        sx = sy
      } else {
        sy = sx
      }
    }
    tx = -src.left * sx
    ty = -src.top * sy

    matrix.setTranslate(dst.left, dst.top)
    if (rotation == 90) {
      matrix.preRotate(90f)
      matrix.preTranslate(0f, -dst.width())
    } else if (rotation == 180) {
      matrix.preRotate(180f)
      matrix.preTranslate(-dst.width(), -dst.height())
    } else if (rotation == 270) {
      matrix.preRotate(270f)
      matrix.preTranslate(-dst.height(), 0f)
    }

    matrix.preScale(sx, sy)
    matrix.preTranslate(tx, ty)
  }

  fun dp(value: Float): Int {
    return if (value == 0f) {
      0
    } else Math.ceil((density * value).toDouble()).toInt()
  }

  fun runOnUIThread(runnable: Runnable) {
    runOnUIThread(runnable, 0)
  }

  private fun runOnUIThread(runnable: Runnable, delay: Long) {
    if (delay == 0.toLong()) {
      PicasaApplication.applicationHandler.post(runnable)
    } else {
      PicasaApplication.applicationHandler.postDelayed(runnable, delay)
    }
  }

}