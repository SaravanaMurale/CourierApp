package com.example.courierapp.activity;

import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.courierapp.MobileAuthActivity;
import com.example.courierapp.R;
import com.example.courierapp.model.RegisterRequest;
import com.example.courierapp.model.RegisterResponse;
import com.example.courierapp.retrofit.ApiClient;
import com.example.courierapp.retrofit.ApiInterface;
import com.example.courierapp.utils.LoaderUtil;
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

import static com.example.courierapp.utils.AppConstant.RESOLVE_HINT;
import static com.example.courierapp.utils.MathUtil.passwordMatch;
import static com.example.courierapp.utils.MathUtil.validateMobile;
import static com.example.courierapp.utils.MathUtil.validatePassword;


public class SignUpActivity extends AppCompatActivity {

    private EditText inputName, inputMobile, inputPassword, inputcPassword;
    private TextInputLayout inputLayoutName, inputLayoutMobile, inputLayoutEmail, inputLayoutPassword;
    private Button btnSignUp;

    TextView login;

    private GoogleSignInButton gmailSignup;

    private GoogleApiClient mCredentialsApiClient;

    String mobileNumber;
    Dialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        Intent intent = getIntent();
        mobileNumber = intent.getStringExtra("MOBILENUMBER");


        inputLayoutName = (TextInputLayout) findViewById(R.id.input_layout_name);
        inputLayoutMobile = (TextInputLayout) findViewById(R.id.input_layout_mobile);
        inputLayoutEmail = (TextInputLayout) findViewById(R.id.input_layout_password);
        inputLayoutPassword = (TextInputLayout) findViewById(R.id.input_layout_cpassword);

        inputName = (EditText) findViewById(R.id.input_name);
        inputMobile = (EditText) findViewById(R.id.input_mobile);
        inputPassword = (EditText) findViewById(R.id.input_password);
        inputcPassword = (EditText) findViewById(R.id.input_cpassword);

        btnSignUp = (Button) findViewById(R.id.btn_signup);
        login = (TextView) findViewById(R.id.btn_login);

        inputName.addTextChangedListener(new MyTextWatcher(inputName));
        //inputMobile.addTextChangedListener(new MyTextWatcher(inputMobile));
        inputMobile.setText(mobileNumber);
        inputPassword.addTextChangedListener(new MyTextWatcher(inputPassword));
        inputcPassword.addTextChangedListener(new MyTextWatcher(inputcPassword));

        gmailSignup = (GoogleSignInButton) findViewById(R.id.gmailSignup);

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

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                registerUser();

                /*Intent intent = new Intent(SignUpActivity.this, OTPActivity.class);
                launchActivity(intent);*/
            }
        });

        /*login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               *//* Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                launchActivity(intent);*//*
            }
        });
*/
    }

    private void registerUser() {

        dialog = LoaderUtil.showProgressBar(this);

        ApiInterface apiInterface = ApiClient.getAPIClient().create(ApiInterface.class);
        RegisterRequest registerRequest = new RegisterRequest(inputName.getText().toString(), inputMobile.getText().toString(), inputPassword.getText().toString());

        Call<RegisterResponse> call = apiInterface.doRegister(registerRequest);

        call.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {


                RegisterResponse registerResponse = response.body();

                System.out.println("ReceivedData" + registerResponse.getMessage());

                LoaderUtil.dismisProgressBar(SignUpActivity.this, dialog);

                launchMobileAuthActivity();

            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                LoaderUtil.dismisProgressBar(SignUpActivity.this, dialog);
            }
        });
    }

    private void launchMobileAuthActivity() {

        System.out.println("SingnupActivity" + mobileNumber);
        Intent intent = new Intent(SignUpActivity.this, MobileAuthActivity.class);
        intent.putExtra("NUMBER", mobileNumber);
        intent.putExtra("FROM",1);
        startActivity(intent);
        finish();

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
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESOLVE_HINT) {
            if (resultCode == RESULT_OK) {
                Credential credential = data.getParcelableExtra(Credential.EXTRA_KEY);

                System.out.println("GmailUserID" + credential.getId());
                System.out.println("GmailUser" + credential.getName() + " " + credential.getPassword() + " " + credential.getId() + " " + credential.getIdTokens());

                if (credential.getId() != null) {
                    Intent intent = new Intent(SignUpActivity.this, DrawerActivity.class);
                    startActivity(intent);
                }

            }
        }


    }


    private void launchActivity(Intent intent) {


        startActivity(intent);

    }


    private class MyTextWatcher implements TextWatcher {

        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            String name = inputName.getText().toString().trim();
            String mobile = inputMobile.getText().toString().trim();
            String password = inputPassword.getText().toString().trim();
            String cPassword = inputcPassword.getText().toString().trim();


            btnSignUp.setEnabled(!name.isEmpty() && validateMobile(mobile) && !cPassword.isEmpty() && validatePassword(password) && passwordMatch(password, cPassword));

            if (btnSignUp.isEnabled()) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    btnSignUp.setBackground(getDrawable(R.drawable.rectangle_shpae));
                    btnSignUp.setTextColor(getApplication().getResources().getColor(R.color.white));
                }
            } else if (!btnSignUp.isEnabled()) {
                btnSignUp.setEnabled(false);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    btnSignUp.setBackground(getDrawable(R.color.btn_disable));
                }
            }


        }

        public void afterTextChanged(Editable editable) {

        }
    }

}
