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

package com.ayatk.biblio.data.datasource.novel

import com.ayatk.biblio.data.narou.NarouClient
import com.ayatk.biblio.data.narou.entity.NarouIndex
import com.ayatk.biblio.domain.repository.IndexRepository
import com.ayatk.biblio.model.Index
import com.ayatk.biblio.model.Novel
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single
import io.reactivex.rxkotlin.toObservable
import javax.inject.Inject

class IndexRemoteDataSource
@Inject constructor(val client: NarouClient) :
    IndexRepository {

  override fun findAll(novel: Novel): Single<List<Index>> {
    return client.getTableOfContents(novel.code)
        .map { convertNarouToModel(novel, it) }
  }

  override fun find(novel: Novel, page: Int): Maybe<Index> {
    return findAll(novel)
        .toObservable()
        .flatMap { it.toObservable() }
        .filter { it.page == page }
        .singleElement()
  }

  override fun save(indices: List<Index>): Completable {
    return Completable.create { /* no-op */ }
  }

  override fun delete(novel: Novel): Single<Int> {
    return Single.create { /* no-op */ }
  }

  private fun convertNarouToModel(
      novel: Novel, narouTables: List<NarouIndex>
  ): List<Index> {
    return narouTables.map {
      Index(
          id = "${novel.code}-${it.id}",
          novel = novel,
          title = it.title,
          isChapter = it.isChapter,
          page = it.page,
          publishDate = it.publishDate,
          lastUpdate = it.lastUpdate
      )
    }
  }
}
