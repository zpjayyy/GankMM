package com.jay.gankmm.ui.base;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.widget.SwipeRefreshLayout;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.jay.gankmm.R;
import com.jay.gankmm.widget.MultiSwipeRefreshLayout;

/**
 * Created by jay on 16/2/16.
 */
public abstract class SwipeRefreshBaseActivity extends ToolBarActivity {

  @Bind(R.id.swipe_refresh_layout) public MultiSwipeRefreshLayout mSwipeRefreshLayout;

  @Override public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
    super.onCreate(savedInstanceState, persistentState);
    ButterKnife.bind(this);
  }

  @Override protected void onPostCreate(Bundle savedInstanceState) {
    super.onPostCreate(savedInstanceState);
    trySetupSwipeRefresh();
  }

  void trySetupSwipeRefresh() {
    if(mSwipeRefreshLayout != null) {
      mSwipeRefreshLayout.setColorSchemeColors(R.color.refresh_progress_1,
          R.color.refresh_progress_2, R.color.refresh_progress_3);
      mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
        @Override public void onRefresh() {
          requestDataRefresh();
        }
      });
    }
  }

  public void requestDataRefresh() {}

  public void setRefreshing(boolean refreshing) {
    if(mSwipeRefreshLayout == null) {
      return;
    }

    if(!refreshing) {
      mSwipeRefreshLayout.postDelayed(new Runnable() {
        @Override public void run() {
          mSwipeRefreshLayout.setRefreshing(false);
        }
      }, 1000);
    } else {
      mSwipeRefreshLayout.setRefreshing(true);
    }
  }

  public void setProgressViewOffset(boolean scale, int start, int end) {
    mSwipeRefreshLayout.setProgressViewOffset(scale, start, end);
  }

  public void setSwipeableChildren(
      MultiSwipeRefreshLayout.CanChildScrollUpCallback canChildScrollUpCallback) {

    mSwipeRefreshLayout.setCanChildScrollUpCallback(canChildScrollUpCallback);
  }
}
