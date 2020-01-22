package com.elink.fusion.observer;



import com.elink.fusion.bean.BaseBean;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeoutException;

/**
 * @Description：
 * @Author： Evloution_
 * @Date： 2019-11-29
 * @Email： 15227318030@163.com
 */
public abstract class HttpResultSubscriber<T> implements Subscriber<BaseBean<T>> {
    @Override
    public void onSubscribe(Subscription s) {

    }

    @Override
    public void onNext(BaseBean<T> tBaseBean) {
        if (tBaseBean.isSuccess()) {
            //onSuccess(tBaseBean.getData());
        } else {
            _onError(tBaseBean.getCode());
        }

    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        //在这里做全局的错误处理
        if (e instanceof ConnectException ||
                e instanceof SocketTimeoutException ||
                e instanceof TimeoutException) {
            //网络错误
            _onError(-9999);
        }
    }

    @Override
    public void onComplete() {

    }

    public abstract void onSuccess(T t);

    public abstract void _onError(int status);
}
