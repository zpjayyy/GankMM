package com.jay.gankmm.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.jay.gankmm.R;
import com.jay.gankmm.adapter.MeiziListAdapter;
import com.jay.gankmm.face.OnMeiziTouchListener;
import com.jay.gankmm.model.Meizi;
import com.jay.gankmm.ui.base.SwipeRefreshBaseActivity;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jay on 16/2/15.
 */
public class MainActivity extends SwipeRefreshBaseActivity {

  private static final int PRELOAD_SIZE = 6;

  @Bind(R.id.recycler_view) RecyclerView mRecyclerView;

  MeiziListAdapter mMeiziListAdapter;
  List<Meizi> mMeiziList;
  boolean mIsFirstTimeTouchBottom = true;
  int mPage = 1;

  @Override protected int provideContentViewId() {
    return R.layout.activity_main;
  }

  @Override public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
    super.onCreate(savedInstanceState, persistentState);
    ButterKnife.bind(this);

    mMeiziList = new ArrayList<>();

    setUpRecyclerView();
  }

  @Override protected void onPostCreate(Bundle savedInstanceState) {
    super.onPostCreate(savedInstanceState);
    new Handler().postDelayed(() -> setRefreshing(true), 365);
  }

  private void setUpRecyclerView() {
    final StaggeredGridLayoutManager layoutManager =
        new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
    mRecyclerView.setLayoutManager(layoutManager);
    mMeiziListAdapter = new MeiziListAdapter(mMeiziList);
    mRecyclerView.setAdapter(mMeiziListAdapter);

    mRecyclerView.addOnScrollListener(getScrollToBottomListener(layoutManager));
    mMeiziListAdapter.setOnMeiziTouchListener(getOnMeiziTouchListener());

  }

  private void getData() {

  }

  RecyclerView.OnScrollListener getScrollToBottomListener(
      StaggeredGridLayoutManager layoutManager) {
    return new RecyclerView.OnScrollListener() {
      @Override public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        boolean isBottom =
            layoutManager.findLastCompletelyVisibleItemPositions(new int[2])[1]
              >= mMeiziListAdapter.getItemCount() - PRELOAD_SIZE;

        if(!mSwipeRefreshLayout.isRefreshing() && isBottom) {
          if(!mIsFirstTimeTouchBottom) {
            mSwipeRefreshLayout.setRefreshing(true);
            mPage += 1;
            getData();
          }
        } else {
          mIsFirstTimeTouchBottom = false;
        }
      }
    };
  }

  private OnMeiziTouchListener getOnMeiziTouchListener() {
    return (v, meiziView, card, meizi) -> {
      if(meizi == null) return;

      if(v == meiziView) {

      } else if(v == card) {

      }
    };
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    ButterKnife.unbind(this);
  }
}
