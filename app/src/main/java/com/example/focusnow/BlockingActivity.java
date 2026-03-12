package com.example.focusnow;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class BlockingActivity extends AppCompatActivity {

    String[] motivation = {

            "Your parents are investing in your education.",
            "Your future self will thank you.",
            "Your friends may be studying right now.",
            "Stay focused. Success needs discipline.",
            "Don't lose this valuable time."
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blocking);

        TextView tvMessage = findViewById(R.id.tvMessage);

        int attempt = getIntent().getIntExtra("attempts", 0);

        String msg = motivation[attempt % motivation.length];

        tvMessage.setText(msg);
    }
}