package com.jay.gankmm.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * Created by jay on 16/2/24.
 */
public class GankFragment extends Fragment {

  private static final String ARG_YEAR = "year";
  private static final String ARG_MONTH = "month";
  private static final String ARG_DAY = "day";

  public static GankFragment newStance(int year, int month, int day) {
    GankFragment fragment = new GankFragment();
    Bundle args = new Bundle();
    args.putInt(ARG_YEAR, year);
    args.putInt(ARG_MONTH, month);
    args.putInt(ARG_DAY, day);
    fragment.setArguments(args);
    return fragment;
  }

  public GankFragment() {}

}
