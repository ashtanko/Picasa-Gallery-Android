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

import android.app.Application
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import io.reactivex.schedulers.Schedulers
import io.shtanko.picasagallery.Config
import io.shtanko.picasagallery.data.PreferenceHelper
import io.shtanko.picasagallery.data.PreferencesModule
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.CallAdapter
import retrofit2.Converter.Factory
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module(includes = arrayOf(PreferencesModule::class))
class ApiModule {

  @Provides
  fun provideGson(): Gson {
    val gsonBuilder = GsonBuilder()
    gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
    gsonBuilder.setPrettyPrinting()
    return gsonBuilder.create()
  }

  @Provides
  fun provideOkHttpCache(app: Application): Cache {
    val cacheSize = 10 * 1024 * 1024
    val cache = Cache(app.cacheDir, cacheSize.toLong())
    return cache
  }

  @Provides
  fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
    val httpLoggingInterceptor = HttpLoggingInterceptor()
    httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
    return httpLoggingInterceptor
  }

  @Provides
  fun providePicasaInterceptor(token: String): Interceptor {
    val picasaInterceptor = PicasaNetworkInterceptor(token)
    return picasaInterceptor
  }

  @Provides
  fun provideOkHttpClient(
      httpLoggingInterceptor: HttpLoggingInterceptor, interceptor: Interceptor): OkHttpClient {
    val client = OkHttpClient.Builder()
        .addNetworkInterceptor(httpLoggingInterceptor)
        .addNetworkInterceptor(interceptor)
        .build()
    return client
  }

  @Provides
  fun provideCallAdapterFactory(): CallAdapter.Factory {
    val rxAdapter = RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io())
    return rxAdapter
  }

  @Provides
  fun provideConvertFactory(gson: Gson): Factory {
    return GsonConverterFactory.create(gson)
  }

  @Provides
  fun provideRetrofit(converterFactory: Factory,
      callAdapterFactory: CallAdapter.Factory, okHttpClient: OkHttpClient): Retrofit {
    val retrofit = Retrofit.Builder()
        .addConverterFactory(converterFactory)
        .baseUrl(Config.PICASA_BASE_API_URL)
        .addCallAdapterFactory(callAdapterFactory)
        .client(okHttpClient)
        .build()
    return retrofit
  }

  @Provides
  fun providePicasaService(retrofit: Retrofit): PicasaService {
    return retrofit.create(PicasaService::class.java)
  }

  @Provides
  fun provideApiManagerImpl(service: PicasaService): ApiManager {
    return ApiManagerImpl(service)
  }

  @Provides
  fun provideToken(preferenceHelper: PreferenceHelper): String {
    return preferenceHelper.getToken()
  }

}