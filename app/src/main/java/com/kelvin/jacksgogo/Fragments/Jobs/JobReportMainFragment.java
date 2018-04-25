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
import com.kelvin.jacksgogo.Utils.JGGAppManager;
import com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events.JGGAppointmentModel;
import com.yanzhenjie.album.Action;
import com.yanzhenjie.album.Album;
import com.yanzhenjie.album.AlbumFile;
import com.yanzhenjie.album.api.widget.Widget;
import com.yanzhenjie.album.impl.OnItemClickListener;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;

import static com.kelvin.jacksgogo.CustomView.Views.JGGActionbarView.EditStatus.ADD_BILLABLE_ITEM;
import static com.kelvin.jacksgogo.CustomView.Views.JGGActionbarView.EditStatus.ADD_TOOLS;
import static com.kelvin.jacksgogo.Utils.Global.APPOINTMENT_TYPE;
import static com.kelvin.jacksgogo.Utils.Global.JOBS;
import static com.kelvin.jacksgogo.Utils.Global.setBoldText;
import static com.kelvin.jacksgogo.Utils.JGGTimeManager.appointmentMonthDate;
import static com.kelvin.jacksgogo.Utils.JGGTimeManager.getDayMonthYear;
import static com.kelvin.jacksgogo.Utils.JGGTimeManager.getTimePeriodString;

public class JobReportMainFragment extends Fragment implements View.OnClickListener, TextWatcher {

    private OnFragmentInteractionListener mListener;
    private JobReportActivity mActivity;
    private Context mContext;

    private TextView lblStartTime;
    private TextView lblPickupAddress;
    private EditText txtComment;
    private LinearLayout beforeLayout;
    private LinearLayout btnTakeBeforePhoto;
    private RecyclerView beforeRecyclerView;
    private LinearLayout billableLayout;
    private TextView lblBillableDesc;
    private TextView lblBillableBudget;
    private LinearLayout approvedLayout;
    private TextView lblWaiting;
    private LinearLayout endJobLayout;
    private TextView lblPinTitle;
    private EditText txtPin;
    private EditText txtPinDesc;
    private LinearLayout afterLayout;
    private LinearLayout btnTakeAfterPhoto;
    private RecyclerView afterRecyclerView;
    private LinearLayout toolsLayout;
    private LinearLayout btnAddTools;
    private LinearLayout btnAddBillabe;
    private TextView btnSubmit;

    private JGGAppointmentModel mJob;
    private String billableDesc = "";
    private Double billableBudget;
    private ArrayList<AlbumFile> beforePhotoAlbums = new ArrayList<>();
    private JGGImageGalleryAdapter beforePhotoAdapter;
    private ArrayList<AlbumFile> afterPhotoAlbums = new ArrayList<>();
    private JGGImageGalleryAdapter afterPhotoAdapter;
    private View view;
    private boolean readyForSubmit;

    private String mUserType;

    public JobReportMainFragment() {
        // Required empty public constructor
    }

    public static JobReportMainFragment newInstance(String userType) {
        JobReportMainFragment fragment = new JobReportMainFragment();
        Bundle args = new Bundle();
        args.putString("jgg_usertype", userType);
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
        view = inflater.inflate(R.layout.fragment_job_report_main, container, false);
        mJob = JGGAppManager.getInstance().getSelectedAppointment();
        initView(view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!getUserVisibleHint()) {
            return;
        }
        onRefreshView();
    }

