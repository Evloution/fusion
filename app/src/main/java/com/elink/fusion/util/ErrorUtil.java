package com.elink.fusion.util;

import android.net.ParseException;

import com.elink.fusion.bean.ApiException;
import com.google.gson.JsonParseException;

import org.json.JSONException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import retrofit2.HttpException;

/**
 * @Description：服务器请求异常时到这里解析异常信息
 * @Author： Evloution_
 * @Date： 2019-11-30
 * @Email： 15227318030@163.com
 */
public class ErrorUtil {

    private static final int UNAUTHORIZED = 401;
    private static final int FORBIDDEN = 403;
    private static final int NOT_FOUND = 404;
    private static final int REQUEST_TIMEOUT = 408;
    private static final int INTERNAL_SERVER_ERROR = 500;
    private static final int BAD_GATEWAY = 502;
    private static final int SERVICE_UNAVAILABLE = 503;
    private static final int GATEWAY_TIMEOUT = 504;

    public static String requestHandle(Throwable e) {
        String msg;
        if (e instanceof HttpException) {
            HttpException httpException = (HttpException) e;
            switch (httpException.code()) {
                case UNAUTHORIZED:
                case FORBIDDEN:
                case NOT_FOUND:
                case REQUEST_TIMEOUT:
                case GATEWAY_TIMEOUT:
                case INTERNAL_SERVER_ERROR:
                case BAD_GATEWAY:
                case SERVICE_UNAVAILABLE:
                default:
                    msg = "服务器错误";
                    break;
            }
        } else if (e instanceof ApiException) {
            //后台异常
            ApiException apiException = (ApiException) e;
            msg = apiException.getMessage();
        } else if (e instanceof JsonParseException || e instanceof JSONException || e instanceof ParseException) {
            msg = "解析错误";
        } else if (e instanceof ConnectException || e instanceof SocketTimeoutException || e instanceof UnknownHostException) {
            msg = "连接失败，请检查网络";
        }  else if (e instanceof NumberFormatException){
            msg = "数字格式化异常";
        } else {
            msg = "请求失败";
        }
        return msg;
    }
}
