package com.example.focusnow;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class PermissionActivity extends AppCompatActivity {

    Button btnUsageAccess;
    Button btnOverlayPermission;
    Button btnContinue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission);

        btnUsageAccess = findViewById(R.id.btnUsageAccess);
        btnOverlayPermission = findViewById(R.id.btnOverlayPermission);
        btnContinue = findViewById(R.id.btnContinue);

        // Open Usage Access Settings
        btnUsageAccess.setOnClickListener(v -> {
            Intent intent = new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);
            startActivity(intent);
        });

        // Open Overlay Permission Settings
        btnOverlayPermission.setOnClickListener(v -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                Intent intent = new Intent(
                        Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:" + getPackageName())
                );
                startActivity(intent);
            }
        });

        btnContinue.setOnClickListener(v -> {

            boolean usageGranted = PermissionUtils.hasUsageAccess(this);
            boolean overlayGranted = Settings.canDrawOverlays(this);

            if (usageGranted && overlayGranted) {

                Intent intent = new Intent(
                        PermissionActivity.this,
                        DashboardActivity.class
                );

                startActivity(intent);
                finish();

            } else {

                Toast.makeText(
                        this,
                        "Please enable all permissions to continue",
                        Toast.LENGTH_LONG
                ).show();
            }

        });
    }
}