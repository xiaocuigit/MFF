package com.monash.app.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.monash.app.R;
import com.monash.app.adapter.BaseRecyclerViewAdapter;
import com.monash.app.adapter.FriendsAdapter;
import com.monash.app.bean.Friend;
import com.monash.app.bean.User;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FriendsActivity extends BaseActivity {

    @BindView(R.id.recyclerView) RecyclerView recyclerView;

    @BindView(R.id.no_friends_tip) TextView noFriendsTip;

    private FriendsAdapter recyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        init();
//        initRecyclerView();
    }

    private void init() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Friends");

        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        Logger.addLogAdapter(new AndroidLogAdapter());
    }

    private void initRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setHasFixedSize(true);

        recyclerAdapter = new FriendsAdapter(initItemDate(), this);

        recyclerAdapter.setOnInViewClickListener(R.id.ib_friend_more,
                new BaseRecyclerViewAdapter.onInternalClickListenerImpl<User>(){
                    @Override
                    public void OnClickListener(View parentV, View v, Integer position, User values) {
                        super.OnClickListener(parentV, v, position, values);
                        showPopupMenu(v, values);
                    }
                });
        recyclerAdapter.setFirstOnly(false);
        recyclerAdapter.setDuration(300);
        recyclerView.setAdapter(recyclerAdapter);
    }

    private List<User> initItemDate() {

        return null;
    }

    private void showPopupMenu(View view, final User friend) {
        PopupMenu popup = new PopupMenu(this, view);

        popup.getMenuInflater().inflate(R.menu.menu_friend_more, popup.getMenu());

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                switch (id) {
                    case R.id.delete:
                        deleteFriend(friend);
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
        popup.show();
    }

    private void deleteFriend(User friend) {

    }

    @Override
    protected int getLayoutView() {
        return R.layout.activity_friends;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
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
