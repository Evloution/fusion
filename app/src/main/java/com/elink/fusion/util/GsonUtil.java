package com.elink.fusion.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class GsonUtil {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final JsonParser PARSER = new JsonParser();

    public static Gson getGSON() {
        return GSON;
    }

    /**
     * Object 转 json
     * @param object object
     * @return object
     */
    public static String GsonString(Object object){
        return GSON.toJson(object);
    }

    public static <T> T GsonToBean(String gsonString, Class<T> cls) {
        return GSON.fromJson(gsonString, cls);
    }

    /**
     * 转成map的
     *
     * @param gsonString str
     * @return map
     */
    public static Map<String, Object> GsonToMaps(String gsonString) {
        return GSON.fromJson(gsonString, new TypeToken<Map<String, Object>>() {}.getType());
    }

    /**
     * @author I321533
     * @param json
     * @param clazz
     * @return
     */
    public static <T> List<T> jsonToList(String json, Class<T[]> clazz)
    {
        Gson gson = new Gson();
        T[] array = gson.fromJson(json, clazz);
        return Arrays.asList(array);
    }

    /**
     * @param json
     * @param clazz
     * @return
     */
    public static <T> ArrayList<T> jsonToArrayList(String json, Class<T> clazz)
    {
        Type type = new TypeToken<ArrayList<JsonObject>>()
        {}.getType();
        ArrayList<JsonObject> jsonObjects = new Gson().fromJson(json, type);

        ArrayList<T> arrayList = new ArrayList<>();
        for (JsonObject jsonObject : jsonObjects)
        {
            arrayList.add(new Gson().fromJson(jsonObject, clazz));
        }
        return arrayList;
    }

    /**
     * 将json 格式化输出
     * @param str str
     * @return str
     */
    public static String GsonToString(String str){
        try {
            return GSON.toJson(PARSER.parse(str));
        } catch (JsonSyntaxException e){
            e.printStackTrace();
            return str;
        }
    }
}
