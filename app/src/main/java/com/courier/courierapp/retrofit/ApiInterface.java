package com.courier.courierapp.retrofit;

import com.courier.courierapp.model.BaseResponse;
import com.courier.courierapp.model.DirectionsApiResponseDTO;
import com.courier.courierapp.model.EmailUpdateRequest;
import com.courier.courierapp.model.GetMyAllShipmentDTO;
import com.courier.courierapp.model.GetMyAllShipmentResponse;
import com.courier.courierapp.model.GetPaymentUserDetailsRequest;
import com.courier.courierapp.model.GetToeknResponse;
import com.courier.courierapp.model.GmailLoginResponse;
import com.courier.courierapp.model.GmailRegisterRequest;
import com.courier.courierapp.model.LoginAuthResponse;
import com.courier.courierapp.model.LoginRequest;
import com.courier.courierapp.model.LoginResponse;
import com.courier.courierapp.model.MobileNumUpdateRequest;
import com.courier.courierapp.model.PDFGenerationRequest;
import com.courier.courierapp.model.PDFGenerationResponse;
import com.courier.courierapp.model.PasswordUpdateRequest;
import com.courier.courierapp.model.PaymentRequest;
import com.courier.courierapp.model.PaymentUserResponse;
import com.courier.courierapp.model.RegisterRequest;
import com.courier.courierapp.model.RegisterResponse;
import com.courier.courierapp.model.SavePushNotification;
import com.courier.courierapp.model.SchedulePickupRequest;
import com.courier.courierapp.model.SchedulePickupResponse;
import com.courier.courierapp.model.UpdatePriceRequest;
import com.courier.courierapp.model.UserNameUpdateRequest;
import com.courier.courierapp.utils.BaseURL;

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
    Call<BaseResponse> updateForgetPassword(@Body LoginRequest loginRequest);

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
    Call<PDFGenerationResponse> getSinglePDFDocumnet(@Header("Authorization") String token, @Body PDFGenerationRequest pdfGenerationRequest);


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

    @POST(BaseURL.DOMAIN_NAME + "pushnotification")
    @Headers({"Content-Type:application/json"})
    Call<BaseResponse> saveNotificationTokenInServer(@Header("Authorization") String token,@Body SavePushNotification loginResponse);

    @GET(BaseURL.DOMAIN_NAME + "getNotification/{userid}")
    @Headers({"Content-Type:application/json"})
    Call<GetToeknResponse> getPushNotificationToken(@Header("Authorization") String token, @Path("userid") int user_id);


}
