package com.macbook.laundry.api;

import com.macbook.laundry.models.Customer;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface AuthService {

//    @POST("administrator/login")
//    Call<ResponseLogin> postLogin(@Body RequestLogin requestLogin);
//
//    @GET("administrator/check-session")
//    Call<User> getCheckLogin(@Header("Authorization") String token);

    @POST("customer/register")
    Call<ResponseBody> postRegisterCustomer(@Body Customer Customer);
}
