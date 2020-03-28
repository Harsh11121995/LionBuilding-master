package com.dies.lionbuilding.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.dies.lionbuilding.R;
import com.dies.lionbuilding.apiservice.ApiService;
import com.dies.lionbuilding.apiservice.ApiServiceCreator;
import com.dies.lionbuilding.application.SessionManager;
import com.dies.lionbuilding.model.ProductModel;
import com.google.android.gms.vision.barcode.Barcode;
import com.notbytes.barcode_reader.BarcodeReaderActivity;
import com.notbytes.barcode_reader.BarcodeReaderFragment;

import java.net.SocketTimeoutException;
import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.schedulers.Schedulers;

public class ScanBarcodeActivity extends AppCompatActivity implements View.OnClickListener,BarcodeReaderFragment.BarcodeReaderListener {

    private BarcodeReaderFragment mBarcodeReaderFragment;
    private static final int BARCODE_READER_ACTIVITY_REQUEST = 1208;
    private TextView mTvResult;
    private TextView mTvResultHeader;

  //  Button btn_cart;


    SessionManager sessionManager;
    ApiService apiservice;
    ProgressDialog pDialog;
    RecyclerView.LayoutManager layoutManager;
    int statusCode;

    Button btn_cart;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_barcode);
        findViewById(R.id.btn_activity).setOnClickListener(this);
      btn_cart=findViewById(R.id.btn_cart);

              btn_cart.setOnClickListener(view -> {
            Intent intent=new Intent(this,ViewProductPointCart.class);
            startActivity(intent);
        });


        sessionManager = new SessionManager(this);
        apiservice = ApiServiceCreator.createService("latest");
       // mBarcodeReaderFragment = attachBarcodeReaderFragment();
    }




//    private void addBarcodeReaderFragment() {
//        BarcodeReaderFragment readerFragment = BarcodeReaderFragment.newInstance(true, false, View.VISIBLE);
//        readerFragment.setListener(this);
//        FragmentManager supportFragmentManager = getSupportFragmentManager();
//        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
//        fragmentTransaction.replace(R.id.fm_container, readerFragment);
//        fragmentTransaction.commitAllowingStateLoss();
//    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.btn_fragment:
//                //addBarcodeReaderFragment();
//                break;
            case R.id.btn_activity:
                FragmentManager supportFragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
                Fragment fragmentById = supportFragmentManager.findFragmentById(R.id.fm_container);
                if (fragmentById != null) {
                    fragmentTransaction.remove(fragmentById);
                }
                fragmentTransaction.commitAllowingStateLoss();
                launchBarCodeActivity();
                break;
        }
    }
    private void launchBarCodeActivity() {
        Intent launchIntent = BarcodeReaderActivity.getLaunchIntent(this, true, false);
        startActivityForResult(launchIntent, BARCODE_READER_ACTIVITY_REQUEST);
    }

    @Override

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != Activity.RESULT_OK) {
            Toast.makeText(this, "error in scanning", Toast.LENGTH_SHORT).show();
            return;
        }

        if (requestCode == BARCODE_READER_ACTIVITY_REQUEST && data != null) {
            Barcode barcode = data.getParcelableExtra(BarcodeReaderActivity.KEY_CAPTURED_BARCODE);
            Toast.makeText(this, barcode.rawValue, Toast.LENGTH_SHORT).show();
//            mTvResultHeader.setText("On Activity Result");
//            mTvResult.setText(barcode.rawValue);
            String barcodeid=barcode.rawValue;

            startActivity(new Intent(this,ProductDetailActivity.class).putExtra("barcode_id",barcodeid));
            //getBarcodeData(barcodeid);
        }
    }

    @Override
    public void onScanned(Barcode barcode) {
        Toast.makeText(this, barcode.rawValue, Toast.LENGTH_SHORT).show();
        mTvResultHeader.setText("Barcode value from fragment");
        mTvResult.setText(barcode.rawValue);
    }

    @Override
    public void onScannedMultiple(List<Barcode> barcodes) {

    }

    @Override
    public void onBitmapScanned(SparseArray<Barcode> sparseArray) {

    }

    @Override
    public void onScanError(String errorMessage) {

    }

    @Override
    public void onCameraPermissionDenied() {
        Toast.makeText(this, "Camera permission denied!", Toast.LENGTH_LONG).show();
    }


    public void getBarcodeData(String barcode_id)
    {
        pDialog = new ProgressDialog(this);
        pDialog.setTitle("Checking Data");
        pDialog.setMessage("Please Wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();

        Observable<ProductModel> responseObservable = apiservice.getBarcodedata(barcode_id);

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
                .subscribe(new Observer<ProductModel>() {
                    @Override
                    public void onCompleted() {
                        pDialog.dismiss();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("error", "" + e.getMessage());
                    }

                    @Override
                    public void onNext(ProductModel friendModel) {
                        statusCode = friendModel.getStatusCode();
                        if (statusCode == 200) {


                        }
                    }
                });

    }





}