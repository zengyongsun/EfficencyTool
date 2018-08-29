package com.example.zengy.efficencytool.core;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.example.common.http.ApiCallback;
import com.example.common.packaging.LoggerUtil;
import com.example.zengy.efficencytool.R;
import com.example.zengy.efficencytool.api.ApiLoader;
import com.example.zengy.efficencytool.api.ApiService;
import com.example.zengy.efficencytool.api.LoginAndRegisterLoader;
import com.example.zengy.efficencytool.bean.LoginBean;
import com.example.zengy.efficencytool.core.main.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.etUserName)
    TextInputEditText etUserName;
    @BindView(R.id.etPassword)
    TextInputEditText etPassword;

    public static final int START_REGISTER = 1000;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        mContext = this;
        initView();
    }

    private void initView() {
        toolbar.setTitle(R.string.login);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

    @OnClick(R.id.btRegister)
    public void onBtRegisterClicked() {
        //注册成功回来，就自动登录，加强用户体验
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivityForResult(intent, START_REGISTER);
    }

    @OnClick(R.id.btLogin)
    public void onBtLoginClicked() {
        String userName = etUserName.getText().toString();
        String password = etPassword.getText().toString();

        ApiLoader.reqLogin(userName, password, new ApiCallback<LoginBean>() {
            @Override
            public void onNext(LoginBean loginBean) {
                Intent intent = new Intent(mContext, MainActivity.class);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onCompleted() {

            }
        });

//        new LoginAndRegisterLoader().login(userName, password).subscribe(new Observer<LoginBean>() {
//            @Override
//            public void onSubscribe(Disposable d) {
//                LoggerUtil.d(d);
//            }
//
//            @Override
//            public void onNext(LoginBean loginBean) {
//                LoggerUtil.d(loginBean.toString());
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                LoggerUtil.d(e.getMessage());
//            }
//
//            @Override
//            public void onComplete() {
//                LoggerUtil.d("onComplete");
//            }
//        });
    }
}
