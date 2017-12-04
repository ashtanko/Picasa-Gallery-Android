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

package io.shtanko.picasagallery.view.auth

import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.GoogleSignInOptions.Builder
import com.google.android.gms.auth.api.signin.GoogleSignInOptions.DEFAULT_SIGN_IN
import com.google.android.gms.common.api.Scope
import dagger.Module
import dagger.Provides
import io.shtanko.picasagallery.Config.AUTH_SCOPES
import javax.inject.Singleton

@Module
class GoogleApiModule {

  @Provides
  @Singleton
  fun provideGoogleSignInOptions(): GoogleSignInOptions {
    /** List of OAuth scopes to be requested from the Google sign-in API  */
    fun getAuthScopes(): List<String> = AUTH_SCOPES

    val gsoBuilder = Builder(DEFAULT_SIGN_IN)
    for (scope in getAuthScopes()) {
      gsoBuilder.requestScopes(Scope(scope))
    }
    return gsoBuilder.requestEmail()
        .build()
  }

}