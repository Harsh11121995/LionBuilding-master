package com.dies.lionbuilding.TimeLine;


import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.Service;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.CardView;
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
import android.widget.TextView;

import com.dies.lionbuilding.R;
import com.dies.lionbuilding.apiservice.ApiService;
import com.dies.lionbuilding.apiservice.ApiServiceCreator;
import com.dies.lionbuilding.application.SessionManager;
import com.dies.lionbuilding.application.Utility;
import com.dies.lionbuilding.model.UserDataResponse;
import com.google.android.gms.location.LocationRequest;
import com.squareup.picasso.Picasso;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.schedulers.Schedulers;

//import org.qap.ctimelineview.TimelineRow;


public class TimelineViewAdapter extends ArrayAdapter<TimelineRow> {

    private Context context;
    private Resources res;
    private List<TimelineRow> RowDataList;
    private String AND;
    private Dialog dialog;
    ImageView popupimage;
    TextView dialog_title,dialog_desc;
    EditText et_coment;
    ImageView img_select;
    Button btn_submit;
    public static final String IMAGE_URL ="http://diestechnology.com.au/projects/pets4ever-admin/";


    SessionManager sessionManager;
    ApiService apiservice;
    ProgressDialog pDialog;
    int statusCode;

    private LocationRequest locationRequest;
    LocationManager locationManager;
    Location location;
    private double wayLatitude = 0.0, wayLongitude = 0.0;


    public TimelineViewAdapter(Context context, int resource, ArrayList<TimelineRow> objects, boolean orderTheList) {
        super(context, resource, objects);
        this.context = context;
        res = context.getResources();
        sessionManager=new SessionManager(context);
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
        Button btn_visit =  view.findViewById(R.id.btn_visit);


        View rowUpperLine = (View) view.findViewById(R.id.crowUpperLine);
        View rowLowerLine = (View) view.findViewById(R.id.crowLowerLine);


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



//        card_timeline.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//              //  displayDialog(row.getTitle(),row.getDescription(),row.getImage());
//            }
//        });


        btn_visit.setOnClickListener(view1 -> {
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

                    Utility.displayToast(context,String.valueOf(wayLatitude)+"\n"+String.valueOf(wayLongitude));
                }
            }


         //   displayDialog();
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

        View backgroundView = view.findViewById(R.id.crowBackground);
        if (row.getBackgroundColor() == 0)
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
        }


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

//        if (years > 0) {
//            appendPastTime(dateText, years, R.plurals.years, months, R.plurals.months);
//        } else if (months > 0) {
//            appendPastTime(dateText, months, R.plurals.months, days, R.plurals.days);
//        } else if (days > 0) {
//            appendPastTime(dateText, days, R.plurals.days, hours, R.plurals.hours);
//        } else if (hours > 0) {
//            appendPastTime(dateText, hours, R.plurals.hours, minutes, R.plurals.minutes);
//        } else if (minutes > 0) {
//            appendPastTime(dateText, minutes, R.plurals.minutes, seconds, R.plurals.seconds);
//        } else if (seconds >= 0) {
//            dateText.append(res.getQuantityString(R.plurals.seconds, (int) seconds, (int) seconds));
//        }

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
                if(objects.get(i).getDate()!= null && objects.get(j).getDate() != null)
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
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(true);


        et_coment=dialog.findViewById(R.id.et_coment);
        img_select=dialog.findViewById(R.id.img_select);
        btn_submit=dialog.findViewById(R.id.btn_submit);


        btn_submit.setOnClickListener(view -> {
            if (et_coment.getText().toString().equals("")){

            }else {
                AddCommentShop();
            }

        });

//        Picasso.with(context).load(img)
//                .fit()
//                //.placeholder(R.drawable.no_image)
//                .into(popupimage);


//        dialog_title.setText(date);
//        dialog_desc.setText(desc);


//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item_spin, years);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinner.setAdapter(adapter);


//        txt_address1 = dialog.findViewById(R.id.txt_address1);

        dialog.show();
        Window window = dialog.getWindow();
        assert window != null;
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);


    }


    public void AddCommentShop(){

            pDialog = new ProgressDialog(context);
            pDialog.setTitle("Checking Data");
            pDialog.setMessage("Please Wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();

            Observable<UserDataResponse> responseObservable = apiservice.AddComment(
                    "",
                    "",
                    "",
                    "",
                    "");

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
                        public void onNext(UserDataResponse friendModel) {
                            statusCode = friendModel.getStatusCode();
                            if (statusCode == 200) {

                            }
                        }
                    });


    }

}
