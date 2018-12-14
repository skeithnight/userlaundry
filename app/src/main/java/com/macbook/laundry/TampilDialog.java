package com.macbook.laundry;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.support.v7.app.AlertDialog;

public class TampilDialog {
    Context mcontext;
    ProgressDialog progressDialog;

    public TampilDialog(Context mcontext) {
        this.mcontext = mcontext;
    }

    public void showDialog(String title, String message){
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(mcontext, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(mcontext);
        }
        builder.setTitle(title);
        builder.setMessage(message);
        if (title.equals("Information")) {
            builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // continue with delete
                }
            })
                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // do nothing
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_info);
        }else if (title.equals("Failed")){
            builder.setIcon(android.R.drawable.ic_dialog_alert);
        }else {
            builder.setIcon(R.drawable.ic_check_circle_black_24dp);
        }
        builder.show();
    }

    public void showLoading(){
        progressDialog = new ProgressDialog(mcontext);
        progressDialog.setMessage("Its loading....");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
    }

    public void dismissLoading(){
        progressDialog.dismiss();
    }
}
