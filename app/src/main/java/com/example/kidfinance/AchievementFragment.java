package com.example.kidfinance;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class AchievementFragment extends Fragment {

    //private Button abc;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle saveInstanceState){
        return inflater.inflate(R.layout.fragment_achievement, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle saveInstanceState){
        super.onViewCreated(view, saveInstanceState);

//        abc= (Button)view.findViewById(R.id.button);
//
//        abc.setOnClickListener(new View.OnClickListener(){
//
//            @Override
//            public void onClick(View v){
//                efg(v);}
//        });

    }
    public void efg(View view){
        Toast.makeText( getActivity (), "asdasdasdasdasd", Toast.LENGTH_SHORT).show();
    }
}

