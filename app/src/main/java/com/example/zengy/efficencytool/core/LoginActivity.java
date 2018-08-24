package com.example.zengy.efficencytool.core;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.example.zengy.efficencytool.R;
import com.example.zengy.efficencytool.api.LoginAndRegisterLoader;
import com.example.zengy.efficencytool.bean.LoginBean;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.etUserName)
    TextInputEditText etUserName;
    @BindView(R.id.etPassword)
    TextInputEditText etPassword;

    public static final int START_REGISTER = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
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
        new LoginAndRegisterLoader().login(userName,password).subscribe(new Consumer<LoginBean>() {
            @Override
            public void accept(LoginBean loginBean) throws Exception {
                if (loginBean.errorCode == 0){
                    Toast.makeText(LoginActivity.this, "登陆成功", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
