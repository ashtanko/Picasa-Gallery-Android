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

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.DrawableRequestBuilder
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import com.bumptech.glide.request.animation.ViewPropertyAnimation
import io.shtanko.picasagallery.view.util.OnLoadImageListener

interface ImageHelper {
  fun load(context: Context?, url: String?, listener: OnLoadImageListener?, imageView: ImageView?)

  fun loadImage(context: Context?,
      imageView: ImageView?,
      url: String?,
      noAnimate: Boolean,
      lowPriority: Boolean,
      thumbnail: DrawableRequestBuilder<String>?,
      transformation: BitmapTransformation?,
      animator: ViewPropertyAnimation.Animator?,
      listener: OnLoadImageListener?)

  fun process(context: Context?, imageView: ImageView?, url: String?)
}