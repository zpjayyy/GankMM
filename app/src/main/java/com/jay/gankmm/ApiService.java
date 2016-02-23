package com.jay.gankmm;

import com.jay.gankmm.data.GankData;
import com.jay.gankmm.data.MeiziData;
import com.jay.gankmm.data.VideoData;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by jay on 16/2/23.
 */
public interface ApiService {

  @GET("data/福利/" + RetrofitFactory.meiziSize + "/{page}") Observable<MeiziData> getMeiziData(
      @Path("page") int page);

  @GET("day/{year}/{month}/{day}") Observable<GankData> getGankData(@Path("year") int year,
      @Path("month") int month, @Path("day") int day);

  @GET("data/休息视频/" + RetrofitFactory.meiziSize + "/{page}") Observable<VideoData> get休息视频Data(
      @Path("page") int page);

}
