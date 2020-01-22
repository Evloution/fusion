package com.elink.fusion.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.elink.fusion.R;
import com.elink.fusion.adapter.InstanceAdapter;
import com.elink.fusion.bean.BaseDataListBean;
import com.elink.fusion.bean.InstanceBean;
import com.elink.fusion.constants.Constants;
import com.elink.fusion.log.L;
import com.elink.fusion.presenter.InstancePresenter;
import com.elink.fusion.util.ToastUtil;
import com.elink.fusion.view.DataView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author Evloution
 * @date 2020-01-21
 * @email 15227318030@163.com
 * @description
 */
public class InstanceActivity extends AppCompatActivity {

    @BindView(R.id.fragment_homepage_reload_btn)
    Button fragmentHomepageReloadBtn;
    @BindView(R.id.fragment_homepage_reload_linearlayout)
    LinearLayout fragmentHomepageReloadLinearlayout;
    @BindView(R.id.instance_listview)
    ListView instanceListview;
    @BindView(R.id.swiperefreshlayout)
    SwipeRefreshLayout swiperefreshlayout;
    @BindView(R.id.back_img)
    ImageView backImg;

    private ProgressDialog progressDialog;

    private InstanceAdapter instanceAdapter = null;
    private List<InstanceBean> instanceBeanList = null;
    private InstancePresenter instancePresenter = null;
    private String flowId = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instance);
        ButterKnife.bind(this);
        flowId = getIntent().getStringExtra("flowId");

        initView();
        initData(0);
        initEvent();
    }

    private void initView() {
        progressDialog = new ProgressDialog(this);
        instancePresenter = new InstancePresenter(this);
        instanceBeanList = new ArrayList<>();
        instancePresenter.onCreate();
    }

    private void initData(int code) {
        instancePresenter.getInstancePresenter(flowId);
        instancePresenter.attachView(new DataView<BaseDataListBean<InstanceBean>>() {

            @Override
            public void onSuccess(BaseDataListBean<InstanceBean> TBean) {
                L.e("onSuccess：" + TBean.data);
                if (code == 1) {
                    instanceBeanList = new ArrayList<>();
                }
                for (int i = 0; i < TBean.getData().size(); i++) {
                    instanceBeanList.add(TBean.getData().get(i));
                }
                instanceAdapter = new InstanceAdapter(InstanceActivity.this, instanceBeanList);
                instanceListview.setAdapter(instanceAdapter);
            }

            @Override
            public void onError(String error) {
                L.e("onError：" + error);
                ToastUtil.show(getApplicationContext(), error);
            }

            @Override
            public void showProgress() {
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                progressDialog = ProgressDialog.show(InstanceActivity.this,
                        "", "正在获取...");
            }

            @Override
            public void hideProgress() {
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
            }
        });
        isCloseLoad();
    }

    private void initEvent() {
        // 下拉刷新
        swiperefreshlayout.setOnRefreshListener(() -> {
            Log.e("刷新", "下拉刷新");
            initData(1);
        });

        // 列表的点击事件
        instanceListview.setOnItemClickListener((parent, view, position, id) -> {
            L.e("点击的流程ID是：" + Constants.FLOW_ID);
            L.e("点击的是：" + instanceBeanList.get(position).ID);
            Intent intent = new Intent(InstanceActivity.this, FlownodActivity.class);
            intent.putExtra("instanceId", instanceBeanList.get(position).ID);
            intent.putExtra("title", instanceBeanList.get(position).TITLE);
            startActivity(intent);
        });
    }

    private void isCloseLoad() {
        // 下拉刷新
        //为了保险起见可以先判断当前是否在刷新中（旋转的小圈圈在旋转）....
        if (swiperefreshlayout.isRefreshing()) {
            //关闭刷新动画
            swiperefreshlayout.setRefreshing(false);
        }
    }

    @OnClick({R.id.fragment_homepage_reload_btn, R.id.back_img})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_img:
                finish();
                break;
        }
    }
}
