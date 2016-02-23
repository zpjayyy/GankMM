package com.jay.gankmm;

import android.app.Application;
import android.content.Context;
import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Created by jay on 16/2/23.
 */
public class App extends Application {

  public static Context sContext;

  @Override public void onCreate() {
    super.onCreate();
    sContext = this;

    Fresco.initialize(this);
  }
}
