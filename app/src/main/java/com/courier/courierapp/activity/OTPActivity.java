package com.courier.courierapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.courier.courierapp.R;
import com.courier.courierapp.utils.ToastUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;


public class OTPActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    EditText editText;
    Button otpSubmit;

    String verificationId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        mAuth = FirebaseAuth.getInstance();


        editText = (EditText) findViewById(R.id.otpReceiver);
        otpSubmit = (Button) findViewById(R.id.otpSubmit);


        String number = "+919123521374";

        //System.out.println("CurrentUser"+mAuth.getCurrentUser());

        sendVerificationCode(number);


    }

    void verifyCode(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        signInWithCredential(credential);
    }

    private void sendVerificationCode(String number) {

        System.out.println("ReceivedValue" + number + " " + TimeUnit.SECONDS + " " + TaskExecutors.MAIN_THREAD + " HERE " + changedCallbacks);

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                number,
                60,
                TimeUnit.SECONDS,
                OTPActivity.this,
                changedCallbacks

        );

    }


    private PhoneAuthProvider.OnVerificationStateChangedCallbacks changedCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {


        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);

            verificationId = s;

        }

        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

            String code = phoneAuthCredential.getSmsCode();

            if (code != null) {

                verifyCode(code);
            }

        }


        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {

            Toast.makeText(OTPActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();

        }
    };


    private void signInWithCredential(PhoneAuthCredential credential) {

        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {

                            Intent intent = new Intent(OTPActivity.this, DrawerActivity.class);
                            startActivity(intent);


                            ToastUtils.getInstance(OTPActivity.this).showLongToast("OTP Verified Successfully");

                        } else {
                            ToastUtils.getInstance(OTPActivity.this).showLongToast(task.getException().getMessage());
                        }

                    }
                });
    }


}
