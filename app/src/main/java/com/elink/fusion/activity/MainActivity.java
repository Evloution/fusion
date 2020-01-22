package com.elink.fusion.activity;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.elink.fusion.R;
import com.elink.fusion.bean.BaseDataListBean;
import com.elink.fusion.bean.FlowBean;
import com.elink.fusion.log.L;
import com.elink.fusion.presenter.FlowPresenter;
import com.elink.fusion.util.ToastUtil;
import com.elink.fusion.view.DataView;

public class MainActivity extends AppCompatActivity {

    private FlowPresenter flowPresenter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initData();
    }

    private void initView() {
        flowPresenter = new FlowPresenter(this);
        flowPresenter.onCreate();
    }

    private void initData() {
        flowPresenter.getFlowListPresenter();
        flowPresenter.attachView(new DataView<BaseDataListBean<FlowBean>>() {

            @Override
            public void onSuccess(BaseDataListBean<FlowBean> TBean) {
                L.e("onSuccess：" + TBean.data.get(0).NAME);
            }

            @Override
            public void onError(String error) {
                L.e("onError：" + error);
                ToastUtil.show(getApplicationContext(), error);
            }

            @Override
            public void showProgress() {

            }

            @Override
            public void hideProgress() {

            }
        });
    }
}
