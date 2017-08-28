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

import dagger.internal.Preconditions
import io.reactivex.Flowable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import io.shtanko.picasagallery.core.executor.PostExecutionThread
import io.shtanko.picasagallery.core.executor.ThreadExecutor


abstract class UseCase<T, P>(var threadExecutor: ThreadExecutor,
    var postExecutionThread: PostExecutionThread) {

  /**
   * Builds an {@link rx.Observable} which will be used when executing the current {@link UseCase}.
   */
  internal abstract fun buildUseCaseObservable(params: P): Flowable<T>

  private var disposables = CompositeDisposable()


  fun execute(observer: DisposableObserver<T>, params: P) {
    Preconditions.checkNotNull(observer)
    val observable = this.buildUseCaseObservable(params)
        .subscribeOn(Schedulers.from(threadExecutor))
        .observeOn(postExecutionThread.getScheduler())
    addDisposable(observable.toObservable().subscribeWith(observer))
  }

  fun unSubscribe() {
    if (!disposables.isDisposed) disposables.dispose()
  }

  private fun addDisposable(disposable: Disposable) {
    Preconditions.checkNotNull(disposable)
    Preconditions.checkNotNull(disposables)
    disposables.add(disposable)
  }
}