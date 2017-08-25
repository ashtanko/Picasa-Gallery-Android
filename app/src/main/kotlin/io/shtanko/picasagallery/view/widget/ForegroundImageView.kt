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

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Build.VERSION_CODES
import android.support.annotation.RequiresApi
import android.support.v7.widget.AppCompatImageView
import android.util.AttributeSet
import android.view.ViewOutlineProvider
import io.shtanko.picasagallery.R

@RequiresApi(VERSION_CODES.M)
@SuppressLint("Recycle")
class ForegroundImageView constructor(context: Context, attrs: AttributeSet) : AppCompatImageView(
    context, attrs) {

  private var mForeground: Drawable? = null

  init {
    val a = context.obtainStyledAttributes(attrs, R.styleable.ForegroundView)
    val d = a.getDrawable(R.styleable.ForegroundView_android_foreground)
    if (d != null) {
      foreground = d
    }
    a.recycle()
    outlineProvider = ViewOutlineProvider.BOUNDS
  }

  override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
    super.onSizeChanged(w, h, oldw, oldh)
    if (mForeground != null) {
      mForeground!!.setBounds(0, 0, w, h)
    }
  }

  override fun hasOverlappingRendering(): Boolean {
    return false
  }

  override fun verifyDrawable(dr: Drawable?): Boolean {
    return super.verifyDrawable(dr) || dr === mForeground
  }

  override fun jumpDrawablesToCurrentState() {
    super.jumpDrawablesToCurrentState()
    if (mForeground != null) mForeground!!.jumpToCurrentState()
  }

  override fun drawableStateChanged() {
    super.drawableStateChanged()
    if (mForeground != null && mForeground!!.isStateful) {
      mForeground!!.state = drawableState
    }
  }

}