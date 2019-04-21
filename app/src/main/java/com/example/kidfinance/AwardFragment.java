package com.example.kidfinance;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AwardFragment extends Fragment {

    ViewPager viewPager;
    AwardAdapter adapter;
    List<AwardModel> awards;
    Button setAwardBtn;
    int award_index = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle saveInstanceState) {

        awards = new ArrayList<>();

        //new code by roy
        String json = loadTextFile("kf_target_awardListJSON_config.txt");
        JsonArray arr = new JsonParser().parse(json).getAsJsonArray();



        //new code by roy
        for (JsonElement je : arr) {
            JsonObject all = je.getAsJsonObject();
            String image = all.get("imageResource").getAsString();
            String name = all.get("itemName").getAsString();
            String amount = all.get("numberOfItem").getAsString();
            String description = all.get("description").getAsString();

            AwardModel am = new AwardModel(image, name, amount, description);
            awards.add(am);
        }

        awards.add(new AwardModel(R.drawable.award_hamburger, "Hamburger", "Have a nice burger for a treat!"));
        awards.add(new AwardModel(R.drawable.award_comic, "Comic Books", "Buy your favourite comic series."));
        awards.add(new AwardModel(R.drawable.award_disney, "Disney Trip", "Go to Disneyland for a nice day."));
        awards.add(new AwardModel(R.drawable.award_juice, "Juice", "Have some juice to refresh!"));
        awards.add(new AwardModel(R.drawable.award_milktea, "Bubble Milk Tea", "How nice to have a cup of bubble tea after a long day!"));
        awards.add(new AwardModel(R.drawable.award_movie, "Watch a Movie", "How about watching a new movie?"));
        return inflater.inflate(R.layout.fragment_award, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewPager = view.findViewById(R.id.awardViewPager);
        adapter = new AwardAdapter(awards, getActivity());
        viewPager.setAdapter(adapter);
        if (award_index != 0)
            viewPager.setCurrentItem(award_index);
        viewPager.setPadding(130, 0, 130, 0);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                award_index = i;
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        setAwardBtn = view.findViewById(R.id.setAwardBtn);
        setAwardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), awards.get(award_index).getName() + " has been chosen as award.", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public String loadTextFile(String fileName)
    {
        String text = "";
        try {
            FileInputStream inStream = getContext().openFileInput(fileName);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int length = -1;
            while((length = inStream.read(buffer))!=-1) {
                stream.write(buffer,0,length);
            }
            stream.close();
            inStream.close();
            text = stream.toString();
            Toast.makeText(getContext(),"Loaded",Toast.LENGTH_LONG).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e){
            return e.toString();
        }
        return text;
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
        TextView amount = view.findViewById(R.id.award_amount);

        //new code by roy
        if(awards.get(position).getImage() == -1){
            File imgFile = new File(awards.get(position).getImage_path());
            Bitmap myBitmap;
            myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());

            image.setImageBitmap(myBitmap);
            title.setText(awards.get(position).getName());
            amount.setText("amount: " + awards.get(position).getAmount());
            desc.setText(awards.get(position).getDescription());
        }
        else {
            image.setImageResource(awards.get(position).getImage());
            title.setText(awards.get(position).getName());
            desc.setText(awards.get(position).getDescription());
            amount.setText("amount: " + 1);
        }

        container.addView(view, 0);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
