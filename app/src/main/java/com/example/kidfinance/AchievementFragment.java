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
import android.widget.BaseAdapter;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class AchievementFragment extends Fragment {

    GridView mainGrid;
    static final String[ ] GRID_DATA = new String[] {"Hello", "Hello", "Hello", "Hello"};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle saveInstanceState) {
        // Inflate fragment_achievement.xml + Get gridview object from xml file
        View view_achievement = inflater.inflate(R.layout.fragment_achievement, container, false);
        mainGrid = (GridView) view_achievement.findViewById(R.id.mainGrid);

        return view_achievement;
    }

    public void onViewCreated(View view_achievement, Bundle savedInstanceState) {
        // Set custom adapter (GridAdapter) to mainGrid
        mainGrid.setAdapter(new GridAdapter(getContext(), GRID_DATA));

        // Set Event
        // setSingleEvent(mainGrid);
    }

    private void setToggleEvent(GridLayout mainGrid) {
        // Loop all child item of Main Grid
        for (int i = 0; i < mainGrid.getChildCount(); i++) {
            //You can see , all child item is CardView , so we just cast object to CardView
            final CardView cardView = (CardView) mainGrid.getChildAt(i);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (cardView.getCardBackgroundColor().getDefaultColor() == -1) {
                        // Change background color
                        cardView.setCardBackgroundColor(Color.parseColor("#FF6F00"));
                        Toast.makeText(getActivity(), "State : True", Toast.LENGTH_SHORT).show();

                    }
                    else {
                        // Change background color
                        cardView.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
                        Toast.makeText(getActivity(), "State : False", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    public static class GridAdapter extends BaseAdapter {
        private Context context;
        private final String[] gridValues;

        // Constructor to initialize values
        public GridAdapter(Context context, String[ ] gridValues) {
            this.context = context;
            this.gridValues = gridValues;
        }

        @Override
        public int getCount() {
            // Number of times getView method call depends upon gridValues.length
            return gridValues.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        // Number of times getView method call depends upon gridValues.length
        public View getView(int position, View convertView, ViewGroup parent) {
            // LayoutInflator to call external achievement_item.xml file
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View gridView = convertView;

            if (convertView == null) {
                // gridView = new View(context);

                // get layout from grid_item.xml ( Defined Below )
                gridView = inflater.inflate(R.layout.achievement_item, null);

                // set text and image based on selected text
                // String arrLabel = gridValues[ position ];
                ImageView imageView = (ImageView) gridView.findViewById(R.id.grid_item_image);
                imageView.setImageResource(R.drawable.friends);
                TextView textView = (TextView) gridView.findViewById(R.id.grid_item_label);
                textView.setText("JEFF");

                gridView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(view.getContext(), "Item is clicked", Toast.LENGTH_SHORT).show();
                    }
                });
            }
            else {
                gridView = (View) convertView;
            }

            return gridView;
        }
    }
}


