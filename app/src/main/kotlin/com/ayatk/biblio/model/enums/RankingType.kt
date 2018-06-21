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

package com.ayatk.biblio.model.enums

import androidx.annotation.StringRes
import com.ayatk.biblio.R

enum class RankingType(@StringRes val title: Int) {

  /**
   * DAILY 日間ランキング
   */
  DAILY(R.string.ranking_daily_short),
  /**
   * WEEKLY 週間ランキング
   */
  WEEKLY(R.string.ranking_weekly_short),
  /**
   * MONTHLY 月間ランキング
   */
  MONTHLY(R.string.ranking_monthly_short),
  /**
   * QUARTET 四半期ランキング
   */
  QUARTET(R.string.ranking_quarter_short),
  /**
   * ALL 累計ランキング
   */
  ALL(R.string.ranking_all_short);
}
