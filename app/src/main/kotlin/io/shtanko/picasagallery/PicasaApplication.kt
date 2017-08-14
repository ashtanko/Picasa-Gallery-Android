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

package io.shtanko.picasagallery

import android.app.Application
import io.shtanko.picasagallery.core.ApplicationModule
import io.shtanko.picasagallery.core.BaseComponent
import io.shtanko.picasagallery.core.DaggerBaseComponent
import kotlin.properties.Delegates

class PicasaApplication : Application() {
  companion object {
    @JvmStatic lateinit var graph: BaseComponent
    var app: Application by Delegates.notNull()
  }

  override fun onCreate() {
    super.onCreate()
    app = this
    graph = DaggerBaseComponent.builder()
        .applicationModule(ApplicationModule(this))
        .build()
  }
}