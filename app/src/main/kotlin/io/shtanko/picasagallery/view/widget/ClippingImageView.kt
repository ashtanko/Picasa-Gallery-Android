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

package io.shtanko.picasagallery.view.widget

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapShader
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.Paint.ANTI_ALIAS_FLAG
import android.graphics.RectF
import android.graphics.Shader.TileMode.CLAMP
import android.util.AttributeSet
import android.view.View
import io.shtanko.picasagallery.core.log.FileLog
import io.shtanko.picasagallery.view.util.AndroidUtils
import javax.inject.Inject

class ClippingImageView : View {

  // region private fields
  private val bitmapRect: RectF get() = RectF()
  private val roundRect: RectF get() = RectF()
  private var bitmapShader: BitmapShader? = null
  private val roundPaint: Paint get() = Paint(ANTI_ALIAS_FLAG)
  // endregion

  // region fields
  var radius: Int = 0
  var animationValues: Array<FloatArray>? = null @Inject set

  var bitmap: Bitmap? = null
    get() = field
    set(value) {
      field = value
      if (field != null) {
        bitmapRect.set(0f, 0f, bitmap?.width!!.toFloat(), bitmap?.height!!.toFloat())

        if (needRadius) {
          bitmapShader = BitmapShader(bitmap, CLAMP, CLAMP)
          roundPaint.shader = bitmapShader
        }
      }

      this.invalidate()
    }

  var needRadius: Boolean = false
  var orientation: Int = 0

  private val drawRect: RectF
    get() = RectF()

  var clipBottom: Int = 0
    get() = field
    set(value) {
      field = value
      this.invalidate()
    }

  var clipLeft: Int = 0
    get() = field
    set(value) {
      field = value
      this.invalidate()
    }

  var clipRight: Int = 0
    get() = field
    set(value) {
      field = value
      this.invalidate()
    }

  var clipTop: Int = 0
    get() = field
    set(value) {
      field = value
      this.invalidate()
    }

  var paint: Paint = Paint()


  private val shaderMatrix: Matrix get() = Matrix()
  // endregion

  constructor(context: Context) : super(context)

  constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

  constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs,
      defStyleAttr)

  init {
    paint.isFilterBitmap = true
  }

  override fun onDraw(canvas: Canvas?) {
    if (visibility != VISIBLE)
      return

    if (bitmap != null) {

      val scaleY = scaleY
      canvas?.save()

      when (needRadius) {

        true -> {
          shaderMatrix.reset()
          roundRect.set(0f, 0f, width.toFloat(), height.toFloat())

          val bitmapW: Int
          val bitmapH: Int

          if (orientation % 360 == 90 || orientation % 360 == 270) {
            bitmapW = bitmap!!.height
            bitmapH = bitmap!!.width
          } else {
            bitmapW = bitmap!!.width
            bitmapH = bitmap!!.height
          }

          val scaleW = if (width != 0) bitmapW.toFloat() / width else 1.0f
          val scaleH = if (height != 0) bitmapH.toFloat() / height else 1.0f
          val scale = Math.min(scaleW, scaleH)

          if (Math.abs(scaleW - scaleH) > 0.00001f) {

            val w = Math.floor((width * scale).toDouble()).toInt()
            val h = Math.floor((height * scale).toDouble()).toInt()
            bitmapRect.set(((bitmapW - w) / 2).toFloat(), ((bitmapH - h) / 2).toFloat(),
                w.toFloat(), h.toFloat())
            AndroidUtils.setRectToRect(shaderMatrix, bitmapRect, roundRect, orientation,
                Matrix.ScaleToFit.START)

          } else {
            bitmapRect.set(0f, 0f, bitmap!!.width.toFloat(), bitmap!!.height.toFloat())
            AndroidUtils.setRectToRect(shaderMatrix, bitmapRect, roundRect, orientation,
                Matrix.ScaleToFit.FILL)
          }

          bitmapShader?.setLocalMatrix(shaderMatrix)

          canvas?.clipRect(clipLeft / scaleY, clipTop / scaleY, width - clipRight / scaleY,
              height - clipBottom / scaleY)
          canvas?.drawRoundRect(roundRect, radius.toFloat(), radius.toFloat(), roundPaint)

        }

        false -> {
          if (orientation == 90 || orientation == 270) {

            drawRect.set((-height / 2).toFloat(), (-width / 2).toFloat(), (height / 2).toFloat(),
                (width / 2).toFloat())
            matrix.setRectToRect(bitmapRect, drawRect, Matrix.ScaleToFit.FILL)
            matrix.postRotate(orientation.toFloat(), 0f, 0f)
            matrix.postTranslate((width / 2).toFloat(), (height / 2).toFloat())

          } else if (orientation == 180) {
            drawRect.set((-width / 2).toFloat(), (-height / 2).toFloat(), (width / 2).toFloat(),
                (height / 2).toFloat())
            matrix.setRectToRect(bitmapRect, drawRect, Matrix.ScaleToFit.FILL)
            matrix.postRotate(orientation.toFloat(), 0f, 0f)
            matrix.postTranslate((width / 2).toFloat(), (height / 2).toFloat())
          } else {
            drawRect.set(0f, 0f, width.toFloat(), height.toFloat())
            matrix.setRectToRect(bitmapRect, drawRect, Matrix.ScaleToFit.FILL)
          }

          canvas?.clipRect(clipLeft / scaleY, clipTop / scaleY, width - clipRight / scaleY,
              height - clipBottom / scaleY)
          try {
            canvas?.drawBitmap(bitmap, matrix, paint)
          } catch (e: Exception) {
            FileLog.e(e)
          }
        }
      }

      canvas?.restore()
    }
  }
}