package com.jay.gankmm.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.text.TextUtils;
import android.webkit.WebView;
import android.widget.TextSwitcher;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.jay.gankmm.R;
import com.jay.gankmm.ui.base.ToolBarActivity;

/**
 * Created by jay on 16/2/26.
 */
public class WebActivity extends ToolBarActivity {

  private final String TAG = "web_activity";

  public static final String EXTRA_URL = "extra_url";
  public static final String EXTRA_TITLE = "extra_title";

  @Bind(R.id.tv_title) TextSwitcher mTextSwitcher;
  @Bind(R.id.progressbar) ContentLoadingProgressBar mProgressbar;
  @Bind(R.id.webView) WebView mWebView;

  Context mContext;
  String mUrl, mTitle;

  @Override protected int provideContentViewId() {
    return R.layout.activity_web;
  }

  @Override public boolean canBack() {
    return true;
  }

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    // TODO: add setContentView(...) invocation
    ButterKnife.bind(this);
    mContext = this;
    parseIntent();

    mWebView.loadUrl(mUrl);

    setUpTextSwitcher();
  }

  private void parseIntent() {
    mUrl = getIntent().getStringExtra(EXTRA_URL);
    mTitle = getIntent().getStringExtra(EXTRA_TITLE);
  }

  private void setUpTextSwitcher() {
    mTextSwitcher.setFactory(() -> {
      TextView textView = new TextView(this);
      textView.setTextAppearance(this, R.style.WebTitle);
      textView.setSingleLine(true);
      textView.setEllipsize(TextUtils.TruncateAt.MARQUEE);
      textView.postDelayed(() -> textView.setSelected(true), 1700);
      return textView;
    });

    mTextSwitcher.setInAnimation(this, android.R.anim.fade_in);
    mTextSwitcher.setOutAnimation(this, android.R.anim.fade_out);

    if(mTitle != null) setTitle(mTitle);
  }

  @Override public void setTitle(CharSequence title) {
    super.setTitle(title);
    mTextSwitcher.setText(title);
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    ButterKnife.unbind(this);
  }
}
