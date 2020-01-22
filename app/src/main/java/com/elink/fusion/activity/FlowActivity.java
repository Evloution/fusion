package com.elink.fusion.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.elink.fusion.R;
import com.elink.fusion.adapter.FlowAdapter;
import com.elink.fusion.bean.BaseBean;
import com.elink.fusion.bean.BaseDataListBean;
import com.elink.fusion.bean.FlowBean;
import com.elink.fusion.bean.VersionBean;
import com.elink.fusion.constants.Constants;
import com.elink.fusion.log.L;
import com.elink.fusion.presenter.FlowPresenter;
import com.elink.fusion.presenter.VersionPresenter;
import com.elink.fusion.util.APKVersionCodeUtils;
import com.elink.fusion.util.DownLoadUtils;
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
public class FlowActivity extends AppCompatActivity {

    @BindView(R.id.swiperefreshlayout)
    SwipeRefreshLayout swiperefreshlayout;
    @BindView(R.id.fragment_homepage_reload_btn)
    Button fragmentHomepageReloadBtn;
    @BindView(R.id.fragment_homepage_reload_linearlayout)
    LinearLayout fragmentHomepageReloadLinearlayout;
    @BindView(R.id.flowlist_listview)
    ListView flowlistListview;
    private FlowPresenter flowPresenter = null;
    private VersionPresenter versionPresenter = null;
    private List<FlowBean> flowBeanList = null;
    private FlowAdapter flowAdapter = null;
    private String flowId = null;

    private ProgressDialog progressDialog;

    // APP名字
    private static String appName = null;
    // 新版本号
    private static String newVersion = null;
    // 下载路径
    private static String downLoadPath = null;
    // 更新说明
    private static String publishDesc = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flow);
        ButterKnife.bind(this);

        initView();
        initData(0);
        initEvent();
        //initVersionData(appName);
    }

    private void initView() {
        progressDialog = new ProgressDialog(this);
        flowPresenter = new FlowPresenter(this);
        versionPresenter = new VersionPresenter(this);
        flowBeanList = new ArrayList<>();
        flowPresenter.onCreate();
        versionPresenter.onCreate();
        appName = APKVersionCodeUtils.getAppName(this);
        L.e("APP名称：" + appName);
    }

    private void initData(int code) {
        flowPresenter.getFlowListPresenter();
        flowPresenter.attachView(new DataView<BaseDataListBean<FlowBean>>() {

            @Override
            public void onSuccess(BaseDataListBean<FlowBean> TBean) {
                L.e("onSuccess：" + TBean.data.get(0).NAME);
                if (code == 1) {
                    flowBeanList = new ArrayList<>();
                }
                for (int i = 0; i < TBean.getData().size(); i++) {
                    flowBeanList.add(TBean.getData().get(i));
                }
                flowAdapter = new FlowAdapter(FlowActivity.this, flowBeanList);
                flowlistListview.setAdapter(flowAdapter);
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
                progressDialog = ProgressDialog.show(FlowActivity.this,
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

    private void initVersionData(String name) {
        versionPresenter.getNewVersionPresenter(name);
        versionPresenter.attachView(new DataView<BaseBean<VersionBean>>() {
            @Override
            public void onSuccess(BaseBean<VersionBean> TBean) {
                // 新版本号
                newVersion = TBean.data.version;
                // 下载路径
                downLoadPath = TBean.data.path;
                // 更新说明
                publishDesc = TBean.data.publishdesc;
                L.e("onSuccess path：" + downLoadPath);
                L.e("onSuccess publishdesc：" + publishDesc);
                L.e("onSuccess version：" + newVersion);
                // 比较版本大小 检查是否需要更新
                downLoad(newVersion);
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

    private void initEvent() {
        // 下拉刷新
        swiperefreshlayout.setOnRefreshListener(() -> {
            Log.e("刷新", "下拉刷新");
            initData(1);
        });

        // 列表的点击事件
        flowlistListview.setOnItemClickListener((parent, view, position, id) -> {
            flowId = flowBeanList.get(position).ID;
            L.e("点击的是：" + flowId);
            Constants.FLOW_ID = flowId;
            Intent intent = new Intent(FlowActivity.this, InstanceActivity.class);
            intent.putExtra("flowId", flowId);
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

    @OnClick(R.id.fragment_homepage_reload_btn)
    public void onViewClicked() {
    }

    // 检查是否需要更新
    private void downLoad(String getVersion) {
        if (DownLoadUtils.getVersionInfoFromServer(this, getVersion).equals("1")) {
            DownLoadUtils.showDialog(this, publishDesc, getVersion, Constants.BASE_URL + downLoadPath, appName);
        } else {
            L.e("是最新版本");
        }
    }
}
