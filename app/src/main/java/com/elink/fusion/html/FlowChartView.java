package com.elink.fusion.html;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;

/**
 * @author Evloution
 * @date 2020-01-21
 * @email 15227318030@163.com
 * @description
 */
public class FlowChartView extends WebView {
    public FlowChartView(Context context) {
        this(context, null);
    }

    public FlowChartView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FlowChartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        WebSettings webSettings = getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setSupportZoom(false);
        webSettings.setDisplayZoomControls(false);
        loadUrl("file:///android_asset/flowchart1.html");
    }

    public void refreshBarEchartsWithOption(String optionString) {
        Log.e("optionString", optionString);
        String call = "javascript:loadwindow('" + optionString + "');";
        loadUrl(call);
    }
}
