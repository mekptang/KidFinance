package com.example.kidfinance;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ReturnTargetActivity extends AppCompatActivity {

    private Button reset_button;
    private Button set_button;
    private TextView target_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_return_target);

        reset_button = findViewById(R.id.reset_button);
        set_button = findViewById(R.id.set_button);
        target_text = findViewById(R.id.target_text);

        reset_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                target_text.setText("");
            }
        });

        set_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("result", target_text.getText().toString());
                ReturnTargetActivity.this.setResult(RESULT_OK, intent);
                ReturnTargetActivity.this.finish();
            }
        });
    }
}
