package com.monash.app.ui.activity;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;

import com.monash.app.App;
import com.monash.app.bean.User;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

/**
 * Created by abner on 2018/4/5.
 */

public abstract class BaseActivity extends AppCompatActivity {

    public User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutView());
        user = App.getUser();
    }

    protected abstract
    @LayoutRes
    int getLayoutView();

}
