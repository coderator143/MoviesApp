package com.example.movie_mvvm.Data.API;

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

    private static final String API_KEY="5bdd7f40c40addb963f95f92684bfa34";
    private static final String BASE_URL="https://api.themoviedb.org/3/";
    public static final String POSTER_BASE_URL="https://image.tmdb.org/t/p/w342";
    public static final int FIRST_PAGE=1;
    public static final int POST_PER_PAGE=20;

    public TheMovieDBInterface getClient() {
        Interceptor requestIntercceptor=new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                HttpUrl url=chain.request()
                        .url()
                        .newBuilder()
                        .addQueryParameter("api_key", API_KEY)
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
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(TheMovieDBInterface.class);
    }
}
