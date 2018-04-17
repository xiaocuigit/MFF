package com.monash.app.utils;

import java.security.PublicKey;

/**
 * Created by abner on 2018/4/9.
 *
 */

public class ConfigUtil {
    // GET PUT POST DELETE URL
    private static String BASE_PROFILE_URL = "http://192.168.1.112:8080/friendfinder/webresources/app.profile/";
    private static String BASE_LOCATION_URL = "http://192.168.1.112:8080/friendfinder/webresources/app.location/";
    private static String BASE_FRIEND_URL = "http://192.168.1.112:8080/friendfinder/webresources/app.friendship/";

    public static String POST_USER_REGISTER = BASE_PROFILE_URL + "register/";
    public static String POST_USER_UPDATE = BASE_PROFILE_URL + "";
    public static String GET_SEARCH_FRIENDS = BASE_PROFILE_URL + "findByAnyAttribute/";
    public static String GET_USER_BY_EMAIL = BASE_PROFILE_URL + "findByEmail/";
    public static String GET_UNITS_REPORTS = BASE_PROFILE_URL + "countFavUnit";
    public static String GET_USER_FRIENDS = BASE_FRIEND_URL + "searchFriends/";
    public static String GET_LOCATION_REPORT = BASE_LOCATION_URL + "countFrequency/";

    public static String GET_FRIEND_BY_ID = BASE_FRIEND_URL + "findByFriendshipID/";
    public static String POST_ADD_FRIEND = BASE_FRIEND_URL + "addFriend/";

    public static String updateUserInfoURL = "";
    public static String deleteFriendURL = "";



    // EVENT TYPE
    public static  int EVENT_LOGIN = 0;
    public static int EVENT_SIGN_UP = 1;
    public static int EVENT_EDIT_USER_INFO = 2;

    public static int EVENT_LOAD_CURRENT_WEATHER = 3;
    public static int EVENT_LOAD_PREDICT_WEATHER = 4;
    public static int EVENT_LOAD_IMAGE = 5;
    public static int EVENT_GET_UNITS_REPORT = 6;
    public static int EVENT_GET_LOCATION_REPORT = 7;
    public static int EVENT_ADD_FRIEND = 8;
    public static int EVENT_SEARCH_FRIENDS = 9;
    public static int EVENT_GET_USER_FRIENDS = 10;
}
