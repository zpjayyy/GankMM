package com.jay.gankmm.ui;

import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AlertDialog;
import android.widget.ImageView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.jay.gankmm.R;
import com.jay.gankmm.ui.base.ToolBarActivity;
import com.squareup.picasso.Picasso;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by jay on 16/2/24.
 */
public class PictureActivity extends ToolBarActivity {

  public static final String EXTRA_IMAGE_URL = "image_url";
  public static final String EXTRA_IMAGE_TITLE = "image_title";
  public static final String TRANSIT_PIC = "picture";

  @Bind(R.id.picture) ImageView mImageView;

  PhotoViewAttacher mPhotoViewAttacher;
  String mImageUrl, mImageTitle;

  @Override protected int provideContentViewId() {
    return R.layout.activity_picture;
  }

  @Override public boolean canBack() {
    return true;
  }

  private void parseIntent() {
    mImageUrl = getIntent().getStringExtra(EXTRA_IMAGE_URL);
    mImageTitle = getIntent().getStringExtra(EXTRA_IMAGE_TITLE);
  }

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    // TODO: add setContentView(...) invocation
    ButterKnife.bind(this);

    parseIntent();

    ViewCompat.setTransitionName(mImageView, TRANSIT_PIC);
    Picasso.with(this).load(mImageUrl).into(mImageView);

    setAppBarAlpha(0.7f);
    setTitle(mImageTitle);

    setUpPhotoAttacher();
  }

  private void setUpPhotoAttacher() {
    mPhotoViewAttacher = new PhotoViewAttacher(mImageView);
    mPhotoViewAttacher.setOnViewTapListener((view, x, y) -> hideOrShowToolbar());

    mPhotoViewAttacher.setOnLongClickListener(v -> {
      new AlertDialog.Builder(PictureActivity.this).setMessage(
          getString(R.string.ask_saving_picture))
          .setNegativeButton(android.R.string.cancel, (dialog, which) -> {
            dialog.dismiss();
          })
          .setPositiveButton(android.R.string.ok, (dialog, which1) -> {
            saveImageToGallery();
            dialog.dismiss();
          })
          .show();

      return true;
    });
  }

  private void saveImageToGallery() {

  }

  @Override protected void onDestroy() {
    super.onDestroy();
    ButterKnife.unbind(this);
  }
}
