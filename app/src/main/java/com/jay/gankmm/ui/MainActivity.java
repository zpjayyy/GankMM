package com.jay.gankmm.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.jay.gankmm.R;
import com.jay.gankmm.adapter.MeiziListAdapter;
import com.jay.gankmm.data.MeiziData;
import com.jay.gankmm.data.VideoData;
import com.jay.gankmm.face.OnMeiziTouchListener;
import com.jay.gankmm.model.Meizi;
import com.jay.gankmm.ui.base.SwipeRefreshBaseActivity;
import java.util.ArrayList;
import java.util.List;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by jay on 16/2/15.
 */
public class MainActivity extends SwipeRefreshBaseActivity {

  private static final String TAG = "MainActivity";

  private static final int PRELOAD_SIZE = 6;

  @Bind(R.id.recycler_view) RecyclerView mRecyclerView;

  MeiziListAdapter mMeiziListAdapter;
  List<Meizi> mMeiziList;
  boolean mIsFirstTimeTouchBottom = true;
  int mPage = 1;

  @Override protected int provideContentViewId() {
    return R.layout.activity_main;
  }

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    ButterKnife.bind(this);

    mMeiziList = new ArrayList<>();

    setUpRecyclerView();
  }

  @Override protected void onPostCreate(Bundle savedInstanceState) {
    super.onPostCreate(savedInstanceState);
    new Handler().postDelayed(() -> setRefreshing(true), 365);
    getData(true);
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

  private void getData(boolean clean) {
    Subscription s = Observable.zip(sApiService.getMeiziData(mPage), sApiService.get休息视频Data(mPage),
        (meiziData, videoData) -> createMeiziDataWithVideoDesc(meiziData, videoData))
        .map(meiziData -> meiziData.results)
        .flatMap(Observable::from)
        .toSortedList((meizi, meizi2) -> meizi2.createdAt.compareTo(meizi.createdAt))
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(meiziList -> {
          if(clean) mMeiziList.clear();
          mMeiziList.addAll(meiziList);
          mMeiziListAdapter.notifyDataSetChanged();
          setRefreshing(false);
        }, throwable -> loadError(throwable));

    addSubscription(s);
  }

  private void loadError(Throwable throwable) {
    throwable.printStackTrace();
    setRefreshing(false);
    Snackbar.make(mRecyclerView, R.string.snap_load_fail, Snackbar.LENGTH_LONG)
        .setAction(R.string.retry, v -> {requestDataRefresh();})
        .show();
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
            getData(false);
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
        startPictureActivity(meizi, meiziView);
      } else if(v == card) {
        Intent i = new Intent(MainActivity.this, GankActivity.class);
        i.putExtra(GankActivity.EXTRA_GANK_DATE, meizi.createdAt);
        startActivity(i);
      }
    };
  }


  private void startPictureActivity(Meizi meizi, View transitView) {
    Intent i = new Intent(MainActivity.this, PictureActivity.class);
    i.putExtra(PictureActivity.EXTRA_IMAGE_URL, meizi.url);
    i.putExtra(PictureActivity.EXTRA_IMAGE_TITLE, meizi.desc);

    ActivityOptionsCompat optionsCompat =
        ActivityOptionsCompat.makeSceneTransitionAnimation(MainActivity.this, transitView,
            PictureActivity.TRANSIT_PIC);
    ActivityCompat.startActivity(MainActivity.this, i, optionsCompat.toBundle());
  }

  private MeiziData createMeiziDataWithVideoDesc(MeiziData meiziData, VideoData videoData) {
    for(int i = 0; i < meiziData.results.size(); i++) {
      Meizi m = meiziData.results.get(i);
      m.desc = m.desc + " " + videoData.results.get(i).desc;
    }

    return meiziData;
  }

  @Override public void onToolbarClick() {
    mRecyclerView.smoothScrollToPosition(0);
  }

  @Override public void requestDataRefresh() {
    super.requestDataRefresh();
    setRefreshing(false);
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    ButterKnife.unbind(this);
  }
}
