package com.elink.fusion.util;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;


public class JSONAnalysisUtils {
    private static JSONObject object = null;
    public static String msg = null;
    public static String code = null;
    public static String data = null;
    public static int  count = 0;

    /**
     * JSON解析出msg
     *
     * @param string
     * @return msg信息
     */
    public static String JSONMsgAnalysis(String string) {
        try {
            object = new JSONObject(string);
            msg = object.optString("msg");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return msg;
    }
    /**
     * JSON解析出count
     *
     * @param string
     * @return msg信息
     */
    public static int JSONCountAnalysis(String string) {
        try {
            object = new JSONObject(string);
            count = object.optInt("count");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return count;
    }
    /**
     * JSON解析出code
     *
     * @param string
     * @return code码
     */
    public static String JSONCodeAnalysis(String string) {
        try {
            object = new JSONObject(string);
            code = object.optString("code");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return code;
    }

    /**
     * JSON解析出返回值
     *
     * @param string
     * @return data
     */
    public static String JSONDataAnalysis(String string) {
        try {
            object = new JSONObject(string);
            data = object.optString("data");
            
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return data;
    }

    /**
     * JSON解析出返回值
     *
     * @param string
     * @return data
     */
    public static JSONArray JSONDataJsonObject(String string) {
    	JSONArray jsonData =null;
        try {
            object = new JSONObject(string);
            jsonData =object.getJSONArray("data");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonData;
    }

    /**
     * 解析APK下载路径
     * @param string
     * @return
     */
    public static String JSONPathAnalysis(String string) {
        try {
            object = new JSONObject(string);
            data = object.optString("path");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return data;
    }

    /**
     * 解析出更新日志
     * @param string
     * @return
     */
    public static String JSONPublishdescAnalysis(String string) {
        try {
            object = new JSONObject(string);
            data = object.optString("publishdesc");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return data;
    }

    /**
     * 解析出新版本
     * @param string
     * @return
     */
    public static String JSONVersionnumAnalysis(String string) {
        try {
            object = new JSONObject(string);
            data = object.optString("version"); // versionnum
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return data;
    }

    /**
     * 解析人员信息
     * @param string
     * @return
     */
    public static String JSONPersonAnalysis(String string, String val) {
        try {
            object = new JSONObject(string);
            data = object.optString(val);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return data;
    }

    public static JSONArray JSONAnalysis(String string, String val) {
        JSONArray data1 = null;
        try {
            object = new JSONObject(string);
            data1 = object.optJSONArray(val);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return data1;
    }

    /**
     * 解析人员头像
     * @param string
     * @return
     */
    public static String JSONPersonImgAnalysis(String string) {
        try {
            object = new JSONObject(string);
            data = object.optString("PICPATH");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return data;
    }

    /**
     * 将数据存到文件中
     *
     * @param context  context
     * @param data     需要保存的数据
     * @param fileName 文件名
     */
    public static void saveDataToFile(Context context, String data, String fileName) {
        FileOutputStream fileOutputStream = null;
        BufferedWriter bufferedWriter = null;
        try {
            /**
             "data"为文件名,MODE_PRIVATE表示如果存在同名文件则覆盖，
             还有一个MODE_APPEND表示如果存在同名文件则会往里面追加内容
             */
            fileOutputStream = context.openFileOutput(fileName,
                    Context.MODE_PRIVATE);
            bufferedWriter = new BufferedWriter(
                    new OutputStreamWriter(fileOutputStream));
            bufferedWriter.write(data);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufferedWriter != null) {
                    bufferedWriter.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 得到json文件中的内容
     *
     * @param context
     * @param fileName
     * @return
     */
    public static String getJson(Context context, String fileName) {
        FileInputStream in = null;
        BufferedReader reader = null;
        StringBuilder content = new StringBuilder();
        try{
            in = context.openFileInput(fileName);//文件名
            reader = new BufferedReader(new InputStreamReader(in));
            String line = "";
            while ((line = reader.readLine()) != null){
                content.append(line);
            }
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            if (reader !=null){
                try{
                    reader.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
        return content.toString();
    }
}
