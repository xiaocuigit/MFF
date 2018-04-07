package com.monash.app.ui.activity;

import android.support.design.widget.TextInputEditText;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.monash.app.R;

import butterknife.BindView;

public class SignUpActivity extends AppCompatActivity {


    @BindView(R.id.input_first_name) TextInputEditText user_first_name;

    @BindView(R.id.input_surname) TextInputEditText user_surname;

    @BindView(R.id.input_email) TextInputEditText user_email;

    @BindView(R.id.input_user_id) TextInputEditText user_id;

    @BindView(R.id.input_password) TextInputEditText user_password;

    @BindView(R.id.input_password_again) TextInputEditText user_password_again;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

    }

}
