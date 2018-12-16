package com.macbook.laundry;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import com.macbook.laundry.activities.LoginActivity;
import com.macbook.laundry.activities.SignUpActivity;

public class TampilDialog {
    Context mcontext;
    ProgressDialog progressDialog;

    public TampilDialog(Context mcontext) {
        this.mcontext = mcontext;
    }

    public void showDialog(String title, String message, final String route) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mcontext);
        builder.setTitle(title);
        builder.setMessage(message);
        if (title.equals("Information")) {
            builder.setCancelable(false);
            builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // continue with delete
                    if (route == "main-activity") {
                        Intent intent = new Intent(mcontext, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        mcontext.startActivity(intent);
                    } else if (route == "login-activity") {
                        Intent intent = new Intent(mcontext, LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        mcontext.startActivity(intent);
                    }
                }
            })
                    .setIcon(android.R.drawable.ic_dialog_info);
        } else if (title.equals("Failed")) {
            builder.setIcon(android.R.drawable.ic_dialog_alert);
        } else {
            builder.setIcon(R.drawable.ic_check_circle_black_24dp);
        }
        builder.show();
    }

    public void showLoading() {
        progressDialog = new ProgressDialog(mcontext);
        progressDialog.setMessage("Its loading....");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
    }

    public void dismissLoading() {
        progressDialog.dismiss();
    }
}
