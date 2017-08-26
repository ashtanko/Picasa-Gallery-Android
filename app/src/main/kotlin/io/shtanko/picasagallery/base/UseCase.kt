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

import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposables
import io.reactivex.schedulers.Schedulers
import io.shtanko.picasagallery.core.executor.PostExecutionThread
import io.shtanko.picasagallery.core.executor.ThreadExecutor


abstract class UseCase(var threadExecutor: ThreadExecutor,
    var postExecutionThread: PostExecutionThread) {

  /**
   * Builds an {@link rx.Observable} which will be used when executing the current {@link UseCase}.
   */
  abstract fun buildUseCaseObservable(): Observable<*>

  private var disposables = Disposables.empty()


  fun execute(useCaseSubscriber: Observer<*>) {
    this.disposables = this.buildUseCaseObservable()
        .subscribeOn(Schedulers.from(threadExecutor))
        .observeOn(postExecutionThread.getScheduler())
        .subscribe()
  }

  fun unSubscribe() {
    if (!disposables.isDisposed) disposables.dispose()
  }
}