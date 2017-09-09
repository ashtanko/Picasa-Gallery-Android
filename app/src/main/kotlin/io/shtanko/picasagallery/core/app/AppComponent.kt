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

package io.shtanko.picasagallery.core.app

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import dagger.android.support.AndroidSupportInjectionModule
import io.shtanko.picasagallery.AlbumsRepositoryModule
import io.shtanko.picasagallery.PhotosDataSourceModule
import io.shtanko.picasagallery.UserModule
import io.shtanko.picasagallery.core.ActivityBindingModule
import io.shtanko.picasagallery.core.executor.JobModule
import io.shtanko.picasagallery.core.prefs.PreferencesModule
import io.shtanko.picasagallery.data.account.AccountModule
import io.shtanko.picasagallery.data.api.ApiModule
import io.shtanko.picasagallery.data.photo.PhotosDataModule
import io.shtanko.picasagallery.view.auth.GoogleApiModule
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(
    AndroidSupportInjectionModule::class,
    AppModule::class,
    ActivityBindingModule::class,
    ApiModule::class,
    PreferencesModule::class,
    AlbumsRepositoryModule::class,
    UserModule::class,
    JobModule::class,
    AccountModule::class,
    PhotosDataSourceModule::class,
    PhotosDataModule::class,
    GoogleApiModule::class
))
interface AppComponent : AndroidInjector<DaggerApplication> {

  override fun inject(instance: DaggerApplication?)

  @Component.Builder
  interface Builder {

    @BindsInstance
    fun application(application: Application): Builder

    fun build(): AppComponent
  }
}
