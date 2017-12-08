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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kelvin.jacksgogo.Activities.Search.JGGImageCropActivity;
import com.kelvin.jacksgogo.Adapter.Service.JGGImageGalleryAdapter;
import com.kelvin.jacksgogo.R;
import com.theartofdev.edmodo.cropper.CropImage;
import com.yanzhenjie.album.Action;
import com.yanzhenjie.album.Album;
import com.yanzhenjie.album.AlbumFile;
import com.yanzhenjie.album.api.widget.Widget;

import java.io.File;
import java.util.ArrayList;

public class PostServiceDescribeFragment extends Fragment
        implements View.OnClickListener, TextWatcher {

    private OnFragmentInteractionListener mListener;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onNextButtonClick(String title, String comment, String tags);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    private Context mContext;

    private RecyclerView recyclerView;
    private JGGImageGalleryAdapter mAdapter;
    private ArrayList<AlbumFile> mAlbumFiles;

    private EditText txtServiceTitle;
    private EditText txtServiceDesc;
    private EditText txtServiceTag;
    private LinearLayout btnTakePhoto;
    private LinearLayout btnNext;
    private TextView lblNext;

    private String strTitle;
    private String strDescription;
    private String strTags;

    public PostServiceDescribeFragment() {
        // Required empty public constructor
    }

    public static PostServiceDescribeFragment newInstance(String param1, String param2) {
        PostServiceDescribeFragment fragment = new PostServiceDescribeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_post_service_describe, container, false);

        initiView(view);
        initRecyclerView(view);

        return view;
    }

    private void initiView(View view) {

        txtServiceTitle = view.findViewById(R.id.txt_post_service_title);
        txtServiceTitle.addTextChangedListener(this);
        txtServiceDesc = view.findViewById(R.id.txt_post_service_description);
        txtServiceDesc.addTextChangedListener(this);
        txtServiceTag = view.findViewById(R.id.txt_post_service_tag);
        txtServiceTag.addTextChangedListener(this);
        btnTakePhoto = view.findViewById(R.id.btn_post_service_take_photo);
        btnTakePhoto.setOnClickListener(this);
        btnNext = view.findViewById(R.id.btn_post_service_next);
        lblNext = view.findViewById(R.id.lbl_post_service_next);
    }

    private void initRecyclerView(View view) {
        recyclerView = view.findViewById(R.id.describe_photo_recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(mContext, 4));

        WindowManager wm = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        int width = display.getWidth();

        int itemSize = (width) / 4;
        mAdapter = new JGGImageGalleryAdapter(mContext, itemSize, new com.yanzhenjie.album.impl.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (position < mAlbumFiles.size()) {
                    String name = (String)mAlbumFiles.get(position).getPath();
                    Uri imageUri = Uri.parse(new File(name).toString());
                    Intent intent = new Intent(mContext, JGGImageCropActivity.class);
                    intent.putExtra("imageUri", name);
                    startActivityForResult(intent, 1);
                } else {
                    selectImage();
                }
            }
        });
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
                                .mediaItemCheckSelector(ContextCompat.getColor(mContext, R.color.JGGGreen), Color.GREEN) // Image or video selection box.
                                .bucketItemCheckSelector(ContextCompat.getColor(mContext, R.color.JGGGreen), ContextCompat.getColor(mContext, R.color.JGGGreen)) // Select the folder selection box.
                                .buttonStyle( // Used to configure the style of button when the image/video is not found.
                                        Widget.ButtonStyle.newLightBuilder(mContext) // With Widget's Builder model.
                                                .setButtonSelector(ContextCompat.getColor(mContext, R.color.JGGGreen), Color.WHITE) // Button selector.
                                                .build()
                                )
                                .build()
                )
                .onResult(new Action<ArrayList<AlbumFile>>() {
                    @Override
                    public void onAction(int requestCode, @NonNull ArrayList<AlbumFile> result) {
                        mAlbumFiles = result;
                        recyclerView.setVisibility(View.VISIBLE);
                        mAdapter.notifyDataSetChanged(mAlbumFiles);
                        btnTakePhoto.setVisibility(View.GONE);
                    }
                })
                .onCancel(new Action<String>() {
                    @Override
                    public void onAction(int requestCode, @NonNull String result) {
                        Toast.makeText(mContext, R.string.canceled, Toast.LENGTH_LONG).show();
                    }
                })
                .start();
    }

    // TODO: Rename method, update argument and hook method into UI event
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

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_post_service_next) {
            listener.onNextButtonClick(strTitle, strDescription, strTags);
        } else if (view.getId() == R.id.btn_post_service_take_photo) {
            selectImage();
        }
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if (txtServiceTitle.length() > 0
                && txtServiceDesc.length() > 0) {

            strTitle = txtServiceTitle.getText().toString();
            strDescription = txtServiceDesc.getText().toString();
            strTags = txtServiceTag.getText().toString();

            lblNext.setTextColor(ContextCompat.getColor(mContext, R.color.JGGWhite));
            btnNext.setBackgroundResource(R.drawable.green_background);
            btnNext.setOnClickListener(this);
        } else {
            lblNext.setTextColor(ContextCompat.getColor(mContext, R.color.JGGGrey2));
            btnNext.setBackgroundResource(R.drawable.grey_background);
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
