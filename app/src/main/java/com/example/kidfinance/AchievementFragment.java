package com.example.kidfinance;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class AchievementFragment extends Fragment {
    GridView mainGrid;
    AchievementInfo[] achievements;

    // Declare arrays to store achievement info + Store the information to AchievementInfo class (i.e. Object achievements)
    int[] achievement_logo = {R.drawable.achievement_calendar, R.drawable.achievement_calendar, R.drawable.achievement_calendar,
            R.drawable.achievement_family_time, R.drawable.achievement_family_time, R.drawable.achievement_family_time,
            R.drawable.achievement_friends, R.drawable.achievement_friends, R.drawable.achievement_friends};
    String[] achievement_title1 = {"Achiever (LV1)", "Achiever (LV2)", "Achiever (LV3)", "Fighter (LV1)", "Fighter (LV2)", "Fighter (LV3)",
            "General (LV1)", "General (LV2)", "General (LV3)"};
    String[] achievement_title2 = {"Achiever (LV1)", "Achiever (LV2)", "Achiever (LV3)", "Fighter (LV1)", "Fighter (LV2)", "Fighter (LV3)",
            "General (LV1)", "General (LV2)", "General (LV3)"};
    String[] achievement_info = {"Achiever (LV1)", "Achiever (LV2)", "Achiever (LV3)", "Fighter (LV1)", "Successfully Saved $10000!", "Fighter (LV3)",
            " General (LV1)", "General (LV2)", "General (LV3)"};
    int[] achievement_success = {1, 0, 0, 1, 1, 1, 0, 0, 0};
    int achievement_length = achievement_logo.length;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Store the achievement  information to AchievementInfo class (i.e. Object achievements)
        achievements = new AchievementInfo[achievement_length];

        for (int i = 0; i < achievement_length; i++) {
            achievements[i] = new AchievementInfo(achievement_logo[i], achievement_title1[i], achievement_title2[i], achievement_info[i], achievement_success[i]);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle saveInstanceState) {
        // Inflate fragment_achievement.xml
        View view_achievement = inflater.inflate(R.layout.fragment_achievement, container, false);

        // Get gridview object from xml file + Set custom adapter (GridAdapter) to mainGrid
        mainGrid = (GridView) view_achievement.findViewById(R.id.achievement_mainGrid);
        mainGrid.setAdapter(new GridAdapter(getContext(), achievements));

        return view_achievement;
    }

    public void onViewCreated(View view_achievement, Bundle savedInstanceState) {
        // Set item click listener to each button
        mainGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle info = new Bundle();
                info.putInt("logo", achievements[position].card_logo);
                info.putString("achievement_title", achievements[position].achievement_title);
                info.putString("achievement_description", achievements[position].achievement_description);
                info.putInt("achievement_completion", achievements[position].achievement_success);

                Fragment achievement_details = new AchievementDetailsFragment();
                achievement_details.setArguments(info);

                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, achievement_details, null)
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    public class AchievementInfo {
        private int card_logo;
        private String card_title;
        private String achievement_title;
        private String achievement_description;
        private int achievement_success;

        public AchievementInfo(int logo, String title1, String title2, String description, int success) {
            this.card_logo = logo;
            this.card_title = title1;
            this.achievement_title = title2;
            this.achievement_description = description;
            this.achievement_success = success;
        }
    }

    public static class GridAdapter extends BaseAdapter {
        private Context context;
        private final AchievementInfo[] achievements_info;

        // Constructor to initialize values
        public GridAdapter(Context context, AchievementInfo[] achievements_info) {
            this.context = context;
            this.achievements_info = achievements_info;
        }

        @Override
        public int getCount() {
            // Number of times getView method call depends upon achievements_info.length
            return achievements_info.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        // Number of times getView method call depends upon achievements_info.length
        public View getView(int position, View current_card, ViewGroup parent) {
            // LayoutInflator to call external achievement_item.xml file
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            if (current_card == null) {
                // get layout from grid_item.xml ( Defined Below achievements_info)
                current_card = inflater.inflate(R.layout.achievement_item, null);
                CardView current_card_inner = (CardView) current_card.findViewById(R.id.achievement_cardView);

                // set text and image based on selected text
                // String arrLabel = gridValues[ position ];
                ImageView logo = (ImageView) current_card.findViewById(R.id.achievement_card_logo);
                logo.setImageResource(achievements_info[position].card_logo);
                TextView title = (TextView) current_card.findViewById(R.id.achievement_card_title);
                title.setText(achievements_info[position].card_title);

                if (achievements_info[position].achievement_success == 1) {
                    ImageView success = (ImageView) current_card.findViewById(R.id.achievement_success_image);
                    success.setImageResource(R.drawable.achievement_success);
                    current_card_inner.setCardBackgroundColor(Color.parseColor("#FFFFFF00"));
                } else {
                    ImageView success = (ImageView) current_card.findViewById(R.id.achievement_success_image);
                    success.setImageResource(0);
                    current_card_inner.setCardBackgroundColor(Color.parseColor("#FFFFFFFF"));
                }
            }

            return current_card;
        }
    }
}


