package com.example.kidfinance;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

public class Guide_Step5 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guide_step5);

        ImageButton guide_next = (ImageButton) this.findViewById(R.id.guide_next);
        guide_next.setOnClickListener(new ImageButton.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Guide_Step6.class);
                startActivity(intent);
            }
        });
    }
}
