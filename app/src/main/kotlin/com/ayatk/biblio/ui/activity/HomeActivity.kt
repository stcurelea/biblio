/*
 * Copyright (c) 2016-2017 Aya Tokikaze. All Rights Reserved.
 */

package com.ayatk.biblio.ui.activity

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.ayatk.biblio.R
import com.ayatk.biblio.databinding.ActivityMainBinding
import com.ayatk.biblio.pref.DefaultPrefs
import com.ayatk.biblio.ui.util.Page
import com.ayatk.biblio.ui.util.helper.BottomNavigationViewHelper

class HomeActivity : BaseActivity() {

  private val binding: ActivityMainBinding by lazy {
    DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    component().inject(this)

    DefaultPrefs.get(this).showTagAtLibrary

    BottomNavigationViewHelper.disableShiftingMode(binding.bottomNav)

    // init fragment
    if (savedInstanceState == null) {
      changePage(Page.forMenuId(R.id.nav_library))
    }
    setSupportActionBar(binding.toolbar)

    binding.bottomNav.setOnNavigationItemSelectedListener({
      changePage(Page.forMenuId(it.itemId))
      invalidateOptionsMenu()
      true
    })
  }

  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.menu_main, menu)
    return super.onCreateOptionsMenu(menu)
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    when (item.itemId) {
      R.id.nav_search -> startActivity(SearchActivity.createIntent(this))
    }
    return super.onOptionsItemSelected(item)
  }

  override fun onPrepareOptionsMenu(menu: Menu): Boolean {
    menu.getItem(0).isVisible = binding.bottomNav.selectedItemId != R.id.nav_settings
    return super.onPrepareOptionsMenu(menu)
  }

  private fun toggleToolbarElevation(enable: Boolean) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      val elevation = if (enable) resources.getDimension(R.dimen.elevation_4dp) else 0.toFloat()
      binding.appBar.elevation = elevation
    }
  }

  private fun changePage(page: Page) {
    binding.toolbar.setTitle(page.titleResId)
    toggleToolbarElevation(page.toggleToolbar)
    replaceFragment(page.createFragment(), R.id.container)
  }

  companion object {
    fun createIntent(context: Context): Intent = Intent(context, HomeActivity::class.java)
  }
}