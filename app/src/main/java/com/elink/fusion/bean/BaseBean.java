package com.elink.fusion.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @Description：主 Bean, 其它的 Bean 都继承这个 Bean,为防止成功失败传输的数据不一致处理
 * @Author： Evloution_
 * @Date： 2019-11-29
 * @Email： 15227318030@163.com
 */
public class BaseBean<T> implements Parcelable {
    public String msg;
    public int code;
    public T data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    /**
     * 判断请求是否成功
     * 0 成功   1 失败
     *
     * @return bool
     */
    public boolean isSuccess() {
        return getCode() == 0;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.code);
        dest.writeString(this.msg);
    }

    public BaseBean() {
    }

    protected BaseBean(Parcel in) {
        this.code = in.readInt();
        this.msg = in.readString();
    }

    public static final Creator<BaseBean> CREATOR = new Creator<BaseBean>() {
        @Override
        public BaseBean createFromParcel(Parcel in) {
            return new BaseBean(in);
        }

        @Override
        public BaseBean[] newArray(int size) {
            return new BaseBean[size];
        }
    };
}
