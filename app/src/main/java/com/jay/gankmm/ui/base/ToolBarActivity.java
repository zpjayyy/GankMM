package com.jay.gankmm.ui.base;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import com.jay.gankmm.R;

/**
 * Created by jay on 16/2/15.
 */
public abstract class ToolBarActivity extends BaseActivity {

  abstract protected int provideContentViewId();

  public void onToolbarClick() {
  }

  protected AppBarLayout mAppBar;
  protected Toolbar mToolbar;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(provideContentViewId());

    mAppBar = (AppBarLayout) findViewById(R.id.app_bar_layout);
    mToolbar = (Toolbar) findViewById(R.id.toolbar);

    if (mToolbar == null || mAppBar == null) {
      throw new IllegalStateException("toolbar is null");
    }

    mToolbar.setOnClickListener(v -> onToolbarClick());

    setSupportActionBar(mToolbar);

    if (canBack()) {
      ActionBar actionBar = getSupportActionBar();
      if (actionBar != null) actionBar.setDisplayHomeAsUpEnabled(true);
    }
  }

  public boolean canBack() {
    return false;
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    if (item.getItemId() == android.R.id.home) {
      onBackPressed();
      return true;
    } else {
      return super.onOptionsItemSelected(item);
    }
  }
}
