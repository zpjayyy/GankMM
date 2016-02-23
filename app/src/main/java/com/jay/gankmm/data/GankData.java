package com.jay.gankmm.data;

import com.jay.gankmm.model.Gank;
import java.util.List;

/**
 * Created by jay on 16/2/23.
 */
public class GankData {

  public Result results;
  public List<String> category;

  public class Result {
    public List<Gank> androidList;
    public List<Gank> 休息视频List;
    public List<Gank> iOSList;
    public List<Gank> 妹纸List;
    public List<Gank> 拓展资源List;
    public List<Gank> 瞎推荐List;
  }

}