    private void initView(View view) {
        lblStartTime = view.findViewById(R.id.lbl_job_start_time);
        lblPickupAddress = view.findViewById(R.id.lbl_report_pickup);
        txtComment = view.findViewById(R.id.txt_start_job_comment);
        beforeLayout = view.findViewById(R.id.before_layout);
        billableLayout = view.findViewById(R.id.billable_layout);
        lblBillableDesc = view.findViewById(R.id.lbl_billable_desc);
        lblBillableBudget = view.findViewById(R.id.lbl_billable_budget);
        approvedLayout = view.findViewById(R.id.approved_layout);
        lblWaiting = view.findViewById(R.id.lbl_waiting_approval);
        endJobLayout = view.findViewById(R.id.end_layout);
        lblPinTitle = view.findViewById(R.id.lbl_pin_code_title);
        txtPin = view.findViewById(R.id.txt_pin_code);
        txtPinDesc = view.findViewById(R.id.txt_pin_code_desc);
        afterLayout = view.findViewById(R.id.after_photo_layout);
        toolsLayout = view.findViewById(R.id.tools_layout);
        btnAddTools = view.findViewById(R.id.btn_tools);
        btnAddBillabe = view.findViewById(R.id.btn_invoice);
        btnSubmit = view.findViewById(R.id.btn_submit_report);
        btnTakeBeforePhoto = view.findViewById(R.id.btn_take_before_photo);
        btnTakeAfterPhoto = view.findViewById(R.id.btn_take_after_photo);

        txtComment.addTextChangedListener(this);
        txtPin.addTextChangedListener(this);
        txtPinDesc.addTextChangedListener(this);
        btnTakeBeforePhoto.setOnClickListener(this);
        btnTakeAfterPhoto.setOnClickListener(this);
        btnAddTools.setOnClickListener(this);
        btnAddBillabe.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);
    }

    private void onRefreshView() {
        // Start time
        Date postOn = appointmentMonthDate(mJob.getPostOn());
        String postedTime = getDayMonthYear(postOn) + " " + getTimePeriodString(postOn);
        lblStartTime.setText(postedTime);
        lblPickupAddress.setText(mJob.getAddress().getFullAddress());

        // Before Photo view
        if (beforePhotoAlbums.size() > 0) {
        }

        // Billable view
        if (!billableDesc.equals("")) {
            billableLayout.setVisibility(View.VISIBLE);
            lblBillableDesc.setText(billableDesc);
            lblBillableBudget.setText("$ " + String.valueOf(billableBudget));
        }

        String pinCode = " has requested a PIN code. Your recipient should give you the PIN code.";
        String clientName = mJob.getUserProfile().getUser().getFullName();
        lblPinTitle.setText("");
        lblPinTitle.append(setBoldText(clientName));
        lblPinTitle.append(pinCode);
    }

    public void setBillableItem(String desc, Double budget) {
        billableDesc = desc;
        billableBudget = budget;
    }

    private void initRecyclerView(boolean isStartJob) {

        WindowManager wm = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        int width = display.getWidth();
        int itemSize = (width) / 4;

        if (isStartJob) {
            beforeRecyclerView = view.findViewById(R.id.start_mp_recycler_view);
            beforeRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 4));
            beforePhotoAdapter = new JGGImageGalleryAdapter(mContext, itemSize, false, new OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    if (position < beforePhotoAlbums.size()) {
                        String name = (String) beforePhotoAlbums.get(position).getPath();
                        Uri imageUri = Uri.parse(new File(name).toString());
                        Intent intent = new Intent(mContext, JGGImageCropActivity.class);
                        intent.putExtra("imageUri", name);
                        intent.putExtra(APPOINTMENT_TYPE, JOBS);
                        startActivityForResult(intent, 1);
                    } else {
                        selectImage(true);
                    }
                }
            });
            beforeRecyclerView.setAdapter(beforePhotoAdapter);
        } else {
            afterRecyclerView = view.findViewById(R.id.end_recycler_view);
            afterRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 4));
            afterPhotoAdapter = new JGGImageGalleryAdapter(mContext, itemSize, false, new OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    if (position < afterPhotoAlbums.size()) {
                        String name = (String) afterPhotoAlbums.get(position).getPath();
                        Uri imageUri = Uri.parse(new File(name).toString());
                        Intent intent = new Intent(mContext, JGGImageCropActivity.class);
                        intent.putExtra("imageUri", name);
                        intent.putExtra(APPOINTMENT_TYPE, JOBS);
                        startActivityForResult(intent, 1);
                    } else {
                        selectImage(false);
                    }
                }
            });
            beforeRecyclerView.setAdapter(beforePhotoAdapter);
        }
    }

    private void selectImage(final boolean isStartJob) {
        int mColor = ContextCompat.getColor(mContext, R.color.JGGCyan);
        ArrayList<AlbumFile> mAlbumFiles;
        if (isStartJob) mAlbumFiles = beforePhotoAlbums;
        else mAlbumFiles = afterPhotoAlbums;
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
                        if (isStartJob) {
                            beforePhotoAlbums = result;
                            if (beforePhotoAlbums.size() > 0)
                                btnTakeBeforePhoto.setVisibility(View.GONE);
                            else
                                btnTakeBeforePhoto.setVisibility(View.VISIBLE);
                            initRecyclerView(true);
                            beforePhotoAdapter.notifyDataChanged(beforePhotoAlbums);
                        } else {
                            afterPhotoAlbums = result;
                            if (afterPhotoAlbums.size() > 0)
                                btnTakeAfterPhoto.setVisibility(View.GONE);
                            else
                                btnTakeAfterPhoto.setVisibility(View.VISIBLE);
                            initRecyclerView(false);
                            afterPhotoAdapter.notifyDataChanged(afterPhotoAlbums);
                        }
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
        if (view.getId() == R.id.btn_tools) {
            mActivity.setActionbarView(ADD_TOOLS);
            mActivity.getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.job_report_container, new JobReportAddToolsFragment())
                    .commit();
        } else if (view.getId() == R.id.btn_invoice) {
            mActivity.setActionbarView(ADD_BILLABLE_ITEM);
            mActivity.getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.job_report_container, new JobReportBillableItemFragment())
                    .commit();
        } else if (view.getId() == R.id.btn_take_before_photo) {
            selectImage(true);
        } else if (view.getId() == R.id.btn_take_after_photo) {
            selectImage(false);
            btnTakeAfterPhoto.setVisibility(View.GONE);
        } else if (view.getId() == R.id.btn_submit_report) {
            if (readyForSubmit) {

            } else {
                toolsLayout.setVisibility(View.GONE);
                btnSubmit.setVisibility(View.GONE);
                endJobLayout.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if (txtPin.length() > 0) {
            txtPinDesc.setVisibility(View.VISIBLE);
            readyForSubmit = true;
            afterLayout.setVisibility(View.VISIBLE);
            toolsLayout.setVisibility(View.VISIBLE);
            btnSubmit.setVisibility(View.VISIBLE);
            btnSubmit.setText("Submit Report");
        } else if (txtPin.getText().toString().equals("")) {
            txtPinDesc.setVisibility(View.GONE);
            readyForSubmit = false;
            afterLayout.setVisibility(View.GONE);
            toolsLayout.setVisibility(View.GONE);
            btnSubmit.setVisibility(View.GONE);
        }
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
        if (getArguments() != null) {
            mUserType = getArguments().getString("jgg_usertype");
        }
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
