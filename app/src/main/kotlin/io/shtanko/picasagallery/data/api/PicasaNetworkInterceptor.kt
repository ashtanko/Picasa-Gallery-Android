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

import okhttp3.Interceptor
import okhttp3.Interceptor.Chain
import okhttp3.Response


class PicasaNetworkInterceptor(var token: String) : Interceptor {

  override fun intercept(chain: Chain): Response {
    val originalRequest = chain.request()

    val httpUrl = originalRequest.url().newBuilder()
        .addQueryParameter("alt", "json")
        .build()

    val authorizedRequest = originalRequest
        .newBuilder()
        .url(httpUrl).build()
        //.header("Authorization",  token)
        //.header("Gdata-version", "3").build()

    return chain.proceed(authorizedRequest)

  }
}