package com.jay.gankmm.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.jay.gankmm.R;
import com.jay.gankmm.model.Gank;
import com.jay.gankmm.util.StringStyleUtils;
import java.util.List;

/**
 * Created by jay on 16/2/25.
 */
public class GankListAdapter extends RecyclerView.Adapter<GankListAdapter.ViewHolder> {

  private List<Gank> mGankList;

  public GankListAdapter(List<Gank> gankList) {
    this.mGankList = gankList;
  }

  @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_gank, parent, false);
    return new ViewHolder(v);
  }

  @Override public void onBindViewHolder(ViewHolder holder, int position) {
    Gank gank = mGankList.get(position);

    if (position == 0) {
      showCategory(holder);
    } else {
      boolean doesLastAndThis =
          mGankList.get(position - 1).type.equals(mGankList.get(position).type);
      if (!doesLastAndThis) {
        showCategory(holder);
      } else {
        holder.category.setVisibility(View.GONE);
      }
    }

    holder.category.setText(gank.type);

    SpannableStringBuilder builder = new SpannableStringBuilder(gank.desc).append(
        StringStyleUtils.format(holder.title.getContext(), "(Via. " + gank.who + ")",
            R.style.ViaTextAppearance));
    CharSequence gankText = builder.subSequence(0, builder.length());

    holder.title.setText(gankText);
  }

  @Override public int getItemCount() {
    return mGankList.size();
  }

  private void showCategory(ViewHolder holder) {
    if (!holder.category.isShown()) holder.category.setVisibility(View.VISIBLE);
  }

  class ViewHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.tv_category) TextView category;
    @Bind(R.id.tv_title) TextView title;

    public ViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }
  }
}
