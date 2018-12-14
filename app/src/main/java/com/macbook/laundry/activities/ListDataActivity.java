package com.macbook.laundry.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.macbook.laundry.R;
import com.macbook.laundry.TampilDialog;

public class ListDataActivity extends AppCompatActivity {
    String menu, token;

    static String TAG = "Testing";
    private TampilDialog tampilDialog;
    //    SharedPreferences
    SharedPreferences mSPLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_data);
        ButterKnife.bind(this);
        tampilDialog = new TampilDialog(this);

        // show loading
        tampilDialog.showLoading();
        
        getData();
    }

    private void getData() {
        Bundle extras = getIntent().getExtras();
        if(extras == null) {
            menu= null;
        } else {
            menu= extras.getString("menu");
            if (menu.equals("Layanan")){
                setTitle(menu);
//                getDataTipeKamar();
            }else if (menu.equals("Toko")){
                setTitle(menu);
//                getDataMenu();
            }
        }
        mSPLogin = getSharedPreferences("Login", Context.MODE_PRIVATE);
        token = mSPLogin.getString("token",null);

        tampilDialog.dismissLoading();
    }

    @OnClick(R.id.cd_list_toko)
    public void click(){
        // Create a Uri from an intent string. Use the result to create an Intent.
        Uri gmmIntentUri = Uri.parse("geo:0,0?q=-7.002117, 107.622835(Oxy+Laundry)");

// Create an Intent from gmmIntentUri. Set the action to ACTION_VIEW
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
// Make the Intent explicit by setting the Google Maps package
        mapIntent.setPackage("com.google.android.apps.maps");

// Attempt to start an activity that can handle the Intent
        startActivity(mapIntent);
    }
}
