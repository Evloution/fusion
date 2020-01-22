package com.elink.fusion.model;

import android.content.Context;

import com.elink.fusion.bean.BaseBean;
import com.elink.fusion.bean.BaseDataListBean;
import com.elink.fusion.bean.FlowBean;
import com.elink.fusion.bean.InstanceBean;
import com.elink.fusion.bean.VersionBean;
import com.elink.fusion.retrofit.RetrofitApi;
import com.elink.fusion.retrofit.RetrofitHelper;

import io.reactivex.Observable;
import retrofit2.http.Field;


/**
 * @Description：
 * @Author： Evloution_
 * @Date： 2019-11-29
 * @Email： 15227318030@163.com
 */
public class DataModel {

    private RetrofitApi retrofitApi;

    public DataModel(Context context) {
        this.retrofitApi = RetrofitHelper.getInstance(context).initRetrofit();
    }

    /**
     * 用户登录
     *
     * @param user
     * @param password
     * @return
     */
    public Observable<BaseBean> userLoginObservable(String user, String password) {
        return retrofitApi.userLogin(user, password);
    }

    /**
     * 获取所有流程组
     *
     * @return
     */
    public Observable<BaseDataListBean<FlowBean>> getFlowListObservable() {
        return retrofitApi.getFlowList();
    }

    /**
     * 获取流程下的项目
     *
     * @param flowId 流程id
     * @return
     */
    public Observable<BaseDataListBean<InstanceBean>> getInstanceObservable(String flowId) {
        return retrofitApi.getInstance(flowId);
    }

    /**
     * 检查更新
     *
     * @param name
     * @return
     */
    public Observable<BaseBean<VersionBean>> getNewVersionObservable(String name) {
        return retrofitApi.getNewVersion(name);
    }
}
