package com.elink.fusion.bean;

import java.util.List;

/**
 * @Description：服务器返回的值为数组时用这个bean
 * @Author： Evloution_
 * @Date： 2019-12-05
 * @Email： 15227318030@163.com
 */
public class BaseDataListBean<T> {

    /**
     * msg : 获取成功
     * code : 0
     * data : [{"EVENTTIME":"2020-01-13 15:55:00","ID":"802","IP":"13.111.18.33","ISPRIMARY":1,"LATITUDE":null,"LONGITUDE":null,"MONIINTERVAL":3600,"MONIPAUSE":0,"MONITYPE":0,"NAME":"中兴大街-守敬路西卡口1","POINTTYPE":1,"PORT":0,"ROWNUM_":1,"STATUS":0,"WARNGRADE":4}]
     * count : 2029
     */

    public String msg;
    public int code;
    public List<T> data;
    public String count;

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
