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
import android.graphics.Bitmap
import android.graphics.Bitmap.Config.ARGB_8888
import android.graphics.Bitmap.createBitmap
import android.graphics.BitmapShader
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Paint.ANTI_ALIAS_FLAG
import android.graphics.Paint.DITHER_FLAG
import android.graphics.Paint.FILTER_BITMAP_FLAG
import android.graphics.Shader.TileMode.CLAMP
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation


class CircleTransform constructor(context: Context) : BitmapTransformation(context) {

	private fun circleCrop(pool: BitmapPool, source: Bitmap): Bitmap {
		val sourceWidth = source.width
		val sourceHeight = source.height
		val size = Math.min(sourceWidth, sourceHeight)
		val x = (sourceWidth - size) / 2
		val y = (sourceHeight - size) / 2
		val squared = createBitmap(source, x, y, size, size)
		var result = pool.get(size, size, ARGB_8888)
		if (result == null)
			result = createBitmap(size, size, ARGB_8888)
		val canvas = Canvas(result)
		val paint = Paint(FILTER_BITMAP_FLAG or DITHER_FLAG or ANTI_ALIAS_FLAG)
		paint.shader = BitmapShader(squared, CLAMP, CLAMP)
		val r = size / 2f
		canvas.drawCircle(r, r, r, paint)
		return result
	}

	override fun transform(pool: BitmapPool, toTransform: Bitmap, outWidth: Int,
			outHeight: Int): Bitmap = circleCrop(pool, toTransform)

	override fun getId(): String = javaClass.name
}