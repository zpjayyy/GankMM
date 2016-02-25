package com.jay.gankmm.data;

import com.google.gson.annotations.SerializedName;
import com.jay.gankmm.model.Gank;
import java.util.List;

/**
 * Created by jay on 16/2/23.
 */
public class GankData {

  public Result results;
  public List<String> category;

  public class Result {
    @SerializedName("Android") public List<Gank> AndroidList;
    @SerializedName("iOS") public List<Gank> iOSList;
    @SerializedName("瞎推荐") public List<Gank> 瞎推荐List;
    @SerializedName("App") public List<Gank> AppList;
    @SerializedName("拓展资源") public List<Gank> 拓展资源List;
    @SerializedName("休息视频") public List<Gank> 休息视频List;
    @SerializedName("福利") public List<Gank> 福利List;
  }

}
