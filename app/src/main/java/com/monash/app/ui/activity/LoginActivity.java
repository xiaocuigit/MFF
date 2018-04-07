package com.monash.app.ui.activity;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.TextView;

import com.monash.app.App;
import com.monash.app.R;
import com.monash.app.bean.User;
import com.monash.app.bean.weather.CurrentWeather;
import com.monash.app.bean.weather.PredictWeather;
import com.monash.app.bean.weather.WeatherDaily;
import com.monash.app.utils.GsonUtil;
import com.monash.app.utils.HttpUtil;
import com.monash.app.utils.WeatherUtil;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.input_email) TextInputEditText user_email;

    @BindView(R.id.input_password) TextInputEditText user_password;

    @BindView(R.id.tv_signup_account) TextView sign_up_account;

    @BindView(R.id.tv_forget_password) TextView forget_password;

    private String userEmail;
    private String userPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);
        Logger.addLogAdapter(new AndroidLogAdapter());
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

    @OnClick(R.id.btn_login)
    void login(){
//        String lat = "31.27";
//        String lon = "120.73";
//        WeatherUtil.getInstance().handleCurrentWeatherInfo(lat, lon);
//        WeatherUtil.getInstance().handlePredictWeatherInfo(lat, lon, 3);

        if(validateInput()){
            if(isAuthorizedUser()){
                Intent intent = new Intent(this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        }
    }

    @OnClick(R.id.tv_signup_account)
    void createAccount(){
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }

    /**
     * jtao0001@student.monash.edu
     * 123456
     * @return
     */
    private boolean isAuthorizedUser(){
        boolean flag = false;
        String str = "http://192.168.1.108:8080/friendfinder/webresources/app.profile/findByEmail/";

        String userInfo = HttpUtil.getInstance().get(str + userEmail);
        if(!userInfo.equals("")) {
            Logger.d(userInfo);
            User user = GsonUtil.getInstance().getUsers(userInfo).get(0);
            if(user.getPassword().equals(userPassword)){
                Logger.d(user.getPassword() + "\n" + user.getEmail());
                Logger.d(user.getFirstName() + " " + user.getSurName());
                App.setUser(user);
                flag = true;
            }else {
                user_password.setError("Password Incorrect");
                user_password.setText("");
            }
        }
        else {
            Logger.d("error");
            Logger.d(userInfo);
        }
        return flag;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getCurrentWeather(CurrentWeather currentWeather){
        if(currentWeather != null) {
            String cityName = currentWeather.getWeatherLocation().getCity_name();
            String temperature = currentWeather.getWeatherNow().getTemperature();
            String weather = currentWeather.getWeatherNow().getWeather_now();
            String humidity = currentWeather.getWeatherNow().getHumidity();
            if (cityName != null && temperature != null) {
                Logger.d("now");
                Logger.d(cityName + ":" + weather + ", " + temperature + ", " + humidity);
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getPredictWeather(PredictWeather predictWeather){
        if(predictWeather != null) {
            List<WeatherDaily> weatherDailies = predictWeather.getWeatherDailyList();
            if(weatherDailies != null){
                int days = weatherDailies.size();
                if(days > 2){
                    Logger.d("predict");
                    Logger.d(weatherDailies.get(1).getText_day() + " : " + weatherDailies.get(1).getDate());
                }
            }
        }
    }

    private boolean validateInput() {
        userEmail = user_email.getText().toString();
        userPassword = user_password.getText().toString();
        userEmail = "jtao0001@student.monash.edu";
//        userPassword = "123456";

        boolean flag = true;

        if(TextUtils.isEmpty(userEmail)){
            user_email.setError("Please input right email");
            flag = false;
        }
        if(TextUtils.isEmpty(userPassword)){
            user_password.setError("Please input right password");
            flag = false;
        }
        return flag;
    }
}
