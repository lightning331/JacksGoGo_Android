package com.kelvin.jacksgogo.Fragments.Jobs;

import android.app.AlertDialog;
import android.app.ProgressDialog;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kelvin.jacksgogo.Activities.Jobs.JobReportActivity;
import com.kelvin.jacksgogo.Activities.Search.JGGImageCropActivity;
import com.kelvin.jacksgogo.Adapter.Services.JGGImageGalleryAdapter;
import com.kelvin.jacksgogo.CustomView.Views.JGGAlertView;
import com.kelvin.jacksgogo.R;
import com.kelvin.jacksgogo.Utils.API.JGGAPIManager;
import com.kelvin.jacksgogo.Utils.API.JGGURLManager;
import com.kelvin.jacksgogo.Utils.Global;
import com.kelvin.jacksgogo.Utils.JGGAppManager;
import com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events.JGGAppointmentModel;
import com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events.JGGBillableModel;
import com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events.JGGReportResultModel;
import com.kelvin.jacksgogo.Utils.Responses.JGGPostAppResponse;
import com.kelvin.jacksgogo.Utils.Responses.JGGReportResultResponse;
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
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Field;

import static com.kelvin.jacksgogo.CustomView.Views.JGGActionbarView.EditStatus.ADD_BILLABLE_ITEM;
import static com.kelvin.jacksgogo.CustomView.Views.JGGActionbarView.EditStatus.ADD_TOOLS;
import static com.kelvin.jacksgogo.Utils.Global.APPOINTMENT_TYPE;
import static com.kelvin.jacksgogo.Utils.Global.JOBS;
import static com.kelvin.jacksgogo.Utils.Global.PostStatus.EDIT;
import static com.kelvin.jacksgogo.Utils.Global.PostStatus.POST;
import static com.kelvin.jacksgogo.Utils.Global.createProgressDialog;
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
    @BindView(R.id.txt_start_job_comment)           EditText editBeforeComment;
    @BindView(R.id.before_layout)                   LinearLayout beforeLayout;
    @BindView(R.id.btn_take_before_photo)           LinearLayout llTakeBeforePhoto;
    @BindView(R.id.start_mp_recycler_view)          RecyclerView beforeRecyclerView;
    @BindView(R.id.billable_layout)                 LinearLayout billableLayout;
    @BindView(R.id.lbl_billable_desc)               TextView txtBillableDesc;
    @BindView(R.id.lbl_billable_budget)             TextView txtBillableBudget;
    @BindView(R.id.approved_layout)                 LinearLayout approvedLayout;
    @BindView(R.id.lbl_waiting_approval)            TextView txtWaiting;

    @BindView(R.id.end_layout)                      LinearLayout endLayout;
    @BindView(R.id.end_job_layout)                  LinearLayout endJobLayout;
    @BindView(R.id.pincode_layout)                  LinearLayout pincodeLayout;
    @BindView(R.id.lbl_end_job_time)                TextView txtEndJobTime;
    @BindView(R.id.lbl_pin_code_title)              TextView txtPinTitle;
    @BindView(R.id.txt_pin_code)                    EditText editPin;

    @BindView(R.id.txt_after_comment)               EditText editAfterComment;
    @BindView(R.id.after_photo_layout)              LinearLayout afterLayout;
    @BindView(R.id.btn_take_after_photo)            LinearLayout llTakeAfterPhoto;
    @BindView(R.id.end_recycler_view)               RecyclerView afterRecyclerView;
    @BindView(R.id.tools_layout)                    LinearLayout toolsLayout;
    @BindView(R.id.btn_tools)                       LinearLayout llAddTools;
    @BindView(R.id.btn_invoice)                     LinearLayout llAddBillabe;
    @BindView(R.id.btn_submit_report)               Button btnSubmit;
    @BindView(R.id.lbl_request_time)                TextView txtRequestTime;

    private JGGAppointmentModel mJob;
    private ArrayList<AlbumFile> beforePhotoAlbums = new ArrayList<>();
    private JGGImageGalleryAdapter beforePhotoAdapter;
    private ArrayList<String> beforeAttachmentURLs = new ArrayList<>();

    private ArrayList<AlbumFile> afterPhotoAlbums = new ArrayList<>();
    private JGGImageGalleryAdapter afterPhotoAdapter;
    private ArrayList<String> afterAttachmentURLs = new ArrayList<>();
    private View view;
    private boolean readyForSubmit;

    private String mUserType;
    private JGGReportResultModel mReportResultModel;

    private boolean isBeforePhoto;

    private boolean isReportBeforeAfterPhoto;
    private boolean isReportGeotracking;
    private boolean isReportPinCode;

    private AlertDialog alertDialog;
    private ProgressDialog progressDialog;

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
        ButterKnife.bind(this, view);

        mJob = JGGAppManager.getInstance().getSelectedAppointment();
        mReportResultModel = JGGAppManager.getInstance().getReportResultModel();

        checkReportType();
        initView();
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
        initRecyclerView();
        onRefreshView();
    }

    private void initView() {
        if (isReportBeforeAfterPhoto) {
            beforeLayout.setVisibility(View.VISIBLE);
        } else {
            beforeLayout.setVisibility(View.GONE);
        }
        endLayout.setVisibility(View.GONE);

        editBeforeComment.addTextChangedListener(this);
        editAfterComment.addTextChangedListener(this);
        editPin.addTextChangedListener(this);
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

    private void initRecyclerView() {

        WindowManager wm = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        int width = display.getWidth();
        int itemSize = (width) / 4;

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
                    selectBeforeImage();
                }
            }
        });
        beforeRecyclerView.setAdapter(beforePhotoAdapter);
        if (mReportResultModel.getBeforeAlbumFiles() != null){
            if (mReportResultModel.getBeforeAlbumFiles().size() > 0) {
                beforePhotoAdapter.notifyDataChanged(mReportResultModel.getBeforeAlbumFiles());
                llTakeBeforePhoto.setVisibility(View.GONE);
            } else {
                llTakeBeforePhoto.setVisibility(View.VISIBLE);
            }
        } else {
            llTakeBeforePhoto.setVisibility(View.VISIBLE);
        }


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
                    selectAfterImage();
                }
            }
        });
        afterRecyclerView.setAdapter(afterPhotoAdapter);
        if (mReportResultModel.getAfterAlbumFiles() != null) {
            if (mReportResultModel.getAfterAlbumFiles().size() > 0) {
                afterPhotoAdapter.notifyDataChanged(mReportResultModel.getAfterAlbumFiles());
                llTakeAfterPhoto.setVisibility(View.GONE);
            } else {
                llTakeAfterPhoto.setVisibility(View.VISIBLE);
            }
        } else {
            llTakeAfterPhoto.setVisibility(View.VISIBLE);
        }
    }

    private void selectBeforeImage() {
        int mColor = ContextCompat.getColor(mContext, R.color.JGGCyan);
        ArrayList<AlbumFile> mAlbumFiles;
        mAlbumFiles = beforePhotoAlbums;

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
                        beforePhotoAlbums = result;
                        mReportResultModel.setBeforeAlbumFiles(result);

                        if (beforePhotoAlbums.size() > 0)
                            llTakeBeforePhoto.setVisibility(View.GONE);
                        else
                            llTakeBeforePhoto.setVisibility(View.VISIBLE);
                        beforePhotoAdapter.notifyDataChanged(beforePhotoAlbums);
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
    private void selectAfterImage() {
        int mColor = ContextCompat.getColor(mContext, R.color.JGGCyan);
        ArrayList<AlbumFile> mAlbumFiles;
        mAlbumFiles = afterPhotoAlbums;

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
                        afterPhotoAlbums = result;
                        mReportResultModel.setAfterAlbumFiles(result);

                        if (afterPhotoAlbums.size() > 0)
                            llTakeAfterPhoto.setVisibility(View.GONE);
                        else
                            llTakeAfterPhoto.setVisibility(View.VISIBLE);
                        afterPhotoAdapter.notifyDataChanged(afterPhotoAlbums);
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

    private void onShowSubmitReportDialog() {
        String msgDescription = "";
        String clientName = mJob.getUserProfile().getUser().getFullName();
        msgDescription = setBoldText(clientName) + " " + mContext.getResources().getString(R.string.msg_job_complete);
        JGGAlertView builder = new JGGAlertView(mContext,
                mContext.getResources().getString(R.string.alert_job_completed),
                msgDescription,
                false,
                mContext.getResources().getString(R.string.alert_cancel),
                R.color.JGGCyan,
                R.color.JGGCyan10Percent,
                mContext.getResources().getString(R.string.alert_ok),
                R.color.JGGCyan);
        alertDialog = builder.create();
        builder.setOnItemClickListener(new JGGAlertView.OnItemClickListener() {
            @Override
            public void onDoneButtonClick(View view) {
                if (view.getId() == R.id.btn_alert_cancel)
                    alertDialog.dismiss();
                else {
                    alertDialog.dismiss();

                    onUploadImages();
                }
            }
        });
        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.show();
    }

    private void uploadBeforeImages(final int index) {
        if (beforePhotoAlbums == null) {
            uploadAfterImages(0);
        } else {
            if (index < beforePhotoAlbums.size()) {
                String name = (String) beforePhotoAlbums.get(index).getPath();
                Uri imageUri = Uri.parse(new File(name).toString());
                File file = new File(String.valueOf(imageUri));

                // Parsing any Media type file
                RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
                MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
                RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), file.getName());

                progressDialog = Global.createProgressDialog(mContext);

                JGGAPIManager manager = JGGURLManager.createService(JGGAPIManager.class, mContext);
                Call<JGGPostAppResponse> call = manager.uploadAttachmentFile(fileToUpload);
                call.enqueue(new Callback<JGGPostAppResponse>() {
                    @Override
                    public void onResponse(Call<JGGPostAppResponse> call, Response<JGGPostAppResponse> response) {
                        progressDialog.dismiss();
                        if (response.isSuccessful()) {
                            if (response.body().getSuccess()) {
                                String url = response.body().getValue();
                                beforeAttachmentURLs.add(url);
                                uploadBeforeImages(index + 1);
                            } else {
                                btnSubmit.setClickable(true);
                                Toast.makeText(mContext, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            btnSubmit.setClickable(true);
                            int statusCode = response.code();
                            Toast.makeText(mContext, response.message(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<JGGPostAppResponse> call, Throwable t) {
                        progressDialog.dismiss();
                        btnSubmit.setClickable(true);
                        Toast.makeText(mContext, "Request time out!", Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                if (beforePhotoAlbums.size() > 0) {
                    mReportResultModel.setBeforePhotoURLs(beforeAttachmentURLs);
                    mReportResultModel.setPhoto(true);
                }
                uploadAfterImages(0);
            }
        }
    }

    private void uploadAfterImages(final int index) {
        if (afterPhotoAlbums == null) {
            onSubmitReport();
        } else {
            if (index < afterPhotoAlbums.size()) {
                String name = (String) afterPhotoAlbums.get(index).getPath();
                Uri imageUri = Uri.parse(new File(name).toString());
                File file = new File(String.valueOf(imageUri));

                // Parsing any Media type file
                RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
                MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
                RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), file.getName());

                progressDialog = Global.createProgressDialog(mContext);

                JGGAPIManager manager = JGGURLManager.createService(JGGAPIManager.class, mContext);
                Call<JGGPostAppResponse> call = manager.uploadAttachmentFile(fileToUpload);
                call.enqueue(new Callback<JGGPostAppResponse>() {
                    @Override
                    public void onResponse(Call<JGGPostAppResponse> call, Response<JGGPostAppResponse> response) {
                        progressDialog.dismiss();
                        if (response.isSuccessful()) {
                            if (response.body().getSuccess()) {
                                String url = response.body().getValue();
                                afterAttachmentURLs.add(url);
                                uploadAfterImages(index + 1);
                            } else {
                                btnSubmit.setClickable(true);
                                Toast.makeText(mContext, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            btnSubmit.setClickable(true);
                            int statusCode = response.code();
                            Toast.makeText(mContext, response.message(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<JGGPostAppResponse> call, Throwable t) {
                        progressDialog.dismiss();
                        btnSubmit.setClickable(true);
                        Toast.makeText(mContext, "Request time out!", Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                if (afterPhotoAlbums.size() > 0) {
                    mReportResultModel.setAfterPhotoURLs(afterAttachmentURLs);
                    mReportResultModel.setPhoto(true);
                }
                onSubmitReport();
            }
        }
    }

    private void onUploadImages() {
        btnSubmit.setClickable(false);
        uploadBeforeImages(0);
    }


    // TODO - submit report api integration
    private void onSubmitReport() {
        progressDialog = createProgressDialog(mContext);

        JGGAPIManager apiManager = JGGURLManager.createService(JGGAPIManager.class, mContext);
        Call<JGGReportResultResponse> call = apiManager.reportResult(mReportResultModel);
        call.enqueue(new Callback<JGGReportResultResponse>() {
            @Override
            public void onResponse(Call<JGGReportResultResponse> call, Response<JGGReportResultResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    if (response.body().getSuccess()) {
//                        onShowReportActivity();
                        String reportID = response.body().getValue();
                        mReportResultModel.setID(reportID);

                        mActivity.finish();
                    } else {
                        btnSubmit.setClickable(true);
                        Toast.makeText(mContext, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    btnSubmit.setClickable(true);
                    Toast.makeText(mContext, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JGGReportResultResponse> call, Throwable t) {
                progressDialog.dismiss();
                btnSubmit.setClickable(true);
                Toast.makeText(mContext, "Request time out!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @OnClick(R.id.btn_tools)
    public void onClickAddTools() {
        if (isBeforePhoto) {
            mReportResultModel.setBeforeAlbumFiles(beforePhotoAlbums);
        } else {
            mReportResultModel.setAfterAlbumFiles(afterPhotoAlbums);
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
        selectBeforeImage();
    }

    @OnClick(R.id.btn_take_after_photo)
    public void onClickAfterPhoto() {
        isBeforePhoto = false;
        selectAfterImage();
        llTakeAfterPhoto.setVisibility(View.GONE);
    }

    @OnClick(R.id.btn_submit_report)
    public void onClickSubmitReport() {
        if (readyForSubmit) {
            onShowSubmitReportDialog();
        } else {
            toolsLayout.setVisibility(View.GONE);
            btnSubmit.setVisibility(View.GONE);

            endLayout.setVisibility(View.VISIBLE);
            if (isReportPinCode) {
                pincodeLayout.setVisibility(View.VISIBLE);
            } else {
                readyForSubmit = true;
                pincodeLayout.setVisibility(View.GONE);
                afterLayout.setVisibility(View.VISIBLE);
                toolsLayout.setVisibility(View.VISIBLE);
                btnSubmit.setVisibility(View.VISIBLE);
                btnSubmit.setText(R.string.submit_report);
            }

            // TODO - set Job End Time
            Date now = new Date();
            SimpleDateFormat simpleDateFormat =
                    new SimpleDateFormat("MMM dd, yyyy h:mm a");

            String endTime = simpleDateFormat.format(now);
            mReportResultModel.setEndJobDate(endTime);
        }
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        if (editable == editPin.getEditableText()) {
            if (editPin.length() > 0) {
                mReportResultModel.setPinCode(editPin.getText().toString());

                editAfterComment.setVisibility(View.VISIBLE);
                readyForSubmit = true;
                afterLayout.setVisibility(View.VISIBLE);
                toolsLayout.setVisibility(View.VISIBLE);
                btnSubmit.setVisibility(View.VISIBLE);
                btnSubmit.setText(R.string.submit_report);
            } else {
                editAfterComment.setVisibility(View.GONE);
                readyForSubmit = false;
                afterLayout.setVisibility(View.GONE);
                toolsLayout.setVisibility(View.GONE);
                btnSubmit.setVisibility(View.GONE);
            }
        }
        else if (editable == editAfterComment.getEditableText()) {
            mReportResultModel.setAfterComment(editAfterComment.getText().toString());
        }
        else if (editable == editBeforeComment.getEditableText()) {
            mReportResultModel.setBeforeComment(editBeforeComment.getText().toString());
        }
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
