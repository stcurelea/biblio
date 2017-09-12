/*
 * Copyright (c) 2016-2017 Aya Tokikaze. All Rights Reserved.
 */

package com.ayatk.biblio.view.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.ayatk.biblio.R
import com.ayatk.biblio.databinding.FragmentNovelInfoBinding
import com.ayatk.biblio.model.Novel
import com.ayatk.biblio.repository.library.LibraryRepository
import com.ayatk.biblio.view.helper.Navigator
import com.ayatk.biblio.viewmodel.NovelInfoViewModel
import org.parceler.Parcels
import javax.inject.Inject

class NovelInfoFragment : BaseFragment() {

  private lateinit var binding: FragmentNovelInfoBinding

  @Inject
  lateinit var navigator: Navigator

  @Inject
  lateinit var libraryRepository: LibraryRepository

  private val novel: Novel by lazy {
    Parcels.unwrap<Novel>(arguments.getParcelable(BUNDLE_ARGS_NOVEL))
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                            savedInstanceState: Bundle?): View? {
    binding = FragmentNovelInfoBinding.inflate(inflater, container, false)
    binding.viewModel = NovelInfoViewModel(context, navigator, libraryRepository, novel)

    novel.novelTags.map {
      val tagItem = this.onGetLayoutInflater(savedInstanceState).inflate(R.layout.view_tag, null)
      val textView = tagItem.findViewById<TextView>(R.id.tag)
      textView.text = it
      binding.tagContainer.addView(textView)
    }

    libraryRepository.find(novel)
        .subscribe(
            {
              it.tag.map {
                val tagItem = this.onGetLayoutInflater(savedInstanceState).inflate(
                    R.layout.view_tag,
                    null)
                val textView = tagItem.findViewById<TextView>(R.id.tag)
                textView.text = it
                binding.userTagContainer.addView(textView)
              }
            }
        )

    return binding.root
  }

  override fun onAttach(context: Context?) {
    super.onAttach(context)
    component().inject(this)
  }

  companion object {
    private val BUNDLE_ARGS_NOVEL = "NOVEL"

    fun newInstance(novel: Novel): NovelInfoFragment {
      return NovelInfoFragment().apply {
        arguments = Bundle().apply {
          putParcelable(BUNDLE_ARGS_NOVEL, Parcels.wrap(novel))
        }
      }
    }
  }
}
