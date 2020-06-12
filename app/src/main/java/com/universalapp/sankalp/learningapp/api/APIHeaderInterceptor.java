package com.universalapp.sankalp.learningapp.api;

import com.universalapp.sankalp.learningapp.utils.Utils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by asifi on 4/19/2018.
 */

public class APIHeaderInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder builder = chain.request().newBuilder();
        builder.addHeader("user_timezone_app_offset", Utils.getTimeZone());

        try {
            return chain.proceed(builder.build());
        } catch (Throwable e) {
            if (e instanceof IOException) {
                throw e;
            } else {
                throw new IOException(e);
            }
        }
    }
}