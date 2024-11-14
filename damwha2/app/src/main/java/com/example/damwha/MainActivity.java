package com.example.damwha;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button buttonSignup, buttonEmailVerification, buttonLogin, buttonLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // UI 요소 초기화는 setContentView 이후에 해야 함
        buttonSignup = findViewById(R.id.button_signup);
        buttonEmailVerification = findViewById(R.id.button_email_verification);
        buttonLogin = findViewById(R.id.button_login);
        buttonLogout = findViewById(R.id.button_logout);

        buttonSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SignupActivity.class));
            }
        });

        buttonEmailVerification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, PhoneNumberVerificationActivity.class));
            }
        });

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });

        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 로그아웃 로직 구현 후 로그인 화면으로 돌아가기
                if (!isFinishing()) {
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                }
            }
        });
    }
}
