package com.example.focusnow;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Toast;

import android.app.AlertDialog;
import android.widget.NumberPicker;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.Date;

public class FocusSessionActivity extends AppCompatActivity {

    private TextView tvTimer;
    private ProgressBar progressBar;
    private Button btnStart, btnPause;

    private CountDownTimer countDownTimer;
    private boolean isRunning = false;

    private long totalTime = 1500000; // 25 minutes
    private long timeLeft = totalTime;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_focus_session);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        tvTimer = findViewById(R.id.tvTimer);
        progressBar = findViewById(R.id.progressBar);
        btnStart = findViewById(R.id.btnStart);
        btnPause = findViewById(R.id.btnPause);

        progressBar.setMax((int) (totalTime / 1000));
        progressBar.setProgress((int) (totalTime / 1000));

        updateTimerText();

        btnStart.setOnClickListener(v -> startTimer());
        btnPause.setOnClickListener(v -> pauseTimer());

        tvTimer.setOnClickListener(v -> showTimePickerDialog());
    }

    private void startTimer() {

        if (isRunning) return;

        countDownTimer = new CountDownTimer(timeLeft, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {

                timeLeft = millisUntilFinished;

                updateTimerText();

                progressBar.setProgress((int) (timeLeft / 1000));
            }

            @Override
            public void onFinish() {

                isRunning = false;

                saveSessionToFirestore();

                stopBlockingService();   // 🔴 stop blocker when session ends

                Toast.makeText(FocusSessionActivity.this,
                        "Session Complete 🎉 Saved!",
                        Toast.LENGTH_LONG).show();
            }

        }.start();

        isRunning = true;

        startBlockingService();   // 🟢 start app blocker
    }

    private void pauseTimer() {

        if (countDownTimer != null) {

            countDownTimer.cancel();

            isRunning = false;

            stopBlockingService();   // stop blocking when paused
        }
    }

    private void startBlockingService() {

        Intent intent = new Intent(this,
                com.example.focusnow.service.AppMonitorService.class);

        startService(intent);
    }

    private void stopBlockingService() {

        Intent intent = new Intent(this,
                com.example.focusnow.service.AppMonitorService.class);

        stopService(intent);
    }

    private void updateTimerText() {

        int minutes = (int) (timeLeft / 1000) / 60;
        int seconds = (int) (timeLeft / 1000) % 60;

        String timeFormatted = String.format("%02d:%02d", minutes, seconds);

        tvTimer.setText(timeFormatted);
    }

    private void saveSessionToFirestore() {

        if (mAuth.getCurrentUser() == null) {

            Toast.makeText(this, "User not logged in!", Toast.LENGTH_SHORT).show();

            return;
        }

        String userId = mAuth.getCurrentUser().getUid();

        String sessionId = UUID.randomUUID().toString();

        Map<String, Object> session = new HashMap<>();

        session.put("session_id", sessionId);
        session.put("user_id", userId);
        session.put("date", new Date());

        int durationMinutes = (int) (totalTime / 60000);

        session.put("duration", durationMinutes);
        session.put("completed", true);

        db.collection("focus_sessions")
                .document(sessionId)
                .set(session)
                .addOnSuccessListener(aVoid ->
                        Toast.makeText(this,
                                "Saved to Firestore ✅",
                                Toast.LENGTH_SHORT).show())

                .addOnFailureListener(e ->
                        Toast.makeText(this,
                                "Error: " + e.getMessage(),
                                Toast.LENGTH_SHORT).show());
    }

    private void showTimePickerDialog() {

        if (isRunning) {

            Toast.makeText(this,
                    "Pause timer before changing time",
                    Toast.LENGTH_SHORT).show();

            return;
        }

        final NumberPicker picker = new NumberPicker(this);

        picker.setMinValue(5);
        picker.setMaxValue(180);

        picker.setValue((int) (totalTime / 60000));

        picker.setWrapSelectorWheel(false);

        new AlertDialog.Builder(this)

                .setTitle("Set Focus Time (minutes)")

                .setView(picker)

                .setPositiveButton("Start", (dialog, which) -> {

                    int minutes = picker.getValue();

                    totalTime = minutes * 60 * 1000;

                    timeLeft = totalTime;

                    progressBar.setMax((int) (totalTime / 1000));

                    progressBar.setProgress((int) (totalTime / 1000));

                    updateTimerText();

                    startTimer();

                })

                .setNegativeButton("Cancel", null)

                .show();
    }
}