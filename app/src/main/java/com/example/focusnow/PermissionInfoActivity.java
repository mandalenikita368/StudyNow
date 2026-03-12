package com.example.focusnow;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class PermissionInfoActivity extends AppCompatActivity {

    Button btnContinue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission_info);

        btnContinue = findViewById(R.id.btnContinue);

        btnContinue.setOnClickListener(v -> {

            // Mark permission screen shown
            SharedPreferences prefs =
                    getSharedPreferences("FocusPrefs", MODE_PRIVATE);

            prefs.edit().putBoolean("permissions_done", true).apply();

            // Open Permission Activity
            startActivity(new Intent(
                    PermissionInfoActivity.this,
                    PermissionActivity.class
            ));

            finish();
        });
    }
}