package com.example.focusnow;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class SplashScreen extends AppCompatActivity {

    private static final int SPLASH_DELAY = 2000;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        mAuth = FirebaseAuth.getInstance();

        TextView tvAppName = findViewById(R.id.tvAppName);

        SpannableString appName = new SpannableString("StudyNow");

        // Study → black
        appName.setSpan(
                new ForegroundColorSpan(getResources().getColor(R.color.text_primary)),
                0,
                5,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        );

        // Now → primary color
        appName.setSpan(
                new ForegroundColorSpan(getResources().getColor(R.color.primary)),
                5,
                8,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        );

        tvAppName.setText(appName);

        new Handler(Looper.getMainLooper()).postDelayed(() -> {

            if (mAuth.getCurrentUser() != null) {

                SharedPreferences prefs =
                        getSharedPreferences("FocusPrefs", MODE_PRIVATE);

                boolean permissionsDone =
                        prefs.getBoolean("permissions_done", false);

                if (permissionsDone) {

                    startActivity(new Intent(
                            SplashScreen.this,
                            DashboardActivity.class
                    ));

                } else {

                    startActivity(new Intent(
                            SplashScreen.this,
                            PermissionInfoActivity.class
                    ));

                }

            } else {

                startActivity(new Intent(
                        SplashScreen.this,
                        LoginActivity.class
                ));
            }

            finish();

        }, SPLASH_DELAY);
    }
}