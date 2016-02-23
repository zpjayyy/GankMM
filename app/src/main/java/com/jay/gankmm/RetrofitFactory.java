package com.jay.gankmm;

/**
 * Created by jay on 16/2/23.
 */
public class RetrofitFactory {

  protected static final Object monitor = new Object();
  static ApiService sSingleton = null;
  public static final int meiziSize = 10;
  public static final int gankSize = 5;

  public static ApiService getSingleton() {
    synchronized (monitor) {
      if (sSingleton == null) {
        sSingleton = new RetrofitClient().getService();
      }

      return sSingleton;
    }
  }

}
