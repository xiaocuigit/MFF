package com.monash.app.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.monash.app.App;
import com.monash.app.R;
import com.monash.app.adapter.BaseRecyclerViewAdapter;
import com.monash.app.adapter.FriendsAdapter;
import com.monash.app.bean.Friend;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import org.greenrobot.eventbus.EventBus;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

public class FriendsActivity extends BaseActivity {

    @BindView(R.id.recyclerView_user_friends) RecyclerView recyclerView;

    @BindView(R.id.no_friends_tip) TextView noFriendsTip;

    private FriendsAdapter recyclerAdapter;
    private List<Friend> friends;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        init();
        initData();
        initRecyclerView();
    }

    private void initData() {
        friends = App.getFriends();
    }

    private void init() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Friends");

        ButterKnife.bind(this);
        Logger.addLogAdapter(new AndroidLogAdapter());
    }

    private void initRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setHasFixedSize(true);

        if (friends != null) {
            noFriendsTip.setVisibility(View.GONE);
            recyclerAdapter = new FriendsAdapter(friends, this);
            recyclerAdapter.setOnInViewClickListener(R.id.friends_item_root,
                    new BaseRecyclerViewAdapter.onInternalClickListenerImpl<Friend>(){
                        @Override
                        public void OnClickListener(View parentV, View v, Integer position, Friend values) {
                            super.OnClickListener(parentV, v, position, values);
                            showDetailInfo(values);
                        }
                    });
            recyclerAdapter.setOnInViewClickListener(R.id.ib_friend_more,
                    new BaseRecyclerViewAdapter.onInternalClickListenerImpl<Friend>(){
                        @Override
                        public void OnClickListener(View parentV, View v, Integer position, Friend values) {
                            super.OnClickListener(parentV, v, position, values);
                            showPopupMenu(v, values);
                        }
                    });
            recyclerAdapter.setFirstOnly(false);
            recyclerAdapter.setDuration(300);
            recyclerView.setAdapter(recyclerAdapter);
        } else {
            noFriendsTip.setVisibility(View.VISIBLE);
        }
    }

    private void showDetailInfo(Friend values) {
        Intent intent = new Intent(this, FriendInfoActivity.class);
        intent.putExtra("tag", "friends");
        intent.putExtra("friend", values);
        startActivity(intent);
    }

    private void showPopupMenu(View view, final Friend friend) {
        PopupMenu popup = new PopupMenu(this, view);

        popup.getMenuInflater().inflate(R.menu.menu_friend, popup.getMenu());

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                switch (id) {
                    case R.id.delete:
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
        popup.show();
    }

    @Override
    protected int getLayoutView() {
        return R.layout.activity_friends;
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
