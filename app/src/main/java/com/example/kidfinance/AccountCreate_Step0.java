package com.example.kidfinance;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

import java.io.File;

public class AccountCreate_Step0 extends AppCompatActivity {

    String account_file_name = "account.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // If account.txt exists, we directly go to MainActivity
        // Why not use SharedPreference is because if an user reset the app, he/she will never be able to reset account details as the app is not first access
        if (fileExists(getApplicationContext(), account_file_name) == true) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        } else {
            setContentView(R.layout.account_create_step0);

            ImageButton next_button = (ImageButton) findViewById(R.id.create_account_step0_next);
            next_button.setOnClickListener(new ImageButton.OnClickListener() {
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), AccountCreate_Step1.class);
                    startActivity(intent);
                }
            });
        }
    }

    // Check if account.txt exists
    public boolean fileExists(Context context, String filename) {
        File file = context.getFileStreamPath(filename);

        if (file == null || !file.exists()) {
            return false;
        }

        return true;
    }
}
