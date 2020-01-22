package com.elink.fusion.retrofit;

import android.content.Context;


import com.elink.fusion.constants.Constants;
import com.elink.fusion.converter.MyGsonConverterFactory;
import com.elink.fusion.log.L;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * @Description：初始化Retrofit，设置请求API的baseUrl、gson解析方式
 * @Author： Evloution_
 * @Date： 2019-11-27
 * @Email： 15227318030@163.com
 */
public class RetrofitHelper {
    private Context context;

    private static RetrofitHelper instance = null;
    private static OkHttpClient okHttpClient;
    private Retrofit retrofit;

    public RetrofitHelper(Context context) {
        this.context = context;
        initRetrofit();
    }

    private RetrofitHelper() {
    }

    /**
     * 单例封装
     *
     * @param context
     * @return
     */
    public static RetrofitHelper getInstance(Context context) {
        if (instance == null)
            instance = new RetrofitHelper(context);
        return instance;
    }

    /**
     * 初始化Retrofit
     */
    public RetrofitApi initRetrofit() {
        L.e("RetrofitHelper----initRetrofit");
        retrofit = new Retrofit.Builder()
                .addConverterFactory(MyGsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(initOkHttp())
                .baseUrl(Constants.BASE_URL)
                // 设置解析转换工厂
                //.addConverterFactory(GsonConverterFactory.create())
                //这里是自定义的解析转换工厂 GsonConverterFactory
                .build();
        return retrofit.create(RetrofitApi.class);
    }

    public static OkHttpClient initOkHttp() {
        L.e("RetrofitHelper----initOkHttp");
        if (okHttpClient == null) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(
                    message -> L.e("okhttp==" + message)
            );
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            okHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(new Interceptor() {
                        @Override
                        public Response intercept(Chain chain) throws IOException {
                            Request request = chain.request()
                                    .newBuilder()
                                    .addHeader("User-Agent", "Windows")
                                    .build();
                            return chain.proceed(request);
                        }
                    })
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .readTimeout(12, TimeUnit.SECONDS)
                    .writeTimeout(12, TimeUnit.SECONDS)
                    .addInterceptor(loggingInterceptor)
                    .build();
        }
        return okHttpClient;
    }
}
