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

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.app.Activity
import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PixelFormat.TRANSLUCENT
import android.os.Build.VERSION.SDK_INT
import android.os.Build.VERSION_CODES.LOLLIPOP
import android.os.Build.VERSION_CODES.M
import android.view.ActionMode
import android.view.ActionMode.Callback
import android.view.GestureDetector
import android.view.Gravity.START
import android.view.Gravity.TOP
import android.view.MotionEvent
import android.view.MotionEvent.ACTION_DOWN
import android.view.MotionEvent.ACTION_MOVE
import android.view.MotionEvent.ACTION_POINTER_DOWN
import android.view.View
import android.view.View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
import android.view.View.SYSTEM_UI_FLAG_LAYOUT_STABLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.view.WindowInsets
import android.view.WindowManager
import android.view.WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS
import android.view.WindowManager.LayoutParams.FLAG_LAYOUT_INSET_DECOR
import android.view.WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN
import android.view.WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
import android.view.WindowManager.LayoutParams.MATCH_PARENT
import android.view.animation.DecelerateInterpolator
import android.widget.FrameLayout
import android.widget.Scroller
import io.shtanko.picasagallery.R
import io.shtanko.picasagallery.core.log.FileLog
import io.shtanko.picasagallery.extensions.createFrame
import io.shtanko.picasagallery.vendors.utils.BackgroundDrawable
import io.shtanko.picasagallery.vendors.utils.ClippingImageView
import io.shtanko.picasagallery.view.util.AndroidUtils
import io.shtanko.picasagallery.view.widget.FrameLayoutDrawer

class PhotoViewer : GestureDetector.OnDoubleTapListener, GestureDetector.OnGestureListener {

  private var scale = 1f
  private var translationX: Float = 0.toFloat()
  private var translationY: Float = 0.toFloat()
  private var canZoom = true
  private var imageMoveAnimation: AnimatorSet? = null
  private var gestureDetector: GestureDetector? = null
  private var isVisible: Boolean = false
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
  private var zooming: Boolean = false
  private var zoomAnimation: Boolean = false
  private var animateToScale: Float = 0.toFloat()
  private var animateToX: Float = 0.toFloat()
  private var animateToY: Float = 0.toFloat()
  private var animationStartTime: Long = 0
  private val interpolator = DecelerateInterpolator(1.5f)


  init {
    blackPaint.color = 0xff000000.toInt()
  }


  override fun onDoubleTap(p0: MotionEvent?): Boolean {

    if (!canZoom || scale == 1.0f && (translationY != 0.toFloat() || translationX != 0.toFloat())) {
      return false
    }



    return true
  }

  override fun onDoubleTapEvent(p0: MotionEvent?): Boolean = false

  override fun onSingleTapConfirmed(p0: MotionEvent?): Boolean {
    return true
  }


  private fun animateTo(newScale: Float, newTx: Float, newTy: Float, isZoom: Boolean) {
    animateTo(newScale, newTx, newTy, isZoom, 250)
  }

  private fun animateTo(newScale: Float, newTx: Float, newTy: Float, isZoom: Boolean,
      duration: Int) {

    if (scale == newScale && translationX == newTx && translationY == newTy) {
      return
    }

    zoomAnimation = isZoom
    animateToScale = newScale
    animateToX = newTx
    animateToY = newTy
    animationStartTime = System.currentTimeMillis()

    imageMoveAnimation = AnimatorSet()

    imageMoveAnimation?.playTogether(
        ObjectAnimator.ofFloat(this, "animationValue", 0.toFloat(), 1.toFloat())
    )

    imageMoveAnimation?.interpolator = interpolator
    imageMoveAnimation?.duration = duration.toLong()

    imageMoveAnimation?.addListener(object : AnimatorListenerAdapter() {
      override fun onAnimationEnd(animation: Animator) {
        imageMoveAnimation = null
        containerView?.invalidate()
      }
    })
    imageMoveAnimation?.start()
  }


  private fun updateMinMax(scale: Float) {

  }

  private fun onTouchEvent(ev: MotionEvent?): Boolean {

    if (ev?.actionMasked == ACTION_DOWN || ev?.actionMasked == ACTION_POINTER_DOWN) {

    } else if (ev?.actionMasked == ACTION_MOVE) {

    }

    return false
  }

  fun initActivity(activity: Activity) {
    parentActivity = activity
    scroller = Scroller(activity)

    windowView = object : FrameLayout(activity) {

      override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean =
          super.onInterceptTouchEvent(ev)

      override fun onTouchEvent(event: MotionEvent?): Boolean =
          isVisible && this@PhotoViewer.onTouchEvent(event)

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


        if (changed) {
          translationX = 0f
          translationY = 0f
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

    windowView?.addView(animatingImageView, createFrame(40, 40F))


    containerView = FrameLayoutDrawer(activity)
    containerView?.isFocusable = false

    windowView?.addView(containerView, createFrame(MATCH_PARENT, MATCH_PARENT, TOP or START))


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
      containerView?.systemUiVisibility = SYSTEM_UI_FLAG_LAYOUT_STABLE or SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
    }

    windowLayoutParams = WindowManager.LayoutParams()
    windowLayoutParams?.height = MATCH_PARENT
    windowLayoutParams?.format = TRANSLUCENT
    windowLayoutParams?.width = MATCH_PARENT
    windowLayoutParams?.gravity = TOP or START
    windowLayoutParams?.flags = FLAG_NOT_FOCUSABLE

    if (SDK_INT >= LOLLIPOP) {
      windowLayoutParams?.flags = FLAG_LAYOUT_IN_SCREEN or
          FLAG_LAYOUT_INSET_DECOR or
          FLAG_NOT_FOCUSABLE or
          FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS
    } else {
      windowLayoutParams?.flags = FLAG_NOT_FOCUSABLE
    }

    gestureDetector = GestureDetector(containerView?.context, this)
    gestureDetector?.setOnDoubleTapListener(this)

  }

  fun openPhoto() {
    isVisible = true
    val wm = parentActivity?.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    wm.addView(windowView, windowLayoutParams)

    animatingImageView?.visibility = VISIBLE

    animatingImageView?.alpha = 1.0f
    animatingImageView?.pivotX = 0.0f
    animatingImageView?.pivotY = 0.0f

    val bitmap = BitmapFactory.decodeResource(parentActivity?.resources,
        R.drawable.image_placeholder)

    val layoutParams = animatingImageView?.layoutParams
    layoutParams?.width = bitmap.width
    layoutParams?.height = bitmap.height
    animatingImageView?.layoutParams = layoutParams



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

  override fun onShowPress(p0: MotionEvent?) {
    TODO(
        "not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun onSingleTapUp(p0: MotionEvent?): Boolean {
    TODO(
        "not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun onDown(p0: MotionEvent?): Boolean {
    TODO(
        "not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun onFling(p0: MotionEvent?, p1: MotionEvent?, p2: Float, p3: Float): Boolean {
    TODO(
        "not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun onScroll(p0: MotionEvent?, p1: MotionEvent?, p2: Float, p3: Float): Boolean {
    TODO(
        "not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun onLongPress(p0: MotionEvent?) {
    TODO(
        "not implemented") //To change body of created functions use File | Settings | File Templates.
  }

}