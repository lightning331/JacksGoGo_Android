package com.kelvin.jacksgogo.Activities.Search;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.kelvin.jacksgogo.R;
import com.theartofdev.edmodo.cropper.CropImageOptions;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;

public class JGGImageCropActivity extends AppCompatActivity
        implements CropImageView.OnSetImageUriCompleteListener,
        CropImageView.OnCropImageCompleteListener,
        View.OnClickListener {

    private ImageView btnClose;
    private ImageView btnCrop;
    private ImageView btnDelete;
    private ImageView btnDone;
    private ImageView mCropedImageView;
    private CropImageView mCropImageView;
    private LinearLayout cropButtonLayout;

    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jggimage_crop);

        Bundle extra = getIntent().getExtras();
        String filePath = extra.getString("imageUri");
        imageUri = Uri.fromFile(new File(filePath));

        initView();
    }

    private void initView() {
        cropButtonLayout = (LinearLayout) findViewById(R.id.crop_button_layout);
        mCropedImageView = (ImageView) findViewById(R.id.croped_image_view);
        mCropedImageView.setImageURI(imageUri);
        btnClose = (ImageView) findViewById(R.id.btn_crop_close);
        btnCrop = (ImageView) findViewById(R.id.btn_crop);
        btnDelete = (ImageView) findViewById(R.id.btn_crop_delete);
        btnDone = (ImageView) findViewById(R.id.btn_crop_ok);

        btnClose.setOnClickListener(this);
        btnCrop.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        btnDone.setOnClickListener(this);
    }

    private void initCropImageView(Uri uri) {
        mCropImageView = (CropImageView) findViewById(R.id.cropImageView);
        mCropImageView.setOnSetImageUriCompleteListener(this);
        mCropImageView.setOnCropImageCompleteListener(this);

        CropImageOptions options = new CropImageOptions();
        options.scaleType =
                options.scaleType == CropImageView.ScaleType.FIT_CENTER
                        ? CropImageView.ScaleType.CENTER_INSIDE
                        : options.scaleType == CropImageView.ScaleType.CENTER_INSIDE
                        ? CropImageView.ScaleType.CENTER
                        : options.scaleType == CropImageView.ScaleType.CENTER
                        ? CropImageView.ScaleType.CENTER_CROP
                        : CropImageView.ScaleType.FIT_CENTER;

        mCropImageView.setScaleType(options.scaleType);
        mCropImageView.setCropShape(options.cropShape);
        mCropImageView.setGuidelines(options.guidelines);
        mCropImageView.setFixedAspectRatio(options.fixAspectRatio);
        mCropImageView.setShowCropOverlay(options.showCropOverlay);
        mCropImageView.setShowProgressBar(options.showProgressBar);
        mCropImageView.setAutoZoomEnabled(options.autoZoomEnabled);
        mCropImageView.setFlippedHorizontally(options.flipHorizontally);
        mCropImageView.setFlippedVertically(options.flipVertically);

        mCropImageView.setImageUriAsync(uri);
    }

    @Override
    public void onCropImageComplete(CropImageView view, CropImageView.CropResult result) {
        handleCropResult(result);
    }

    @Override
    public void onSetImageUriComplete(CropImageView view, Uri uri, Exception error) {
        if (error == null) {
            Log.e("AIC", "Image load successful", error);
            //Toast.makeText(this, "Image load successful", Toast.LENGTH_SHORT).show();
        } else {
            Log.e("AIC", "Failed to load image by URI", error);
            //Toast.makeText(this, "Image load failed: " + error.getMessage(), Toast.LENGTH_LONG)
            //       .show();
        }
    }

    private void handleCropResult(CropImageView.CropResult result) {
        if (result.getError() == null) {
            //Toast.makeText(this, "Image cropped successful----------------", Toast.LENGTH_SHORT).show();
            Uri cropedUri = result.getUri();
//            mCropedImageView.setVisibility(View.VISIBLE);
//            mCropedImageView.setImageURI(cropedUri);
//            mCropImageView.setVisibility(View.GONE);
            onBackPressed();
        } else {
            Log.e("AIC", "Failed to crop image", result.getError());
            Toast.makeText(
                    this,
                    "Image crop failed: " + result.getError().getMessage(),
                    Toast.LENGTH_LONG)
                    .show();
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_crop_close) {
            onBackPressed();
        } else if (view.getId() == R.id.btn_crop) {
            mCropedImageView.setVisibility(View.GONE);
            btnDone.setVisibility(View.VISIBLE);
            cropButtonLayout.setVisibility(View.GONE);
            initCropImageView(imageUri);
        } else if (view.getId() == R.id.btn_crop_delete) {

        } else if (view.getId() == R.id.btn_crop_ok) {
            mCropImageView.getCroppedImageAsync();
        }
    }
}
