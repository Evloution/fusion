package com.elink.fusion.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.elink.fusion.R;
import com.elink.fusion.constants.Constants;
import com.elink.fusion.log.L;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author Evloution
 * @date 2020-01-21
 * @email 15227318030@163.com
 * @description
 */
public class FlownodActivity extends AppCompatActivity {

    @BindView(R.id.flownod_webview)
    WebView flownodWebview;
    @BindView(R.id.flownod_title_txt)
    TextView flownodTitleTxt;
    @BindView(R.id.progressbar)
    ProgressBar progressbar;
    @BindView(R.id.back_img)
    ImageView backImg;

    private String instanceId = null;
    private String title = null;
    // 服务器网页地址
    private String flowChartUrl = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flownod);
        ButterKnife.bind(this);

        // 获取标题
        title = getIntent().getStringExtra("title");
        // 获取ID
        instanceId = getIntent().getStringExtra("instanceId");
        flownodTitleTxt.setText(title);
        isWhichUrl();
        initView();
        initData();
    }

    /**
     * 50d8b636afc05715ac6c46ccd51a9b22 - 邢台市生态环保局邢台县分局-建设项目环境影响报告书
     * b95a3ea00da84ea1a304280549b02b89 - 邢台市生态环保局邢台县分局-建设项目环境影响报告表
     * a06fc6b0ed1c4fe49dccb19815df9883 - 邢台市生态环保局邢台县分局-省版排污许可证
     * 6edec5c5464444e7936ad0bd21ec5a3f - 邢台市生态环保局邢台县分局-国版排污许可证
     * <p>
     * "http://192.168.0.103/views/flowchart/flowchart1.html"
     * 判断要加载哪个网页
     */
    private void isWhichUrl() {
        String url = Constants.BASE_URL;
        if (Constants.FLOW_ID == "50d8b636afc05715ac6c46ccd51a9b22" || "50d8b636afc05715ac6c46ccd51a9b22".equals(Constants.FLOW_ID)) {
            // 邢台市生态环保局邢台县分局-建设项目环境影响报告书
            flowChartUrl = url + "views/flowchart/flow_report_Android.html";
        } else if (Constants.FLOW_ID == "b95a3ea00da84ea1a304280549b02b89" || "b95a3ea00da84ea1a304280549b02b89".equals(Constants.FLOW_ID)) {
            // 邢台市生态环保局邢台县分局-建设项目环境影响报告表
            flowChartUrl = url + "views/flowchart/flow_reportform_Android.html";
        } else if (Constants.FLOW_ID == "a06fc6b0ed1c4fe49dccb19815df9883" || "a06fc6b0ed1c4fe49dccb19815df9883".equals(Constants.FLOW_ID)) {
            // 邢台市生态环保局邢台县分局-省版排污许可证
            flowChartUrl = url + "views/flowchart/flow_province_Android.html";
        } else if (Constants.FLOW_ID == "6edec5c5464444e7936ad0bd21ec5a3f" || "6edec5c5464444e7936ad0bd21ec5a3f".equals(Constants.FLOW_ID)) {
            // 邢台市生态环保局邢台县分局-国版排污许可证
            flowChartUrl = url + "views/flowchart/flow_country_Android.html";
        }
    }

    private void initView() {
        //String optionString = "309d0e9c8c2f40fc80b27afe2e6276ad";
        L.e("instanceId:" + instanceId);
        // 自适应屏幕
        flownodWebview.getSettings()
                .setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        flownodWebview.getSettings().setLoadWithOverviewMode(true);
        // 支持javascript
        flownodWebview.getSettings().setJavaScriptEnabled(true);
        // 设置可以支持缩放
        flownodWebview.getSettings().setSupportZoom(false);
        // 清缓存和记录，缓存引起的白屏
        flownodWebview.clearCache(true);
        flownodWebview.clearHistory();
        flownodWebview.requestFocus();
        WebSettings webSettings = flownodWebview.getSettings();
        webSettings.setJavaScriptEnabled(true);//设置webview支持调用js方法
        //android_asset这个目录是加载的你assets目录或者网页目录
        flownodWebview.loadUrl(flowChartUrl);
        //系统默认会通过手机浏览器打开网页，为了能够直接通过WebView显示网页，则必须设置
        flownodWebview.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                refreshBarEchartsWithOption(instanceId);
            }
        });
    }

    private void initData() {
    }

    /**
     * 加载js数据
     *
     * @param optionString
     */
    public void refreshBarEchartsWithOption(String optionString) {
        Log.e("optionString", optionString);
        String call = "javascript:loadwindow('" + optionString + "');";
        flownodWebview.loadUrl(call);
    }

    @OnClick(R.id.back_img)
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_img:
                finish();
                break;
        }
    }
}
