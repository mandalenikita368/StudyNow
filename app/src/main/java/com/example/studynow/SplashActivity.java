package com.example.studynow;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    private static final long SPLASH_DELAY = 2000; // 2 sec delay

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(() -> {
            SharedPreferences prefs = getSharedPreferences("app_prefs", MODE_PRIVATE);
            boolean isLoggedIn = prefs.getBoolean("isLoggedIn", false);
            boolean isAccountCreated = prefs.getBoolean("isAccountCreated", false);

            Intent intent;
            if (!isAccountCreated) {
                // First-time user → go to RegisterActivity
                intent = new Intent(SplashActivity.this, RegisterActivity.class);
            } else if (!isLoggedIn) {
                // Account created but not logged in → go to LoginActivity
                intent = new Intent(SplashActivity.this, LoginActivity.class);
            } else {
                // Already logged in → MainActivity
                intent = new Intent(SplashActivity.this, MainActivity.class);
            }

            startActivity(intent);
            finish();
        }, SPLASH_DELAY);
    }
}