package com.sammy.test.quizdemo.data.network;

import android.support.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.sammy.test.quizdemo.data.local.MockQuestionsClient;

import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * retrofit client
 */
public class ApiClient {
 
    public static final String BASE_URL = "http://sammytest.blah/";
    private static Retrofit mRetrofit;
 
 
    public static Retrofit getClient(@Nullable Interceptor mockInterceptor) {
        if (mRetrofit==null) {
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();
            //Add logging
            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            okhttp3.OkHttpClient.Builder httpClientBuilder = new okhttp3.OkHttpClient
                    .Builder()
                    .readTimeout(60, TimeUnit.SECONDS)
                    .connectTimeout(60, TimeUnit.SECONDS)
                    .addInterceptor(httpLoggingInterceptor);

            if(mockInterceptor != null)
                    httpClientBuilder.addInterceptor(new MockQuestionsClient());

            mRetrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(httpClientBuilder.build())
                    .build();
        }
        return mRetrofit;
    }
}