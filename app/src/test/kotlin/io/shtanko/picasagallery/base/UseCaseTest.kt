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

package io.shtanko.picasagallery.base

import com.nhaarman.mockito_kotlin.mock
import io.shtanko.picasagallery.core.executor.PostExecutionThread
import io.shtanko.picasagallery.core.executor.ThreadExecutor
import io.shtanko.picasagallery.data.DefaultObserver
import org.hamcrest.MatcherAssert
import org.hamcrest.core.Is.`is`
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class UseCaseTest {

  private val mockThreadExecutor = mock<ThreadExecutor>()
  private val mockPostExecutionThread = mock<PostExecutionThread>()
  private var useCase: UseCaseTestClass? = null

  @Before
  fun setUp() {
    useCase = UseCaseTestClass(mockThreadExecutor, mockPostExecutionThread)
  }

  @Test
  fun when_executing_use_caseTest() {
    val subscriber = TestObserver()
    useCase?.execute(subscriber, UseCaseTestClass.Params.createQuery())
    useCase?.unSubscribe()
    MatcherAssert.assertThat(subscriber.isDisposed, `is`(true))
  }

  inner class TestObserver : DefaultObserver<TestModel>() {

  }
}