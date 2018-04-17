package com.monash.app.ui.activity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.monash.app.R;
import com.monash.app.bean.Friend;
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
    @BindView(R.id.tv_friend_info_startDate) TextView tvStartDate;
    @BindView(R.id.ll_friend_startDate) LinearLayout llFriendStartDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        initData();
    }

    private void initData() {
        String tag = getIntent().getStringExtra("tag");
        if (tag.equals("search")){
            User user = (User) getIntent().getSerializableExtra("search");
            showSearchedFriendInfo(user);
        } else {
            Friend friend = (Friend) getIntent().getSerializableExtra("friend");
            showFriendInfo(friend);
        }
    }

    private void init() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Detail Information");
        ButterKnife.bind(this);
    }

    void showSearchedFriendInfo(User user){
        llFriendStartDate.setVisibility(View.GONE);
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

    void showFriendInfo(Friend friend){
        llFriendStartDate.setVisibility(View.VISIBLE);
        tvName.setText(friend.getFriend().getSurName() + " " + friend.getFriend().getFirstName());
        tvGender.setText(friend.getFriend().getGender());
        tvEmail.setText(friend.getFriend().getEmail());
        tvStudyMode.setText(friend.getFriend().getStudyMode());
        tvNation.setText(friend.getFriend().getNationality());
        tvLanguage.setText(friend.getFriend().getLanguage());
        tvCourse.setText(friend.getFriend().getCourse());
        tvAddress.setText(friend.getFriend().getAddress());
        tvSuburb.setText(friend.getFriend().getSuburb());
        tvFavMovie.setText(friend.getFriend().getFavMovie());
        tvFavSport.setText(friend.getFriend().getFavSport());
        tvFavUnit.setText(friend.getFriend().getFavUnit());
        tvStartDate.setText(friend.getStartDate());

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
