package com.example.zengy.efficencytool.core.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.zengy.efficencytool.R;
import com.hannesdorfmann.mosby3.mvp.MvpActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends MvpActivity<MainView, MainPresenter> implements MainView {
    @BindView(R.id.bottomLayout)
    RadioGroup bottomLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        bottomLayout.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rbOne:
                        Toast.makeText(MainActivity.this, "rbOne", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.rbTwo:
                        Toast.makeText(MainActivity.this, "rbTwo", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.rbThree:
                        Toast.makeText(MainActivity.this, "rbThree", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.rbFour:
                        Toast.makeText(MainActivity.this, "rbFour", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }
            }
        });

    }

    @NonNull
    @Override
    public MainPresenter createPresenter() {
        return new MainPresenter();
    }
}