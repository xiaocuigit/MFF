package com.monash.app.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemSelected;
import butterknife.OnTextChanged;

import com.bumptech.glide.load.engine.Resource;
import com.codetroopers.betterpickers.calendardatepicker.CalendarDatePickerDialogFragment;
import com.monash.app.R;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ProfileActivity extends BaseActivity implements CalendarDatePickerDialogFragment.OnDateSetListener{

    @BindView(R.id.tv_studID) TextView tvUserID;
    @BindView(R.id.tv_email) TextView tvUserEmail;
    @BindView(R.id.tv_studentName) TextView tvUserFullName;
    @BindView(R.id.tv_birthDate) TextView tvUserBirthDate;
    @BindView(R.id.rg_gender) RadioGroup rgUserGenderGroup;
    @BindView(R.id.rb_secret)RadioButton rbSecret;
    @BindView(R.id.rb_female) RadioButton rbFemale;
    @BindView(R.id.rb_male) RadioButton rbMale;
    @BindView(R.id.rg_studyMode) RadioGroup rgStudyModeGroup;
    @BindView(R.id.rb_fullTime) RadioButton rbFullTime;
    @BindView(R.id.rb_partTime) RadioButton rbPartTime;
    @BindView(R.id.et_nationality) EditText etNationality;
    @BindView(R.id.et_language) EditText etLanguage;
    @BindView(R.id.sp_course) Spinner spCourse;
    @BindView(R.id.et_address) EditText etAddress;
    @BindView(R.id.et_suburb) EditText etSuburb;
    @BindView(R.id.et_currentJob) EditText etCurrentJob;
    @BindView(R.id.et_favoriteMovie) EditText etFavMovie;
    @BindView(R.id.sp_favoriteSport) Spinner spFavSport;
    @BindView(R.id.sp_favoriteUnit) Spinner spFavUnit;
    @BindView(R.id.tv_subscribeDate) TextView tvSubscribeDate;

    private String gender;
    private String studyModel;
    private static String CHANGE_BIRTH_DATE = "CHANGE_BIRTH_DATE";
    private static String CHANGE_SUBSCRIBE_DATE = "CHANGE_SUBSCRIBE_DATE";
    private boolean isChanged = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ButterKnife.bind(this);
        Logger.addLogAdapter(new AndroidLogAdapter());
        initUserInfo();
        initListener();
    }

    private void initListener() {
        rgUserGenderGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int id = group.getCheckedRadioButtonId();
                switch (id){
                    case R.id.rb_secret:
                        gender = rbSecret.getText().toString();
                        break;
                    case R.id.rb_female:
                        gender = rbFemale.getText().toString();
                        break;
                    case R.id.rb_male:
                        gender = rbMale.getText().toString();
                        break;
                }
            }
        });

        rgStudyModeGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int id = group.getCheckedRadioButtonId();
                switch (id){
                    case R.id.rb_fullTime:
                        studyModel = rbFullTime.getText().toString();
                        break;
                    case R.id.rb_partTime:
                        studyModel = rbPartTime.getText().toString();
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
        tvUserID.setText(String.valueOf(user.getStudID()));
        tvUserEmail.setText(user.getEmail());
        String userName = user.getSurName() + " " + user.getFirstName();
        tvUserFullName.setText(userName);
        String birthDate = handleDate(user.getBirthDate());
        if (birthDate != null)
            tvUserBirthDate.setText(birthDate);
        String gender = user.getGender();
        if (gender != null){
            if (gender.equals("M")){
                rgUserGenderGroup.check(rbMale.getId());
            } else if (gender.equals("F")){
                rgUserGenderGroup.check(rbFemale.getId());
            } else {
                rgUserGenderGroup.check(rbSecret.getId());
            }
        }

        String model = user.getStudyMode();
        if (model != null){
            if(model.equals("full-time")){
                rgStudyModeGroup.check(rbFullTime.getId());
            } else {
                rgStudyModeGroup.check(rbPartTime.getId());
            }
        }
        String[] courses = getResources().getStringArray(R.array.courses);
        String course = user.getCourse();
        if (course != null){
            for (int i = 1; i < courses.length; i++){
                if (course.equals(courses[i])){
                    spCourse.setSelection(i, true);
                    break;
                }
            }
        } else {
            spCourse.setSelection(0, true);
        }
        String nationality = user.getNationality();
        if (nationality != null){
            etNationality.setText(captureFirstLetter(nationality));
            etNationality.setFocusable(false);
            etNationality.setFocusableInTouchMode(false);
        }
        String language = user.getLanguage();
        if (language != null){
            etLanguage.setText(captureFirstLetter(language));
            etLanguage.setFocusable(false);
            etLanguage.setFocusableInTouchMode(false);
        }
        etAddress.setText(user.getAddress());
        etSuburb.setText(user.getSuburb());
        etCurrentJob.setText(user.getCurrentJob());
        etFavMovie.setText(user.getFavMovie());
        String[] sports = getResources().getStringArray(R.array.sports);
        String[] units = getResources().getStringArray(R.array.units);
        String favSport = user.getFavSport();
        String favUnit = user.getFavUnit();
        if (favSport != null){
            favSport = favSport.toLowerCase();
            for (int i = 1; i < sports.length; i++){
                if (favSport.equals(sports[i].toLowerCase())){
                    spFavSport.setSelection(i, true);
                    break;
                }
            }
        } else {
            spFavSport.setSelection(0, true);
        }
        if (favUnit != null){
            favUnit = favUnit.toLowerCase();
            for (int i = 1; i < units.length; i++){
                if (favUnit.equals(units[i].toLowerCase())){
                    spFavUnit.setSelection(i, true);
                }
            }
        } else {
            spFavUnit.setSelection(0, true);
        }
        etFavMovie.setText(user.getFavMovie());
        String subscriptionDate = handleDate(user.getSubscriptionDate());
        if (subscriptionDate != null){
            tvSubscribeDate.setText(subscriptionDate);
        }
        isChanged = false;
    }

    private String handleDate(Date date){
        if (date != null)
            return DateFormat.getDateInstance(DateFormat.MEDIUM).format(date);
        else
            return null;

    }
    private String captureFirstLetter(String str){
        char[] arr = str.toCharArray();
        if (arr[0] >= 'a') {
            arr[0] -= 32;
        }
        return String.valueOf(arr);
    }
    @Override
    protected int getLayoutView() {
        return R.layout.activity_profile;
    }

    @OnClick(R.id.btn_saveProfile)
    void saveProfileChange(){
        if (checkChangedInfo()){
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private boolean checkChangedInfo() {

        return false;
    }


    @OnTextChanged(R.id.et_nationality)
    public void onNationalityChanged(){
        if (user != null) {
            user.setNationality(captureFirstLetter(etNationality.getText().toString()));
            isChanged = true;
        }
    }

    @OnTextChanged(R.id.et_language)
    public void onLanguageChanged(){
        if (user != null) {
            user.setLanguage(captureFirstLetter(etLanguage.getText().toString()));
            isChanged = true;
        }
    }

    @OnTextChanged(R.id.et_address)
    public void onAddressChanged(){
        if (user != null) {
            user.setAddress(etAddress.getText().toString());
            isChanged = true;
        }
    }

    @OnTextChanged(R.id.et_suburb)
    public void onSuburbChanged(){
        if (user != null) {
            user.setSuburb(etSuburb.getText().toString());
            isChanged = true;
        }
    }

    @OnTextChanged(R.id.et_currentJob)
    public void onCurrentJobChanged(){
        if (user != null) {
            user.setCurrentJob(etCurrentJob.getText().toString());
            isChanged = true;
        }
    }

    @OnTextChanged(R.id.et_favoriteMovie)
    public void onMovieChanged(){
        if (user != null) {
            user.setFavMovie(etFavMovie.getText().toString());
            isChanged = true;
        }
    }

    @OnItemSelected(R.id.sp_course)
    public void onCourseItemSelected(){
        String course = spCourse.getSelectedItem().toString();
        if (course != null && user != null){
            Logger.d(course);
            user.setCourse(course);
            isChanged = true;
        }
    }

    @OnItemSelected(R.id.sp_favoriteSport)
    public void onSportItemSelected(){
        String sport = spFavSport.getSelectedItem().toString();
        if (sport != null && user != null) {
            Logger.d(sport);
            user.setFavSport(spFavSport.getSelectedItem().toString());
            isChanged = true;
        }
    }

    @OnItemSelected(R.id.sp_favoriteUnit)
    public void onUnitSelected(){
        String unit = spFavUnit.getSelectedItem().toString();
        if (unit != null && user != null){
            Logger.d(unit);
            user.setFavUnit(spFavUnit.getSelectedItem().toString());
            isChanged = true;
        }
    }

    @OnClick(R.id.tv_birthDate)
    void changeBirthDate(){
        CalendarDatePickerDialogFragment cdp = new CalendarDatePickerDialogFragment()
                .setFirstDayOfWeek(Calendar.SUNDAY)
                .setOnDateSetListener(this)
                .setDoneText("OK")
                .setCancelText("Cancel");
        cdp.show(getSupportFragmentManager(), CHANGE_BIRTH_DATE);
    }


    @OnClick(R.id.tv_subscribeDate)
    void changeSubscribeDate(){
        CalendarDatePickerDialogFragment cdp = new CalendarDatePickerDialogFragment()
                .setFirstDayOfWeek(Calendar.SUNDAY)
                .setOnDateSetListener(this)
                .setDoneText("OK")
                .setCancelText("Cancel");
        cdp.show(getSupportFragmentManager(), CHANGE_SUBSCRIBE_DATE);
    }

    @Override
    public void onDateSet(CalendarDatePickerDialogFragment dialog, int year, int monthOfYear, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, monthOfYear - 1, dayOfMonth);
        Date date = calendar.getTime();
        String tag = dialog.getTag();
        isChanged = true;
        if (tag.equals(CHANGE_BIRTH_DATE)) {
            tvUserBirthDate.setText(handleDate(date));
        } else {
            tvSubscribeDate.setText(handleDate(date));
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
