package com.monash.app.ui.activity;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;

import com.monash.app.bean.User;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

/**
 * Created by abner on 2018/4/5.
 */

public abstract class BaseActivity extends AppCompatActivity {

    private static User user = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutView());
    }

    protected abstract
    @LayoutRes
    int getLayoutView();

    public static void initUser(User initUser){
        if(user == null){
            user = initUser;
        }
    }

    public static User getUser(){
        return user;
    }
}
