package com.jay.gankmm.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.jay.gankmm.R;
import com.jay.gankmm.widget.MultiSwipeRefreshLayout;

/**
 * Created by jay on 16/2/22.
 */
public class SwipeRefreshFragment extends Fragment {

  public MultiSwipeRefreshLayout mSwipeRefreshLayout;

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    trySetupSwipeRefresh(view);
  }

  void trySetupSwipeRefresh(View root) {
    mSwipeRefreshLayout = (MultiSwipeRefreshLayout) root.findViewById(R.id.swipe_refresh_layout);

    if(mSwipeRefreshLayout != null) {
      mSwipeRefreshLayout.setOnRefreshListener(() -> requestDataRefresh());
    }
  }

  public void requestDataRefresh() {}

  public void setRefreshing(boolean refreshing) {
    if(mSwipeRefreshLayout == null) {
      return;
    }

    if(!refreshing) {
      mSwipeRefreshLayout.postDelayed(() -> mSwipeRefreshLayout.setRefreshing(false), 1000);
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
