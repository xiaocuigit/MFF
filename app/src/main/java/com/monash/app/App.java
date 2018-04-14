package com.monash.app;

import android.app.Application;
import android.content.Context;

import com.monash.app.bean.User;

import org.litepal.LitePal;

import java.util.List;

/**
 * Created by abner on 2018/4/7.
 */

public class App extends Application {

    private static User user;
    private static List<User> users;

    @Override
    public void onCreate() {
        super.onCreate();
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

    public static List<User> getUsers() {
        return users;
    }

    public static void setUsers(List<User> users) {
        App.users = users;
    }
}
