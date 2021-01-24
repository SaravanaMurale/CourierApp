package com.courier.courierapp.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentSender;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.courier.courierapp.MobileAuthActivity;
import com.courier.courierapp.R;
import com.courier.courierapp.model.BaseResponse;
import com.courier.courierapp.model.GmailLoginResponse;
import com.courier.courierapp.model.GmailRegisterRequest;
import com.courier.courierapp.model.LoginAuthResponse;
import com.courier.courierapp.model.LoginRequest;
import com.courier.courierapp.model.LoginResponse;
import com.courier.courierapp.retrofit.ApiClient;
import com.courier.courierapp.retrofit.ApiInterface;
import com.courier.courierapp.utils.LoaderUtil;
import com.courier.courierapp.utils.MathUtil;
import com.courier.courierapp.utils.PreferenceUtil;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.credentials.Credential;
import com.google.android.gms.auth.api.credentials.HintRequest;
import com.google.android.gms.auth.api.credentials.IdentityProviders;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.material.textfield.TextInputLayout;
import com.shobhitpuri.custombuttons.GoogleSignInButton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.courier.courierapp.utils.AppConstant.RESOLVE_HINT;
import static com.courier.courierapp.utils.MathUtil.validateMobile;
import static com.courier.courierapp.utils.MathUtil.validatePassword;


public class LoginActivity extends AppCompatActivity {

    private EditText loginMobile, loginPassword;
    private TextInputLayout inputLoginMobile, inputLoginPassword;
    private Button btnSignIn;

    private TextView forgetPassword, loginSignup;

    private GoogleSignInButton gmailSignup;

    private GoogleApiClient mCredentialsApiClient;

    Typeface typeface;
    TextView othersignin;

    Dialog dialog;


/*Dialog showDialog=Utils.showProgressBar(this);
 Utils.dismisProgressBar(getApplicationContext(),showDialog);
 */


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        typeface = MathUtil.getOctinPrisonFont(LoginActivity.this);

        inputLoginMobile = (TextInputLayout) findViewById(R.id.input_login_mobile);
        inputLoginPassword = (TextInputLayout) findViewById(R.id.input_login_password);

        loginMobile = (EditText) findViewById(R.id.login_mobile);
        loginPassword = (EditText) findViewById(R.id.login_password);

        forgetPassword = (TextView) findViewById(R.id.login_forgetPassword);

        gmailSignup = (GoogleSignInButton) findViewById(R.id.gmailSignup);

        loginSignup = (TextView) findViewById(R.id.loginSignup);
        othersignin = (TextView) findViewById(R.id.othersignin);


        mCredentialsApiClient = new GoogleApiClient.Builder(this)
                /*.addConnectionCallbacks((GoogleApiClient.ConnectionCallbacks) SignUpActivity.this)
                .addOnConnectionFailedListener((GoogleApiClient.OnConnectionFailedListener) SignUpActivity.this)*/
                .addApi(Auth.CREDENTIALS_API)
                .setAccountName(IdentityProviders.GOOGLE)
                .build();


        gmailSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestHint();
            }
        });

        btnSignIn = (Button) findViewById(R.id.btn_login);

        btnSignIn.setTypeface(typeface);
        othersignin.setTypeface(typeface);

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loginUser();

            }
        });


        loginMobile.addTextChangedListener(new MyTextWatcher(loginMobile));
        loginPassword.addTextChangedListener(new MyTextWatcher(loginPassword));

        forgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                launchActivity();
            }
        });

        loginSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                requestHintForMobileNumber();
            }
        });


    }


    private void requestHint() {

        mCredentialsApiClient.connect();

        HintRequest hintRequest = new HintRequest.Builder()
                //.setPhoneNumberIdentifierSupported(true)
                .setEmailAddressIdentifierSupported(true)
                .build();


        PendingIntent intent = Auth.CredentialsApi.getHintPickerIntent(
                mCredentialsApiClient, hintRequest);
        try {
            startIntentSenderForResult(intent.getIntentSender(),
                    RESOLVE_HINT, null, 0, 0, 0);
        } catch (IntentSender.SendIntentException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == RESOLVE_HINT) {
            if (resultCode == RESULT_OK) {
                Credential credential = data.getParcelableExtra(Credential.EXTRA_KEY);

                System.out.println("GmailUserID(GMAIL)" + credential.getId());
                System.out.println("GmailUser" + credential.getName() + " " + credential.getPassword() + " " + credential.getId() + " " + credential.getIdTokens());

                if (credential.getId().contains("+")) {
                    String mobileNumber = credential.getId();
                    if (mobileNumber.length() == 13) {
                        String numWithOutCC = MathUtil.removeCountryCode(mobileNumber);
                        //System.out.println("+RemovedMobileNumber" + numWithOutCC);
                        sendMobileToServerToCheck(numWithOutCC);
                    }
                } else if (credential.getId() != null) {
                    registerGmailUser(credential.getId(), credential.getName());
                    //launchHomeScreen();
                }
            } else {
                Intent intent = new Intent(LoginActivity.this, MobileAuthActivity.class);
                intent.putExtra("NUMBER", "9486994621");
                startActivity(intent);
                finish();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    private class MyTextWatcher implements TextWatcher {

        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        @SuppressLint("ResourceAsColor")
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            String mobile = loginMobile.getText().toString().trim();
            String password = loginPassword.getText().toString().trim();

            btnSignIn.setEnabled(validateMobile(mobile) && validatePassword(password));

            if (btnSignIn.isEnabled()) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    btnSignIn.setBackground(getDrawable(R.drawable.rectangle_shpae));
                    btnSignIn.setTextColor(getApplication().getResources().getColor(R.color.white));
                }
            } else if (!btnSignIn.isEnabled()) {
                btnSignIn.setEnabled(false);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    btnSignIn.setBackground(getDrawable(R.color.btn_disable));
                    btnSignIn.setTextColor(R.color.black);
                }
            }

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }

    private void launchSignUpActivity(String numWithoutCC) {

        Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
        intent.putExtra("MOBILENUMBER", numWithoutCC);
        startActivity(intent);

    }

    private void requestHintForMobileNumber() {
        mCredentialsApiClient.connect();

        HintRequest hintRequest = new HintRequest.Builder()
                .setPhoneNumberIdentifierSupported(true)
                .setEmailAddressIdentifierSupported(false)
                .build();

        PendingIntent intent =
                Auth.CredentialsApi.getHintPickerIntent(mCredentialsApiClient, hintRequest);
        try {
            startIntentSenderForResult(intent.getIntentSender(), RESOLVE_HINT, null, 0, 0, 0, new Bundle());
        } catch (IntentSender.SendIntentException e) {
            Log.e("Login", "Could not start hint picker Intent", e);
        }
    }

    private void launchActivity() {
        Intent intent = new Intent(LoginActivity.this, ForgetPasswordActivity.class);
        startActivity(intent);
    }

    private void launchHomeScreen() {

        Intent intent = new Intent(LoginActivity.this, DrawerActivity.class);
        startActivity(intent);
        finish();

    }

    private void sendMobileToServerToCheck(final String numWithOutCC) {

        dialog = LoaderUtil.showProgressBar(this);

        ApiInterface apiInterface = ApiClient.getAPIClient().create(ApiInterface.class);
        Call<BaseResponse> call = apiInterface.checkMobileNumberInServer(numWithOutCC);
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                BaseResponse baseResponse = response.body();

                System.out.println("SuccessResponse" + baseResponse.getSuccess());

                LoaderUtil.dismisProgressBar(LoginActivity.this, dialog);

                if (baseResponse.getSuccess() == true) {
                    //Already member to your app
                    launchFogetPasswordActivity(numWithOutCC);
                } else if (baseResponse.getSuccess() == false) {
                    launchSignUpActivity(numWithOutCC);
                }


            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {

            }
        });


    }

    private void launchFogetPasswordActivity(String mbl) {
        Intent intent = new Intent(LoginActivity.this, ForgetPasswordActivity.class);
        intent.putExtra("MOBILENUMBER", mbl);
        startActivity(intent);
    }


    private void registerGmailUser(final String id, final String name) {
        dialog = LoaderUtil.showProgressBar(this);

        ApiInterface apiInterface = ApiClient.getAPIClient().create(ApiInterface.class);
        String gmailPassword = "";
        GmailRegisterRequest gmailRegisterRequest = new GmailRegisterRequest(id, name, gmailPassword);

        final Call<GmailLoginResponse> call = apiInterface.doRegisterGmailUser(gmailRegisterRequest);

        call.enqueue(new Callback<GmailLoginResponse>() {
            @Override
            public void onResponse(Call<GmailLoginResponse> call, Response<GmailLoginResponse> response) {

                GmailLoginResponse gmailLoginResponse = response.body();

                LoaderUtil.dismisProgressBar(LoginActivity.this, dialog);

                if (gmailLoginResponse.isGmailUserStatus() == true) {
                    getGmailUserDetails(id, name);
                } else if (gmailLoginResponse.isGmailUserStatus() == false) {
                    getGmailUserDetails(id, name);
                }


            }

            @Override
            public void onFailure(Call<GmailLoginResponse> call, Throwable t) {

            }
        });


    }

    private void getGmailUserDetails(String id, String name) {

        dialog = LoaderUtil.showProgressBar(this);

        ApiInterface apiInterface = ApiClient.getAPIClient().create(ApiInterface.class);
        String gmailPassword = "";
        GmailRegisterRequest gmailLoginRequest = new GmailRegisterRequest(id, gmailPassword);

        Call<LoginAuthResponse> call = apiInterface.doGetGmailUserDetails(gmailLoginRequest);
        call.enqueue(new Callback<LoginAuthResponse>() {
            @Override
            public void onResponse(Call<LoginAuthResponse> call, Response<LoginAuthResponse> response) {
                LoginAuthResponse loginAuthResponse = response.body();

                if (loginAuthResponse.getAuthToken() != null && loginAuthResponse.getTokenType() != null) {

                    PreferenceUtil.setValueString(LoginActivity.this, PreferenceUtil.AUTH_TOKEN, loginAuthResponse.getAuthToken());
                    PreferenceUtil.setValueString(LoginActivity.this, PreferenceUtil.BEARER, loginAuthResponse.getTokenType());

                    /*System.out.println("ReceivedToken" + PreferenceUtil.getValueString(LoginActivity.this, PreferenceUtil.BEARER) + " " + PreferenceUtil.getValueString(LoginActivity.this, PreferenceUtil.AUTH_TOKEN));

                    String s = PreferenceUtil.getValueString(LoginActivity.this, PreferenceUtil.BEARER) + " " + PreferenceUtil.getValueString(LoginActivity.this, PreferenceUtil.AUTH_TOKEN);
*/
                    LoaderUtil.dismisProgressBar(LoginActivity.this, dialog);

                    getUserDetails();


                }


            }

            @Override
            public void onFailure(Call<LoginAuthResponse> call, Throwable t) {

            }
        });
    }


    private void loginUser() {

        dialog = LoaderUtil.showProgressBar(this);

        ApiInterface apiInterface = ApiClient.getAPIClient().create(ApiInterface.class);
        LoginRequest loginRequest = new LoginRequest(loginMobile.getText().toString(), loginPassword.getText().toString());

        Call<LoginAuthResponse> call = apiInterface.doCheckLogin(loginRequest);

        call.enqueue(new Callback<LoginAuthResponse>() {
            @Override
            public void onResponse(Call<LoginAuthResponse> call, Response<LoginAuthResponse> response) {

                LoginAuthResponse loginAuthResponse = response.body();

                if (loginAuthResponse.getSuccess()) {
                    if (loginAuthResponse.getAuthToken() != null && loginAuthResponse.getTokenType() != null) {



                        String token=PreferenceUtil.getValueString(LoginActivity.this,"notification_token");

                        if(token!=null){
                            saveFirebaseNotificationTokenInServer();
                            System.out.println("FirebaseTomer"+token);
                        }else {

                        }




                        PreferenceUtil.setValueString(LoginActivity.this, PreferenceUtil.AUTH_TOKEN, loginAuthResponse.getAuthToken());
                        PreferenceUtil.setValueString(LoginActivity.this, PreferenceUtil.BEARER, loginAuthResponse.getTokenType());

                        System.out.println("ReceivedToken" + PreferenceUtil.getValueString(LoginActivity.this, PreferenceUtil.BEARER) + " " + PreferenceUtil.getValueString(LoginActivity.this, PreferenceUtil.AUTH_TOKEN));

                    /*String s = PreferenceUtil.getValueString(LoginActivity.this, PreferenceUtil.BEARER) + " " + PreferenceUtil.getValueString(LoginActivity.this, PreferenceUtil.AUTH_TOKEN);

                    Toast.makeText(LoginActivity.this, s, Toast.LENGTH_LONG).show();*/

                        LoaderUtil.dismisProgressBar(LoginActivity.this, dialog);

                        getUserDetails();


                    }
                } else {
                    Toast.makeText(LoginActivity.this, "You have entered wrong username or password", Toast.LENGTH_LONG).show();
                }


            }

            @Override
            public void onFailure(Call<LoginAuthResponse> call, Throwable t) {

            }
        });

    }

    private void saveFirebaseNotificationTokenInServer() {

        dialog = LoaderUtil.showProgressBar(this);
        ApiInterface apiInterface = ApiClient.getAPIClient().create(ApiInterface.class);

        /*int userid=PreferenceUtil.getValueInt(LoginActivity.this,PreferenceUtil.USER_ID);
       String pToken=PreferenceUtil.getValueString(LoginActivity.this,PreferenceUtil.NOTIFICATION);*/

        LoginResponse loginResponse=new LoginResponse(PreferenceUtil.getValueInt(LoginActivity.this,PreferenceUtil.USER_ID),PreferenceUtil.getValueString(LoginActivity.this,PreferenceUtil.NOTIFICATION));

        Call<BaseResponse> call=apiInterface.saveNotificationTokenInServer(PreferenceUtil.getValueString(LoginActivity.this, PreferenceUtil.BEARER) + " " + PreferenceUtil.getValueString(LoginActivity.this, PreferenceUtil.AUTH_TOKEN),loginResponse);
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {

                BaseResponse baseResponse=response.body();
                LoaderUtil.dismisProgressBar(LoginActivity.this, dialog);
                if(baseResponse.getSuccess()){
                    System.out.println("TokenInsertedSuccessfully");
                }else {
                    System.out.println("TokenIsNotInsertedSuccessfully");
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                LoaderUtil.dismisProgressBar(LoginActivity.this, dialog);
            }
        });


    }

    private void getUserDetails() {
        dialog = LoaderUtil.showProgressBar(this);

        ApiInterface apiInterface = ApiClient.getAPIClient().create(ApiInterface.class);
        Call<LoginResponse> call = apiInterface.doGetUserDetails(PreferenceUtil.getValueString(LoginActivity.this, PreferenceUtil.BEARER) + " " + PreferenceUtil.getValueString(LoginActivity.this, PreferenceUtil.AUTH_TOKEN));
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                //System.out.println("ReceivedUserId" + loginResponse.getUserId());
                LoginResponse loginResponse = response.body();

                PreferenceUtil.setValueSInt(LoginActivity.this, PreferenceUtil.USER_ID, loginResponse.getUserId());
                LoaderUtil.dismisProgressBar(LoginActivity.this, dialog);

                launchHomeScreen();


            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {

            }
        });


    }


}
