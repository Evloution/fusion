package com.elink.fusion.presenter;

import android.content.Context;

import com.elink.fusion.bean.BaseBean;
import com.elink.fusion.bean.BaseDataListBean;
import com.elink.fusion.bean.VersionBean;
import com.elink.fusion.log.L;
import com.elink.fusion.model.DataModel;
import com.elink.fusion.util.ErrorUtil;
import com.elink.fusion.view.DataView;
import com.elink.fusion.view.View;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author Evloution
 * @date 2020-01-13
 * @email 15227318030@163.com
 * @description 告警信息的确认
 */
public class VersionPresenter implements Presenter {

    private Context context;
    private DataModel dataModel = null;
    private CompositeDisposable compositeDisposable = null;
    private DataView<BaseBean<VersionBean>> dataView = null;
    private BaseBean<VersionBean> baseDataListBean = null;

    public VersionPresenter(Context context) {
        this.context = context;
    }

    @Override
    public void onCreate() {
        dataModel = new DataModel(context);
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void attachView(View view) {
        dataView = (DataView<BaseBean<VersionBean>>) view;
        // 弹加载框框
        dataView.showProgress();
    }

    /**
     *
     */
    public void getNewVersionPresenter(String name) {
        Observable<BaseBean<VersionBean>> getNewVersionObservable = dataModel.getNewVersionObservable(name);
        getNewVersionObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseBean<VersionBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        L.e("MonitoringPointPresenter  onSubscribe");
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(BaseBean<VersionBean> baseDataListBeans) {
                        L.e("onNext");
                        baseDataListBean = baseDataListBeans;
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        // 关闭加载框
                        dataView.hideProgress();
                        L.e("Presenter onError：" + ErrorUtil.requestHandle(e));
                        dataView.onError(ErrorUtil.requestHandle(e));
                    }

                    @Override
                    public void onComplete() {
                        L.e("onComplete!");
                        // 关闭加载框
                        dataView.hideProgress();
                        if (dataView != null) {
                            dataView.onSuccess(baseDataListBean);
                            L.e("baseDataListBean:" + baseDataListBean);
                        }
                    }
                });
    }

    @Override
    public void onDestory() {
        if (compositeDisposable != null && !compositeDisposable.isDisposed()) {
            compositeDisposable.dispose(); //解除订阅
            compositeDisposable.clear(); //取消所有订阅
            compositeDisposable = null;
        }
    }
}
