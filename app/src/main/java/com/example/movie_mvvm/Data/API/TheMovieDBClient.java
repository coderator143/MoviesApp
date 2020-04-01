package com.example.movie_mvvm.Data.API;

import com.example.movie_mvvm.Utilities.Constants;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class TheMovieDBClient {

    public APIService getClient() {
        Interceptor requestIntercceptor=new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                HttpUrl url=chain.request()
                        .url()
                        .newBuilder()
                        .addQueryParameter("api_key", Constants.API_KEY)
                        .build();

                Request request=chain.request()
                        .newBuilder()
                        .url(url)
                        .build();

                return chain.proceed(request);
            }
        };

        OkHttpClient okHttpClient=new OkHttpClient.Builder()
                .addInterceptor(requestIntercceptor)
                .connectTimeout(60, TimeUnit.SECONDS)
                .build();

        return new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(Constants.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(APIService.class);
    }
}
