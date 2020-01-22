package com.elink.fusion.view;


/**
 * @Description：
 * @Author： Evloution_
 * @Date： 2019-11-28
 * @Email： 15227318030@163.com
 */
public interface DataView<T> extends View {
    void onSuccess(T TBean);
    void onError(String error);
    void showProgress();
    void hideProgress();
}
