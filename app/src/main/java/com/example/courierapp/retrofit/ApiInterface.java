package com.example.courierapp.retrofit;

import com.example.courierapp.model.BaseResponse;
import com.example.courierapp.model.DirectionsApiResponseDTO;
import com.example.courierapp.model.EmailUpdateRequest;
import com.example.courierapp.model.GetMyAllShipmentDTO;
import com.example.courierapp.model.GetMyAllShipmentResponse;
import com.example.courierapp.model.GetPaymentUserDetailsRequest;
import com.example.courierapp.model.GmailLoginResponse;
import com.example.courierapp.model.GmailRegisterRequest;
import com.example.courierapp.model.LoginAuthResponse;
import com.example.courierapp.model.LoginRequest;
import com.example.courierapp.model.LoginResponse;
import com.example.courierapp.model.MobileNumUpdateRequest;
import com.example.courierapp.model.PDFGenerationRequest;
import com.example.courierapp.model.PasswordUpdateRequest;
import com.example.courierapp.model.PaymentRequest;
import com.example.courierapp.model.PaymentUserResponse;
import com.example.courierapp.model.RegisterRequest;
import com.example.courierapp.model.RegisterResponse;
import com.example.courierapp.model.SchedulePickupRequest;
import com.example.courierapp.model.SchedulePickupResponse;
import com.example.courierapp.model.UpdatePriceRequest;
import com.example.courierapp.model.UserNameUpdateRequest;
import com.example.courierapp.utils.BaseURL;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {

    //Login
   /* @Headers({"Content-Type:application/json"})
    @POST("get_single_user.php")
    Call<SignedInJSONResponse> getLoginUser(@Body SignInDTO signInDTO);
*/

   /*@GET(BuildConfig.NAJDI_END_POINTS + "products")
    @Headers({"Content-Type:application/json", "Authorization" + ": " + Constants.BASIC_64_AUTH})
    Call<List<ProductListResponse>> getProducts();
*/

    @GET(BaseURL.GOOGLE_MAP_URL + "/maps/api/directions/json")
    Call<DirectionsApiResponseDTO> getRouteDistance(@Query("origin") String origin, @Query("destination") String destination, @Query("key") String key);

    /*Call<DirectionsApiResponseDTO> getRouteDistance(String s, double v, double v1, String apiKey);*/

    @POST(BaseURL.DOMAIN_NAME + "register")
    Call<RegisterResponse> doRegister(@Body RegisterRequest registerRequest);

    @POST(BaseURL.DOMAIN_NAME + BaseURL.SUB_PATH + "login")
    @Headers({"Content-Type:application/json"})
    Call<LoginAuthResponse> doCheckLogin(@Body LoginRequest loginRequest);

    //@Headers({"Content-Type:application/json", "Authorization" + ": " + AppConstant.AUTHTOKEN})
    @POST(BaseURL.DOMAIN_NAME + BaseURL.SUB_PATH + "User")
    @Headers({"Content-Type:application/json"})
    Call<LoginResponse> doGetUserDetails(@Header("Authorization") String token);

    @Headers({"Content-Type:application/json"})
    // @GET(BaseURL.DOMAIN_NAME+BaseURL.SUB_CHECK)
    @GET(BaseURL.DOMAIN_NAME + "check/{mobileNumber}")
    Call<BaseResponse> checkMobileNumberInServer(@Path("mobileNumber") String mobile);

    @Headers({"Content-Type:application/json"})
    @POST(BaseURL.DOMAIN_NAME + "createTrackno")
    Call<SchedulePickupResponse> doPostSchedulePickup(@Header("Authorization") String token, @Body SchedulePickupRequest schedulePickupRequest);


    @POST(BaseURL.DOMAIN_NAME + "register")
    @Headers({"Content-Type:application/json"})
    Call<GmailLoginResponse> doRegisterGmailUser(@Body GmailRegisterRequest gmailLoginRequest);

    @POST(BaseURL.DOMAIN_NAME + "auth/login")
    @Headers({"Content-Type:application/json"})
    Call<LoginAuthResponse> doGetGmailUserDetails(@Body GmailRegisterRequest gmailLoginRequest);


    @PUT(BaseURL.DOMAIN_NAME + "forgotpassword")
    @Headers({"Content-Type:application/json"})
    Call<BaseResponse> updateForgetPassword();

    @POST(BaseURL.DOMAIN_NAME + "updateEmail")
    @Headers({"Content-Type:application/json"})
    Call<BaseResponse> updateEmail(@Header("Authorization") String token, @Body EmailUpdateRequest emailUpdateRequest);


    @PUT(BaseURL.DOMAIN_NAME + "updateMobileno")
    @Headers({"Content-Type:application/json"})
    Call<BaseResponse> updateMobileNumber(@Header("Authorization") String token, @Body MobileNumUpdateRequest mobileNumUpdateRequest);


    @PUT(BaseURL.DOMAIN_NAME + "updateuserName")
    @Headers({"Content-Type:application/json"})
    Call<BaseResponse> updateUserName(@Header("Authorization") String token, @Body UserNameUpdateRequest userNameUpdateRequest);


    @PUT(BaseURL.DOMAIN_NAME + "updatePassword")
    @Headers({"Content-Type:application/json"})
    Call<BaseResponse> updatePassword(@Header("Authorization") String token, @Body PasswordUpdateRequest passwordUpdateRequest);

    @PUT(BaseURL.DOMAIN_NAME + "insertPrice")
    @Headers({"Content-Type:application/json"})
    Call<BaseResponse> updatePrice(@Header("Authorization") String token, @Body UpdatePriceRequest updatePriceRequest);


    @GET(BaseURL.DOMAIN_NAME + "userOrderList/{userId}")
    @Headers({"Content-Type:application/json"})
    Call<GetMyAllShipmentResponse> getAllMyShipment(@Header("Authorization") String token, @Path("userId") int userId);

    @POST(BaseURL.DOMAIN_NAME + "pdfGenerate")
    @Headers({"Content-Type:application/json"})
    Call<GetMyAllShipmentDTO> getSinglePDFDocumnet(@Header("Authorization") String token, @Body PDFGenerationRequest pdfGenerationRequest);


    @Headers({"Content-Type:application/json"})
    Call<BaseResponse> getProgressOfTrackingNumber();


    @POST(BaseURL.PAYMENT_URL + "492159427221256")
    @Headers({"Content-Type:application/json"})
    Call<BaseResponse> paymentRequest(@Body PaymentRequest paymentRequest);

    @POST(BaseURL.DOMAIN_NAME + "paymentuserDetails")
    @Headers({"Content-Type:application/json"})
    Call<PaymentUserResponse> getPaymentUserDetails(@Header("Authorization") String token,@Body GetPaymentUserDetailsRequest getPaymentUserDetailsRequest);

    @POST(BaseURL.PAYMENT_URL + "createPayment")
    @Headers({"Content-Type:application/json"})
    Call<ResponseBody> paymentGatewayURLCall(@Body PaymentRequest paymentRequest);


}
