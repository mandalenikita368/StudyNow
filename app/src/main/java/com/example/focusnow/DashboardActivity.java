package com.example.focusnow;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class DashboardActivity extends AppCompatActivity {

    BottomNavigationView bottomNav;
    Button btnStartSession;
    TextView tvViewReport;   // NEW

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dashboard);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

        // Bottom Navigation
        bottomNav = findViewById(R.id.bottomNav);
        bottomNav.setSelectedItemId(R.id.nav_home);

        bottomNav.setOnItemSelectedListener(item -> {
            return true;
        });

        // Start Session Button
        btnStartSession = findViewById(R.id.btnStartSession);

        btnStartSession.setOnClickListener(v -> {
            Intent intent = new Intent(DashboardActivity.this, FocusSessionActivity.class);
            startActivity(intent);
        });

        // 🔹 View Focus Report Click
        tvViewReport = findViewById(R.id.tvViewReport);

        tvViewReport.setOnClickListener(v -> {
            Intent intent = new Intent(DashboardActivity.this, FocusAnalyticsActivity.class);
            startActivity(intent);
        });
    }
}