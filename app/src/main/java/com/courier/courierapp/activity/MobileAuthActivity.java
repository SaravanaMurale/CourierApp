package com.courier.courierapp.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.courier.courierapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.hbb20.CountryCodePicker;

import java.util.concurrent.TimeUnit;

public class MobileAuthActivity extends AppCompatActivity {

    private static final String TAG = "PhoneAuth";

    private EditText phoneText;
    private EditText codeText;
    private Button verifyButton;
    private Button sendButton;
//    private Button resendButton;

    private RelativeLayout generateOtpLayout, receiveOtpLayout;



    private String phoneVerificationId;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks
            verificationCallbacks;
    private PhoneAuthProvider.ForceResendingToken resendToken;

    private FirebaseAuth fbAuth;
    CountryCodePicker ccp;

    String mobileNum;
    int fromStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile_auth);

        fbAuth = FirebaseAuth.getInstance();

        Intent intent = getIntent();
        mobileNum = intent.getStringExtra("NUMBER");
        fromStatus=intent.getIntExtra("FROM",0);

        phoneText = (EditText) findViewById(R.id.input_phoneTxt);
        codeText = (EditText) findViewById(R.id.pinView);
        verifyButton = (Button) findViewById(R.id.verifyOtpBtn);
        sendButton = (Button) findViewById(R.id.generateOtpBtn);
//        resendButton = (Button) findViewById(R.id.resendOtpBtn);
        generateOtpLayout = (RelativeLayout) findViewById(R.id.phoneAuth);
        receiveOtpLayout = (RelativeLayout) findViewById(R.id.receiveOTP);


        ccp = (CountryCodePicker) findViewById(R.id.ccp);

//        verifyButton.setEnabled(false);
//        resendButton.setEnabled(false);


        System.out.println("OTPMOBILENUMBER"+mobileNum);

        verificationCallbacks =
                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                    @Override
                    public void onVerificationCompleted(
                            PhoneAuthCredential credential) {

//                        resendButton.setEnabled(false);
//                        verifyButton.setEnabled(false);
                        codeText.setText("");
                        signInWithPhoneAuthCredential(credential);
                    }

                    @Override
                    public void onVerificationFailed(FirebaseException e) {
                        e.printStackTrace();
                        if (e instanceof FirebaseAuthInvalidCredentialsException) {
                            Toast.makeText(MobileAuthActivity.this, "Invalid number, please enter correct number with your country code...", Toast.LENGTH_SHORT).show();
                        } else if (e instanceof FirebaseTooManyRequestsException) {
                            Toast.makeText(MobileAuthActivity.this, "Something went wrong, try again later...", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCodeSent(String verificationId,
                                           PhoneAuthProvider.ForceResendingToken token) {

                        phoneVerificationId = verificationId;
                        resendToken = token;

                        verifyButton.setEnabled(true);
                        sendButton.setEnabled(false);
//                        resendButton.setEnabled(true);
                        Toast.makeText(MobileAuthActivity.this, "Please Wait...", Toast.LENGTH_SHORT).show();
                    }
                };

        verifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = codeText.getText().toString();

                if (TextUtils.isEmpty(code)) {
                    Toast.makeText(MobileAuthActivity.this, "Invalid verification code", Toast.LENGTH_SHORT).show();
                } else {
                    PhoneAuthCredential credential =
                            PhoneAuthProvider.getCredential(phoneVerificationId, code);
                    signInWithPhoneAuthCredential(credential);
                }
            }
        });

        sendMobileNumberToFirebase(mobileNum);
    }

    private void sendMobileNumberToFirebase(String mobileNum) {
        String countryCode="+91";
        String countryCodeMalasiya ="+60";
        String phoneNumber=countryCodeMalasiya+mobileNum;
        System.out.println("MobileNumberWithCountryCode"+phoneNumber);
        try {
            PhoneAuthProvider.getInstance().verifyPhoneNumber(
                    phoneNumber,        // Phone number to verify
                    60,                 // Timeout duration
                    TimeUnit.SECONDS,   // Unit of timeout
                    MobileAuthActivity.this,               // Activity (for callback binding)
                    verificationCallbacks);
        } catch (Exception e) {
            e.printStackTrace();

            System.out.println("OTPERROR" + e.getMessage().toString());
            Toast.makeText(MobileAuthActivity.this, "" + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }


    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        fbAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            codeText.setText("");
//                            resendButton.setEnabled(false);
                            verifyButton.setEnabled(false);
                            FirebaseUser user = task.getResult().getUser();
                            String phoneNumber = user.getPhoneNumber();

                            if(fromStatus==1){
                                Intent intent = new Intent(MobileAuthActivity.this, LoginActivity.class);
                                //intent.putExtra("MOBILENUM", mobileNum);
                                startActivity(intent);
                                finish();
                            }else if(fromStatus==2){
                                Intent intent = new Intent(MobileAuthActivity.this, PasswordChangeActivity.class);
                                intent.putExtra("MOBILENUM", mobileNum);
                                startActivity(intent);
                                finish();
                            }



                        } else {
                            String message = task.getException().toString();
                            Toast.makeText(MobileAuthActivity.this, "Error" + message, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}