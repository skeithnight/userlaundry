package com.macbook.laundry.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Toast;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.macbook.laundry.R;
import com.macbook.laundry.activities.ListDataActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListDataFragment extends Fragment {

    View mview;


    public ListDataFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mview = inflater.inflate(R.layout.fragment_list_data, container, false);
//        Initiate Butterknife
        ButterKnife.bind(this,mview);

        return mview;
    }

    @OnClick({R.id.cd_list_data_1,R.id.cd_list_data_2})
    public void click(View view){
        switch (view.getId()) {
            case R.id.cd_list_data_1:
                goToListData("Layanan");
//                Toast.makeText(mview.getContext(), "Woooi", Toast.LENGTH_SHORT).show();
                break;
            case R.id.cd_list_data_2:
                goToListData("Toko");
//                Toast.makeText(mview.getContext(), "222222", Toast.LENGTH_SHORT).show();
                break;
        }
    }
    private void goToListData(String menu) {
        Intent intent = new Intent(mview.getContext(), ListDataActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("menu",menu);
        startActivity(intent);
    }
}
