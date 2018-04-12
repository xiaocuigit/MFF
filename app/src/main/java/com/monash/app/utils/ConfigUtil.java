package com.monash.app.utils;

/**
 * Created by abner on 2018/4/9.
 *
 */

public class ConfigUtil {
    public static String BASE_PROFILE_URL = "http://192.168.1.112:8080/friendfinder/webresources/app.profile/";
    public static String BASE_LOCATION_URL = "http://192.168.1.112:8080/friendfinder/webresources/app.location/";

    public static String USER_REGISTER = BASE_PROFILE_URL + "register/";
    public static String USER_UPDATE = BASE_PROFILE_URL + "";

    public static String GET_USER_BY_EMAIL = BASE_PROFILE_URL + "findByEmail/";
    public static String GET_UNITS_REPORTS = BASE_PROFILE_URL + "countFavUnit";
    public static String GET_LOCATION_REPORT = BASE_LOCATION_URL + "countFrequency/";
    public static String updateUserInfoURL = "";
    public static String deleteFriendURL = "";

    public static  int EVENT_LOGIN = 0;
    public static int EVENT_SIGN_UP = 1;
    public static int EVENT_EDIT_USER_INFO = 2;

    public static int EVENT_LOAD_CURRENT_WEATHER = 3;
    public static int EVENT_LOAD_PREDICT_WEATHER = 4;
    public static int EVENT_LOAD_IMAGE = 5;
    public static int EVENT_GET_UNITS_REPORT = 6;
    public static int EVENT_GET_LOCATION_REPORT = 7;
}
