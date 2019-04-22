package com.example.kidfinance;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class AchievementGridAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<AchievementModel> achievements_info;

    // Constructor to initialize values
    public AchievementGridAdapter(Context context, ArrayList<AchievementModel> achievements_info) {
        this.context = context;
        this.achievements_info = achievements_info;
    }

    @Override
    public int getCount() {
        // Number of times getView method call depends upon achievements_info.length
        return achievements_info.size();
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
            logo.setImageResource(achievements_info.get(position).getAchievementLogo());
            TextView title = (TextView) current_card.findViewById(R.id.achievement_card_title);
            title.setText(achievements_info.get(position).getAchievementTitle());

            if (achievements_info.get(position).getAchievementSuccess() == 1) {
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