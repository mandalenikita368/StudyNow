package com.example.studynow;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.PopupMenu;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Chronometer chronometer;
    private boolean running = false;
    private long pauseOffset = 0;

    private Button btnStart, btnStop;
    private ImageButton btnProfile, btnMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        chronometer = findViewById(R.id.chronometer);
        btnStart = findViewById(R.id.btnStart);
        btnStop = findViewById(R.id.btnStop);
        btnProfile = findViewById(R.id.btnProfile);
        btnMenu = findViewById(R.id.btnMenu);

        // Start / Stop buttons
        btnStart.setOnClickListener(v -> startStudy());
        btnStop.setOnClickListener(v -> stopStudy());

        // Profile button
        btnProfile.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
            startActivity(intent);
        });

        // Menu button
        btnMenu.setOnClickListener(this::showMenu);
    }

    private void startStudy() {
        if (!running) {
            chronometer.setBase(SystemClock.elapsedRealtime() - pauseOffset);
            chronometer.start();
            running = true;
        }
    }

    private void stopStudy() {
        if (running) {
            chronometer.stop();
            pauseOffset = SystemClock.elapsedRealtime() - chronometer.getBase();
            running = false;
        }
    }

    private void showMenu(View v) {
        PopupMenu popupMenu = new PopupMenu(this, v);
        popupMenu.getMenu().add("Invite Friends");
        popupMenu.getMenu().add("Start Live Session");
        popupMenu.getMenu().add("Analyse Yourself");
        popupMenu.getMenu().add("Logout");

        popupMenu.setOnMenuItemClickListener(item -> {
            switch (item.getTitle().toString()) {
                case "Invite Friends":
                    shareApp();
                    break;
                case "Start Live Session":
                    // TODO: implement live session
                    break;
                case "Analyse Yourself":
                    // TODO: implement analyse yourself
                    break;
                case "Logout":
                    logoutUser();
                    break;
            }
            return true;
        });

        popupMenu.show();
    }

    private void shareApp() {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "Join me on StudyNow app!");
        sendIntent.setType("text/plain");
        startActivity(Intent.createChooser(sendIntent, "Invite via"));
    }

    private void logoutUser() {
        // Clear logged-in user
        SharedPreferences prefs = getSharedPreferences("app_prefs", MODE_PRIVATE);
        prefs.edit().remove("loggedInUser").apply();

        // Redirect to RegisterActivity
        Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}
