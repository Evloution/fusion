package com.elink.fusion.presenter;


import com.elink.fusion.view.View;

/**
 * @Description：基类方法用于Activity或Fragment在onDestory解绑View
 * 所有presenter的基类，子类实现其方法，
 * @Author： Evloution_
 * @Date： 2019-11-27
 * @Email： 15227318030@163.com
 */
public interface Presenter {
    void onCreate();
    void attachView(View view);
    void onDestory();
}
