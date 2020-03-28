package com.dies.lionbuilding.activity;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.location.Location;
import android.location.LocationManager;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dies.lionbuilding.R;
import com.dies.lionbuilding.apiservice.ApiService;
import com.dies.lionbuilding.apiservice.ApiServiceCreator;
import com.dies.lionbuilding.application.SessionManager;
import com.dies.lionbuilding.application.Utility;
import com.dies.lionbuilding.model.UserDataResponse;
import com.dies.lionbuilding.util.AlarmReceiver;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.Map;

import id.zelory.compressor.Compressor;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Observable;
import rx.Observer;
import rx.schedulers.Schedulers;

public class SendLocation extends AppCompatActivity {

    public String media_type, filePath, filePath1;
    AlarmManager alarmManager;
    PendingIntent pendingIntent;
    ProgressDialog pDialog;
    int statusCode;
    ApiService apiservice;
    SessionManager sessionManager;
    double latitude, longitude;
    String lat, lon;
    ImageView back;
    TextView toolbar_title;
    CardView card_start_day, card_end_day;
    ImageView openCamera;
    LocationManager locationManager;
    Location location;
    Uri mCapturedImageURI;
    Integer CAPTURE_IMAGE = 3;
    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            Bundle b = intent.getExtras();

            String message = b.getString("message");
            String message1 = b.getString("message1");


            Log.e("newmesage", "" + message + "   " + message1);
            // AddBankDetail(message, message1);
        }
    };
    Boolean status = true;
    private double wayLatitude = 0.0, wayLongitude = 0.0;
    private String TAG = "TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_location);
        card_start_day = findViewById(R.id.card_start_day);
        card_end_day = findViewById(R.id.card_end_day);
        toolbar_title = findViewById(R.id.toolbar_Title);
        toolbar_title.setText("Start / End Day");
        back = (ImageView) findViewById(R.id.back_icon);
        openCamera = (ImageView) findViewById(R.id.uploadPic);
        Button button = findViewById(R.id.btn);
        Button btn_cancel = findViewById(R.id.btn_cancel);
        apiservice = ApiServiceCreator.createService("latest");
        sessionManager = new SessionManager(this);
        AlarmManager alarmMgr = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        locationManager = (LocationManager) this.getSystemService(Service.LOCATION_SERVICE);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();
            }
        });

