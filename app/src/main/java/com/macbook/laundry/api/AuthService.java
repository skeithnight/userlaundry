package com.macbook.laundry.api;

import com.macbook.laundry.models.Customer;
import com.macbook.laundry.models.ResponseLogin;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface AuthService {

    @POST("customer/login")
    Call<ResponseLogin> postLogin(@Body Customer customerLogin);
//
    @GET("customer/check-session")
    Call<Customer> getCheckLogin(@Header("Authorization") String token);

    @POST("customer/register")
    Call<ResponseBody> postRegisterCustomer(@Body Customer Customer);
}
