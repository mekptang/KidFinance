package com.example.kidfinance;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class AccountCreate_Step1 extends AppCompatActivity {
    public static final String gender = "com.example.application.example.gender";

    ImageButton boy_button;
    ImageButton girl_button;
    ImageButton next_button;
    int selected_gender = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_create_step1);

        boy_button = (ImageButton) findViewById(R.id.create_account_step1_boy);
        girl_button = (ImageButton) findViewById(R.id.create_account_step1_girl);
        next_button = (ImageButton) findViewById(R.id.create_account_step1_next);

        boy_button.setOnClickListener(new ImageButton.OnClickListener() {
            public void onClick(View view) {
                boy_button.setImageResource(R.drawable.create_account_step1_boy_tick);
                girl_button.setImageResource(R.drawable.create_account_step1_girl);
                selected_gender = 0;
            }
        });

        girl_button.setOnClickListener(new ImageButton.OnClickListener() {
            public void onClick(View view) {
                girl_button.setImageResource(R.drawable.create_account_step1_girl_tick);
                boy_button.setImageResource(R.drawable.create_account_step1_boy);
                selected_gender = 1;
            }
        });

        next_button.setOnClickListener(new ImageButton.OnClickListener() {
            public void onClick(View view) {
                if (selected_gender == 0 || selected_gender == 1) {
                    Intent intent = new Intent(getApplicationContext(), AccountCreate_Step2.class);
                    intent.putExtra(gender, selected_gender);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Please select your gender before continue!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}

