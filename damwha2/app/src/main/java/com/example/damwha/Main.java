package com.example.damwha;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class Main extends AppCompatActivity {

    private ImageView imageViewProfile;
    private TextView textViewName, textViewNickname, textViewPhoneNumber, textViewLiving;
    private Button buttonLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        // UI 요소 초기화
        imageViewProfile = findViewById(R.id.imageViewProfile);
        textViewName = findViewById(R.id.textViewName);
        textViewNickname = findViewById(R.id.textViewNickname);
        textViewPhoneNumber = findViewById(R.id.textViewPhoneNumber);
        textViewLiving = findViewById(R.id.textViewLiving);
        buttonLogout = findViewById(R.id.buttonLogout);

        // 사용자 정보 : 임시, 데베연결해서 수정하기
        textViewName.setText("Name: John Doe");
        textViewNickname.setText("Nickname: johnd");
        textViewPhoneNumber.setText("Phone Number: 123-456-7890");
        textViewLiving.setText("Living: New York");

        // 로그아웃 버튼 누를때
        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle logout logic here (e.g., clear user session, redirect to login screen)
                Toast.makeText(Main.this, "Logging out...", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Main.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });
    }
}
