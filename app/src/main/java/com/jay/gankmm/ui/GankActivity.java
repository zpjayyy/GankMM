package com.jay.gankmm.ui;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.jay.gankmm.R;
import com.jay.gankmm.RetrofitFactory;
import com.jay.gankmm.adapter.GankPagerAdapter;
import com.jay.gankmm.ui.base.ToolBarActivity;
import com.jay.gankmm.util.DateUtils;
import java.util.Date;

/**
 * Created by jay on 16/2/24.
 */
public class GankActivity extends ToolBarActivity implements ViewPager.OnPageChangeListener {

  public static final String EXTRA_GANK_DATE = "gank_date";

  @Bind(R.id.pager) ViewPager mViewPager;
  @Bind(R.id.tabLayout) TabLayout mTabLayout;

  GankPagerAdapter mPagerAdapter;
  Date mGankDate;

  @Override protected int provideContentViewId() {
    return R.layout.activity_gank;
  }

  @Override public boolean canBack() {
    return true;
  }

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    // TODO: add setContentView(...) invocation
    ButterKnife.bind(this);

    mGankDate = (Date) getIntent().getSerializableExtra(EXTRA_GANK_DATE);
    setTitle(DateUtils.toDate(mGankDate));

    initViewPager();
    initTabLayout();

  }

  private void initViewPager() {
    mPagerAdapter = new GankPagerAdapter(getSupportFragmentManager(), mGankDate);
    mViewPager.setAdapter(mPagerAdapter);
    mViewPager.setOffscreenPageLimit(1);
    mViewPager.addOnPageChangeListener(this);
  }

  private void initTabLayout() {
    for(int i = 0; i < mPagerAdapter.getCount(); i++) {
      mTabLayout.addTab(mTabLayout.newTab());
    }

    mTabLayout.setupWithViewPager(mViewPager);
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    ButterKnife.unbind(this);
  }

  @Override
  public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

  }

  @Override public void onPageSelected(int position) {
    setTitle(DateUtils.toDate(mGankDate, -position));
  }

  @Override public void onPageScrollStateChanged(int state) {

  }

}
