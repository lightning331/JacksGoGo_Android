package com.kelvin.jacksgogo.Fragments.Search;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kelvin.jacksgogo.Activities.Search.JGGImageCropActivity;
import com.kelvin.jacksgogo.Adapter.Services.JGGImageGalleryAdapter;
import com.kelvin.jacksgogo.R;
import com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events.JGGAppointmentModel;
import com.yanzhenjie.album.Action;
import com.yanzhenjie.album.Album;
import com.yanzhenjie.album.AlbumFile;
import com.yanzhenjie.album.api.widget.Widget;

import java.io.File;
import java.util.ArrayList;

import static com.kelvin.jacksgogo.Utils.Global.EVENTS;
import static com.kelvin.jacksgogo.Utils.Global.GOCLUB;
import static com.kelvin.jacksgogo.Utils.JGGAppManager.selectedAppointment;
import static com.kelvin.jacksgogo.Utils.Global.APPOINTMENT_TYPE;
import static com.kelvin.jacksgogo.Utils.Global.JOBS;
import static com.kelvin.jacksgogo.Utils.Global.SERVICES;

public class PostServiceDescribeFragment extends Fragment
        implements View.OnClickListener, TextWatcher {

    private OnFragmentInteractionListener mListener;
    private Context mContext;

    private RecyclerView recyclerView;
    private JGGImageGalleryAdapter mAdapter;
    private ArrayList<AlbumFile> mAlbumFiles;

    private TextView lblTitle;
    private TextView lblDescription;
    private TextView lblTags;
    private EditText txtServiceTitle;
    private EditText txtServiceDesc;
    private EditText txtServiceTag;
    private LinearLayout btnTakePhoto;
    private ImageView imgTakePhoto;
    private TextView txtTakePhoto;
    private RelativeLayout btnNext;
    private TextView lblNext;

    private String strTitle;
    private String strDesc;
    private String strTags;

    private String appType;
    private JGGAppointmentModel creatingApp;
    private int nextButton;
    private int imageSelectionColor;

    public PostServiceDescribeFragment() {
        // Required empty public constructor
    }

    public static PostServiceDescribeFragment newInstance(String type) {
        PostServiceDescribeFragment fragment = new PostServiceDescribeFragment();
        Bundle args = new Bundle();
        args.putString(APPOINTMENT_TYPE, type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            appType = getArguments().getString(APPOINTMENT_TYPE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_post_service_describe, container, false);

        creatingApp = selectedAppointment;

        initView(view);
        initRecyclerView();

        return view;
    }

    private void initView(View view) {

        recyclerView = view.findViewById(R.id.describe_photo_recycler_view);
        lblTitle = view.findViewById(R.id.lbl_title);
        lblDescription = view.findViewById(R.id.lbl_description);
        lblTags = view.findViewById(R.id.lbl_tags);
        txtServiceTitle = view.findViewById(R.id.txt_post_service_title);
        txtServiceTitle.addTextChangedListener(this);
        txtServiceDesc = view.findViewById(R.id.txt_post_service_description);
        txtServiceDesc.addTextChangedListener(this);
        txtServiceTag = view.findViewById(R.id.txt_post_service_tag);
        txtServiceTag.addTextChangedListener(this);
        btnTakePhoto = view.findViewById(R.id.btn_post_service_take_photo);
        btnTakePhoto.setOnClickListener(this);
        imgTakePhoto = view.findViewById(R.id.img_take_photo);
        txtTakePhoto = view.findViewById(R.id.lbl_take_photo);
        btnNext = view.findViewById(R.id.btn_post_service_next);
        lblNext = view.findViewById(R.id.lbl_post_service_next);
        imageSelectionColor = ContextCompat.getColor(mContext, R.color.JGGGreen);
        if (appType.equals(JOBS)) {
            lblTitle.setText(R.string.post_job_desc_title);
            lblDescription.setText(R.string.post_job_desc_description);
            lblTags.setText(R.string.post_job_desc_tag);
            btnTakePhoto.setBackgroundResource(R.drawable.cyan_border_background);
            imgTakePhoto.setImageResource(R.mipmap.icon_photo_cyan);
            txtTakePhoto.setTextColor(getResources().getColor(R.color.JGGCyan));
            nextButton = R.drawable.cyan_background;
            imageSelectionColor = ContextCompat.getColor(mContext, R.color.JGGCyan);
        } else if (appType.equals(GOCLUB)) {
            lblTitle.setText(R.string.post_go_club_desc_title);
            lblDescription.setText(R.string.post_go_club_desc_description);
            lblTags.setText(R.string.post_go_club_desc_tag);
            txtServiceTitle.setHint("e.g. We Love JacksGoGo");
            txtServiceDesc.setHint("e.g. Everything about JacksGoGo");
            btnTakePhoto.setBackgroundResource(R.drawable.purple_border_background);
            imgTakePhoto.setImageResource(R.mipmap.icon_photo_purple);
            txtTakePhoto.setTextColor(getResources().getColor(R.color.JGGPurple));
            nextButton = R.drawable.purple_background;
            imageSelectionColor = ContextCompat.getColor(mContext, R.color.JGGPurple);
        } else if (appType.equals(GOCLUB)) {
            lblTitle.setText("Give your event a short title.");
            lblDescription.setText("Give a description for your event.");
            lblTags.setText("Add tags to your event (optional.");
            txtServiceTitle.setHint("e.g. Neighbourhood Football Match");
            txtServiceDesc.setHint("e.g. Friendly neighbourhood match.");
            btnTakePhoto.setBackgroundResource(R.drawable.purple_border_background);
            imgTakePhoto.setImageResource(R.mipmap.icon_photo_purple);
            txtTakePhoto.setTextColor(getResources().getColor(R.color.JGGPurple));
            nextButton = R.drawable.purple_background;
            imageSelectionColor = ContextCompat.getColor(mContext, R.color.JGGGreen);
        }
        if (appType.equals(SERVICES) || appType.equals(JOBS)) {
            // Set Job Describe Data
            txtServiceTitle.setText(creatingApp.getTitle());
            txtServiceDesc.setText(creatingApp.getDescription());
            txtServiceTag.setText(creatingApp.getTags());
        }
    }

    private void initRecyclerView() {
        recyclerView.setLayoutManager(new GridLayoutManager(mContext, 4));

        WindowManager wm = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        int width = display.getWidth();

        int itemSize = (width) / 4;
        mAdapter = new JGGImageGalleryAdapter(mContext, itemSize, false, new com.yanzhenjie.album.impl.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (position < mAlbumFiles.size()) {
                    String name = (String)mAlbumFiles.get(position).getPath();
                    Uri imageUri = Uri.parse(new File(name).toString());
                    Intent intent = new Intent(mContext, JGGImageCropActivity.class);
                    intent.putExtra("imageUri", name);
                    intent.putExtra(APPOINTMENT_TYPE, SERVICES);
                    startActivityForResult(intent, 1);
                } else {
                    selectImage();
                }
            }
        });
        if (appType.equals(SERVICES) || appType.equals(JOBS)) {
            if (creatingApp.getAlbumFiles() == null) {

            } else {
                if (creatingApp.getAlbumFiles().size() != 0) {
                    recyclerView.setVisibility(View.VISIBLE);
                    mAlbumFiles = creatingApp.getAlbumFiles();
                    mAdapter.notifyDataChanged(mAlbumFiles);
                }
            }
        } else if (appType.equals(GOCLUB)) {

        }
        recyclerView.setAdapter(mAdapter);
    }

    private void selectImage() {
        Album.image(this)
                .multipleChoice()
                .requestCode(200)
                .camera(true)
                .columnCount(3)
                .selectCount(7)
                .checkedList(mAlbumFiles)
                .widget(
                        Widget.newLightBuilder(mContext)
                                .title("Include Photos") // Title.
                                .statusBarColor(ContextCompat.getColor(mContext, R.color.JGGGrey1)) // StatusBar color.
                                .toolBarColor(Color.WHITE) // Toolbar color.
                                .navigationBarColor(Color.GREEN) // Virtual NavigationBar color of Android5.0+.
                                .mediaItemCheckSelector(imageSelectionColor, imageSelectionColor) // Image or video selection box.
                                .bucketItemCheckSelector(imageSelectionColor, imageSelectionColor) // Select the folder selection box.
                                .buttonStyle( // Used to configure the style of button when the image/video is not found.
                                        Widget.ButtonStyle.newLightBuilder(mContext) // With Widget's Builder model.
                                                .setButtonSelector(imageSelectionColor, Color.WHITE) // Button selector.
                                                .build()
                                )
                                .build()
                )
                .onResult(new Action<ArrayList<AlbumFile>>() {
                    @Override
                    public void onAction(int requestCode, @NonNull ArrayList<AlbumFile> result) {
                        mAlbumFiles = result;
                        recyclerView.setVisibility(View.VISIBLE);
                        mAdapter.notifyDataChanged(mAlbumFiles);
                        btnTakePhoto.setVisibility(View.GONE);
                    }
                })
                .onCancel(new Action<String>() {
                    @Override
                    public void onAction(int requestCode, @NonNull String result) {
                        //Toast.makeText(mContext, R.string.canceled, Toast.LENGTH_LONG).show();
                    }
                })
                .start();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_post_service_next) {
            if (appType.equals(SERVICES) || appType.equals(JOBS))
                setAppointmentData();
            else if (appType.equals(GOCLUB))
                setGoClubData();
            else if (appType.equals(EVENTS))
                setEventData();
        } else if (view.getId() == R.id.btn_post_service_take_photo) {
            selectImage();
        }
    }

    private void setAppointmentData() {
        strTitle = txtServiceTitle.getText().toString();
        strDesc = txtServiceDesc.getText().toString();
        strTags = txtServiceTag.getText().toString();
        creatingApp.setTitle(strTitle);
        creatingApp.setDescription(strDesc);
        creatingApp.setTags(strTags);
        creatingApp.setAlbumFiles(mAlbumFiles);
        selectedAppointment = creatingApp;
        listener.onNextButtonClick();
    }

    private void setGoClubData() {

    }

    private void setEventData() {

    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if (txtServiceTitle.length() > 0
                && txtServiceDesc.length() > 0) {

            lblNext.setTextColor(ContextCompat.getColor(mContext, R.color.JGGWhite));
            btnNext.setBackgroundResource(nextButton);
            if (appType.equals(JOBS)) btnNext.setBackgroundResource(R.drawable.cyan_background);
            btnNext.setOnClickListener(this);
        } else {
            lblNext.setTextColor(ContextCompat.getColor(mContext, R.color.JGGGrey2));
            btnNext.setBackgroundResource(R.drawable.grey_background);
            btnNext.setClickable(false);
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {

        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onNextButtonClick();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
