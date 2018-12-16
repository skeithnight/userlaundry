package com.macbook.laundry.controller;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import com.google.android.gms.auth.api.Auth;
import com.macbook.laundry.MainActivity;
import com.macbook.laundry.TampilDialog;
import com.macbook.laundry.activities.LoginActivity;
import com.macbook.laundry.api.APIClient;
import com.macbook.laundry.api.AuthService;
import com.macbook.laundry.models.Customer;
import com.macbook.laundry.models.ResponseLogin;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.IOException;

public class Authorization {

    private static String TAG = "Testing";
    //    SharedPreferences
    SharedPreferences mSPLogin;
    Context context;
    TampilDialog tampilDialog;

    public Authorization(Context context) {
        this.context = context;
    }

    private void initializeSP() {
        mSPLogin = context.getSharedPreferences("Login", Context.MODE_PRIVATE);
        tampilDialog = new TampilDialog(context);
    }

    public boolean CheckSession() {
        initializeSP();
        String token = mSPLogin.getString("token", null);
        if (token != null) {
            checkToken(token);
            return true;
        }
        return false;
    }

    private void checkToken(String token) {
        AuthService authService = APIClient.getClient().create(AuthService.class);
        authService.getCheckLogin("Bearer "+token).enqueue(new Callback<Customer>() {
            @Override
            public void onResponse(Call<Customer> call, Response<Customer> response) {
                if (!response.isSuccessful()){
                    Intent intent = new Intent(context,LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    context.startActivity(intent);
                }else {
                    SharedPreferences.Editor editor = mSPLogin.edit();
                    editor.putString("id",response.body().getId());
                    editor.putString("name",response.body().getNama());
                    editor.putString("username",response.body().getUsername());
                    editor.commit();
                }
            }

            @Override
            public void onFailure(Call<Customer> call, Throwable t) {
                Log.i(TAG, "onFailure: "+t.getMessage());
                Intent intent = new Intent(context,LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(intent);
            }
        });
    }

    public void logout(){
        initializeSP();
        mSPLogin.edit().clear().commit();
        Intent intent = new Intent(context, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
    }
}
