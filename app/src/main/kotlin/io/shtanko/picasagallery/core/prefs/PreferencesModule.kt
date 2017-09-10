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

package io.shtanko.picasagallery.core.prefs

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import io.shtanko.picasagallery.R
import io.shtanko.picasagallery.core.app.AppModule

@Module(includes = arrayOf(AppModule::class))
class PreferencesModule {

  @Provides
  fun provideSharedPreferences(context: Context): SharedPreferences =
      context.getSharedPreferences(context.getString(R.string.preference_file_key),
          Context.MODE_PRIVATE)

  @Provides
  fun providePreferencesHelper(sharedPreferences: SharedPreferences): PreferenceHelper =
      PreferenceHelper(sharedPreferences)

}