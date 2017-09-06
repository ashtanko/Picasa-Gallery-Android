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

import android.app.Activity
import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PixelFormat.TRANSLUCENT
import android.os.Build
import android.os.Build.VERSION.SDK_INT
import android.os.Build.VERSION_CODES.LOLLIPOP
import android.os.Build.VERSION_CODES.M
import android.view.ActionMode
import android.view.ActionMode.Callback
import android.view.Gravity.START
import android.view.Gravity.TOP
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsets
import android.view.WindowManager
import android.view.WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
import android.view.WindowManager.LayoutParams.MATCH_PARENT
import android.widget.FrameLayout
import android.widget.Scroller
import io.shtanko.picasagallery.R
import io.shtanko.picasagallery.core.log.FileLog
import io.shtanko.picasagallery.vendors.utils.BackgroundDrawable
import io.shtanko.picasagallery.vendors.utils.ClippingImageView
import io.shtanko.picasagallery.vendors.utils.LayoutHelper
import io.shtanko.picasagallery.view.util.AndroidUtils
import io.shtanko.picasagallery.view.widget.FrameLayoutDrawer

class PhotoViewer {

  var parentActivity: Activity? = null
  private var scroller: Scroller? = null
  var windowView: FrameLayout? = null
  private var containerView: FrameLayoutDrawer? = null
  private var lastInsets: Any? = null
  private val blackPaint = Paint()
  private var animatingImageView: ClippingImageView? = null
  private var windowLayoutParams: WindowManager.LayoutParams? = null
  private val backgroundDrawable = BackgroundDrawable(0xff000000.toInt())
  private val animationValues = Array(2) { FloatArray(8) }

  init {
    blackPaint.color = 0xff000000.toInt()
  }

  fun initActivity(activity: Activity) {
    parentActivity = activity
    scroller = Scroller(activity)

    windowView = object : FrameLayout(activity) {

      override fun drawChild(canvas: Canvas?, child: View?, drawingTime: Long): Boolean {
        val result = super.drawChild(canvas, child, drawingTime)
        if (SDK_INT >= LOLLIPOP && lastInsets != null) {
          val insets = lastInsets as WindowInsets
          canvas?.drawRect(0f, measuredHeight.toFloat(), measuredWidth.toFloat(),
              (measuredHeight + insets.systemWindowInsetBottom).toFloat(), blackPaint)
        }
        return result
      }

      override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {

        var widthSize = View.MeasureSpec.getSize(widthMeasureSpec)
        var heightSize = View.MeasureSpec.getSize(heightMeasureSpec)

        if (SDK_INT >= LOLLIPOP) {
          if (lastInsets != null) {
            val insets = lastInsets as WindowInsets

            heightSize -= insets.systemWindowInsetBottom
            widthSize -= insets.systemWindowInsetRight
          }

        } else {
          if (heightSize > AndroidUtils.displaySize.y) {
            heightSize = AndroidUtils.displaySize.y
          }
        }

        setMeasuredDimension(widthSize, heightSize)

        if (SDK_INT >= LOLLIPOP && lastInsets != null) {
          widthSize -= (lastInsets as WindowInsets).systemWindowInsetLeft
        }

        val layoutParams = animatingImageView?.layoutParams
        if (layoutParams != null) {
          animatingImageView?.measure(
              View.MeasureSpec.makeMeasureSpec(layoutParams.width, View.MeasureSpec.AT_MOST),
              View.MeasureSpec.makeMeasureSpec(layoutParams.height, View.MeasureSpec.AT_MOST))
        }

        containerView?.measure(
            View.MeasureSpec.makeMeasureSpec(widthSize, View.MeasureSpec.EXACTLY),
            View.MeasureSpec.makeMeasureSpec(heightSize, View.MeasureSpec.EXACTLY))
      }

      override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        var x = 0

        if (SDK_INT >= LOLLIPOP && lastInsets != null) {
          x += (lastInsets as WindowInsets).systemWindowInsetLeft
        }

        animatingImageView?.layout(x, 0, x + animatingImageView!!.measuredWidth,
            animatingImageView?.measuredHeight!!)

        containerView?.layout(x, 0, x + containerView?.measuredWidth!!,
            containerView?.measuredHeight!!)

        if (changed) {

        }
      }

      override fun startActionModeForChild(originalView: View?, callback: Callback?,
          type: Int): ActionMode {
        if (SDK_INT >= M) {
          val view = parentActivity?.findViewById<View>(android.R.id.content)
          if (view is ViewGroup) {
            try {
              return view.startActionModeForChild(originalView, callback, type)
            } catch (e: Throwable) {
              FileLog.e(e)
            }
          }
        }
        return super.startActionModeForChild(originalView, callback, type)
      }
    }

    windowView?.setBackgroundDrawable(backgroundDrawable)
    windowView?.clipChildren = true
    windowView?.isFocusable = false

    animatingImageView = ClippingImageView(activity)
    animatingImageView?.setAnimationValues(animationValues)
    windowView?.addView(animatingImageView, LayoutHelper.createFrame(40, 40F))


    containerView = FrameLayoutDrawer(activity)
    containerView?.isFocusable = false

    windowView?.addView(containerView,
        LayoutHelper.createFrame(LayoutHelper.MATCH_PARENT, LayoutHelper.MATCH_PARENT,
            TOP or START))

    if (SDK_INT >= LOLLIPOP) {
      containerView?.fitsSystemWindows = true

      containerView?.setOnApplyWindowInsetsListener({ _, insets ->
        lastInsets = insets
        val oldInsets = lastInsets as WindowInsets
        if (oldInsets.toString() != insets.toString()) {
          windowView?.requestLayout()
        }
        insets.consumeSystemWindowInsets()
      })
    }

    windowLayoutParams = WindowManager.LayoutParams()
    windowLayoutParams?.height = MATCH_PARENT
    windowLayoutParams?.format = TRANSLUCENT
    windowLayoutParams?.width = MATCH_PARENT
    windowLayoutParams?.gravity = TOP or START
    windowLayoutParams?.flags = FLAG_NOT_FOCUSABLE
  }

  fun openPhoto() {
    val wm = parentActivity?.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    wm.addView(windowView, windowLayoutParams)

    val bitmap = BitmapFactory.decodeResource(parentActivity?.resources,
        R.drawable.image_placeholder)
    animatingImageView?.setImageBitmap(bitmap)
  }

  private fun destroyPhotoViewer() {
    if (windowView?.parent != null) {
      val wm = parentActivity?.getSystemService(Context.WINDOW_SERVICE) as WindowManager
      wm.removeViewImmediate(windowView)
    }
    windowView = null
  }

  fun closePhoto() {
    destroyPhotoViewer()
  }

  private fun onPhotoClosed() {
    containerView?.post {
      animatingImageView?.setImageBitmap(null)
      try {
        if (windowView?.parent != null) {
          val wm = parentActivity?.getSystemService(Context.WINDOW_SERVICE) as WindowManager
          wm.removeView(windowView)
        }
      } catch (e: Exception) {
        FileLog.e(e)
      }
    }
  }

}