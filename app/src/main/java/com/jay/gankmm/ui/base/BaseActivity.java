package com.jay.gankmm.ui.base;

import android.support.v7.app.AppCompatActivity;
import com.jay.gankmm.ApiService;
import com.jay.gankmm.RetrofitFactory;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by jay on 16/2/15.
 */
public class BaseActivity extends AppCompatActivity {

  public static final ApiService sApiService = RetrofitFactory.getSingleton();

  private CompositeSubscription mCompositeSubscription;

  public CompositeSubscription getCompositeSubscription() {
    if(this.mCompositeSubscription == null) {
      this.mCompositeSubscription = new CompositeSubscription();
    }

    return mCompositeSubscription;
  }

  public void addSubscription(Subscription s) {
    if(this.mCompositeSubscription == null) {
      mCompositeSubscription = new CompositeSubscription();
    }

    mCompositeSubscription.add(s);
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    if(mCompositeSubscription != null) {
      mCompositeSubscription.unsubscribe();
    }
  }
}
