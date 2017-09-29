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

import android.support.test.espresso.IdlingResource
import java.util.concurrent.atomic.AtomicInteger

class SimpleCountingIdlingResource
(resourceName: String) : IdlingResource {

	private val resourceName: String = checkNotNull(resourceName)
	private val counter = AtomicInteger(0)

	// written from main thread, read from any thread.
	@Volatile private var resourceCallback: IdlingResource.ResourceCallback? = null

	override fun getName(): String = resourceName

	override fun isIdleNow(): Boolean = counter.get() == 0

	override fun registerIdleTransitionCallback(resourceCallback: IdlingResource.ResourceCallback) {
		this.resourceCallback = resourceCallback
	}

	/**
	 * Increments the count of in-flight transactions to the resource being monitored.
	 */
	fun increment() {
		counter.getAndIncrement()
	}

	/**
	 * Decrements the count of in-flight transactions to the resource being monitored.
	 *
	 * If this operation results in the counter falling below 0 - an exception is raised.
	 *
	 * @throws IllegalStateException if the counter is below 0.
	 */
	fun decrement() {
		val counterVal = counter.decrementAndGet()
		if (counterVal == 0) {
			// we've gone from non-zero to zero. That means we're idle now! Tell espresso.
			if (null != resourceCallback) {
				resourceCallback!!.onTransitionToIdle()
			}
		}

		if (counterVal < 0) {
			throw IllegalArgumentException("Counter has been corrupted!")
		}
	}
}