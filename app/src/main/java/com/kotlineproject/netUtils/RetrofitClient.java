package com.kotlineproject.netUtils;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by CRAFT BOX on 5/1/2017.
 */

public class RetrofitClient {

    private static Retrofit retrofit = null;
    //    public static String service_url = "http://24.24.25.216/shanti/service/";
    public static String service_url = "http://24.24.25.215/ibike/service/";
    //    public static String service_url = "http://sh.craftboxtechnology.com/service/";
    public static String message_pack_name = "LOGIXX";

    public static Retrofit getClient() {

        if (retrofit == null) {
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .readTimeout(36000, TimeUnit.SECONDS)
                    .connectTimeout(36000, TimeUnit.SECONDS)
                    .build();
            retrofit = new Retrofit.Builder()
                    .baseUrl(service_url)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static String service_url1 = "http://zaykaking.in/service/";
    private static Retrofit retrofit1 = null;

    public static Retrofit getClient1() {

        if (retrofit1 == null) {
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .readTimeout(36000, TimeUnit.SECONDS)
                    .connectTimeout(36000, TimeUnit.SECONDS)
                    .build();
            retrofit1 = new Retrofit.Builder()
                    .baseUrl(service_url1)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit1;
    }
}
