package com.macbook.laundry.fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import com.google.gson.Gson;
import com.macbook.laundry.R;
import com.macbook.laundry.TampilDialog;
import com.macbook.laundry.activities.ListDataActivity;
import com.macbook.laundry.adapter.RecyclerViewAdapterMenuLaundry;
import com.macbook.laundry.adapter.RecyclerViewAdapterTransaksi;
import com.macbook.laundry.api.APIClient;
import com.macbook.laundry.api.DataService;
import com.macbook.laundry.models.MenuLaundry;
import com.macbook.laundry.models.Transaksi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class PesananFragment extends Fragment {

    View mView;
    String token, idUser;

    static String TAG = "Testing";
    private TampilDialog tampilDialog;
    //    SharedPreferences
    SharedPreferences mSPLogin;
    // RecyclerView
    RecyclerView recyclerView;

    public PesananFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_pesanan, container, false);
        ButterKnife.bind(this, mView);
        tampilDialog = new TampilDialog(mView.getContext());

        // show loading
        tampilDialog.showLoading();

        getData();
        return mView;
    }


    //    Data Menu Pesanan
    ArrayList<Transaksi> transaksiArrayList = new ArrayList<Transaksi>();
    private RecyclerViewAdapterTransaksi mAdapter;

    private void getData() {
        mSPLogin = mView.getContext().getSharedPreferences("Login", Context.MODE_PRIVATE);
        token = mSPLogin.getString("token", null);
        idUser = mSPLogin.getString("id", null);
        if (token != null) {

            DataService dataService = APIClient.getClient().create(DataService.class);
            dataService.getListTransaksi("Bearer " + token, idUser).enqueue(new Callback<ArrayList<Transaksi>>() {
                @Override
                public void onResponse(Call<ArrayList<Transaksi>> call, Response<ArrayList<Transaksi>> response) {
                    tampilDialog.dismissLoading();
                    if (response.isSuccessful()) {
                        transaksiArrayList = response.body();

                        // initiate RecyclerView
                        recyclerView = (RecyclerView) mView.findViewById(R.id.rv_list_pesanan);
                        recyclerView.setVisibility(View.VISIBLE);

                        mAdapter = new RecyclerViewAdapterTransaksi(transaksiArrayList);
                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mView.getContext());
                        recyclerView.setLayoutManager(mLayoutManager);
                        recyclerView.setItemAnimator(new DefaultItemAnimator());
                        recyclerView.setAdapter(mAdapter);
                        mAdapter.notifyDataSetChanged();
                    }else {
                        tampilDialog.showDialog("Failed", response.message(),"");
                    }

                }

                @Override
                public void onFailure(Call<ArrayList<Transaksi>> call, Throwable t) {
                    tampilDialog.dismissLoading();
                    tampilDialog.showDialog("Failed", t.getMessage(),"");
                }
            });
        }
    }
}
