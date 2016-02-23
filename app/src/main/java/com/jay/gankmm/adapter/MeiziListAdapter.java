package com.jay.gankmm.adapter;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.facebook.drawee.view.SimpleDraweeView;
import com.jay.gankmm.R;
import com.jay.gankmm.face.OnMeiziTouchListener;
import com.jay.gankmm.model.Meizi;
import java.util.List;

/**
 * Created by jay on 16/2/22.
 */
public class MeiziListAdapter extends RecyclerView.Adapter<MeiziListAdapter.ViewHolder> {

  private List<Meizi> mList;
  private OnMeiziTouchListener mOnMeiziTouchListener;

  public MeiziListAdapter(List<Meizi> meiziList) {
    this.mList = meiziList;
  }

  @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_meizi, parent, false);
    return new ViewHolder(v);
  }

  @Override public void onBindViewHolder(ViewHolder holder, int position) {
    Meizi meizi = mList.get(position);

    holder.meizi = meizi;
    holder.titleView.setText(meizi.desc);
    holder.card.setTag(meizi.desc);

    holder.meiziView.setImageURI(Uri.parse(meizi.url));
  }

  @Override public void onViewRecycled(ViewHolder holder) {
    super.onViewRecycled(holder);
  }

  @Override public int getItemCount() {
    return mList.size();
  }

  public void setOnMeiziTouchListener(OnMeiziTouchListener onMeiziTouchListener) {
    this.mOnMeiziTouchListener = onMeiziTouchListener;
  }

  class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    @Bind(R.id.dv_meizi) SimpleDraweeView meiziView;
    @Bind(R.id.tv_title) TextView titleView;

    View card;
    Meizi meizi;

    public ViewHolder(View itemView) {
      super(itemView);
      card = itemView;
      ButterKnife.bind(this, itemView);
      meiziView.setOnClickListener(this);
      card.setOnClickListener(this);
    }

    @Override public void onClick(View v) {
      mOnMeiziTouchListener.onTouch(v, meiziView, card, meizi);
    }
  }
}
