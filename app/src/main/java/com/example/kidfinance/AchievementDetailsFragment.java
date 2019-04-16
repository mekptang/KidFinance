package com.example.kidfinance;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class AchievementDetailsFragment extends Fragment {

    Bundle bundle;
    CardView current_card;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle saveInstanceState) {
        // Set up the background
        View view_achievement_details = inflater.inflate(R.layout.fragment_achievement_details, container, false);
        CardView current_card = (CardView) view_achievement_details.findViewById(R.id.achievement_details_cardView);

        // Display the details of the achievement
        bundle = this.getArguments();

        if (bundle != null) {
            int achievement_logo_id = bundle.getInt("logo", 0);
            String achievement_title = bundle.getString("achievement_title");
            String achievement_description = bundle.getString("achievement_description");
            int achievement_completion = bundle.getInt("achievement_completion", 0);

            ImageView logo = (ImageView) current_card.findViewById(R.id.achievement_details_achievement_logo);
            TextView title = (TextView) current_card.findViewById(R.id.achievement_details_achievement_title);
            TextView description = (TextView) current_card.findViewById(R.id.achievement_details_achievement_description);
            ImageView completion_logo = (ImageView) current_card.findViewById(R.id.achievement_details_success_image);

            logo.setImageResource(achievement_logo_id);
            title.setText(achievement_title);
            description.setText(achievement_description);

            if (achievement_completion == 1) {
                completion_logo.setImageResource(R.drawable.achievement_success);
            }
            else {
                completion_logo.setImageResource(0);
            }
        }

        return view_achievement_details;
    }

    public void onViewCreated(View view_achievement_details, Bundle savedInstanceState) {
        ImageButton back_button = (ImageButton) view_achievement_details.findViewById(R.id.achievement_details_back_button);
        back_button.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View view) {
                int i = 0;
                FragmentManager fm = getActivity().getSupportFragmentManager();
                fm.popBackStack();
            }
        });
    }
}
