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

import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.gson.GsonDeserializer
import com.github.kittinunf.fuel.rx.rx_responseObject
import io.shtanko.picasagallery.Config
import io.shtanko.picasagallery.data.model.UserFeedResponseEntity
import org.hamcrest.CoreMatchers.notNullValue
import org.junit.Assert.assertThat
import org.junit.Test

class NetworkTest {

  private val MOCK_USER_ID = "113288841856843375771"

  init {
    FuelManager.instance.basePath = Config.PICASA_BASE_API_URL

    Fuel.testMode {
      timeout = 15000
    }
  }

  @Test
  fun get_user_responseTest() {
    val (response, data) = Fuel.get(Config.configureUserPath(MOCK_USER_ID)).rx_responseObject(
        GsonDeserializer<UserFeedResponseEntity>())
        .test()
        .apply { awaitTerminalEvent() }
        .assertNoErrors()
        .assertValueCount(1)
        .assertComplete()
        .values()[0]

    assertThat(response, notNullValue())
    assertThat(data, notNullValue())
  }

}