//        registerReceiver(broadcastReceiver, new IntentFilter("broadCastName"));

        openCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    String fileName = "temp.jpg";
                    ContentValues values = new ContentValues();
                    values.put(MediaStore.Images.Media.TITLE, fileName);
                    mCapturedImageURI = getContentResolver()
                            .insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                                    values);
                    takePictureIntent
                            .putExtra(MediaStore.EXTRA_OUTPUT, mCapturedImageURI);
                    startActivityForResult(takePictureIntent, CAPTURE_IMAGE);

                }
            }
        });

        if (sessionManager.getStatus()) {
            card_start_day.setVisibility(View.VISIBLE);
            card_end_day.setVisibility(View.GONE);
        } else {
            card_start_day.setVisibility(View.GONE);
            card_end_day.setVisibility(View.VISIBLE);
        }


        card_start_day.setOnClickListener(view -> {

            if (openCamera.getDrawable() == null) {

                Log.e(TAG, "image null--");
                Utility.displayToast(this, "Capture the image first");

            } else {
                Log.e(TAG, "image is not null--");
                if (locationManager != null) {
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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

                        Log.e(TAG, "startday location--: " + wayLatitude + "----" + wayLongitude);
                        Utility.displayToast(this, String.valueOf(wayLatitude) + "\n" + String.valueOf(wayLongitude));
                    }
                }

                startdayApi(this);

                // status = true;
                if (sessionManager.getStatus()) {
                    sessionManager.setStatus(false);
                    scheduleNotify();
                    card_start_day.setVisibility(View.GONE);
                    card_end_day.setVisibility(View.VISIBLE);
                    openCamera.setImageResource(0);
                }

            }

            //card_start_day.setVisibility(View.GONE);

        });

        card_end_day.setOnClickListener(view -> {

            if (openCamera.getDrawable() == null) {
                Log.e(TAG, "image null--");

                Utility.displayToast(this, "Capture the image first");
            } else {
                Log.e(TAG, "image is not null--");
                if (locationManager != null) {
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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

                        Log.e(TAG, "endday location--: " + wayLatitude + "----" + wayLongitude);
                        Utility.displayToast(this, String.valueOf(wayLatitude) + "\n" + String.valueOf(wayLongitude));
                    }
                }

                userendofdayLocation(this);

                if (!sessionManager.getStatus()) {
                    status = true;
                    sessionManager.setStatus(status);
                    sheduleOfNotify();
                    card_start_day.setVisibility(View.VISIBLE);
                    card_end_day.setVisibility(View.GONE);
                    openCamera.setImageResource(0);
                }
            }

            //card_end_day.setVisibility(View.GONE);
            //  alarmManager.cancel(pendingIntent);
        });
    }


    private void startdayApi(Context context) {

        if (filePath1 == null) {
            filePath1 = "";
        }

        Map<String, RequestBody> map = new HashMap<>();
        Compressor compressedImageFile = new Compressor(context);
        compressedImageFile.setQuality(60);
        if (!filePath1.equals("")) {
            File file = new File(filePath1);
            // File compressfile = null;
            //compressfile = compressedImageFile.compressToFile(file);
            RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
            map.put("track_simage\"; filename=\"" + file.getName() + "\"", requestBody);
        }

        try {

            RequestBody ex_user_id = RequestBody.create(MediaType.parse("text/plain"), sessionManager.getUserData().get(0).getUserId());
            RequestBody ex_lat = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(wayLatitude));
            RequestBody ex_long = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(wayLongitude));

            map.put("userId", ex_user_id);
            map.put("trck_latitude", ex_lat);
            map.put("trck_longitude", ex_long);

            Observable<UserDataResponse> responseObservable = apiservice.addLatlong(map);

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
//                        pDialog.dismiss();
                        }

                        @Override
                        public void onError(Throwable e) {

                            Log.e("error", "" + e.getMessage());
                        }

                        @Override
                        public void onNext(UserDataResponse addBankModel) {
                            statusCode = addBankModel.getStatusCode();
                            if (statusCode == 200) {
                                Utility.displayToast(context, addBankModel.getMessage());

//                            startActivity(new Intent(AddBank.this, DashBoardActivity.class));


                            } else {
//                            Utility.displayToast(AddBank.this, addBankModel.getMessage());
                            }


                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void scheduleNotify() {

        Intent notificationIntent = new Intent(this, AlarmReceiver.class);
        notificationIntent.putExtra("image", filePath1);
        pendingIntent = PendingIntent.getBroadcast(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, 0, 1000 * 60 * 5, pendingIntent); // Millisec * Second * Minute
        Toast.makeText(this, " schedulenotify call", Toast.LENGTH_SHORT).show();
    }


    private void sheduleOfNotify() {

        Intent myIntent = new Intent(this, AlarmReceiver.class);
//The params for below (Context,ID,Intent,Flag)-- This ID is what needs to be same for //seting and removing the alarm. You can keep it static 0 as you have used, if you do not //have multiple alarms in your app.
        pendingIntent = PendingIntent.getBroadcast(this, 0, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);
        Toast.makeText(this, "end day", Toast.LENGTH_SHORT).show();
    }


    private void userendofdayLocation(Context context) {

        if (filePath1 == null) {
            filePath1 = "";
        }

        Map<String, RequestBody> map = new HashMap<>();
        Compressor compressedImageFile = new Compressor(context);
        compressedImageFile.setQuality(60);
        if (!filePath1.equals("")) {
            File file = new File(filePath1);
            // File compressfile = null;
            //compressfile = compressedImageFile.compressToFile(file);
            RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
            map.put("track_eimage\"; filename=\"" + file.getName() + "\"", requestBody);
        }

        try {

            RequestBody ex_user_id = RequestBody.create(MediaType.parse("text/plain"), sessionManager.getUserData().get(0).getUserId());
            RequestBody ex_lat = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(wayLatitude));
            RequestBody ex_long = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(wayLongitude));

            map.put("userId", ex_user_id);
            map.put("trck_latitude", ex_lat);
            map.put("trck_longitude", ex_long);
            Observable<UserDataResponse> responseObservable = apiservice.endofdayLatlong(map);

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
//                        pDialog.dismiss();
                        }

                        @Override
                        public void onError(Throwable e) {

                            Log.e("error", "" + e.getMessage());
                        }

                        @Override
                        public void onNext(UserDataResponse addBankModel) {
                            statusCode = addBankModel.getStatusCode();
                            if (statusCode == 200) {
                                Utility.displayToast(context, addBankModel.getMessage());
                            } else {
//                            Utility.displayToast(AddBank.this, addBankModel.getMessage());
                            }
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            if (requestCode == CAPTURE_IMAGE) {
                media_type = "image";
                ImageCropFunctionCustom(mCapturedImageURI);
            } else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
                if (data != null) {
                    CropImage.ActivityResult result = CropImage.getActivityResult(data);
                    // Uri uri = result.getUri();
                    Compressor compressedImageFile = new Compressor(this);
                    compressedImageFile.setQuality(60);
                    try {
                        File file = compressedImageFile.compressToFile(new File(result.getUri().getPath()));
                        Uri uri = Uri.fromFile(file);


                        // Uri uri = result.getUri();

                        // arrayListuri.add(uri);
                        Bundle bundle = data.getExtras();
                        assert bundle != null;
                        filePath1 = uri.getPath();
                        filePath = compressImage(filePath1);
                        if (filePath1 != null) {
                            openCamera.setImageBitmap(BitmapFactory.decodeFile(filePath1));
                            Log.e(TAG, "onActivityResult--" + filePath1);

                            //imageView.setImageResource(R.drawable.no_image);
                            // Utility.getAppcon().getSession().arraylistimage = arrayListuri;
                        } else {
                            if (uri != null) {
                                filePath1 = uri.getPath();
                                filePath = compressImage(filePath1);
                                openCamera.setImageResource(0);
                                openCamera.setImageBitmap(BitmapFactory.decodeFile(filePath));
                                // Utility.getAppcon().getSession().arraylistimage = arrayListuri;
                            }
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

    }

    public void ImageCropFunctionCustom(Uri uri) {
        Intent intent = CropImage.activity(uri)
                .setGuidelines(CropImageView.Guidelines.ON)
                .getIntent(this);
        startActivityForResult(intent, CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE);
    }


    public String compressImage(String imageUri) {

        String filePath = getRealPathFromURI(imageUri);
        Bitmap scaledBitmap = null;

        BitmapFactory.Options options = new BitmapFactory.Options();

//      by setting this field as true, the actual bitmap pixels are not loaded in the memory. Just the bounds are loaded. If
//      you try the use the bitmap here, you will get null.
        options.inJustDecodeBounds = true;
        Bitmap bmp = BitmapFactory.decodeFile(filePath, options);

        int actualHeight = options.outHeight;
        int actualWidth = options.outWidth;

//      max Height and width values of the compressed image is taken as 816x612

        float maxHeight = 816.0f;
        float maxWidth = 612.0f;
        float imgRatio = actualWidth / actualHeight;
        float maxRatio = maxWidth / maxHeight;

//      width and height values are set maintaining the aspect ratio of the image

        if (actualHeight > maxHeight || actualWidth > maxWidth) {
            if (imgRatio < maxRatio) {
                imgRatio = maxHeight / actualHeight;
                actualWidth = (int) (imgRatio * actualWidth);
                actualHeight = (int) maxHeight;
            } else if (imgRatio > maxRatio) {
                imgRatio = maxWidth / actualWidth;
                actualHeight = (int) (imgRatio * actualHeight);
                actualWidth = (int) maxWidth;
            } else {
                actualHeight = (int) maxHeight;
                actualWidth = (int) maxWidth;

            }
        }

//      setting inSampleSize value allows to load a scaled down version of the original image

        options.inSampleSize = calculateInSampleSize(options, actualWidth, actualHeight);

//      inJustDecodeBounds set to false to load the actual bitmap
        options.inJustDecodeBounds = false;

//      this options allow android to claim the bitmap memory if it runs low on memory
        options.inPurgeable = true;
        options.inInputShareable = true;
        options.inTempStorage = new byte[16 * 1024];

        try {
//          load the bitmap from its path
            bmp = BitmapFactory.decodeFile(filePath, options);
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();

        }
        try {
            scaledBitmap = Bitmap.createBitmap(actualWidth, actualHeight, Bitmap.Config.ARGB_8888);
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();
        }

        float ratioX = actualWidth / (float) options.outWidth;
        float ratioY = actualHeight / (float) options.outHeight;
        float middleX = actualWidth / 2.0f;
        float middleY = actualHeight / 2.0f;

        Matrix scaleMatrix = new Matrix();
        scaleMatrix.setScale(ratioX, ratioY, middleX, middleY);

        Canvas canvas = new Canvas(scaledBitmap);
        canvas.setMatrix(scaleMatrix);
        canvas.drawBitmap(bmp, middleX - bmp.getWidth() / 2, middleY - bmp.getHeight() / 2, new Paint(Paint.FILTER_BITMAP_FLAG));

//      check the rotation of the image and display it properly
        ExifInterface exif;
        try {
            exif = new ExifInterface(filePath);

            int orientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION, 0);
            Log.d("EXIF", "Exif: " + orientation);
            Matrix matrix = new Matrix();
            if (orientation == 6) {
                matrix.postRotate(90);
                Log.d("EXIF", "Exif: " + orientation);
            } else if (orientation == 3) {
                matrix.postRotate(180);
                Log.d("EXIF", "Exif: " + orientation);
            } else if (orientation == 8) {
                matrix.postRotate(270);
                Log.d("EXIF", "Exif: " + orientation);
            }
            scaledBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0,
                    scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix,
                    true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        FileOutputStream out = null;
        String filename = getFilename();
        try {
            out = new FileOutputStream(filename);

//          write the compressed bitmap at the destination specified by filename.
            scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 20, out);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return filename;

    }

    private String getRealPathFromURI(String contentURI) {
        Uri contentUri = Uri.parse(contentURI);
        Cursor cursor = getContentResolver().query(contentUri, null, null, null, null);
        if (cursor == null) {
            return contentUri.getPath();
        } else {
            cursor.moveToFirst();
            int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(index);
        }
    }

    public int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        final float totalPixels = width * height;
        final float totalReqPixelsCap = reqWidth * reqHeight * 2;
        while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
            inSampleSize++;
        }

        return inSampleSize;
    }

    public String getFilename() {
        File file = new File(Environment.getExternalStorageDirectory().getPath(), "MyFolder/Images");
        if (!file.exists()) {
            file.mkdirs();
        }
        String uriSting = (file.getAbsolutePath() + "/" + System.currentTimeMillis() + ".jpg");
        return uriSting;

    }

    /*
    private void AddBankDetail(String lt, String ln) {
//        pDialog = new ProgressDialog(context);
//        pDialog.setTitle("Checking Data");
//        pDialog.setMessage("Please Wait...");
//        pDialog.setIndeterminate(false);
//        pDialog.setCancelable(false);
//        pDialog.show();

        Observable<UserDataResponse> responseObservable = apiservice.addLatlong(sessionManager.getUserData().get(0).getUserId(),
                lt,
                ln
        );

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
//                        pDialog.dismiss();
                    }

                    @Override
                    public void onError(Throwable e) {

                        Log.e("error", "" + e.getMessage());
                    }

                    @Override
                    public void onNext(UserDataResponse userDataResponse) {
                        statusCode = userDataResponse.getStatusCode();
                        if (statusCode == 200) {
                            Utility.displayToast(SendLocation.this, userDataResponse.getMessage());
//                            startActivity(new Intent(AddBank.this, DashBoardActivity.class));


                        } else {
//                            Utility.displayToast(AddBank.this, addBankModel.getMessage());
                        }


                    }
                });

    }
*/

//    public class CountDownTimerTest extends CountDownTimer {
//        public CountDownTimerTest(long startTime, long interval) {
//            super(startTime, interval);
//        }
//
//        @Override
//        public void onFinish() {
//            text.setText("Time's up!");
//            timeElapsedView.setText("Time Elapsed: " + String.valueOf(startTime));
//        }
//
//        @Override
//        public void onTick(long millisUntilFinished) {
//            text.setText("Time remain:" + millisUntilFinished);
//            timeElapsed = startTime - millisUntilFinished;
//            timeElapsedView.setText("Time Elapsed: " + String.valueOf(timeElapsed));
//        }
//    }


}


