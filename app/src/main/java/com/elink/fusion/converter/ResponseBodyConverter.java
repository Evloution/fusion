package com.elink.fusion.converter;

import com.elink.fusion.bean.ApiException;
import com.elink.fusion.bean.BaseBean;
import com.elink.fusion.log.L;
import com.elink.fusion.util.GsonUtil;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * @Description：
 * @Author： Evloution_
 * @Date： 2019-12-06
 * @Email： 15227318030@163.com
 */
public class ResponseBodyConverter<T> implements Converter<ResponseBody, T> {

    private final TypeAdapter<T> adapter;
    private final Gson gson;
    private Type type;
    private JSONObject jsonObject = null;

    public ResponseBodyConverter(TypeAdapter<T> adapter, Gson gson) {
        this.adapter = adapter;
        this.gson = gson;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        String json = value.string();
        L.e("json:" + json);

        // 第一次解析，判断code是否成功，不成功直接抛异常
        BaseBean obj = GsonUtil.GsonToBean(json, BaseBean.class);
        if (!obj.isSuccess()) {
            //如果是服务端返回的错误码，则抛出自定义异常
            L.e("obj错误:" + obj.data);
            throw new ApiException(obj.getCode(), obj.getMsg());
        }

        T result = null;
        // 第二次解析
        try {
            result = adapter.fromJson(json);
        } catch (Exception e) {
            try {
                jsonObject = new JSONObject(json);
                JSONArray array = jsonObject.getJSONArray("data");
                type = new TypeToken<List<T>>() {}.getType();
                result = gson.fromJson(String.valueOf(array), type);
            } catch (JSONException e1) {
                e1.printStackTrace();
            }
        } finally {
            value.close();
        }
        return result;
    }
}
