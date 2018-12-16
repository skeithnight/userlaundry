package com.macbook.laundry.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.macbook.laundry.R;
import com.macbook.laundry.models.MenuLaundry;

import java.util.ArrayList;

public class RecyclerViewAdapterMenuLaundry extends RecyclerView.Adapter<RecyclerViewAdapterMenuLaundry.MyViewHolder> {
    private ArrayList<MenuLaundry> menuLaundryArrayList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView namaLaundry, hargaLaundry;

        public MyViewHolder(View view) {
            super(view);
            namaLaundry = (TextView) view.findViewById(R.id.tv_nama_layanan);
            hargaLaundry = (TextView) view.findViewById(R.id.tv_harga);
        }
    }

    public RecyclerViewAdapterMenuLaundry(ArrayList<MenuLaundry> menuLaundryArrayList) {
        this.menuLaundryArrayList = menuLaundryArrayList;
    }

    @Override
    public RecyclerViewAdapterMenuLaundry.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view_menu_laundry, parent, false);

        return new RecyclerViewAdapterMenuLaundry.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerViewAdapterMenuLaundry.MyViewHolder holder, int position) {
        MenuLaundry menuLaundry = menuLaundryArrayList.get(position);

        holder.namaLaundry.setText(menuLaundry.getJenis());
        holder.hargaLaundry.setText("Rp. "+String.valueOf(menuLaundry.getHarga())+ " /"+menuLaundry.getSatuan().toUpperCase());
    }

    @Override
    public int getItemCount() {
        return menuLaundryArrayList.size();
    }
}
