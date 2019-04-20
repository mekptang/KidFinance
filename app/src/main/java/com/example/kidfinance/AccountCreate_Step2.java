package com.example.kidfinance;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class AccountCreate_Step2 extends AppCompatActivity {

    Intent intent;
    ImageButton icon_button;
    EditText name_input;
    NumberPicker age_input;
    CardView next_button;

    int gender;
    String name;
    int age;
    int icon_id;

    String account_file_name = "account.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_create_step2);

        // Get data from AccountCreate_Step1;
        intent = getIntent();
        gender = intent.getIntExtra(AccountCreate_Step1.gender, 0);

        // Initialize the values of the components
        icon_button = (ImageButton) findViewById(R.id.create_account_step2_icon);
        name_input = (EditText) findViewById(R.id.create_account_step2_name_edit);
        age_input = (NumberPicker) findViewById(R.id.create_account_step2_age_edit);
        next_button = (CardView) findViewById(R.id.create_account_step2_create_button);

        if (gender == 0) {
            icon_button.setImageResource(R.drawable.create_account_step2_boy_photo);
        }
        else {
            icon_button.setImageResource(R.drawable.create_account_step2_girl_photo);
        }
        age_input.setMinValue(1);
        age_input.setMaxValue(100);
        age_input.setValue(1);

        // Set Actions for Each Component
        icon_button.setOnClickListener(new ImageButton.OnClickListener() {
            public void onClick(View view) {
                name = name_input.getText().toString();
            }
        });

        next_button.setOnClickListener(new ImageButton.OnClickListener() {
            public void onClick(View view) {
                name = name_input.getText().toString();
                age = age_input.getValue();
                icon_id = R.drawable.create_account_step2_boy_photo;

                if (name != "") {
                    AccountModel new_account = new AccountModel(name, gender, age, icon_id);

                    String listSerializedToJson = new Gson().toJson(new_account);
                    writeToFile(listSerializedToJson, getApplicationContext(), account_file_name);
                    Toast.makeText(getApplicationContext(), "Account created successfully!", Toast.LENGTH_LONG).show();

                    // Go to MainActivity
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(getApplicationContext(), "Please enter your name before continue!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    // File I/O for storing account info
    public boolean fileExists(Context context, String filename) {
        File file = context.getFileStreamPath(filename);

        if (file == null || !file.exists()) {
            return false;
        }

        return true;
    }

    private void writeToFile(String data, Context context, String fileName) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput(fileName, Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }
}
