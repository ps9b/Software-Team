package com.example.damwha;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class LogoutActivity extends AppCompatActivity {

    private Button buttonConfirmLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logout);

        // Initialize UI elements
        buttonConfirmLogout = findViewById(R.id.buttonConfirmLogout);

        // Set logout button listener
        buttonConfirmLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle logout logic here (e.g., clear user session, redirect to login screen)
                Toast.makeText(LogoutActivity.this, "Logging out...", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LogoutActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });
    }
}