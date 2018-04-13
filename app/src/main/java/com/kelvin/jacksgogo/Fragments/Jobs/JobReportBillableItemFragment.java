package com.kelvin.jacksgogo.Fragments.Jobs;

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

import com.kelvin.jacksgogo.Activities.Jobs.JobReportActivity;
import com.kelvin.jacksgogo.Activities.Search.JGGImageCropActivity;
import com.kelvin.jacksgogo.Adapter.Services.JGGImageGalleryAdapter;
import com.kelvin.jacksgogo.R;
import com.yanzhenjie.album.Action;
import com.yanzhenjie.album.Album;
import com.yanzhenjie.album.AlbumFile;
import com.yanzhenjie.album.api.widget.Widget;

import java.io.File;
import java.util.ArrayList;

import static com.kelvin.jacksgogo.CustomView.Views.JGGActionbarView.EditStatus.JOB_REPORT;
import static com.kelvin.jacksgogo.Utils.Global.APPOINTMENT_TYPE;
import static com.kelvin.jacksgogo.Utils.Global.JGGUserType.PROVIDER;
import static com.kelvin.jacksgogo.Utils.Global.JOBS;

public class JobReportBillableItemFragment extends Fragment implements TextWatcher {

    private OnFragmentInteractionListener mListener;
    private Context mContext;
    private JobReportActivity mActivity;

    private LinearLayout btnTakePhoto;
    private EditText txtItemDesc;
    private LinearLayout budgetLayout;
    private EditText txtBudget;
    private TextView btnSendApproval;

    private RecyclerView recyclerView;
    private JGGImageGalleryAdapter mAdapter;
    private ArrayList<AlbumFile> mAlbumFiles;

    public JobReportBillableItemFragment() {
        // Required empty public constructor
    }

    public static JobReportBillableItemFragment newInstance(String param1, String param2) {
        JobReportBillableItemFragment fragment = new JobReportBillableItemFragment();
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
        View view = inflater.inflate(R.layout.fragment_job_report_billable_item, container, false);

        initView(view);
        initRecyclerView(view);

        return view;
    }

    private void initView(View view) {
        txtItemDesc = view.findViewById(R.id.txt_billable_item_desc);
        txtItemDesc.addTextChangedListener(this);
        budgetLayout = view.findViewById(R.id.budget_layout);
        txtBudget = view.findViewById(R.id.txt_billable_item_budget);
        txtBudget.addTextChangedListener(this);
        btnSendApproval = view.findViewById(R.id.btn_send_billable_item);
        btnSendApproval.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendBillableItemForApproval();
            }
        });
        btnTakePhoto = view.findViewById(R.id.btn_item_take_photo);
        btnTakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });
    }

    private void initRecyclerView(View view) {
        recyclerView = view.findViewById(R.id.item_photo_recycler_view);
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
                    intent.putExtra(APPOINTMENT_TYPE, JOBS);
                    startActivityForResult(intent, 1);
                } else {
                    selectImage();
                }
            }
        });
        recyclerView.setAdapter(mAdapter);
    }

    private void sendBillableItemForApproval() {
        if (txtItemDesc.length() > 0 && txtBudget.length() > 0) {
            mActivity.setActionbarView(JOB_REPORT);
            JobReportMainFragment frag = JobReportMainFragment.newInstance(PROVIDER.toString());
            frag.setBillableItem(txtItemDesc.getText().toString(), Double.parseDouble(txtBudget.getText().toString()));
            mActivity.getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.job_report_container, frag)
                    .addToBackStack("report_main")
                    .commit();
        } else {
            if (txtItemDesc.getText().toString().equals(""))
                txtItemDesc.setBackgroundResource(R.drawable.red_border_background);
            if (txtBudget.getText().toString().equals(""))
                budgetLayout.setBackgroundResource(R.drawable.red_border_background);
        }
    }

    private void selectImage() {
        int mColor = ContextCompat.getColor(mContext, R.color.JGGCyan);
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
                                .navigationBarColor(mColor) // Virtual NavigationBar color of Android5.0+.
                                .mediaItemCheckSelector(mColor, mColor) // Image or video selection box.
                                .bucketItemCheckSelector(mColor, mColor) // Select the folder selection box.
                                .buttonStyle( // Used to configure the style of button when the image/video is not found.
                                        Widget.ButtonStyle.newLightBuilder(mContext) // With Widget's Builder model.
                                                .setButtonSelector(mColor, Color.WHITE) // Button selector.
                                                .build()
                                )
                                .build()
                )
                .onResult(new Action<ArrayList<AlbumFile>>() {
                    @Override
                    public void onAction(int requestCode, @NonNull ArrayList<AlbumFile> result) {
                        mAlbumFiles = result;
                        btnTakePhoto.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                        mAdapter.notifyDataChanged(mAlbumFiles);
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
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if (txtItemDesc.length() > 0)
            txtItemDesc.setBackgroundResource(R.drawable.grey_border_background);
        if (txtBudget.length() > 0)
            budgetLayout.setBackgroundResource(R.drawable.grey_border_background);
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

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
        mActivity = ((JobReportActivity) context);
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

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
