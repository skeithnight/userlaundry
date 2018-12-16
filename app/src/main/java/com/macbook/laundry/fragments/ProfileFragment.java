package com.macbook.laundry.fragments;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.github.rubensousa.raiflatbutton.RaiflatButton;
import com.macbook.laundry.R;
import com.macbook.laundry.activities.LoginActivity;
import com.macbook.laundry.controller.Authorization;
import com.macbook.laundry.models.Customer;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    View mView;
    private static String TAG = "Testing";
    //    SharedPreferences
    SharedPreferences mSPLogin;
    Customer customer = new Customer();

    @BindView(R.id.name_profile)
    EditText et_name;
    @BindView(R.id.username_profile)
    EditText et_username;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_profile, container, false);
        ButterKnife.bind(this,mView);
        
        getDataProfile();

        return mView;
    }

    private void getDataProfile() {
        mSPLogin = mView.getContext().getSharedPreferences("Login", Context.MODE_PRIVATE);
        customer.setId(mSPLogin.getString("id",null));
        customer.setNama(mSPLogin.getString("name",null));
        customer.setUsername(mSPLogin.getString("username",null));

        if (customer.getId() != null){
            et_name.setText(customer.getNama());
            et_username.setText(customer.getUsername());
        }
    }

    @OnClick(R.id.btn_logout)
    public void button(Button btn){

        Authorization authorization = new Authorization(mView.getContext());
        authorization.logout();
    }

}
