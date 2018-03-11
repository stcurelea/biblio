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

import android.annotation.SuppressLint
import com.ayatk.biblio.di.DaggerAppComponent
import com.ayatk.biblio.util.Analytics
import com.ayatk.biblio.util.forest.CrashlyticsTree
import com.crashlytics.android.Crashlytics
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import io.fabric.sdk.android.Fabric
import timber.log.Timber

@SuppressLint("Registered")
open class App : DaggerApplication() {

  override fun onCreate() {
    super.onCreate()

    Fabric.with(this, Crashlytics())

    Analytics.init(this)

    // Timber forest
    Timber.plant(CrashlyticsTree())
  }

  override fun applicationInjector(): AndroidInjector<out DaggerApplication> =
      DaggerAppComponent
          .builder()
          .application(this)
          .build()
}
