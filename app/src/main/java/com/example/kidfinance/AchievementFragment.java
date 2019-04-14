package com.example.kidfinance;

import android.app.Activity;
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
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class AchievementFragment extends Fragment {
    GridView mainGrid;

    // Declare arrays to store achievement info + Store the information to AchievementInfo class (i.e. Object achievements)
    int[] logo                   =  {R.drawable.achievement_calendar, R.drawable.achievement_calendar, R.drawable.achievement_calendar,
                                    R.drawable.achievement_family_time, R.drawable.achievement_family_time, R.drawable.achievement_family_time,
                                    R.drawable.achievement_friends, R.drawable.achievement_friends, R.drawable.achievement_friends};
    String[] title1               =  {"Achiever (LV1)", "Achiever (LV2)", "Achiever (LV3)", "Fighter (LV1)", "Fighter (LV2)", "Fighter (LV3)",
                                    "General (LV1)", "General (LV2)", "General (LV3)"};
    String[] title2               =  {"Achiever (LV1)", "Achiever (LV2)", "Achiever (LV3)", "Fighter (LV1)", "Fighter (LV2)", "Fighter (LV3)",
                                    "General (LV1)", "General (LV2)", "General (LV3)"};
    String[] achievement_info1  =  {"Achiever (LV1)", "Achiever (LV2)", "Achiever (LV3)", "Fighter (LV1)", "Fighter (LV2)", "Fighter (LV3)",
                                    " General (LV1)", "General (LV2)", "General (LV3)"};
    String[] achievement_info2  =  {"Achiever (LV1)", "Achiever (LV2)", "Achiever (LV3)", "Fighter (LV1)", "Fighter (LV2)", "Fighter (LV3)",
                                    "General (LV1)", "General (LV2)", "General (LV3)"};
    String[] achievement_info3  =  {"Achiever (LV1)", "Achiever (LV2)", "Achiever (LV3)", "Fighter (LV1)", "Fighter (LV2)", "Fighter (LV3)",
                                    "General (LV1)", "General (LV2)", "General (LV3)"};

    int achievement_length = logo.length;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle saveInstanceState) {
        // Store the achievement  information to AchievementInfo class (i.e. Object achievements)
        AchievementInfo[] achievements = new AchievementInfo[achievement_length];

        for (int i = 0; i < achievement_length; i++) {
            achievements[i] = new AchievementInfo(logo[i], title1[i], title2[i], achievement_info1[i], achievement_info2[i], achievement_info3[i]);
        }

        // Inflate fragment_achievement.xml
        View view_achievement = inflater.inflate(R.layout.fragment_achievement, container, false);

        // Get gridview object from xml file + Set custom adapter (GridAdapter) to mainGrid
        mainGrid = (GridView) view_achievement.findViewById(R.id.mainGrid);
        mainGrid.setAdapter(new GridAdapter(getContext(), achievements));

        return view_achievement;
    }

    public void onViewCreated(View view_achievement, Bundle savedInstanceState) {
        // Set item click listener to each button
        mainGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CardView current_card = (CardView) view.findViewById(R.id.cardView);

                if (current_card.getCardBackgroundColor().getDefaultColor() == -1) {
                    // Change background color
                    current_card.setCardBackgroundColor(Color.parseColor("#FF6F00"));
                    Toast.makeText(getActivity(), "State : True", Toast.LENGTH_SHORT).show();
                }
                else {
                    // Change background color
                    current_card.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
                    Toast.makeText(getActivity(), "State : False", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public class AchievementInfo {
        private int card_logo;
        private String card_title;
        private String achievement_title;
        private String achievement_info1;
        private String achievement_info2;
        private String achievement_info3;

        public AchievementInfo(int logo, String title1, String title2, String info1, String info2, String info3) {
            this.card_logo = logo;
            this.card_title = title1;
            this.achievement_title = title2;
            this.achievement_info1 = info1;
            this.achievement_info2 = info2;
            this.achievement_info3 = info3;
        }
    }

    public static class GridAdapter extends BaseAdapter {
        private Context context;
        private final AchievementInfo[] achievements_info;

        // Constructor to initialize values
        public GridAdapter(Context context, AchievementInfo[ ] achievements_info) {
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
        public View getView(int position, View convertView, ViewGroup parent) {
            // LayoutInflator to call external achievement_item.xml file
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View gridView = convertView;

            if (convertView == null) {
                // get layout from grid_item.xml ( Defined Below )
                gridView = inflater.inflate(R.layout.achievement_item, null);

                // set text and image based on selected text
                // String arrLabel = gridValues[ position ];
                ImageView imageView = (ImageView) gridView.findViewById(R.id.grid_item_image);
                imageView.setImageResource(achievements_info[position].card_logo);
                TextView textView = (TextView) gridView.findViewById(R.id.grid_item_label);
                textView.setText(achievements_info[position].card_title);
            }
            else {
                gridView = (View) convertView;
            }

            return gridView;
        }
    }
}


