package com.monash.app.ui.activity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import com.monash.app.R;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

public class ProfileActivity extends BaseActivity {

    @BindView(R.id.tv_studID) TextView userID;
    @BindView(R.id.tv_email) TextView userEmail;
    @BindView(R.id.tv_studentName) TextView userFullName;
    @BindView(R.id.tv_birthDate) TextView userBirthDate;
    @BindView(R.id.rg_gender) RadioGroup userGenderGroup;
    @BindView(R.id.rb_secret)RadioButton secret;
    @BindView(R.id.rb_female) RadioButton female;
    @BindView(R.id.rb_male) RadioButton male;
    @BindView(R.id.rg_studyMode) RadioGroup studyModeGroup;
    @BindView(R.id.rb_fullTime) RadioButton fullTime;
    @BindView(R.id.rb_partTime) RadioButton partTime;
    @BindView(R.id.sp_nationality) Spinner nationality;
    @BindView(R.id.sp_language) Spinner language;
    @BindView(R.id.sp_course) Spinner course;
    @BindView(R.id.et_address) EditText address;
    @BindView(R.id.et_suburb) EditText suburb;
    @BindView(R.id.et_currentJob) EditText currentJob;
    @BindView(R.id.et_favoriteMovie) EditText favMovie;
    @BindView(R.id.et_favoriteSport) EditText favSport;
    @BindView(R.id.et_favoriteUnit) EditText favUnit;
    @BindView(R.id.tv_subscribeDate) TextView subscribeDate;

    private String gender;
    private String studyModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        Logger.addLogAdapter(new AndroidLogAdapter());
        initUserInfo();
        initListener();
    }

    private void initListener() {
        userGenderGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int id = group.getCheckedRadioButtonId();
                switch (id){
                    case R.id.rb_secret:
                        gender = secret.getText().toString();
                        break;
                    case R.id.rb_female:
                        gender = female.getText().toString();
                        break;
                    case R.id.rb_male:
                        gender = male.getText().toString();
                        break;
                }
            }
        });

        studyModeGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int id = group.getCheckedRadioButtonId();
                switch (id){
                    case R.id.rb_fullTime:
                        studyModel = fullTime.getText().toString();
                        break;
                    case R.id.rb_partTime:
                        studyModel = partTime.getText().toString();
                        break;
                }
            }
        });
    }

    private void initUserInfo(){
        if (user == null){
            Logger.d("user is null");
            return;
        }
        userID.setText(user.getStudID().toString());
        userEmail.setText(user.getEmail());
        String userName = user.getSurName() + " " + user.getFirstName();
        userFullName.setText(userName);
        userBirthDate.setText(user.getBirthDate().toString());
        String gender = user.getGender();
        if (gender.equals("M")){
            male.setSelected(true);
            female.setSelected(false);
        } else {
            male.setSelected(false);
            female.setSelected(true);
        }
        String model = user.getStudyMode();
        if(model.equals("full-time")){
            fullTime.setSelected(true);
            partTime.setSelected(false);
        } else {
            fullTime.setSelected(false);
            partTime.setSelected(true);
        }
    }

    @Override
    protected int getLayoutView() {
        return R.layout.activity_profile;
    }

    @OnClick(R.id.btn_saveProfile)
    void saveProfileChange(){

    }

    @OnClick(R.id.tv_birthDate)
    void changeBirthDate(){

    }

    @OnClick(R.id.tv_subscribeDate)
    void changeSubscribeDate(){

    }
}
