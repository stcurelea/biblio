/*
 * Copyright (c) 2016-2018 ayatk.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ayatk.biblio.ui.body

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.os.bundleOf
import com.ayatk.biblio.databinding.FragmentNovelBodyBinding
import com.ayatk.biblio.event.NovelBodySelectedEvent
import com.ayatk.biblio.event.SubtitleChangeEvent
import com.ayatk.biblio.model.Novel
import dagger.android.support.DaggerFragment
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.parceler.Parcels
import javax.inject.Inject

class NovelBodyFragment : DaggerFragment() {

  @Inject
  lateinit var viewModel: NovelBodyViewModel

  private lateinit var binding: FragmentNovelBodyBinding

  private val novel: Novel by lazy {
    Parcels.unwrap<Novel>(arguments?.getParcelable(BUNDLE_ARGS_NOVEL))
  }

  private val page: Int by lazy {
    arguments?.getInt(BUNDLE_ARGS_NOVEL_PAGE) ?: 1
  }

  override fun onCreateView(
      inflater: LayoutInflater, container: ViewGroup?,
      savedInstanceState: Bundle?
  ): View? {
    binding = FragmentNovelBodyBinding.inflate(inflater, container, false)
    binding.viewModel = viewModel

    return binding.root
  }

  override fun onResume() {
    super.onResume()
    EventBus.getDefault().register(this)
    viewModel.start(novel, page)
  }

  override fun onPause() {
    super.onPause()
    EventBus.getDefault().unregister(this)
  }

  @Subscribe
  fun onEvent(event: NovelBodySelectedEvent) {
    if (event.position + 1 == page) {
      EventBus.getDefault().post(SubtitleChangeEvent(viewModel.novelBody.subtitle))
    }
  }

  companion object {
    private val BUNDLE_ARGS_NOVEL = "NOVEL"
    private val BUNDLE_ARGS_NOVEL_PAGE = "NOVEL_PAGE"

    fun newInstance(novel: Novel, page: Int): NovelBodyFragment {
      return NovelBodyFragment().apply {
        arguments = bundleOf(
            BUNDLE_ARGS_NOVEL to Parcels.wrap(novel),
            BUNDLE_ARGS_NOVEL_PAGE to page
        )
      }
    }
  }
}
