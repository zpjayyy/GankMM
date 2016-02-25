package com.jay.gankmm.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.ImageView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.jay.gankmm.R;
import com.jay.gankmm.adapter.GankListAdapter;
import com.jay.gankmm.data.GankData;
import com.jay.gankmm.model.Gank;
import com.jay.gankmm.ui.base.BaseActivity;
import java.util.ArrayList;
import java.util.List;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by jay on 16/2/24.
 */
public class GankFragment extends Fragment {

  private static final String TAG = "gank_fragment";

  private static final String ARG_YEAR = "year";
  private static final String ARG_MONTH = "month";
  private static final String ARG_DAY = "day";


  @Bind(R.id.iv_video) ImageView mVideoImageView;
  @Bind(R.id.rv_gank) RecyclerView mRecyclerView;
  @Bind(R.id.stub_empty_view) ViewStub mEmptyViewStub;

  int mYear, mMonth, mDay;
  List<Gank> mGankList;
  String mViewoPreviewUrl;
  Subscription mSubscription;
  GankListAdapter mGankListAdapter;

  public static GankFragment newStance(int year, int month, int day) {
    GankFragment fragment = new GankFragment();
    Bundle args = new Bundle();
    args.putInt(ARG_YEAR, year);
    args.putInt(ARG_MONTH, month);
    args.putInt(ARG_DAY, day);
    fragment.setArguments(args);
    return fragment;
  }

  public GankFragment() {
  }

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mGankList = new ArrayList<>();
    mGankListAdapter = new GankListAdapter(mGankList);
    parseArguments();
  }

  private void parseArguments() {
    Bundle bundle = getArguments();
    mYear = bundle.getInt(GankFragment.ARG_YEAR);
    mMonth = bundle.getInt(GankFragment.ARG_MONTH);
    mDay = bundle.getInt(ARG_DAY);
  }

  @Nullable @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View rootView = inflater.inflate(R.layout.fragment_gank, container, false);
    ButterKnife.bind(this, rootView);
    initRecyclerView();
    return rootView;
  }

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    if(mGankList.size() == 0) getData();
  }

  private void initRecyclerView() {
    LinearLayoutManager manager = new LinearLayoutManager(getActivity());
    mRecyclerView.setLayoutManager(manager);
    mRecyclerView.setAdapter(mGankListAdapter);
  }

  private void getData() {
    mSubscription = BaseActivity.sApiService.getGankData(mYear, mMonth, mDay)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .map(gankData -> gankData.results)
        .map(this::addAllResults)
        .subscribe(ganks -> {
          if(ganks.isEmpty()) {
            showEmptyView();
          } else {
            mGankListAdapter.notifyDataSetChanged();
          }
        }, Throwable::printStackTrace);
  }

  private List<Gank> addAllResults(GankData.Result result) {
    if(result.AndroidList != null) mGankList.addAll(result.AndroidList);
    if(result.iOSList != null) mGankList.addAll(result.iOSList);
    if(result.瞎推荐List != null) mGankList.addAll(result.瞎推荐List);
    if(result.AppList != null) mGankList.addAll(result.AppList);
    if(result.拓展资源List != null) mGankList.addAll(result.拓展资源List);
    if(result.休息视频List != null) mGankList.addAll(0, result.休息视频List);

    return mGankList;
  }

  private void showEmptyView() {
    mEmptyViewStub.inflate();
  }

  @Override public void onDestroyView() {
    super.onDestroyView();
    ButterKnife.unbind(this);
  }

  @Override public void onDestroy() {
    super.onDestroy();
    if(mSubscription != null) {
      mSubscription.unsubscribe();
    }
  }
}
