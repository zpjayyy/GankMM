package com.jay.gankmm;

import android.util.Log;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by jay on 16/2/23.
 */
public class RetrofitClient {

  private static final String TAG = "okhttp";

  final ApiService service;

  final static Gson gson =
      new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS").serializeNulls().create();

  public RetrofitClient() {
    OkHttpClient client = new OkHttpClient().newBuilder()
        .readTimeout(12, TimeUnit.SECONDS)
        .addInterceptor(new LoggingInterceptor())
        .build();

    Retrofit retrofit = new Retrofit.Builder().client(client)
        .baseUrl("http://gank.avosapps.com/api/")
        .addConverterFactory(GsonConverterFactory.create(gson))
        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
        .build();
    service = retrofit.create(ApiService.class);
  }

  public ApiService getService() {
    return service;
  }

  class LoggingInterceptor implements Interceptor {
    @Override public Response intercept(Interceptor.Chain chain) throws IOException {
      Request request = chain.request();

      long t1 = System.nanoTime();
      Log.d(TAG, String.format("Sending request %s on %s%n%s",
          request.url(), chain.connection(), request.headers()));

      Response response = chain.proceed(request);

      long t2 = System.nanoTime();
      Log.d(TAG, String.format("Received response for %s in %.1fms%n%s",
          response.request().url(), (t2 - t1) / 1e6d, response.headers()));

      return response;
    }
  }
}
