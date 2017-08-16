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

package io.shtanko.picasagallery.view.profile

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.AppCompatImageView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import io.shtanko.picasagallery.R
import io.shtanko.picasagallery.util.CircleTransform
import io.shtanko.picasagallery.view.profile.ProfileContract.Presenter


class ProfileFragment : Fragment(), ProfileContract.View {

  val dummyImage = "https://images-na.ssl-images-amazon.com/images/M/MV5BOTAzNTY1NjYwMl5BMl5BanBnXkFtZTgwNTIwNjMzMjI@._CR786,95,469,469_UX402_UY402._SY201_SX201_AL_.jpg"

  override var presenter: Presenter? = null

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
      savedInstanceState: Bundle?): View? {
    val rootView = inflater.inflate(R.layout.fragment_profile, container, false)

    with(rootView) {
      rootView.findViewById<AppCompatImageView>(R.id.avatar).apply {
        Glide.with(activity)
            .load(dummyImage)
            .transform(CircleTransform(activity.applicationContext))
            .into(this)
      }
    }

    return rootView
  }

}