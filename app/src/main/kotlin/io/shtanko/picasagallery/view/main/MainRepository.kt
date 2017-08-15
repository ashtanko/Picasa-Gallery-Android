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

package io.shtanko.picasagallery.view.main

import com.google.gson.Gson
import io.shtanko.picasagallery.PicasaApplication
import io.shtanko.picasagallery.data.PreferenceHelper
import io.shtanko.picasagallery.data.api.DaggerApiComponent
import io.shtanko.picasagallery.data.api.PicasaService
import javax.inject.Inject


class MainRepository {

  @Inject lateinit var gson: Gson
  @Inject lateinit var picasaService: PicasaService
  @Inject lateinit var preferenceHelper: PreferenceHelper


  init {
    DaggerApiComponent.builder().baseComponent(PicasaApplication.graph).build().inject(this)

    picasaService.getUser(preferenceHelper.getUserId()).subscribe { it ->
      it.version
    }
    println("")
  }
}