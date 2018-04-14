package com.monash.app.ui.activity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.monash.app.R;
import com.monash.app.bean.User;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FriendInfoActivity extends BaseActivity {

    @BindView(R.id.tv_friend_info_gender) TextView tvGender;
    @BindView(R.id.tv_friend_info_name) TextView tvName;
    @BindView(R.id.tv_friend_info_email) TextView tvEmail;
    @BindView(R.id.tv_friend_info_studyMode) TextView tvStudyMode;
    @BindView(R.id.tv_friend_info_nation) TextView tvNation;
    @BindView(R.id.tv_friend_info_language) TextView tvLanguage;
    @BindView(R.id.tv_friend_info_course) TextView tvCourse;
    @BindView(R.id.tv_friend_info_address) TextView tvAddress;
    @BindView(R.id.tv_friend_info_suburb) TextView tvSuburb;
    @BindView(R.id.tv_friend_info_favUnit) TextView tvFavUnit;
    @BindView(R.id.tv_friend_info_favMovie) TextView tvFavMovie;
    @BindView(R.id.tv_friend_info_favSport) TextView tvFavSport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Detail Information");
        EventBus.getDefault().register(this);
        ButterKnife.bind(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    void showDetailInfo(User user){
        if (user != null){
            tvName.setText(user.getSurName() + " " + user.getFirstName());
            tvGender.setText(user.getGender());
            tvEmail.setText(user.getEmail());
            tvStudyMode.setText(user.getStudyMode());
            tvNation.setText(user.getNationality());
            tvLanguage.setText(user.getLanguage());
            tvCourse.setText(user.getCourse());
            tvAddress.setText(user.getAddress());
            tvSuburb.setText(user.getSuburb());
            tvFavMovie.setText(user.getFavMovie());
            tvFavSport.setText(user.getFavSport());
            tvFavUnit.setText(user.getFavUnit());
        }
    }

    @Override
    protected int getLayoutView() {
        return R.layout.activity_friend_info;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                EventBus.getDefault().unregister(this);
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
