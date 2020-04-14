package com.dies.lionbuilding;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toolbar;

import com.dies.lionbuilding.TimeLine.Helper;
import com.dies.lionbuilding.TimeLine.TimelineRow;
import com.dies.lionbuilding.activity.MultiRouteViewMap;
import com.dies.lionbuilding.activity.RouteManagement.RouteViewActivity;
import com.dies.lionbuilding.adapter.RouteAdapter;
import com.dies.lionbuilding.apiservice.ApiConstants;
import com.dies.lionbuilding.apiservice.ApiService;
import com.dies.lionbuilding.apiservice.ApiServiceCreator;
import com.dies.lionbuilding.application.SessionManager;
import com.dies.lionbuilding.application.Utility;
import com.dies.lionbuilding.model.ExpanseModel;
import com.dies.lionbuilding.model.RouteModel;
import com.dies.lionbuilding.model.UserDataResponse;
import com.dies.lionbuilding.util.DisplayMetricsHandler;
import com.google.android.gms.location.LocationRequest;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.zelory.compressor.Compressor;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Observable;
import rx.Observer;
import rx.schedulers.Schedulers;

public class ShopTimelineViewActivity extends AppCompatActivity {

    @BindView(R.id.timeline_listView)
    ListView timeline_listView;
    @BindView(R.id.toolbar_Title)
    TextView toolbar_Title;
    @BindView(R.id.back_icon)
    ImageView back_icon;
    @BindView(R.id.btn_view_map)
    Button btn_view_map;

    @BindView(R.id.txt_route_name)
    TextView txt_route_name;

    @BindView(R.id.txt_date)
    TextView date;

    SessionManager sessionManager;
    ApiService apiservice;
    ProgressDialog pDialog;
    RecyclerView.LayoutManager layoutManager;
    int statusCode;

    ImageView img_select;


    List<RouteModel.Data> arrayList;
    ArrayList<TimelineRow> timelineRowsList;
    List<RouteModel.Data> arrayListdata;
    TimelineViewAdapter myAdapter;

    //ArrayList<String> arrayListlatlang=new ArrayList<>();
    String mediaPath;
    TimelineRow myRow;
    String d_idd, status;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_timeline_view);
        ButterKnife.bind(this);
        // arrayList=Utility.getAppcon().getSession().arrayListRoute;
        arrayListdata = new ArrayList<>();
        sessionManager = new SessionManager(this);
        apiservice = ApiServiceCreator.createService("latest");

        toolbar_Title.setText("Today Route");
        if (sessionManager.getKeyRoll().equals("RM")) {
            getRmRoute();
        } else {
            getRoute();
        }

        String datedate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        date.setText(datedate);

        back_icon.setOnClickListener(view -> {
            finish();
        });


        btn_view_map.setOnClickListener(view -> {
            Intent intent = new Intent(this, MultiRouteViewMap.class);
            startActivity(intent);
        });


