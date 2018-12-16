package com.macbook.laundry.activities;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.*;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.gson.Gson;
import com.macbook.laundry.R;
import com.macbook.laundry.TampilDialog;
import com.macbook.laundry.adapter.RecyclerViewAdapterMenuLaundry;
import com.macbook.laundry.api.APIClient;
import com.macbook.laundry.api.DataService;
import com.macbook.laundry.models.Customer;
import com.macbook.laundry.models.MenuLaundry;
import com.macbook.laundry.models.Transaksi;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class PesanActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    private static String TAG = "Testing";
    private String token, idUser;

    private Date date;

    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;
    private GoogleApiClient mGoogleApiClient;
    private int PLACE_PICKER_REQUEST = 1;
    private Place place;

    private TampilDialog tampilDialog;
    //    SharedPreferences
    SharedPreferences mSPLogin;

    @BindView(R.id.btn_tanggal)
    Button btnTanggal;
    @BindView(R.id.btn_lokasi)
    Button btnLokasi;
    @BindView(R.id.btn_layanan)
    Button btnLayanan;
    @BindView(R.id.alamat_pesan)
    EditText etAlamat;
    @BindView(R.id.listLayanan_pesan)
    ListView lvLayanan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesan);
        ButterKnife.bind(this);
        tampilDialog = new TampilDialog(this);

        // show loading
        tampilDialog.showLoading();
//        Get Data Layanan
        getDataLayanan();

        mGoogleApiClient = new GoogleApiClient
                .Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .enableAutoManage(this, this)
                .build();

        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

    }

    @OnClick({R.id.btn_lokasi, R.id.btn_layanan, R.id.btn_tanggal, R.id.btn_simpan})
    public void onclickButton(Button btn) {
        switch (btn.getId()) {
            case R.id.btn_lokasi:
                showLokasi();
                break;
//                Log.i(TAG, "onclickButton: "+R.id.btn_lokasi+" : "+btn.getId());
            case R.id.btn_tanggal:
                showDateDialog();
                break;
//                Log.i(TAG, "onclickButton: "+R.id.btn_lokasi+" : "+btn.getId());
            case R.id.btn_layanan:
                showLayananDialog();
                break;
            case R.id.btn_simpan:
                simpanPesanan();
                break;
        }
    }

    private void simpanPesanan() {
        if (CheckData()) {
            tampilDialog.showLoading();
            Customer customer = new Customer(idUser);
            Transaksi transaksi = new Transaksi(customer, etAlamat.getText().toString(), place.getLatLng().latitude, place.getLatLng().longitude, date.getTime(), listPilihLayanan, "Jemput");
            DataService dataService = APIClient.getClient().create(DataService.class);
            dataService.postTransaksi(
                    "Bearer " + token,
                    String.valueOf(place.getLatLng().latitude),
                    String.valueOf(place.getLatLng().longitude),
                    transaksi
                    ).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    tampilDialog.dismissLoading();
                    if (response.isSuccessful()) {
                        tampilDialog.showDialog("Information", "silahkan menunggu kurir datang ke tempat anda","main-activity");

                    }else {
                        tampilDialog.showDialog("Failed", response.message(),"");

                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    tampilDialog.dismissLoading();
                    tampilDialog.showDialog("Failed", t.getMessage(),"");
                }
            });
        } else {
            tampilDialog.showDialog("Failed", "The data is empty!","");
        }
    }

    private boolean CheckData() {
        if (place != null || date != null || listPilihLayanan != null) {
            if (listPilihLayanan.size() != 0) {
                return true;
            }
        }
        return false;
    }

    ArrayList<MenuLaundry> listDataMenuLaundry = new ArrayList<MenuLaundry>();
    ArrayList<String> listLayanan;

    private void getDataLayanan() {
        mSPLogin = getSharedPreferences("Login", Context.MODE_PRIVATE);
        token = mSPLogin.getString("token", null);
        idUser = mSPLogin.getString("id", null);
        if (token != null) {

            DataService dataService = APIClient.getClient().create(DataService.class);
            dataService.getListMenuLaundry("Bearer " + token).enqueue(new Callback<ArrayList<MenuLaundry>>() {
                @Override
                public void onResponse(Call<ArrayList<MenuLaundry>> call, Response<ArrayList<MenuLaundry>> response) {
                    listDataMenuLaundry = response.body();
                    listLayanan = new ArrayList<>();
                    for (MenuLaundry item : listDataMenuLaundry
                            ) {
                        listLayanan.add(item.getJenis() + " | Rp. " + item.getHarga() + "/" + item.getSatuan());
                    }
                    tampilDialog.dismissLoading();
                }

                @Override
                public void onFailure(Call<ArrayList<MenuLaundry>> call, Throwable t) {
                    tampilDialog.dismissLoading();
                    tampilDialog.showDialog("Failed", t.getMessage(),"");
                }
            });
        }
    }

    ArrayList<MenuLaundry> listPilihLayanan;
    ArrayList<String> listStringPilihLayanan;

    private void showLayananDialog() {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
        final CharSequence[] charSequenceItems = listLayanan.toArray(new CharSequence[listLayanan.size()]);
        listPilihLayanan = new ArrayList<MenuLaundry>();
        final boolean[] pilihan = new boolean[listLayanan.size()];
        mBuilder.setTitle("Pilih Layanan")
                .setMultiChoiceItems(charSequenceItems, null, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        pilihan[which] = isChecked;
                    }
                })
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                        listStringPilihLayanan = new ArrayList<String>();
                        for (int i = 0; i < listLayanan.size(); i++) {
                            if (pilihan[i]) {
                                listPilihLayanan.add(listDataMenuLaundry.get(i));
                                listStringPilihLayanan.add(listLayanan.get(i));
                                pilihan[i] = false;
                            }
                        }

                        String[] arrayPilihan = listStringPilihLayanan.toArray(new String[listStringPilihLayanan.size()]);
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                                PesanActivity.this,
                                android.R.layout.simple_list_item_1, arrayPilihan
                        );

                        // set data
                        lvLayanan.setAdapter(adapter);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                })
                .show();
    }

    private void showDateDialog() {
        Calendar newCalendar = Calendar.getInstance();

        /**
         * Initiate DatePicker dialog
         */
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                /**
                 * Method ini dipanggil saat kita selesai memilih tanggal di DatePicker
                 */

                /**
                 * Set Calendar untuk menampung tanggal yang dipilih
                 */
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);

                /**
                 * Update TextView dengan tanggal yang kita pilih
                 */
                btnTanggal.setText(dateFormatter.format(newDate.getTime()));
                date = newDate.getTime();
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        /**
         * Tampilkan DatePicker dialog
         */
        datePickerDialog.show();
    }

    private void showLokasi() {
        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

        try {
            startActivityForResult(builder.build(this), PLACE_PICKER_REQUEST);
        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                place = PlacePicker.getPlace(data, this);
                String toastMsg = String.valueOf(place.getAddress());
                etAlamat.setText(toastMsg);
            }
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(this, "Failed Connection", Toast.LENGTH_SHORT).show();
    }
}
