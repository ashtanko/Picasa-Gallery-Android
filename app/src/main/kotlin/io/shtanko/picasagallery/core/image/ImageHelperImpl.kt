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

package io.shtanko.picasagallery.core.image

import android.R.interpolator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator.ofFloat
import android.content.Context
import android.graphics.ColorMatrixColorFilter
import android.os.Build.VERSION.SDK_INT
import android.os.Build.VERSION_CODES.JELLY_BEAN
import android.os.Build.VERSION_CODES.LOLLIPOP
import android.view.animation.AnimationUtils.loadInterpolator
import android.widget.ImageView
import com.bumptech.glide.DrawableRequestBuilder
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority.LOW
import com.bumptech.glide.Priority.NORMAL
import com.bumptech.glide.load.engine.DiskCacheStrategy.SOURCE
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import com.bumptech.glide.load.resource.drawable.GlideDrawable
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.animation.ViewPropertyAnimation.Animator
import com.bumptech.glide.request.target.Target
import io.shtanko.picasagallery.R
import io.shtanko.picasagallery.view.util.FadeAnimator
import io.shtanko.picasagallery.view.util.ObservableColorMatrix
import io.shtanko.picasagallery.view.util.OnLoadImageListener
import java.lang.Exception

class ImageHelperImpl : ImageHelper {


  override fun process(context: Context?, imageView: ImageView?, url: String?) {
    load(context, url, object : OnLoadImageListener {
      override fun onLoadSucceed() {

        if (SDK_INT >= LOLLIPOP) {
          imageView?.setHasTransientState(true)

          val matrix = ObservableColorMatrix
          val saturation = ofFloat(matrix, "saturation", 0f,
              1f)

          saturation.addUpdateListener { imageView?.colorFilter = ColorMatrixColorFilter(matrix) }
          saturation.duration = 2800

          saturation.interpolator = loadInterpolator(context,
              interpolator.fast_out_slow_in)

          saturation.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: android.animation.Animator?) {
              clean(imageView)
            }
          })
          saturation.start()
        } else {
          clean(imageView)
        }
      }

      override fun onLoadFailed() {
        clean(imageView)
      }
    }, imageView)
  }

  private fun clean(imageView: ImageView?) {
    imageView?.clearColorFilter()
    if (SDK_INT >= JELLY_BEAN) {
      imageView?.setHasTransientState(false)
    }
  }

  override fun load(context: Context?, url: String?, listener: OnLoadImageListener?,
      imageView: ImageView?) {

    val thumbnailRequest = Glide
        .with(context)
        .load(url)
        .diskCacheStrategy(SOURCE)
        .placeholder(R.drawable.image_placeholder)
        .listener(object : RequestListener<String, GlideDrawable> {

          override fun onException(e: Exception?, model: String, target: Target<GlideDrawable>,
              isFirstResource: Boolean): Boolean = false

          override fun onResourceReady(resource: GlideDrawable, model: String,
              target: Target<GlideDrawable>,
              isFromMemoryCache: Boolean, isFirstResource: Boolean): Boolean {
            imageView?.isEnabled = true
            return false
          }
        })

    if (listener != null && SDK_INT >= LOLLIPOP) {
      val matrix = ObservableColorMatrix
      matrix.setSaturation(0.0F)
      imageView?.colorFilter = ColorMatrixColorFilter(matrix)
      imageView?.isEnabled = false
    }

    loadImage(context, imageView, url, false, false,
        if (listener == null) null else thumbnailRequest, null,
        if (listener == null) null else FadeAnimator, listener)

  }

  override fun loadImage(context: Context?, imageView: ImageView?, url: String?, noAnimate: Boolean,
      lowPriority: Boolean, thumbnail: DrawableRequestBuilder<String>?,
      transformation: BitmapTransformation?, animator: Animator?, listener: OnLoadImageListener?) {

    val builder = Glide
        .with(context)
        .load(url)
        .placeholder(R.drawable.image_placeholder)
        .diskCacheStrategy(SOURCE)
        .listener(object : RequestListener<String, GlideDrawable> {

          override fun onException(e: Exception?, model: String, target: Target<GlideDrawable>,
              isFirstResource: Boolean): Boolean {
            listener?.onLoadFailed()
            return false
          }

          override fun onResourceReady(resource: GlideDrawable, model: String,
              target: Target<GlideDrawable>,
              isFromMemoryCache: Boolean, isFirstResource: Boolean): Boolean {
            listener?.onLoadSucceed()
            return false
          }
        })

    if (noAnimate) {
      builder.dontAnimate()
    }
    if (lowPriority) {
      builder.priority(LOW)
    } else {
      builder.priority(NORMAL)
    }

    if (thumbnail != null) {
      builder.thumbnail(thumbnail)
    }
    if (transformation != null) {
      builder.transform(transformation)
    }
    if (animator != null) {
      builder.animate(animator)
    }
    builder.into(imageView)
  }
}