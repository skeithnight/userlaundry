package com.macbook.laundry.activities

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.annotation.TargetApi
import android.content.pm.PackageManager
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.app.LoaderManager.LoaderCallbacks
import android.database.Cursor
import android.net.Uri
import android.os.AsyncTask
import android.os.Build
import android.os.Bundle
import android.provider.ContactsContract
import android.text.TextUtils
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.ArrayAdapter
import android.widget.TextView

import java.util.ArrayList
import android.Manifest.permission.READ_CONTACTS
import android.content.*
import android.widget.Toast
import com.google.gson.Gson
import com.macbook.laundry.MainActivity
import com.macbook.laundry.R
import com.macbook.laundry.TampilDialog
import com.macbook.laundry.api.APIClient
import com.macbook.laundry.api.AuthService
import com.macbook.laundry.controller.Authorization
import com.macbook.laundry.models.Customer
import com.macbook.laundry.models.ResponseLogin

import kotlinx.android.synthetic.main.activity_login.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

/**
 * A login screen that offers login via username_login/password_login.
 */
class LoginActivity : AppCompatActivity() {

    val tampilDialog:TampilDialog = TampilDialog(baseContext)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        // Set up the login form.
        password_login.setOnEditorActionListener(TextView.OnEditorActionListener { _, id, _ ->
            if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                attemptLogin()
                return@OnEditorActionListener true
            }
            false
        })

        sign_in_button.setOnClickListener { attemptLogin() }
        register_login.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
// start your next activity
            startActivity(intent)
        }
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid username_login, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private fun attemptLogin() {

        // Reset errors.
        username_login.error = null
        password_login.error = null

        // Store values at the time of the login attempt.
        val username_loginStr = username_login.text.toString()
        val password_loginStr = password_login.text.toString()

        var cancel = false
        var focusView: View? = null

        // Check for a valid password_login, if the user entered one.
        if (!TextUtils.isEmpty(password_loginStr) && !ispassword_loginValid(password_loginStr)) {
            password_login.error = getString(R.string.error_incorrect_password)
            focusView = password_login
            cancel = true
        }

        // Check for a valid username_login address.
        if (TextUtils.isEmpty(username_loginStr)) {
            username_login.error = getString(R.string.error_field_required)
            focusView = username_login
            cancel = true
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView?.requestFocus()
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            val customer:Customer = Customer()
            customer.username = username_loginStr
            customer.password = password_loginStr
            showProgress(true)
//            val gson: Gson = Gson()
//            Toast.makeText(this@LoginActivity, gson.toJson(customer),Toast.LENGTH_LONG).show()
            getToken(customer)
        }
    }

    private fun ispassword_loginValid(password_login: String): Boolean {
        //TODO: Replace this with your own logic
        return password_login.length > 4
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private fun showProgress(show: Boolean) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            val shortAnimTime = resources.getInteger(android.R.integer.config_shortAnimTime).toLong()

            login_form.visibility = if (show) View.GONE else View.VISIBLE
            login_form.animate()
                .setDuration(shortAnimTime)
                .alpha((if (show) 0 else 1).toFloat())
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        login_form.visibility = if (show) View.GONE else View.VISIBLE
                    }
                })

            login_progress.visibility = if (show) View.VISIBLE else View.GONE
            login_progress.animate()
                .setDuration(shortAnimTime)
                .alpha((if (show) 1 else 0).toFloat())
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        login_progress.visibility = if (show) View.VISIBLE else View.GONE
                    }
                })
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            login_progress.visibility = if (show) View.VISIBLE else View.GONE
            login_form.visibility = if (show) View.GONE else View.VISIBLE
        }
    }

    private fun getToken(customer: Customer) {

        val service = APIClient.getClient().create(AuthService::class.java)

        service.postLogin(customer).enqueue(object : Callback<ResponseLogin>{
            override fun onFailure(call: Call<ResponseLogin>?, t: Throwable?) {
                showProgress(false)
                TampilDialog(this@LoginActivity).showDialog("Failed",t.toString(),"")
            }

            override fun onResponse(call: Call<ResponseLogin>?, response: Response<ResponseLogin>?) {
                if (response != null) {
                    if (response.isSuccessful) {
                        val responseLogin: ResponseLogin = response.body()
                        val mSPLogin: SharedPreferences = getSharedPreferences("Login", Context.MODE_PRIVATE)
                        val editor = mSPLogin.edit()

                        editor.putString("token", responseLogin.token)
                        editor.commit()

                        val intent = Intent(this@LoginActivity, MainActivity::class.java)
                        startActivity(intent)
                    }else{
                        showProgress(false)
                        TampilDialog(this@LoginActivity).showDialog("Failed",response.message(),"")
                    }
                }
            }

        })
    }

}
