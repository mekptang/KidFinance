package com.example.kidfinance;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
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
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class AccountCreate_Step2 extends AppCompatActivity {

    private static final int PICK_IMAGE = 100;

    Intent intent;
    ImageButton icon_button;
    EditText name_input;
    NumberPicker age_input;
    CardView next_button;

    int gender;
    String name;
    int age;
    Uri icon_uri;
    String icon_path;
    String icon_uri_string;
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
        } else {
            icon_button.setImageResource(R.drawable.create_account_step2_girl_photo);
        }
        age_input.setMinValue(1);
        age_input.setMaxValue(100);
        age_input.setValue(1);

        // Set Actions for Each Component
        icon_button.setOnClickListener(new ImageButton.OnClickListener() {
            public void onClick(View view) {
                Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(gallery, PICK_IMAGE);
            }
        });

        next_button.setOnClickListener(new ImageButton.OnClickListener() {
            public void onClick(View view) {
                name = name_input.getText().toString();
                age = age_input.getValue();

                if (name != "" && icon_uri != null) {
                    if (icon_uri == null && gender == 0) {
                        icon_uri_string = Uri.parse("android.resource://" + getApplicationContext().getPackageName() + "/" + R.drawable.create_account_step1_boy).toString();
                    } else if (icon_uri == null && gender == 1) {
                        icon_uri_string = Uri.parse("android.resource://" + getApplicationContext().getPackageName() + "/" + R.drawable.create_account_step1_girl).toString();
                    }

                    AccountModel new_account = new AccountModel(name, gender, age, icon_path);
                    String listSerializedToJson = new Gson().toJson(new_account);
                    writeToFile(listSerializedToJson, getApplicationContext(), account_file_name);
                    Toast.makeText(getApplicationContext(), "Account created successfully!", Toast.LENGTH_LONG).show();

                    // Go to MainActivity
                    Intent intent = new Intent(getApplicationContext(), Guide_Step0_1.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Please set your name and icon before continue!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
            FileOutputStream out = null;
            FileInputStream in = null;
            int cursor;

            icon_uri = data.getData();
            icon_path = getPath(icon_uri);
            icon_uri_string = icon_uri.toString();
            icon_button.setImageURI(icon_uri);

            // Save a copy of the selected icon to app storage
            // 验证是否许可权限 + 申请权限
            if (Build.VERSION.SDK_INT >= 23) {
                int REQUEST_CODE_CONTACT = 101;
                String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};

                for (String str : permissions) {
                    if (this.checkSelfPermission(str) != PackageManager.PERMISSION_GRANTED) {
                        this.requestPermissions(permissions, REQUEST_CODE_CONTACT);
                        return;
                    }
                }
            }
        }
    }

    // Get Path of selected account icon
    private String getPath(Uri uri) {
        String[] projection = {MediaStore.Video.Media.DATA};
        Cursor cursor = getApplicationContext().getContentResolver().query(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);
        cursor.moveToFirst();

        return cursor.getString(column_index);
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
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }
}
