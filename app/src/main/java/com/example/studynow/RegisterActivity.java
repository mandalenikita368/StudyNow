package com.example.studynow;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.studynow.database.AppDatabase;
import com.example.studynow.database.User;
import com.example.studynow.database.UserDao;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RegisterActivity extends AppCompatActivity {

    private EditText fullNameEt, emailEt, usernameEt, passwordEt;
    private Spinner educationSpinner, yearSpinner;
    private Button createAccountBtn;
    private AppDatabase db;
    private ExecutorService executor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        db = AppDatabase.getInstance(this);
        executor = Executors.newSingleThreadExecutor();

        fullNameEt = findViewById(R.id.fullName);
        emailEt = findViewById(R.id.email);
        usernameEt = findViewById(R.id.username);
        passwordEt = findViewById(R.id.password);
        educationSpinner = findViewById(R.id.educationSpinner);
        yearSpinner = findViewById(R.id.yearSpinner);
        createAccountBtn = findViewById(R.id.createAccountBtn);

        ArrayAdapter<CharSequence> educationAdapter = ArrayAdapter.createFromResource(this,
                R.array.education_options, android.R.layout.simple_spinner_item);
        educationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        educationSpinner.setAdapter(educationAdapter);

        ArrayAdapter<CharSequence> yearAdapter = ArrayAdapter.createFromResource(this,
                R.array.year_options, android.R.layout.simple_spinner_item);
        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        yearSpinner.setAdapter(yearAdapter);

        createAccountBtn.setOnClickListener(v -> registerUser());
    }

    private void registerUser() {
        String fullName = fullNameEt.getText().toString().trim();
        String email = emailEt.getText().toString().trim();
        String username = usernameEt.getText().toString().trim();
        String password = passwordEt.getText().toString().trim();
        String education = educationSpinner.getSelectedItem().toString();
        String year = yearSpinner.getSelectedItem().toString();

        if (fullName.isEmpty() || email.isEmpty() || username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        executor.execute(() -> {
            UserDao userDao = db.userDao();
            User existingUser = userDao.getUserByUsername(username);

            if (existingUser != null) {
                runOnUiThread(() -> Toast.makeText(RegisterActivity.this,
                        "Username already exists", Toast.LENGTH_SHORT).show());
            } else {
                User newUser = new User();
                newUser.fullName = fullName;
                newUser.email = email;
                newUser.username = username;
                newUser.password = password;
                newUser.currentEducation = education;
                newUser.yearOfStudy = year;

                userDao.insertUser(newUser);

                // Save account created flag
                SharedPreferences prefs = getSharedPreferences("app_prefs", MODE_PRIVATE);
                prefs.edit().putBoolean("isAccountCreated", true).apply();

                runOnUiThread(() -> {
                    Toast.makeText(RegisterActivity.this,
                            "Account created successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                    finish();
                });
            }
        });
    }
}
