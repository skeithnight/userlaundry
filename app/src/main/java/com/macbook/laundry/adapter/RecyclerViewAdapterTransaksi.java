package com.macbook.laundry.adapter;

import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.google.gson.Gson;
import com.macbook.laundry.MainActivity;
import com.macbook.laundry.R;
import com.macbook.laundry.activities.DetailPesananActivity;
import com.macbook.laundry.models.Transaksi;
import de.hdodenhof.circleimageview.CircleImageView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class RecyclerViewAdapterTransaksi extends RecyclerView.Adapter<RecyclerViewAdapterTransaksi.MyViewHolder> {
    private ArrayList<Transaksi> TransaksiArrayList;

    public RecyclerViewAdapterTransaksi(ArrayList<Transaksi> TransaksiArrayList) {
        this.TransaksiArrayList = TransaksiArrayList;
    }

    @Override
    public RecyclerViewAdapterTransaksi.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view_pesanan, parent, false);

        return new RecyclerViewAdapterTransaksi.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final RecyclerViewAdapterTransaksi.MyViewHolder holder, int position) {
        final Transaksi Transaksi = TransaksiArrayList.get(position);

        holder.idPesanan.setText(Transaksi.getId() == null ? "null" : Transaksi.getId());
        holder.tanggal_masuk.setText(Transaksi.getWaktuPesan() == null ? "null" : getDate(Transaksi.getWaktuPesan()));
        holder.toko.setText(Transaksi.getCabang().getNama() == null ? "null" : Transaksi.getCabang().getNama());

        setLogoStatus(holder, Transaksi.getStatus());

        holder.cdPesanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.mView.getContext(), DetailPesananActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Gson gson = new Gson();
                intent.putExtra("transaksi",gson.toJson(Transaksi));
                holder.mView.getContext().startActivity(intent);
            }
        });
    }

    private void setLogoStatus(MyViewHolder holder, String status) {

        if (status.equals("Jemput")) {
            holder.statusLogo.setImageResource(R.drawable.trucking);
        } else if (status.equals("Washing")) {
            holder.statusLogo.setImageResource(R.drawable.washing_machine);

        } else {
            holder.statusLogo.setImageResource(R.drawable.ic_clear_white_24dp);
        }
    }

    @Override
    public int getItemCount() {
        return TransaksiArrayList.size();
    }

    private String getDate(long time) {
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(time);
        String date = DateFormat.format("dd-MM-yyyy", cal).toString();
        return date;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView idPesanan, tanggal_masuk, toko;
        public CircleImageView statusLogo;
        public CardView cdPesanan;
        public View mView;

        public MyViewHolder(View view) {
            super(view);
            mView = view;
            idPesanan = (TextView) view.findViewById(R.id.tv_id_pesanan);
            tanggal_masuk = (TextView) view.findViewById(R.id.tv_tanggal_masuk);
            toko = (TextView) view.findViewById(R.id.tv_toko);
            statusLogo = (CircleImageView) view.findViewById(R.id.status_logo);
            cdPesanan = (CardView) view.findViewById(R.id.cd_pesanan);
        }
    }
}
