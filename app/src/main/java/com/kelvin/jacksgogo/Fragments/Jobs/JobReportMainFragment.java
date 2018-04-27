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
import com.kelvin.jacksgogo.Utils.Global;
import com.kelvin.jacksgogo.Utils.JGGAppManager;
import com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events.JGGAppointmentModel;
import com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events.JGGBillableModel;
import com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events.JGGReportResultModel;
import com.yanzhenjie.album.Action;
import com.yanzhenjie.album.Album;
import com.yanzhenjie.album.AlbumFile;
import com.yanzhenjie.album.api.widget.Widget;
import com.yanzhenjie.album.impl.OnItemClickListener;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;

import static com.kelvin.jacksgogo.CustomView.Views.JGGActionbarView.EditStatus.ADD_BILLABLE_ITEM;
import static com.kelvin.jacksgogo.CustomView.Views.JGGActionbarView.EditStatus.ADD_TOOLS;
import static com.kelvin.jacksgogo.Utils.Global.APPOINTMENT_TYPE;
import static com.kelvin.jacksgogo.Utils.Global.JOBS;
import static com.kelvin.jacksgogo.Utils.Global.setBoldText;
import static com.kelvin.jacksgogo.Utils.JGGTimeManager.appointmentMonthDate;
import static com.kelvin.jacksgogo.Utils.JGGTimeManager.getDayMonthYear;
import static com.kelvin.jacksgogo.Utils.JGGTimeManager.getTimePeriodString;

public class JobReportMainFragment extends Fragment implements TextWatcher {

    private OnFragmentInteractionListener mListener;
    private JobReportActivity mActivity;
    private Context mContext;

    @BindView(R.id.lbl_job_start_time)              TextView txtStartTime;
    @BindView(R.id.lbl_report_pickup)               TextView txtPickupAddress;
    @BindView(R.id.txt_start_job_comment)           EditText editComment;
    @BindView(R.id.before_layout)                   LinearLayout beforeLayout;
    @BindView(R.id.btn_take_before_photo)           LinearLayout llTakeBeforePhoto;
    @BindView(R.id.start_mp_recycler_view)          RecyclerView beforeRecyclerView;
    @BindView(R.id.billable_layout)                 LinearLayout billableLayout;
    @BindView(R.id.lbl_billable_desc)               TextView txtBillableDesc;
    @BindView(R.id.lbl_billable_budget)             TextView txtBillableBudget;
    @BindView(R.id.approved_layout)                 LinearLayout approvedLayout;
    @BindView(R.id.lbl_waiting_approval)            TextView txtWaiting;

    @BindView(R.id.end_layout)                      LinearLayout endJobLayout;
    @BindView(R.id.lbl_end_job_time)                TextView txtEndJobTime;
    @BindView(R.id.lbl_pin_code_title)              TextView txtPinTitle;
    @BindView(R.id.txt_pin_code)                    EditText editPin;
    @BindView(R.id.txt_pin_code_desc)               EditText editPinDesc;
    @BindView(R.id.after_photo_layout)              LinearLayout afterLayout;
    @BindView(R.id.btn_take_after_photo)            LinearLayout llTakeAfterPhoto;
    @BindView(R.id.end_recycler_view)               RecyclerView afterRecyclerView;
    @BindView(R.id.tools_layout)                    LinearLayout toolsLayout;
    @BindView(R.id.btn_tools)                       LinearLayout llAddTools;
    @BindView(R.id.btn_invoice)                     LinearLayout llAddBillabe;
    @BindView(R.id.btn_submit_report)               TextView txtSubmit;
    @BindView(R.id.lbl_request_time)                TextView txtRequestTime;

    private JGGAppointmentModel mJob;
    private ArrayList<AlbumFile> beforePhotoAlbums = new ArrayList<>();
    private JGGImageGalleryAdapter beforePhotoAdapter;
    private ArrayList<AlbumFile> afterPhotoAlbums = new ArrayList<>();
    private JGGImageGalleryAdapter afterPhotoAdapter;
    private View view;
    private boolean readyForSubmit;

    private String mUserType;
    private JGGReportResultModel mReportResultModel;

    private boolean isBeforePhoto;


