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

package io.shtanko.picasagallery.view.album

import io.shtanko.picasagallery.data.entity.internal.Content
import io.shtanko.picasagallery.data.entity.internal.ContentType
import io.shtanko.picasagallery.data.internal.InternalAlbumsRepository
import io.shtanko.picasagallery.util.ActivityScoped
import io.shtanko.picasagallery.view.album.InternalAlbumsContract.Presenter
import io.shtanko.picasagallery.view.album.InternalAlbumsContract.View
import io.shtanko.picasagallery.view.delegate.ViewType
import javax.annotation.Nullable
import javax.inject.Inject

@ActivityScoped
class InternalAlbumsPresenter @Inject constructor(
        private val repository: InternalAlbumsRepository) : Presenter {

    @Nullable
    private var view: View? = null

    override fun takeView(view: View) {
        this.view = view
    }

    override fun dropView() {
        view = null
    }

    override fun onItemClick(view: ViewType) {
        if (view is ContentType) {
            this.view?.viewAlbum(view as Content)
        }
    }

    override fun getContent() {
        view?.let { view ->
            view.setLoadingIndicator(true)
            repository.content()
                    .subscribe(
                            { data ->
                                view.setLoadingIndicator(false)
                                view.showData(data)
                            },
                            { error ->
                                view.setLoadingIndicator(false)
                                view.showError(error.localizedMessage)
                            }
                    )
        }
    }
}