//    Intent intent = new Intent(SendLocation.this, AlarmReceiver.class);
//    boolean flag = (PendingIntent.getBroadcast(SendLocation.this, 0,
//            intent, PendingIntent.FLAG_NO_CREATE)==null);
//    /*Register alarm if not registered already*/
//    if(flag){
//        PendingIntent alarmIntent = PendingIntent.getBroadcast(SendLocation.this, 0,
//                intent, PendingIntent.FLAG_UPDATE_CURRENT);
//
//// Create Calendar obj called calendar
//        Calendar calendar = Calendar.getInstance();
//
//        /* Setting alarm for every one hour from the current time.*/
//        int intervalTimeMillis = 1000 * 60 * 1; // 1 hour
//        alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP,
//                calendar.getTimeInMillis(), intervalTimeMillis,
//                alarmIntent);
//    }

// Observable<UserDataResponse> responseObservable = apiservice.getUserDetails(sessionManager.getKeyMobile(), sessionManager.getKeyToken());
/*
        responseObservable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
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
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("error", e.getLocalizedMessage());
                    }

                    @Override
                    public void onNext(UserDataResponse userDataResponse) {
                        statusCode = userDataResponse.getStatusCode();
                        if (statusCode == 200) {


                            if(status)
                            {

                            }

                            if (userDataResponse.getData().get(0).getStatus_btn().equals(status)) {

                                card_start_day.setVisibility(View.VISIBLE);
                                card_end_day.setVisibility(View.GONE);

                            } else if (userDataResponse.getData().get(0).getStatus_btn().equals("2")) {

                                card_end_day.setVisibility(View.VISIBLE);
                                card_start_day.setVisibility(View.GONE);

                            } else {
                                card_start_day.setVisibility(View.VISIBLE);
                                card_end_day.setVisibility(View.VISIBLE);
                            }


                        }
                    }
                });
*/