    private boolean isReportBeforeAfterPhoto;
    private boolean isReportGeotracking;
    private boolean isReportPinCode;

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
            mUserType = getArguments().getString("jgg_usertype");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_job_report_main, container, false);
        mJob = JGGAppManager.getInstance().getSelectedAppointment();
        mReportResultModel = JGGAppManager.getInstance().getReportResultModel();

        checkReportType();
        initView(view);
        return view;
    }

    private void checkReportType() {
        int reportType = mJob.getReportType();
        switch (reportType) {
            case 1:
                isReportBeforeAfterPhoto = true;
                break;
            case 2:
                isReportGeotracking = true;
                break;
            case 3:
                isReportBeforeAfterPhoto = true;
                isReportGeotracking = true;
                break;
            case 4:
                isReportPinCode = true;
                break;
            case 5:
                isReportBeforeAfterPhoto = true;
                isReportPinCode = true;
                break;
            case 6:
                isReportGeotracking = true;
                isReportPinCode = true;
                break;
            case 7:
                isReportBeforeAfterPhoto = true;
                isReportPinCode = true;
                break;
            default:
                isReportBeforeAfterPhoto = false;
                isReportGeotracking = false;
                isReportPinCode = false;
                break;
        }
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
        endJobLayout.setVisibility(View.GONE);

        editComment.addTextChangedListener(this);
        editPin.addTextChangedListener(this);
        editPinDesc.addTextChangedListener(this);
    }

    private void onRefreshView() {
        // Start time
        Date postOn = appointmentMonthDate(mJob.getPostOn());
        String postedTime = getDayMonthYear(postOn) + " " + getTimePeriodString(postOn);
        txtStartTime.setText(postedTime);
        txtPickupAddress.setText(mJob.getAddress().getFullAddress());

        // TODO - BeforePhoto View
        if (mReportResultModel.getBeforeAlbumFiles() != null) {
            if (mReportResultModel.getBeforeAlbumFiles().size() > 0) {
                beforePhotoAlbums = mReportResultModel.getBeforeAlbumFiles();
                beforePhotoAdapter.notifyDataChanged(beforePhotoAlbums);
            }
        }

        // TODO - AfterPhoto View
        if (mReportResultModel.getAfterAlbumFiles() != null) {
            if (mReportResultModel.getAfterAlbumFiles().size() > 0) {
                afterPhotoAlbums = mReportResultModel.getAfterAlbumFiles();
                afterPhotoAdapter.notifyDataChanged(afterPhotoAlbums);
            }
        }

        // TODO - Billable View
        if (mReportResultModel.getBillableModel() != null) {
            JGGBillableModel billableModel = mReportResultModel.getBillableModel();
            billableLayout.setVisibility(View.VISIBLE);
            txtBillableDesc.setText(billableModel.getItemDescription());
            txtBillableBudget.setText("$ " + String.valueOf(billableModel.getPrice()));
            txtRequestTime.setText(billableModel.getRequestSentDate());
        } else {
            billableLayout.setVisibility(View.GONE);
        }


        // TODO - PIN code View
        String pinCode = getString(R.string.pin_request);

        String clientName = mJob.getUserProfile().getUser().getFullName();
        txtPinTitle.setText("");
        txtPinTitle.append(setBoldText(clientName));
        txtPinTitle.append(pinCode);
    }

    private void initRecyclerView(boolean isStartJob) {

        WindowManager wm = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        int width = display.getWidth();
        int itemSize = (width) / 4;

        if (isStartJob) {
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
                                llTakeBeforePhoto.setVisibility(View.GONE);
                            else
                                llTakeBeforePhoto.setVisibility(View.VISIBLE);
                            initRecyclerView(true);
                            beforePhotoAdapter.notifyDataChanged(beforePhotoAlbums);
                        } else {
                            afterPhotoAlbums = result;
                            if (afterPhotoAlbums.size() > 0)
                                llTakeAfterPhoto.setVisibility(View.GONE);
                            else
                                llTakeAfterPhoto.setVisibility(View.VISIBLE);
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

    @OnClick(R.id.btn_tools)
    public void onClickAddTools() {
        if (isBeforePhoto) {
            mReportResultModel.setBeforeAlbumFiles(beforePhotoAlbums);
            mReportResultModel.setBeforeComment(editComment.getText().toString());
        } else {
            mReportResultModel.setAfterAlbumFiles(afterPhotoAlbums);
            mReportResultModel.setAfterComment(editComment.getText().toString());
        }

        mActivity.setActionbarView(ADD_TOOLS);
        mActivity.getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.job_report_container, new JobReportAddToolsFragment())
                .commit();
    }

    @OnClick(R.id.btn_invoice)
    public void onClickAddBillableItem() {
        mActivity.setActionbarView(ADD_BILLABLE_ITEM);
        mActivity.getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.job_report_container, new JobReportBillableItemFragment())
                .commit();
    }

    @OnClick(R.id.btn_take_before_photo)
    public void onClickBeforePhoto() {
        isBeforePhoto = true;
        selectImage(true);
    }

    @OnClick(R.id.btn_take_after_photo)
    public void onClickAfterPhoto() {
        isBeforePhoto = false;
        selectImage(false);
        llTakeAfterPhoto.setVisibility(View.GONE);
    }

    @OnClick(R.id.btn_submit_report)
    public void onClickSubmitReport() {
        if (readyForSubmit) {

        } else {
            toolsLayout.setVisibility(View.GONE);
            txtSubmit.setVisibility(View.GONE);


            endJobLayout.setVisibility(View.VISIBLE);

            Date now = new Date();
            SimpleDateFormat simpleDateFormat =
                    new SimpleDateFormat("MMM dd, yyyy h:mm a");

            String endTime = simpleDateFormat.format(now);
            mReportResultModel.setEndJobDate(endTime);
        }
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if (editPin.length() > 0) {
            editPinDesc.setVisibility(View.VISIBLE);
            readyForSubmit = true;
            afterLayout.setVisibility(View.VISIBLE);
            toolsLayout.setVisibility(View.VISIBLE);
            txtSubmit.setVisibility(View.VISIBLE);
            txtSubmit.setText("Submit Report");
        } else if (editPin.getText().toString().equals("")) {
            editPinDesc.setVisibility(View.GONE);
            readyForSubmit = false;
            afterLayout.setVisibility(View.GONE);
            toolsLayout.setVisibility(View.GONE);
            txtSubmit.setVisibility(View.GONE);
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
