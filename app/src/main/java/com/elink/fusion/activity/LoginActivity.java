package com.elink.fusion.activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.elink.fusion.R;
import com.elink.fusion.bean.BaseBean;
import com.elink.fusion.log.L;
import com.elink.fusion.presenter.LoginPresenter;
import com.elink.fusion.util.ToastUtil;
import com.elink.fusion.view.DataView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * @author Evloution
 * @date 2020-01-21
 * @email 15227318030@163.com
 * @description
 */
public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.login_username)
    EditText loginUsername;
    @BindView(R.id.login_password)
    EditText loginPassword;
    @BindView(R.id.login_btn)
    Button loginBtn;

    private LoginPresenter loginPresenter = null;
    private ProgressDialog progressDialog;
    private String user = null;
    private String password = null;
    private SharedPreferences sp = null;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        initView();
        initEvent();
    }

    private void initView() {
        progressDialog = new ProgressDialog(this);
        // progressDialog.setIndeterminateDrawable(getResources().getDrawable(R.drawable.bgcolor));
        sp = getSharedPreferences("loginInfo", 0);
        editor = sp.edit();
        loginPresenter = new LoginPresenter(this);
        loginPresenter.onCreate();
        String isAutoLogin = sp.getString("isAutoLogin", "default");
        L.e("isAutoLogin:" + isAutoLogin);
        if (isAutoLogin == "1" || "1".equals(isAutoLogin)) {
            user = sp.getString("user", "default");
            password = sp.getString("password", "default");
            loginUsername.setText(user);
            loginPassword.setText(password);
            initData();
        }
    }

    private void initData() {
        loginPresenter.userLoginPresenter(user, password);
        loginPresenter.attachView(new DataView<BaseBean>() {
            @Override
            public void onSuccess(BaseBean TBean) {
                ToastUtil.show(getApplicationContext(), TBean.msg);
                editor.putString("user", user);
                editor.putString("password", password);
                editor.putString("isAutoLogin", "1");
                editor.commit();
                L.e("onSuccessmsg：" + TBean.msg);
                L.e("onSuccesscode：" + TBean.code);
                L.e("onSuccessdata：" + TBean.data);
                startActivity(new Intent(LoginActivity.this, FlowActivity.class));
                finish();
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
                progressDialog = ProgressDialog.show(LoginActivity.this,
                        "", "正在登录...");
            }

            @Override
            public void hideProgress() {
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
            }
        });
    }

    private void initEvent() {

    }

    @OnClick(R.id.login_btn)
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.login_btn:
                user = loginUsername.getText().toString().trim();
                password = loginPassword.getText().toString().trim();
                if (TextUtils.isEmpty(user)) {
                    ToastUtil.show(LoginActivity.this, "请输入账号");
                } else if (TextUtils.isEmpty(password)) {
                    ToastUtil.show(LoginActivity.this, "请输入密码");
                } else {
                    initData();
                }
                break;
        }
    }
}
