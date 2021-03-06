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

package com.ayatk.biblio.ui.home.library

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ayatk.biblio.domain.usecase.LibraryUseCase
import com.ayatk.biblio.model.Library
import com.ayatk.biblio.model.Novel
import com.ayatk.biblio.ui.util.toResult
import com.ayatk.biblio.util.Result
import com.ayatk.biblio.util.ext.toLiveData
import com.ayatk.biblio.util.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import timber.log.Timber
import javax.inject.Inject

class LibraryViewModel @Inject constructor(
  private val useCase: LibraryUseCase,
  private val schedulerProvider: SchedulerProvider
) : ViewModel() {

  private val compositeDisposable = CompositeDisposable()

  val libraries: LiveData<Result<List<Library>>> by lazy {
    useCase.libraries
      .toResult(schedulerProvider)
      .toLiveData()
  }

  var refreshing = MutableLiveData<Boolean>()

  override fun onCleared() {
    compositeDisposable.clear()
  }

  fun onSwipeRefresh() {
    useCase.update()
      .observeOn(schedulerProvider.ui())
      .subscribeBy(
        onComplete = { refreshing.postValue(false) },
        onError = Timber::e
      )
      .addTo(compositeDisposable)
  }

  fun delete(novel: Novel) =
    useCase.delete(novel)
      .observeOn(schedulerProvider.ui())
      .subscribe()
      .addTo(compositeDisposable)
}
