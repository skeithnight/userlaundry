package com.macbook.laundry.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.github.rubensousa.raiflatbutton.RaiflatButton;
import com.macbook.laundry.R;
import com.macbook.laundry.activities.LoginActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    View mView;

//    @BindView(R.id.btn_logout)
//    Button btn_logout;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_profile, container, false);
        ButterKnife.bind(this,mView);

//        btn_logout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(getContext(), "adadada", Toast.LENGTH_SHORT).show();
//            }
//        });

        return mView;
    }

    @OnClick(R.id.btn_logout)
    public void button(Button btn){
        Toast.makeText(getActivity(), String.valueOf(btn.getId()), Toast.LENGTH_SHORT).show();
//        Toast.makeText(getActivity(), "adada", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(mView.getContext(), LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

}
