package com.jay.gankmm.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.ImageView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.jay.gankmm.R;
import com.jay.gankmm.model.Gank;
import java.util.List;
import rx.Subscription;

/**
 * Created by jay on 16/2/24.
 */
public class GankFragment extends Fragment {

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
  }

  private void parseArguments() {

  }

  @Nullable @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View rootView = inflater.inflate(R.layout.fragment_gank, container, false);

    ButterKnife.bind(this, rootView);
    return rootView;
  }

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
  }

  private void initRecyclerView() {

  }

  @Override public void onDestroyView() {
    super.onDestroyView();
    ButterKnife.unbind(this);
  }
}
