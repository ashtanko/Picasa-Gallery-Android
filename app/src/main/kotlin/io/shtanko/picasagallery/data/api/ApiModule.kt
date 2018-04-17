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

package io.shtanko.picasagallery.data.api

import com.google.gson.FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import io.shtanko.picasagallery.core.prefs.PreferenceHelper
import io.shtanko.picasagallery.core.prefs.PreferencesModule
import javax.inject.Singleton

@Module(includes = [PreferencesModule::class])
class ApiModule {

  @Provides
  @Singleton
  fun provideGson(): Gson {
    val gsonBuilder = GsonBuilder()
    gsonBuilder.setFieldNamingPolicy(LOWER_CASE_WITH_UNDERSCORES)
    gsonBuilder.setPrettyPrinting()
    return gsonBuilder.create()
  }

  @Provides
  @Singleton
  fun provideApiManagerImpl(helper: PreferenceHelper): ApiManager = ApiManagerImpl(helper)
}