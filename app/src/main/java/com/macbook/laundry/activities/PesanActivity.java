package com.macbook.laundry.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;
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
import com.macbook.laundry.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class PesanActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;
    private GoogleApiClient mGoogleApiClient;
    private int PLACE_PICKER_REQUEST = 1;

    @BindView(R.id.btn_tanggal)
    Button btnTanggal;
    @BindView(R.id.btn_lokasi)
    Button btnLokasi;
    @BindView(R.id.btn_layanan)
    Button btnLayanan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesan);
        ButterKnife.bind(this);

        mGoogleApiClient = new GoogleApiClient
                .Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .enableAutoManage(this, this)
                .build();

        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
    }

    @OnClick({R.id.btn_lokasi,R.id.btn_layanan,R.id.btn_tanggal,R.id.btn_simpan})
    public void onclickButton(Button btn){
//        Toast.makeText(this, String.valueOf(btn.getId()), Toast.LENGTH_SHORT).show();
//        Toast.makeText(this, String.valueOf(R.id.btn_tanggal), Toast.LENGTH_SHORT).show();
        switch (btn.getId()){
            case R.id.btn_lokasi:
                showLokasi();
            case R.id.btn_tanggal:
                showDateDialog();
            case R.id.btn_layanan:
                showLayananDialog();
        }
    }

    private void showLayananDialog() {
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
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

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
                Place place = PlacePicker.getPlace(data, this);
                String toastMsg = String.format("Place: %s", place.getName());
                btnLokasi.setText(toastMsg);
            }
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(this, "Failed Connection", Toast.LENGTH_SHORT).show();
    }
}
