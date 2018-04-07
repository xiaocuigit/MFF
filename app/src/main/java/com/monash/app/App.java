package com.monash.app;

import android.app.Application;
import android.content.Context;

import com.monash.app.bean.User;

import org.litepal.LitePal;

/**
 * Created by abner on 2018/4/7.
 */

public class App extends Application {

    private static Context context;
    private static User user;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        LitePal.initialize(this);
    }

    public static User getUser() {
        if (user != null)
            return user;
        else
            return null;
    }

    public static void setUser(User mUser) {
        user = mUser;
    }

    public static Context getContext() {
        return context;
    }
}
