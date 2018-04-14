package com.monash.app.adapter;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.monash.app.R;
import com.monash.app.bean.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by abner on 2018/4/14.
 *
 */

public class FriendsAdapter extends BaseRecyclerViewAdapter<User> {

    private final List<User> originalList;

    public FriendsAdapter(List<User> list) {
        super(list);
        originalList = new ArrayList<>(list);
    }

    public FriendsAdapter(List<User> list, Context context) {
        super(list, context);
        originalList = new ArrayList<>(list);
    }

    @Override
    protected Animator[] getAnimators(View view) {
        if (view.getMeasuredHeight() <=0){
            ObjectAnimator scaleX = ObjectAnimator.ofFloat(view, "scaleX", 1.05f, 1.0f);
            ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, "scaleY", 1.05f, 1.0f);
            return new ObjectAnimator[]{scaleX, scaleY};
        }
        return new Animator[]{
            ObjectAnimator.ofFloat(view, "scaleX", 1.05f, 1.0f),
            ObjectAnimator.ofFloat(view, "scaleY", 1.05f, 1.0f),
        };
    }

    @Override
    public void setList(List<User> list) {
        super.setList(list);
        this.originalList.clear();
        originalList.addAll(list);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        // 这里也出过问题。
        View view = LayoutInflater.from(context).inflate(R.layout.friends_item_layout, parent, false);
        return new FriendsItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        super.onBindViewHolder(viewHolder, position);

        FriendsItemViewHolder holder = (FriendsItemViewHolder) viewHolder;

        User friend = list.get(position);

        if (friend == null)
            return;
        holder.setNameText(friend.getSurName() + friend.getFirstName());
        holder.setEmailText(friend.getEmail());
        holder.setGenderText(friend.getGender());
        holder.setCourseText(friend.getCourse());
        holder.setFavMovieText(friend.getFavMovie());
        holder.setNationalText(friend.getNationality());
        // 为该位置的view绑定动画
        animate(holder, position);
    }


    private class FriendsItemViewHolder extends RecyclerView.ViewHolder{

        private final TextView tvFriendName;
        private final TextView tvFriendEmail;
        private final TextView tvFriendCourse;
        private final TextView tvFriendNational;
        private final TextView tvFriendGender;
        private final TextView tvFriendFavMovie;

        FriendsItemViewHolder(View parent) {
            super(parent);
            tvFriendName = parent.findViewById(R.id.tv_friend_name);
            tvFriendEmail = parent.findViewById(R.id.tv_friend_email);
            tvFriendCourse = parent.findViewById(R.id.tv_friend_course);
            tvFriendGender = parent.findViewById(R.id.tv_friend_gender);
            tvFriendNational = parent.findViewById(R.id.tv_friend_nation);
            tvFriendFavMovie = parent.findViewById(R.id.tv_friend_favMovie);
        }
        void setNameText(CharSequence text){
            setTextView(tvFriendName, text);
        }

        void setEmailText(CharSequence text){
            setTextView(tvFriendEmail, text);
        }

        void setCourseText(CharSequence text){
            setTextView(tvFriendCourse, text);
        }

        void setGenderText(CharSequence text){
            setTextView(tvFriendGender, text);
        }

        void setNationalText(CharSequence text){
            setTextView(tvFriendNational, text);
        }

        void setFavMovieText(CharSequence text){
            setTextView(tvFriendFavMovie, text);
        }

        void setTextView(TextView view, CharSequence text) {
            // 判断view是否存在 以及 输入的内容是否为空
            if (view == null || TextUtils.isEmpty(text))
                return;
            view.setText(text);
        }
    }
}
