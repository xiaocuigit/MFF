package com.monash.app.utils;

import com.google.gson.Gson;
import com.monash.app.bean.weather.CurrentWeather;
import com.monash.app.bean.weather.PredictWeather;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by abner on 2018/4/6.
 */

public class WeatherUtil {
    private String LANGUAGE = "zh-Hans";
    private String UNIT = "c";
    private String KEY = "tvskoyndgiuabzet";

    private static WeatherUtil instance;
    private Gson gson;

    private WeatherUtil(){
        gson = new Gson();
    }

    /**
     * 单例模式
     * @return
     */
    public static WeatherUtil getInstance(){
        return instance == null ? instance = new WeatherUtil() : instance;
    }

    /**
     * 获取访问心知天气服务器未来几天的天气信息的URL
     * @param lat   纬度
     * @param lon   经度
     * @param days  预测的天数
     * @return
     */
    private String getPredictUrl(String lat, String lon, Integer days){
        return "https://api.seniverse.com/v3/weather/daily.json?key=" + KEY +
                "&language=" + LANGUAGE + "&unit=" + UNIT + "&location=" + lat + ":" + lon + "&start=0&days=" + days.toString();
    }

    /**
     * 获取访问心知天气服务器天气实时信息的URL
     * @param lat  纬度
     * @param lon  经度
     * @return
     */
    private String getCurrentUrl(String lat, String lon){
        return "https://api.seniverse.com/v3/weather/now.json?key=" + KEY +
                "&language=" + LANGUAGE + "&unit=" + UNIT + "&location=" + lat + ":" + lon;
    }

    /**
     * 从知心服务器返回的天气数据在result数组里面存放
     * @param url
     * @return
     */
    private String handUrlResponse(String url){
        String result = HttpUtil.getInstance().get(url);
        try {
            JSONObject jsonObject = new JSONObject(result);
            JSONArray jsonArray = jsonObject.getJSONArray("results");
            return jsonArray.getJSONObject(0).toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取当前天气实况，并发送给消息订阅者
     * @param lat
     * @param lon
     * @return
     */
    public void handleCurrentWeatherInfo(String lat, String lon){
        String result = handUrlResponse(getCurrentUrl(lat, lon));
        if(result != null){
            CurrentWeather currentWeather = gson.fromJson(result, CurrentWeather.class);
            if(currentWeather != null){
                EventBus.getDefault().post(currentWeather);
            }
        }
    }

    /**
     * 获取逐日天气预报，并发送给消息订阅者
     * @param lat
     * @param lon
     * @param days
     */
    public void handlePredictWeatherInfo(String lat, String lon, int days){
        String result = handUrlResponse(getPredictUrl(lat, lon, days));
        if(result != null){
            PredictWeather predictWeather = gson.fromJson(result, PredictWeather.class);
            if(predictWeather != null){
                EventBus.getDefault().post(predictWeather);
            }
        }
    }
}
