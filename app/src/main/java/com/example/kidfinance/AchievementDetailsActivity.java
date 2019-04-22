package com.example.kidfinance;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class AchievementDetailsActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_achievement_details);

        Intent intent = getIntent();

        // Set up the background
        CardView current_card = (CardView) this.findViewById(R.id.achievement_details_cardView);

        // Display the details of the achievement
        if (intent.getExtras() != null) {
            int achievement_logo_id = intent.getIntExtra("logo", 0);
            String achievement_title = intent.getStringExtra("achievement_title");
            String achievement_description = intent.getStringExtra("achievement_description");
            int achievement_completion = intent.getIntExtra("achievement_completion", 0);

            ImageView logo = (ImageView) current_card.findViewById(R.id.achievement_details_achievement_logo);
            TextView title = (TextView) current_card.findViewById(R.id.achievement_details_achievement_title);
            TextView description = (TextView) current_card.findViewById(R.id.achievement_details_achievement_description);
            ImageView completion_logo = (ImageView) current_card.findViewById(R.id.achievement_details_success_image);

            logo.setImageResource(achievement_logo_id);
            title.setText(achievement_title);
            description.setText(achievement_description);

            if (achievement_completion == 1) {
                completion_logo.setImageResource(R.drawable.achievement_success);
            } else {
                completion_logo.setImageResource(0);
            }
        }

        ImageButton back_button = (ImageButton) this.findViewById(R.id.achievement_details_back_button);
        back_button.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent();
                AchievementDetailsActivity.this.setResult(RESULT_OK, intent);
                AchievementDetailsActivity.this.finish();
            }
        });
    }
}
