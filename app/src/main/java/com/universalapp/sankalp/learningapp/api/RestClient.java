package com.universalapp.sankalp.learningapp.api;

import android.app.Activity;
import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.universalapp.sankalp.learningapp.utils.CustomDialog;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by user on 2/7/2018.
 */
public class RestClient {

    private static APIServices REST_CLIENT;

    public static APIServices getInstance(final Activity context) {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        Interceptor interceptCode = new Interceptor() {
            @Override
            public Response intercept(@NonNull Chain chain) throws IOException {
                Request request = chain.request();
                final Response response = chain.proceed(request);
                try {
                    // todo deal with the issues the way you need to

                    context.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // Your dialog code.

                            switch (response.code()) {
                                case 500:
                                    CustomDialog.commonDialog(context, "Server Error",
                                            "Internal server error encountered. Please try again later.",
                                            "OK");
                                    break;
                                case 404:
                                    CustomDialog.commonDialog(context, "Not Found",
                                            "Could not get details of your request. Please try again.",
                                            "OK");
                                    break;
                                case 401:
                                    //CustomDialog.displaySessionExpired(context);
                                    break;
                            /*default:
                                Toast.makeText(mContext, "Could not process your request.", Toast.LENGTH_SHORT).show();
                                break;*/
                            }
                        }
                    });

                    return response;
                } catch (Exception e) {
                    e.printStackTrace();
                    return response;
                }
            }
        };

        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(5, TimeUnit.MINUTES)
                .readTimeout(5, TimeUnit.MINUTES)
                .writeTimeout(5, TimeUnit.MINUTES)
                .addInterceptor(interceptor)
                .addInterceptor(new APIHeaderInterceptor())
                .addInterceptor(interceptCode).build();
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Config.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        REST_CLIENT = retrofit.create(APIServices.class);

        return REST_CLIENT;
    }

    //Connection timeout in minute
    public static APIServices getInstance(final Activity context, int connectionTimeout) {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        Interceptor interceptCode = new Interceptor() {
            @Override
            public Response intercept(@NonNull Chain chain) throws IOException {
                Request request = chain.request();
                final Response response = chain.proceed(request);
                try {
                    // todo deal with the issues the way you need to

                    context.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // Your dialog code.

                            switch (response.code()) {
                                case 500:
                                    CustomDialog.commonDialog(context, "Server Error",
                                            "Internal server error encountered. Please try again later.",
                                            "OK");
                                    break;
                                case 404:
                                    CustomDialog.commonDialog(context, "Not Found",
                                            "Could not get details of your request. Please try again.",
                                            "OK");
                                    break;
                                case 401:
                                    //CustomDialog.displaySessionExpired(context);
                                    break;
                            /*default:
                                Toast.makeText(mContext, "Could not process your request.", Toast.LENGTH_SHORT).show();
                                break;*/
                            }
                        }
                    });

                    return response;
                } catch (Exception e) {
                    e.printStackTrace();
                    return response;
                }
            }
        };

        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(connectionTimeout, TimeUnit.MINUTES)
                .readTimeout(2, TimeUnit.MINUTES)
                .writeTimeout(2, TimeUnit.MINUTES)
                .addInterceptor(interceptor)
                .addInterceptor(new APIHeaderInterceptor())
                .addInterceptor(interceptCode).build();
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Config.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        REST_CLIENT = retrofit.create(APIServices.class);

        return REST_CLIENT;
    }

}