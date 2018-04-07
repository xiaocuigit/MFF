package com.monash.app.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.monash.app.R;
import com.monash.app.utils.HttpUtil;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class HomeActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.image_day) AppCompatImageView image_day;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.nav_view) NavigationView navigationView;
    @BindView(R.id.drawer_layout) DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ButterKnife.bind(this);
        Logger.addLogAdapter(new AndroidLogAdapter());

        initDrawer();
        initHeader();
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


    @OnClick(R.id.fab)
    void addFriends(View view){
        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    @OnClick(R.id.btn_load_image)
    void loadImage(){
        // 通过此URL获取必应每日图片的URL地址
        String url = "http://cn.bing.com/HPImageArchive.aspx?format=js&idx=0&n=1";
        String jsonString = HttpUtil.getInstance().get(url);
        Logger.d("jsonArray : " + jsonString);
        String imageURL = "http://s.cn.bing.net" + getImageURL(jsonString);
        if(!imageURL.equals("http://s.cn.bing.net")) {
            Glide.with(HomeActivity.this)
                    .load(imageURL)
                    .into(image_day);
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

        } else if (id == R.id.nav_search) {

        } else if (id == R.id.nav_units_report) {

        } else if (id == R.id.nav_location_report) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_about_us) {

        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
