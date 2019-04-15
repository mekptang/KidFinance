package com.example.kidfinance;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class AwardFragment extends Fragment {

    ViewPager viewPager;
    AwardAdapter adapter;
    List<AwardModel> awards;
    int award_index = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle saveInstanceState) {
        awards = new ArrayList<>();

        return inflater.inflate(R.layout.fragment_award, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewPager = view.findViewById(R.id.awardViewPager);
        adapter = new AwardAdapter(awards, getActivity());
        viewPager.setAdapter(adapter);
        viewPager.setPadding(130, 0, 130, 0);
    }
}

class AwardAdapter extends PagerAdapter {

    private List<AwardModel> awards;
    private LayoutInflater layoutInflater;
    private Context context;

    AwardAdapter(List<AwardModel> awards, Context context) {
        this.awards = awards;
        this.context = context;
    }

    @Override
    public int getCount() {
        return awards.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view.equals(o);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.award_item, container, false);

        ImageView image = view.findViewById(R.id.award_image);
        TextView title = view.findViewById(R.id.award_title);
        TextView desc = view.findViewById(R.id.award_desc);

        image.setImageResource(awards.get(position).getImage());
        title.setText(awards.get(position).getName());
        desc.setText(awards.get(position).getDescription());

        container.addView(view, 0);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
