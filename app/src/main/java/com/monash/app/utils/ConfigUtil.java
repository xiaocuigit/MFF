package com.monash.app.utils;

/**
 * Created by abner on 2018/4/9.
 */

public class ConfigUtil {
    public static String BASE_URL = "http://192.168.1.108:8080/friendfinder/webresources/app.profile/";

    public static String USER_REGISTER = BASE_URL + "register/";
    public static String USER_UPDATE = BASE_URL + "";

    public static String GET_USER_BY_EMAIL = BASE_URL + "findByEmail/";
    public static String updateUserInfoURL = "";
    public static String deleteFriendURL = "";

    public static  int EVENT_LOGIN = 0;
    public static int EVENT_SIGN_UP = 1;
    public static int EVENT_EDIT_USER_INFO = 2;

    public static int EVENT_LOAD_CURRENT_WEATHER = 3;
    public static int EVENT_LOAD_PREDICT_WEATHER = 4;
    public static int EVENT_LOAD_IMAGE = 5;
}
