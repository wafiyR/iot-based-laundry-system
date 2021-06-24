package com.example.laundryrush;

import com.example.laundryrush.model.Device;
import com.example.laundryrush.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RequestInterface {

    @FormUrlEncoded   //FormURLEncode must have at least one @Field
    @POST("userRegistration.php")
    //Call<JSONResponse> create(@Field("user_email") String email, @Field("user_name") String username, @Field("password") String password);

    // email, username and password in the parameters are related to the email, username and password in userRegistration.php e.g $username = $_POST['username'];
    // Call<JSONResponse> register = register is just a variable name
    // is @Query a query parameter for query like sort, etc?
    //Call<JSONResponse> register(@Query("email") String email, @Query("username") String username, @Query("password") String password);

    //Call<JSONResponse> register(@Field("email") String email, @Field("username") String username, @Field("password") String password);
    Call<User> register(@Field("email") String email, @Field("username") String username, @Field("password") String password);

    //Call<User> register(@Query("email") String email, @Query("username") String username, @Query("password") String password);
    //cannot use @Query, -> Application crashed, cannot perform registration


    @FormUrlEncoded
    @POST("userLoginV2.php")
    //Call<JSONResponse> login(@Field("username") String username, @Field("password") String password);
    //Call<JSONResponse> login(@Query("username") String username, @Query("password") String password);
    Call<User> login(@Field("username") String username, @Field("password") String password);
    //Call<User> login(@Query("username") String username, @Query("password") String password);

    @FormUrlEncoded
    @POST("manageProfile.php")

    Call<User> updateProfile(@Field("sp_username") String sp_username, @Field("username") String username, @Field("email") String email);

    @FormUrlEncoded
    @POST("updateUsername.php")

    Call<User> updateUsername(@Field("sp_username") String sp_username, @Field("username") String username);

    @FormUrlEncoded
    @POST("updateEmail.php")

    Call<User> updateEmail(@Field("sp_username") String sp_username, @Field("email") String email);

    @FormUrlEncoded
    @POST("fetch_profile.php")

    Call<List<User>> getProfile(@Field("sp_username") String sp_username);

    @FormUrlEncoded
    @POST("fetch_wallet.php")

    Call<List<User>> getBalance(@Field("balance") String balance);   // @Field("balance") --> need to be same variable name (balance) with $_POST['balance'] in php file

    @FormUrlEncoded
    @POST("updateBalance.php")
    Call<User> updateBalance(@Field("sp_username") String sp_username, @Field("topup") String topup);

    @FormUrlEncoded
    @POST("minusEWallet.php")
    Call<User> minusBalance(@Field("sp_username") String sp_username, @Field("amount") String amount);

    @FormUrlEncoded
    @POST("updatePassword.php")
    Call<User> updatePassword(@Field("sp_username") String sp_username, @Field("curr_password") String curr_password, @Field("new_password") String new_password, @Field("confirm_password") String confirm_password);

    @FormUrlEncoded
    @POST("delete_acc.php")
    Call<User> deleteAccount(@Field("sp_username") String sp_username);

    @FormUrlEncoded
    @POST("adminLogin.php")
    Call<User> adminLogin(@Field("username") String admin_name, @Field("password") String password);


    //@FormUrlEncoded
    @POST("http://iot.maiot.academy/api/v1/accessToken/rpc")  // http://iot.maiot.academy/api/v1/$ACCESS_TOKEN/rpc  // api/plugins/rpc/oneway/{deviceToken} // /api/v1/{deviceToken}/rpc  // api/v1/{deviceToken}/rpc?   // /api/plugins/rpc/oneway/{deviceId}  // api/v1/{deviceToken}/rpc  // api/plugins/rpc/oneway  // api/plugins/rpc/oneway/{deviceToken}    // api/plugins/rpc/oneway?
    Call<Device> trigger(@Body String accessToken); //Device deviceToken  //@Field, @Path, @Body, @Query, @Url?
    // @Body - Sends Java objects as request body.
    // @Url
    // @Query
    // @Field -
    // Call<Device> trigger(@Field("deviceToken") String deviceToken); // @ Field require @FormUrlEncoded
     // http://iot.maiot.academy/api/v1/$ACCESS_TOKEN/rpc

    // Call<Device> trigger(@Field("deviceToken") String deviceToken);

}
