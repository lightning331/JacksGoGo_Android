package com.kelvin.jacksgogo.Fragments.Jobs;

import android.content.Context;
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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kelvin.jacksgogo.Adapter.Service.JGGImageGalleryAdapter;
import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Edit.EditJobTabbarView;
import com.kelvin.jacksgogo.Models.Jobs_Services.JGGServiceModel;
import com.kelvin.jacksgogo.R;
import com.yanzhenjie.album.Action;
import com.yanzhenjie.album.Album;
import com.yanzhenjie.album.AlbumFile;
import com.yanzhenjie.album.api.widget.Widget;

import java.util.ArrayList;

public class EditJobDescribeFragment extends Fragment implements View.OnClickListener, TextWatcher {

    private Context mContext;
    private OnFragmentInteractionListener mListener;

    private RecyclerView recyclerView;
    private JGGImageGalleryAdapter mAdapter;
    private ArrayList<AlbumFile> mAlbumFiles;

    private EditText txtServiceTitle;
    private EditText txtServiceDesc;
    private LinearLayout btnTakePhoto;
    private RelativeLayout btnNext;
    private TextView lblNext;

    private String strTitle;
    private String strDescription;

    private JGGServiceModel serviceObject;
    private boolean isRequest;

    public EditJobDescribeFragment() {
        // Required empty public constructor
    }

    public static EditJobDescribeFragment newInstance(boolean isRequest) {
        EditJobDescribeFragment fragment = new EditJobDescribeFragment();
        Bundle args = new Bundle();
        args.putBoolean("isRequest", isRequest);
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
        View view = inflater.inflate(R.layout.fragment_edit_job_describe, container, false);
        initView(view);
        initRecyclerView(view);
        return view;
    }

    private void initView(View view) {

        txtServiceTitle = view.findViewById(R.id.txt_edit_job_describe_title);
        txtServiceTitle.addTextChangedListener(this);
        txtServiceDesc = view.findViewById(R.id.txt_edit_job_describe_description);
        txtServiceDesc.addTextChangedListener(this);
        btnTakePhoto = view.findViewById(R.id.btn_edit_job_describe_take_photo);
        btnTakePhoto.setOnClickListener(this);
        btnNext = view.findViewById(R.id.btn_edit_job_next);
        lblNext = view.findViewById(R.id.lbl_edit_job_next);

        if (!isRequest) {
            txtServiceTitle.setText("Gerdening");
            txtServiceDesc.setText("Need help with moving the lawn and weeding the garden.");
        } else {
            //if (serviceObject.getTitle() == null) {
                txtServiceTitle.setHint("e.g.Gardening");
                txtServiceDesc.setHint("e.g.My air-cond unit isn't cold.");
                btnNext.setVisibility(View.VISIBLE);
            //} else {
                //txtServiceTitle.setText(serviceObject.getTitle());
                //txtServiceDesc.setText(serviceObject.getComment());
            //}
        }
    }

    private void initRecyclerView(View view) {
        recyclerView = view.findViewById(R.id.edit_job_describe_recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(mContext, 4));

        WindowManager wm = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        int width = display.getWidth();

        int itemSize = (width) / 4;
        mAdapter = new JGGImageGalleryAdapter(mContext, itemSize, true, new com.yanzhenjie.album.impl.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                selectImage();
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

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
        isRequest = getArguments().getBoolean("isRequest");
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
        if (view.getId() == R.id.btn_edit_job_describe_take_photo) {
            selectImage();
        } else if (view.getId() == R.id.btn_edit_job_next) {
            listener.onNextButtonClick(EditJobTabbarView.EditTabStatus.TIME, strTitle, strDescription);
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
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onNextButtonClick(EditJobTabbarView.EditTabStatus status, String jobTitle, String jobDesc);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
