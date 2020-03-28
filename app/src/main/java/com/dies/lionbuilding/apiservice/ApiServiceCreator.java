package com.dies.lionbuilding.apiservice;

import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.reactivex.android.BuildConfig;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Harsh
 * on 16/10/2018.
 */

public class ApiServiceCreator {

    public static ApiService createService(String orderBy) {
        OkHttpClient okHttpClient = createOkHttpClient(orderBy);
        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(ApiConstants.BASE_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(createGson()))
                .build();

        return retrofit.create(ApiService.class);
    }

    @NonNull
    private static Gson createGson() {
        return new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    }

    @NonNull
    private static OkHttpClient createOkHttpClient(String type) {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        if (BuildConfig.DEBUG) {
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        } else {
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.NONE);
        }

        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return new OkHttpClient.Builder()
                .addInterceptor(chain -> {
                    HttpUrl url = chain.request().url().newBuilder()
                            .build();
                    Request newRequest = chain.request().newBuilder().url(url).build();
                    return chain.proceed(newRequest);
                })
                .addInterceptor(loggingInterceptor)
                .build();
    }
}
