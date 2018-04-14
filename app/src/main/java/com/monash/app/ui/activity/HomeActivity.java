package com.monash.app.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.AppCompatImageView;
import android.text.TextUtils;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextClock;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationListener;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.monash.app.R;
import com.monash.app.bean.weather.CurrentWeather;
import com.monash.app.bean.weather.PredictWeather;
import com.monash.app.bean.weather.WeatherDaily;
import com.monash.app.utils.ConfigUtil;
import com.monash.app.utils.EventUtil;
import com.monash.app.utils.HttpUtil;
import com.monash.app.utils.WeatherUtil;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class HomeActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.image_day) AppCompatImageView image_day;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.nav_view) NavigationView navigationView;
    @BindView(R.id.drawer_layout) DrawerLayout drawer;
    @BindView(R.id.tc_current_time) TextClock tcCurrentTime;
    @BindView(R.id.tv_current_weather) TextView tvCurrentWeather;
    @BindView(R.id.tv_predict_weather) TextView tvPredictWeather;
    @BindView(R.id.tv_get_location) TextView tvLocation;

    private String latitude = "31.27";
    private String longitude = "120.73";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        Logger.addLogAdapter(new AndroidLogAdapter());

        initDrawer();
        initHeader();

        initWeatherInfo();
        tcCurrentTime.setFormat12Hour("yyyy-MM-dd hh:mm, EEEE");
    }

    private void initMap() {
        AMapLocationClient mapLocationClient = new AMapLocationClient(getApplicationContext());
        AMapLocationListener mapLocationListener = new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
                if (aMapLocation != null){
                    if (aMapLocation.getErrorCode() == 0){
                        Logger.d(aMapLocation.getLatitude() + ", " + aMapLocation.getLongitude());

                        latitude = String.valueOf(aMapLocation.getLatitude());
                        longitude = String.valueOf(aMapLocation.getLongitude());
                        tvLocation.setText(String.valueOf(aMapLocation.getLatitude()) + "， " + String.valueOf(aMapLocation.getLongitude()));
                    }
                }
            }
        };
        mapLocationClient.setLocationListener(mapLocationListener);
        mapLocationClient.startLocation();
    }

    private void initWeatherInfo() {
        if (TextUtils.isEmpty(latitude) || TextUtils.isEmpty(longitude)){
            Logger.d("Get current location failed.");
            return;
        }
        // 向服务器请求天气信息
        HttpUtil.getInstance().get(WeatherUtil.getCurrentUrl(latitude, longitude),
                ConfigUtil.EVENT_LOAD_CURRENT_WEATHER);
        HttpUtil.getInstance().get(WeatherUtil.getPredictUrl(latitude, longitude, 3),
                ConfigUtil.EVENT_LOAD_PREDICT_WEATHER);
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }


    @OnClick(R.id.fab)
    void addFriends(View view){
        initMap();
        Snackbar.make(view, "Get Location", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    @OnClick(R.id.btn_load_image)
    void loadImage(){
        // 通过此URL获取必应每日图片的URL地址
        String url = "http://cn.bing.com/HPImageArchive.aspx?format=js&idx=0&n=1";
        HttpUtil.getInstance().get(url, ConfigUtil.EVENT_LOAD_IMAGE);

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    void showImage(EventUtil eventUtil){
        if (eventUtil.getEventType() == ConfigUtil.EVENT_LOAD_IMAGE){
            String jsonString = eventUtil.getResult();
            String imageURL = "http://s.cn.bing.net" + getImageURL(jsonString);
            Logger.d("jsonArray : " + jsonString);
            if(!imageURL.equals("http://s.cn.bing.net")) {
                Glide.with(HomeActivity.this)
                        .load(imageURL)
                        .into(image_day);
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    void getCurrentWeather(EventUtil eventUtil){
        if(eventUtil.getEventType() == ConfigUtil.EVENT_LOAD_CURRENT_WEATHER) {
            try {
                JSONObject jsonObject = new JSONObject(eventUtil.getResult());
                JSONArray jsonArray = jsonObject.getJSONArray("results");
                String jsonObjStr = jsonArray.getJSONObject(0).toString();
                Gson gson = new Gson();
                CurrentWeather currentWeather = gson.fromJson(jsonObjStr, CurrentWeather.class);

                String cityName = currentWeather.getWeatherLocation().getCity_name();
                String temperature = currentWeather.getWeatherNow().getTemperature();
                String weather = currentWeather.getWeatherNow().getWeather_now();
                String humidity = currentWeather.getWeatherNow().getHumidity();
                if (cityName != null && temperature != null) {
                    Logger.d("now");
                    Logger.d(cityName + ":" + weather + ", " + temperature + ", " + humidity);
                    tvCurrentWeather.setText(cityName + ":" + weather + ", " + temperature + ", " + humidity);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getPredictWeather(EventUtil eventUtil){
        if(eventUtil.getEventType() == ConfigUtil.EVENT_LOAD_PREDICT_WEATHER) {
            try {
                JSONObject jsonObject = new JSONObject(eventUtil.getResult());
                JSONArray jsonArray = jsonObject.getJSONArray("results");
                String jsonObjStr = jsonArray.getJSONObject(0).toString();
                Gson gson = new Gson();
                PredictWeather predictWeather = gson.fromJson(jsonObjStr, PredictWeather.class);

                List<WeatherDaily> weatherDailies = predictWeather.getWeatherDailyList();
                if(weatherDailies != null){
                    int days = weatherDailies.size();
                    if(days > 2){
                        Logger.d("predict");
                        Logger.d(weatherDailies.get(1).getText_day() + " : " + weatherDailies.get(1).getDate());
                        tvPredictWeather.setText(weatherDailies.get(1).getText_day() + " : " + weatherDailies.get(1).getDate());
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    protected int getLayoutView() {
        return R.layout.activity_home;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {

        } else if (id == R.id.nav_profile) {
            Intent intent = new Intent(this, ProfileActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_friends) {
            startActivity(new Intent(this, FriendsActivity.class));
        } else if (id == R.id.nav_search) {
            startActivity(new Intent(this, SearchActivity.class));
        } else if (id == R.id.nav_report) {
            startActivity(new Intent(this, ReportActivity.class));
        } else if (id == R.id.nav_logout) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_about_us) {

        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void initDrawer() {
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void initHeader() {
        View drawerView = navigationView.inflateHeaderView(R.layout.nav_header_home);

        ImageView userNameImage = drawerView.findViewById(R.id.header_image);
        TextView userName = drawerView.findViewById(R.id.header_user_name);
        TextView userEmail = drawerView.findViewById(R.id.header_user_email);
        if(user != null){
            String userFullName = user.getFirstName() + " " + user.getSurName();
            userName.setText(userFullName);
            userEmail.setText(user.getEmail());
        } else {
            Logger.d("user is null");
        }
    }

    private String getImageURL(String jsonString) {
        try {
            JSONArray jsonArray = new JSONObject(jsonString).getJSONArray("images");
            for(int i = 0; i < jsonArray.length(); i++){
                JSONObject object = jsonArray.getJSONObject(i);
                if(object.has("url")){
                    return object.getString("url");
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
