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

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import com.google.android.gms.common.SignInButton
import io.shtanko.picasagallery.R


class SignInFragment : Fragment(), SignInContract.View {

  override var presenter: SignInContract.Presenter? = null

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
      savedInstanceState: Bundle?): View? {
    val root = inflater.inflate(R.layout.fragment_siginin, container, false)

    with(root) {
      addSignInButton()
    }

    return root
  }

  override fun setLoadingIndicator(active: Boolean) {
    val root = view ?: return
    with(root.findViewById<ProgressBar>(R.id.progress_bar)) {
      post {
        visibility = View.GONE
      }
    }
  }

  private fun addSignInButton() {
    val root = view ?: return
    with(root.findViewById<SignInButton>(R.id.sign_in_button)) {
      setSize(SignInButton.SIZE_STANDARD)
      setOnClickListener {
        presenter?.signIn()
      }
    }
  }

}

