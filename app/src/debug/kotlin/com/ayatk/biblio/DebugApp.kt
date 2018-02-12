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

package com.ayatk.biblio

import com.facebook.stetho.Stetho
import com.tomoima.debot.DebotConfigurator
import jp.wasabeef.takt.Takt
import timber.log.Timber

class DebugApp : App() {
  override fun onCreate() {
    super.onCreate()

    Stetho.initializeWithDefaults(this)
    DebotConfigurator.configureWithDefault()
    Takt.stock(this).play()

    if (BuildConfig.DEBUG) {
      Timber.plant(Timber.DebugTree())
    }
  }

  override fun onTerminate() {
    Takt.finish()
    super.onTerminate()
  }
}