//        arrayListdata=arrayList;
//        for(int i=0;i<arrayList.get(0).getRoute_data().size();i++){
//            timelineRowsList.add(createRandomTimelineRow(i,arrayListdata));
//        }
//        myAdapter = new TimelineViewAdapter(this, 0, timelineRowsList,
//                true);
//        timeline_listView.setAdapter(myAdapter);


        // Helper.getListViewSize(timeline_listView);
    }

    private TimelineRow createRandomTimelineRow(int id, List<RouteModel.Data> list) {


        try {
            //url = new URL("http://diestechnology.com.au/projects/pets4ever-admin/uploads/pet/life_event/cropped3926545454188139230.jpg");
            //ApiConstants.IMAGE_URL+list.get(id).getLife_event_image()
            //  "http://diestechnology.com.au/projects/pets4ever-admin/uploads/pet/life_event/cropped3926545454188139230.jpg"
            //image = BitmapFactory.decodeStream(url.openConnection().getInputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Create new timeline row (pass your Id)
        myRow = new TimelineRow(id);

        //to set the row Date (optional)
        //  myRow.setDate(list.get(id).getPple_year());
        //to set the row Title (optional)
        myRow.setTitle(list.get(0).getRoute_data().get(id).getShop_name());
        myRow.setId(Integer.parseInt(list.get(0).getRoute_data().get(id).getUserId()));

        myRow.setVisit(list.get(0).getRoute_data().get(id).getVisited_status());
        //to set the row Description (optional)
        myRow.setDescription(list.get(0).getRoute_data().get(id).getSl_address_line1() + "\n" + list.get(0).getRoute_data().get(id).getSl_address_line2() + "\n" + list.get(0).getRoute_data().get(id).getSl_pincode());
        //to set the row bitmap image (optional)
        //myRow.setImage(ApiConstants.IMAGE_URL+list.get(id).getLife_event_image());
        //BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher + getRandomNumber(0, 10))
        //to set row Below Line Color (optional)
        myRow.setBellowLineColor(getResources().getColor(R.color.colorDashBoard));
        //to set row Below Line Size in dp (optional)
        //  myRow.setBellowLineSize(10);
        //to set row Image Size in dp (optional)
        myRow.setImageSize(35);
        //to set background color of the row image (optional)
        //  myRow.setBackgroundColor(getRandomColor());
        //to set the Background Size of the row image in dp (optional)
        myRow.setBackgroundSize(getRandomNumber(25, 40));
        //to set row Date text color (optional)
        myRow.setDateColor(getResources().getColor(R.color.colorBlack));
        //myRow.setDateColor(getRandomColor());
        //to set row Title text color (optional)
        myRow.setTitleColor(getResources().getColor(R.color.colorblack));
        //myRow.setTitleColor(getRandomColor());
        //to set row Description text color (optional)
        myRow.setDescriptionColor(getResources().getColor(R.color.gray));

        return myRow;
    }

    public int getRandomColor() {
        Random rnd = new Random();
        int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
        ;
        return color;
    }

    public int getRandomNumber(int min, int max) {
        return min + (int) (Math.random() * max);
    }


    private void getRmRoute() {

        pDialog = new ProgressDialog(this);
        pDialog.setTitle("Checking Data");
        pDialog.setMessage("Please Wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();

        Observable<RouteModel> responseObservable = apiservice.getRmAllRoute(sessionManager.getKeyId());

        responseObservable.subscribeOn(Schedulers.newThread())
                .observeOn(rx.android.schedulers.AndroidSchedulers.mainThread())
                .onErrorResumeNext(throwable -> {
                    if (throwable instanceof retrofit2.HttpException) {
                        retrofit2.HttpException ex = (retrofit2.HttpException) throwable;
                        statusCode = ex.code();
                        Log.e("error", ex.getLocalizedMessage());
                    } else if (throwable instanceof SocketTimeoutException) {
                        statusCode = 1000;
                    }
                    return Observable.empty();
                })
                .subscribe(new Observer<RouteModel>() {
                    @Override
                    public void onCompleted() {
                        pDialog.dismiss();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("error", "" + e.getMessage());
                    }

                    @Override
                    public void onNext(RouteModel routeModel) {
                        statusCode = routeModel.getStatusCode();
                        if (statusCode == 200) {

                            arrayListdata = routeModel.getData();
                            txt_route_name.setText(routeModel.getData().get(0).getRoute_name());
                            timelineRowsList = new ArrayList<>();
                            for (int i = 0; i < routeModel.getData().get(0).getRoute_data().size(); i++) {
                                timelineRowsList.add(createRandomTimelineRow(i, arrayListdata));
                            }
                            myAdapter = new TimelineViewAdapter(ShopTimelineViewActivity.this, 0, timelineRowsList,
                                    true);
                            timeline_listView.setAdapter(myAdapter);

//                            RouteAdapter routeAdapter=new RouteAdapter(RouteViewActivity.this,routeModel.getData());
//                            rcv_route.setAdapter(routeAdapter);
                        }
                    }
                });

    }

    public void getRoute() {
//        rcv_route.setHasFixedSize(true);
//        layoutManager = new LinearLayoutManager(this);
//        rcv_route.setLayoutManager(layoutManager);
        pDialog = new ProgressDialog(this);
        pDialog.setTitle("Checking Data");
        pDialog.setMessage("Please Wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();

        Observable<RouteModel> responseObservable = apiservice.getAllRoute(sessionManager.getKeyId());

        responseObservable.subscribeOn(Schedulers.newThread())
                .observeOn(rx.android.schedulers.AndroidSchedulers.mainThread())
                .onErrorResumeNext(throwable -> {
                    if (throwable instanceof retrofit2.HttpException) {
                        retrofit2.HttpException ex = (retrofit2.HttpException) throwable;
                        statusCode = ex.code();
                        Log.e("error", ex.getLocalizedMessage());
                    } else if (throwable instanceof SocketTimeoutException) {
                        statusCode = 1000;
                    }
                    return Observable.empty();
                })
                .subscribe(new Observer<RouteModel>() {
                    @Override
                    public void onCompleted() {
                        pDialog.dismiss();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("error", "" + e.getMessage());
                    }

                    @Override
                    public void onNext(RouteModel routeModel) {
                        statusCode = routeModel.getStatusCode();
                        if (statusCode == 200) {

                            arrayListdata = routeModel.getData();
                            txt_route_name.setText(routeModel.getData().get(0).getRoute_name());
                            timelineRowsList = new ArrayList<>();
                            for (int i = 0; i < routeModel.getData().get(0).getRoute_data().size(); i++) {
                                timelineRowsList.add(createRandomTimelineRow(i, arrayListdata));
                            }
                            myAdapter = new TimelineViewAdapter(ShopTimelineViewActivity.this, 0, timelineRowsList,
                                    true);
                            timeline_listView.setAdapter(myAdapter);

//                            RouteAdapter routeAdapter=new RouteAdapter(RouteViewActivity.this,routeModel.getData());
//                            rcv_route.setAdapter(routeAdapter);
                        }
                    }
                });

    }


    public class TimelineViewAdapter extends ArrayAdapter<TimelineRow> {

        private Context context;
        private Resources res;
        private List<TimelineRow> RowDataList;
        private String AND;
        private Dialog dialog;
        ImageView popupimage;
        TextView dialog_title, dialog_desc;
        EditText et_coment;
        LinearLayout btn_submit;
        public static final String IMAGE_URL = "http://diestechnology.com.au/projects/pets4ever-admin/";


        SessionManager sessionManager;
        ApiService apiservice;
        ProgressDialog pDialog;
        int statusCode;

        private LocationRequest locationRequest;
        LocationManager locationManager;
        Location location;
        private double wayLatitude = 0.0, wayLongitude = 0.0;
        private String TAG = "TAG";
        //String mediaPath;
        int did;


        public TimelineViewAdapter(Context context, int resource, ArrayList<TimelineRow> objects, boolean orderTheList) {
            super(context, resource, objects);
            this.context = context;
            res = context.getResources();
            sessionManager = new SessionManager(context);
            apiservice = ApiServiceCreator.createService("latest");
            locationRequest = LocationRequest.create();
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            locationRequest.setInterval(10 * 1000); // 10 seconds
            locationRequest.setFastestInterval(5 * 1000); // 5 seconds
            locationManager = (LocationManager) context.getSystemService(Service.LOCATION_SERVICE);

            AND = res.getString(R.string.AND);
            if (orderTheList)
                this.RowDataList = rearrangeByDate(objects);
            else
                this.RowDataList = objects;
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            TimelineRow row = RowDataList.get(position);

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.ctimeline_row, null);

            TextView rowDate = (TextView) view.findViewById(R.id.crowDate);
            TextView rowTitle = (TextView) view.findViewById(R.id.crowTitle);
            TextView rowDescription = (TextView) view.findViewById(R.id.crowDesc);
            ImageView rowImage = (ImageView) view.findViewById(R.id.crowImg);
            Button btn_visit = view.findViewById(R.id.btn_visit);


            View rowUpperLine = (View) view.findViewById(R.id.crowUpperLine);
            View rowLowerLine = (View) view.findViewById(R.id.crowLowerLine);
            // View backgroundView = (View) view.findViewById(R.id.crowBackground);


            did = RowDataList.get(position).getId();


            final float scale = getContext().getResources().getDisplayMetrics().density;


            if (position == 0 && position == RowDataList.size() - 1) {
                rowUpperLine.setVisibility(View.INVISIBLE);
                rowLowerLine.setVisibility(View.INVISIBLE);
            } else if (position == 0) {
                int pixels = (int) (row.getBellowLineSize() * scale + 0.5f);

                rowUpperLine.setVisibility(View.INVISIBLE);
                rowLowerLine.setBackgroundColor(row.getBellowLineColor());
                rowLowerLine.getLayoutParams().width = pixels;
            } else if (position == RowDataList.size() - 1) {
                int pixels = (int) (RowDataList.get(position - 1).getBellowLineSize() * scale + 0.5f);

                rowLowerLine.setVisibility(View.INVISIBLE);
                rowUpperLine.setBackgroundColor(RowDataList.get(position - 1).getBellowLineColor());
                rowUpperLine.getLayoutParams().width = pixels;
            } else {
                int pixels = (int) (row.getBellowLineSize() * scale + 0.5f);
                int pixels2 = (int) (RowDataList.get(position - 1).getBellowLineSize() * scale + 0.5f);

                rowLowerLine.setBackgroundColor(row.getBellowLineColor());
                rowUpperLine.setBackgroundColor(RowDataList.get(position - 1).getBellowLineColor());
                rowLowerLine.getLayoutParams().width = pixels;
                rowUpperLine.getLayoutParams().width = pixels2;
            }


            status = RowDataList.get(position).getVisit();
            if (status.equals("1")) {
                btn_visit.setEnabled(false);
                btn_visit.setText("Visited");
            } else {
                btn_visit.setEnabled(true);
            }


//        card_timeline.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//              //  displayDialog(row.getTitle(),row.getDescription(),row.getImage());
//            }
//        });

            btn_visit.setOnClickListener(view1 -> {


                did = RowDataList.get(position).getId();
                d_idd = String.valueOf(did);
                Log.e(TAG, "visit click--did--: " + did);
                Log.e(TAG, "visit click--d_idd--: " + d_idd);


                if (locationManager != null) {
                    if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                    if (location != null) {
                        wayLatitude = location.getLatitude();
                        wayLongitude = location.getLongitude();

                        Log.e(TAG, "getLocation--: " + wayLatitude + "----" + wayLongitude);
                        Utility.displayToast(context, String.valueOf(wayLatitude) + "\n" + String.valueOf(wayLongitude));
                    }
                }


                displayDialog();
            });

            rowDate.setText(row.getDate());
            if (row.getDateColor() != 0)
                rowDate.setTextColor(row.getDateColor());
            if (row.getTitle() == null)
                rowTitle.setVisibility(View.GONE);
            else {
                rowTitle.setText(row.getTitle());
                if (row.getTitleColor() != 0)
                    rowTitle.setTextColor(row.getTitleColor());
            }
            if (row.getDescription() == null)
                rowDescription.setVisibility(View.GONE);
            else {
                rowDescription.setText(row.getDescription());
                if (row.getDescriptionColor() != 0)
                    rowDescription.setTextColor(row.getDescriptionColor());
            }


            if (row.getImage() != null) {
                //rowImage.setImageBitmap(row.getImage());


                Picasso.with(context).load(row.getImage())
                        //.placeholder(R.drawable.no_image)
                        .into(rowImage);
            }


//        int pixels = (int) (row.getImageSize() * scale + 0.5f);
//        rowImage.getLayoutParams().width = pixels;
//        rowImage.getLayoutParams().height = pixels;

     /*       if (row.getBackgroundColor() == 0)
                backgroundView.setBackground(null);
            else {
                if (row.getBackgroundSize() == -1) {
//                backgroundView.getLayoutParams().width = pixels;
//                backgroundView.getLayoutParams().height = pixels;
                } else {
                    int BackgroundPixels = (int) (row.getBackgroundSize() * scale + 0.5f);
                    backgroundView.getLayoutParams().width = BackgroundPixels;
                    backgroundView.getLayoutParams().height = BackgroundPixels;
                }
//            GradientDrawable background = (GradientDrawable) backgroundView.getBackground();
//            if (background != null) {
//                background.setColor(row.getBackgroundColor());
//            }
            }*/


            ViewGroup.MarginLayoutParams marginParams = (ViewGroup.MarginLayoutParams) rowImage.getLayoutParams();
            // marginParams.setMargins(0, (int) (pixels / 2) * -1, 0, (pixels / 2) * -1);


            return view;
        }


        private String getPastTime(Date date) {

            if (date == null) return "";
            StringBuilder dateText = new StringBuilder();
            Date today = new Date();
            long diff = (today.getTime() - date.getTime()) / 1000;

            long years = diff / (60 * 60 * 24 * 30 * 12);
            long months = (diff / (60 * 60 * 24 * 30)) % 12;
            long days = (diff / (60 * 60 * 24)) % 30;
            long hours = (diff / (60 * 60)) % 24;
            long minutes = (diff / 60) % 60;
            long seconds = diff % 60;

            return dateText.toString();
        }

        private void appendPastTime(StringBuilder s,
                                    long timespan, int nameId,
                                    long timespanNext, int nameNextId) {

            s.append(res.getQuantityString(nameId, (int) timespan, timespan));
            if (timespanNext > 0) {
                s.append(' ').append(AND).append(' ');
                s.append(res.getQuantityString(nameNextId, (int) timespanNext, timespanNext));
            }
        }

        private ArrayList<TimelineRow> rearrangeByDate(ArrayList<TimelineRow> objects) {
            if (objects.get(0) == null) return objects;
            int size = objects.size();
            for (int i = 0; i < size - 1; i++) {
                for (int j = i + 1; j < size; j++) {
                    if (objects.get(i).getDate() != null && objects.get(j).getDate() != null)
                        if (objects.get(i).getDate().compareTo(objects.get(j).getDate()) <= 0)
                            Collections.swap(objects, i, j);
                }

            }
            return objects;
        }

        private void displayDialog() {

            dialog = new Dialog(context);
            dialog.getWindow();
            dialog.setTitle("Address");
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.visit_user_form);
            dialog.getWindow().setLayout((int) (DisplayMetricsHandler.getScreenWidth() - 50), Toolbar.LayoutParams.WRAP_CONTENT);
            //dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setCancelable(true);


            et_coment = dialog.findViewById(R.id.et_coment);
            img_select = dialog.findViewById(R.id.img_select);
            btn_submit = dialog.findViewById(R.id.btn_submit);

            img_select.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    ((Activity) context).startActivityForResult(intent, 1);
                }
            });

            btn_submit.setOnClickListener(view -> {
                if (et_coment.getText().toString().equals("")) {

                } else {

                    if (sessionManager.getKeyRoll().equals("RM")) {

                        AddRMCommentShop();
                    } else {
                        AddCommentShop();
                    }
                }

            });

            dialog.show();
            Window window = dialog.getWindow();
            assert window != null;
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);


        }

        private void AddRMCommentShop() {

            if (mediaPath == null) {
                mediaPath = "";
            }

            Map<String, RequestBody> map = new HashMap<>();
            Compressor compressedImageFile = new Compressor(context);
            compressedImageFile.setQuality(60);
            if (!mediaPath.equals("")) {
                File file = new File(mediaPath);
                File compressfile = null;
                try {
                    compressfile = compressedImageFile.compressToFile(file);
                    RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), compressfile);
                    map.put("tv_image\"; filename=\"" + file.getName() + "\"", requestBody);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            try {

                RequestBody ex_cmnt = RequestBody.create(MediaType.parse("text/plain"), et_coment.getText().toString());
                RequestBody ex_lat = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(wayLatitude));
                RequestBody ex_long = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(wayLongitude));
                RequestBody ex_id = RequestBody.create(MediaType.parse("text/plain"), d_idd);
                RequestBody ex_user_id = RequestBody.create(MediaType.parse("text/plain"), sessionManager.getKeyId());

                map.put("tv_slid", ex_user_id);
                map.put("tv_dlid", ex_id);
                map.put("tv_comment", ex_cmnt);
                map.put("tv_lat", ex_lat);
                map.put("tv_long", ex_long);

                Log.e(TAG, "AddCommentShop--did--: " + did);

                Log.e(TAG, "AddCommentShop--d_idd--: " + d_idd);


                pDialog = new ProgressDialog(context);
                pDialog.setTitle("Checking Data");
                pDialog.setMessage("Please Wait...");
                pDialog.setIndeterminate(false);
                pDialog.setCancelable(false);
                pDialog.show();

                Observable<UserDataResponse> responseObservable = apiservice.AddRMComment(map);

                responseObservable.subscribeOn(Schedulers.newThread())
                        .observeOn(rx.android.schedulers.AndroidSchedulers.mainThread())
                        .onErrorResumeNext(throwable -> {
                            if (throwable instanceof retrofit2.HttpException) {
                                retrofit2.HttpException ex = (retrofit2.HttpException) throwable;
                                statusCode = ex.code();
                                Log.e("error", ex.getLocalizedMessage());
                            } else if (throwable instanceof SocketTimeoutException) {
                                statusCode = 1000;
                            }
                            return Observable.empty();
                        })
                        .subscribe(new Observer<UserDataResponse>() {
                            @Override
                            public void onCompleted() {
                                pDialog.dismiss();
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.e("error", "" + e.getMessage());
                            }

                            @Override
                            public void onNext(UserDataResponse userDataResponse) {
                                statusCode = userDataResponse.getStatusCode();
                                if (statusCode == 200) {
                                    Utility.displayToast(context, userDataResponse.getMessage());
                                    dialog.dismiss();
                                    getRmRoute();

                                } else {
                                    Utility.displayToast(context, userDataResponse.getMessage());
                                }
                            }
                        });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void AddCommentShop() {

            if (mediaPath == null) {
                mediaPath = "";
            }

            Map<String, RequestBody> map = new HashMap<>();
            Compressor compressedImageFile = new Compressor(context);
            compressedImageFile.setQuality(60);
            if (!mediaPath.equals("")) {
                File file = new File(mediaPath);
                File compressfile = null;
                try {
                    compressfile = compressedImageFile.compressToFile(file);
                    RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), compressfile);
                    map.put("tv_image\"; filename=\"" + file.getName() + "\"", requestBody);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            try {

                RequestBody ex_cmnt = RequestBody.create(MediaType.parse("text/plain"), et_coment.getText().toString());
                RequestBody ex_lat = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(wayLatitude));
                RequestBody ex_long = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(wayLongitude));
                RequestBody ex_id = RequestBody.create(MediaType.parse("text/plain"), d_idd);
                RequestBody ex_user_id = RequestBody.create(MediaType.parse("text/plain"), sessionManager.getKeyId());

                map.put("tv_slid", ex_user_id);
                map.put("tv_dlid", ex_id);
                map.put("tv_comment", ex_cmnt);
                map.put("tv_lat", ex_lat);
                map.put("tv_long", ex_long);

                Log.e(TAG, "AddCommentShop--did--: " + did);

                Log.e(TAG, "AddCommentShop--d_idd--: " + d_idd);


                pDialog = new ProgressDialog(context);
                pDialog.setTitle("Checking Data");
                pDialog.setMessage("Please Wait...");
                pDialog.setIndeterminate(false);
                pDialog.setCancelable(false);
                pDialog.show();

                Observable<UserDataResponse> responseObservable = apiservice.AddComment(map);

                responseObservable.subscribeOn(Schedulers.newThread())
                        .observeOn(rx.android.schedulers.AndroidSchedulers.mainThread())
                        .onErrorResumeNext(throwable -> {
                            if (throwable instanceof retrofit2.HttpException) {
                                retrofit2.HttpException ex = (retrofit2.HttpException) throwable;
                                statusCode = ex.code();
                                Log.e("error", ex.getLocalizedMessage());
                            } else if (throwable instanceof SocketTimeoutException) {
                                statusCode = 1000;
                            }
                            return Observable.empty();
                        })
                        .subscribe(new Observer<UserDataResponse>() {
                            @Override
                            public void onCompleted() {
                                pDialog.dismiss();
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.e("error", "" + e.getMessage());
                            }

                            @Override
                            public void onNext(UserDataResponse userDataResponse) {
                                statusCode = userDataResponse.getStatusCode();
                                if (statusCode == 200) {
                                    Utility.displayToast(context, userDataResponse.getMessage());
                                    dialog.dismiss();
                                    getRoute();

                                } else {
                                    Utility.displayToast(context, userDataResponse.getMessage());
                                }
                            }
                        });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && null != data) {
            //selectedImage = data.getData();
//            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = null;
            if (selectedImage != null) {
                cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            }
            assert cursor != null;
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            //filePath1 = cursor.getString(column_index);
            mediaPath = cursor.getString(columnIndex);
            // mediaPath=compressImage(mediaPath1);
            img_select.setImageBitmap(BitmapFactory.decodeFile(mediaPath));
            cursor.close();
        }
    }

}



