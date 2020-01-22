package com.elink.fusion.retrofit;

import com.elink.fusion.bean.BaseBean;
import com.elink.fusion.bean.BaseDataListBean;
import com.elink.fusion.bean.FlowBean;
import com.elink.fusion.bean.InstanceBean;
import com.elink.fusion.bean.VersionBean;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * @Description：管理Retrofit的各种数据请求(post、get)，包含请求api、请求参数
 * @Author： Evloution_
 * @Date： 2019-11-27
 * @Email： 15227318030@163.com
 */
public interface RetrofitApi {

    /**
     * 用户登陆
     *
     * @param user     用户名
     * @param password 密码
     * @return
     */
    @POST("admin/login")
    @FormUrlEncoded
    Observable<BaseBean> userLogin(@Field("user") String user, @Field("password") String password);

    /**
     * 获取所有流程组
     *
     * @return
     */
    @POST("flowchart/getFlowList")
    Observable<BaseDataListBean<FlowBean>> getFlowList();

    /**
     * 获取流程下的项目
     *
     * @param flowId 流程id
     * @return
     */
    @POST("flowchart/getInstance")
    @FormUrlEncoded
    Observable<BaseDataListBean<InstanceBean>> getInstance(@Field("flowId") String flowId);

    /**
     * 检查更新
     *
     * @param name APP名称
     * @return
     */
    @POST("admin/version/getNewVersion")
    @FormUrlEncoded
    Observable<BaseBean<VersionBean>> getNewVersion(@Field("name") String name);
}
