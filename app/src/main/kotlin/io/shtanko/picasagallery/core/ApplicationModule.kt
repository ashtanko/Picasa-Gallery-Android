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

package io.shtanko.picasagallery.core

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import io.shtanko.picasagallery.R
import io.shtanko.picasagallery.data.PreferenceHelper

@Module class ApplicationModule(var context: Context) {
  @Provides fun provideContext(): Context = context

  @Provides fun providePreferences(sharedPreferences: SharedPreferences): PreferenceHelper {
    return PreferenceHelper(sharedPreferences)
  }

  @Provides fun provideSharedPreferences(context: Context): SharedPreferences {
    return context.getSharedPreferences(context.getString(R.string.preference_file_key),
        Context.MODE_PRIVATE);
  }
}