package com.example.fish_bites;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class login extends AppCompatActivity {


    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button login;
    private Button signup;
    private APIService apiService;

    private static final String BASE_URL = "http://127.0.0.1:8000/api/user"; // Change to your Laravel API base URL

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        usernameEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.password);
        login = findViewById(R.id.loginButton);
        signup = findViewById(R.id.SignButton);

        // Initialize Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                 .baseUrl("http://127.0.0.1:8000/api/user") //ganti url nya
                 .addConverterFactory(GsonConverterFactory.create())
                 .build();

        apiService = retrofit.create(APIService.class);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(login.this, "Isi username dan password", Toast.LENGTH_SHORT).show();
                } else {
                    performLogin(username, password);
                }
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(login.this, signup.class);
                startActivity(intent);
            }
        });

    }

    private void performLogin(String username, String password) {
        loginrequest loginRequest = new loginrequest(username, password);
        Call<loginresponse> call = apiService.login(loginRequest);
        call.enqueue(new retrofit2.Callback<loginresponse>() {
            @Override
            public void onResponse(Call<loginresponse> call, retrofit2.Response<loginresponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Toast.makeText(login.this, "Login Sukses: " + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    // Navigate to Home Activity
                    Intent intent = new Intent(login.this, home.class);
                    startActivity(intent);
                    finish(); // Optional: Close the login activity
                } else {
                    Toast.makeText(login.this, "Login Gagal: " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<loginresponse> call, Throwable t) {
                Toast.makeText(login.this, "Kesalahan: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}