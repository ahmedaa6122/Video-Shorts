package com.example.videoshorts.view.activity;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.videoshorts.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

public class LogInActivity extends AppCompatActivity {
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    private final ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
        Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(result.getData());
        try {
            task.getResult(ApiException.class);
            Intent intent = new Intent(LogInActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        } catch (ApiException e) {
            Toast.makeText(LogInActivity.this, "Login error", Toast.LENGTH_SHORT).show();
            Log.e("Hello", String.valueOf(e.getStatusCode()));
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        com.example.videoshorts.databinding.ActivityLoginBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("785779343855-9bq54ic3nesvp690m8eb1kgk3krbamn5.apps.googleusercontent.com")
                .requestEmail()
                .build();
        gsc = GoogleSignIn.getClient(this, gso);
        binding.btnSignIn.setOnClickListener(v -> {
            Intent i = gsc.getSignInIntent();
            activityResultLauncher.launch(i);
        });
    }
}