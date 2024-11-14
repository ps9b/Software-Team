package com.example.damwha;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class PhoneNumberVerificationActivity extends AppCompatActivity {

    private EditText editTextPhoneNumber, editTextVerificationCode;
    private Button buttonSendCode, buttonVerifyCode;
    private String verificationCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_number_verification);

        // Initialize UI elements
        editTextPhoneNumber = findViewById(R.id.editTextPhoneNumber);
        editTextVerificationCode = findViewById(R.id.editTextVerificationCode);
        buttonSendCode = findViewById(R.id.buttonSendCode);
        buttonVerifyCode = findViewById(R.id.buttonVerifyCode);

        // Set button click listeners
        buttonSendCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNumber = editTextPhoneNumber.getText().toString().trim();
                if (!phoneNumber.isEmpty()) {
                    sendVerificationCode(phoneNumber);
                } else {
                    Toast.makeText(PhoneNumberVerificationActivity.this, "Please enter a valid phone number.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        buttonVerifyCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = editTextVerificationCode.getText().toString().trim();
                if (!code.isEmpty()) {
                    verifyCode(code);
                } else {
                    Toast.makeText(PhoneNumberVerificationActivity.this, "Please enter the verification code.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void sendVerificationCode(final String phoneNumber) {
        // Async task to send verification code to the server
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... voids) {
                try {
                    URL url = new URL("http://your-server-url/send_code");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                    conn.setDoOutput(true);

                    // Parameters to send
                    String params = "phoneNumber=" + phoneNumber;
                    OutputStream os = conn.getOutputStream();
                    os.write(params.getBytes());
                    os.flush();
                    os.close();

                    int responseCode = conn.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                        String inputLine;
                        StringBuilder response = new StringBuilder();
                        while ((inputLine = in.readLine()) != null) {
                            response.append(inputLine);
                        }
                        in.close();
                        return response.toString();
                    } else {
                        return "Server returned error: " + responseCode;
                    }
                } catch (Exception e) {
                    return "Exception: " + e.getMessage();
                }
            }

            @Override
            protected void onPostExecute(String result) {
                if (result.startsWith("Exception") || result.startsWith("Server returned error")) {
                    Toast.makeText(PhoneNumberVerificationActivity.this, "Error: " + result, Toast.LENGTH_LONG).show();
                } else {
                    verificationCode = result;  // Assuming the server returns the verification code
                    Toast.makeText(PhoneNumberVerificationActivity.this, "Verification code sent.", Toast.LENGTH_SHORT).show();
                    // Navigate to verification code entry screen
                    Intent intent = new Intent(PhoneNumberVerificationActivity.this, PhoneNumberVerificationActivity.class);
                    startActivity(intent);
                }
            }
        }.execute();
    }

    private void verifyCode(final String code) {
        if (verificationCode != null && verificationCode.equals(code)) {
            // Verification successful, you can proceed further
            Toast.makeText(PhoneNumberVerificationActivity.this, "Verification successful.", Toast.LENGTH_SHORT).show();
        } else {
            // Async task to verify the code with the server
            new AsyncTask<Void, Void, String>() {
                @Override
                protected String doInBackground(Void... voids) {
                    try {
                        URL url = new URL("http://your-server-url/verify_code");
                        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                        conn.setRequestMethod("POST");
                        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                        conn.setDoOutput(true);

                        // Parameters to send
                        String params = "code=" + code;
                        OutputStream os = conn.getOutputStream();
                        os.write(params.getBytes());
                        os.flush();
                        os.close();

                        int responseCode = conn.getResponseCode();
                        if (responseCode == HttpURLConnection.HTTP_OK) {
                            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                            String inputLine;
                            StringBuilder response = new StringBuilder();
                            while ((inputLine = in.readLine()) != null) {
                                response.append(inputLine);
                            }
                            in.close();
                            return response.toString();
                        } else {
                            return "Server returned error: " + responseCode;
                        }
                    } catch (Exception e) {
                        return "Exception: " + e.getMessage();
                    }
                }

                @Override
                protected void onPostExecute(String result) {
                    if (result.equals("success")) {
                        Toast.makeText(PhoneNumberVerificationActivity.this, "Verification successful.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(PhoneNumberVerificationActivity.this, "Verification failed: " + result, Toast.LENGTH_LONG).show();
                    }
                }
            }.execute();
        }
    }
}
