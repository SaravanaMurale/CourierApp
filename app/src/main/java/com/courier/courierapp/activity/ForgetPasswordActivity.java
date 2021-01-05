package com.courier.courierapp.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.courier.courierapp.MobileAuthActivity;
import com.courier.courierapp.R;
import com.courier.courierapp.model.BaseResponse;
import com.courier.courierapp.retrofit.ApiClient;
import com.courier.courierapp.retrofit.ApiInterface;
import com.courier.courierapp.utils.LoaderUtil;
import com.courier.courierapp.utils.ToastUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ForgetPasswordActivity extends AppCompatActivity {

    EditText forgetPasMobile;
    Button btnForgetPassword;
    Dialog dialog;
    String mobileNumber;

    String receivedActivity;
TextView alreadyMember;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        Intent intent = getIntent();
        receivedActivity = intent.getStringExtra("MOBILENUMBER");


        forgetPasMobile = (EditText) findViewById(R.id.forgetPasMobile);
        btnForgetPassword = (Button) findViewById(R.id.btnForgetPassword);
        alreadyMember=(TextView)findViewById(R.id.alreadyMember);

        if (receivedActivity != null) {
            forgetPasMobile.setText(receivedActivity);
            alreadyMember.setVisibility(View.VISIBLE);
        }


        btnForgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mobileNumber = forgetPasMobile.getText().toString();
                if (mobileNumber.length() < 10) {
                    ToastUtils.getInstance(ForgetPasswordActivity.this).showLongToast(getString(R.string.enter));
                } else {
                    sendMobileNumberToServer();
                }
            }
        });


    }

    private void sendMobileNumberToServer() {

        dialog = LoaderUtil.showProgressBar(this);

        ApiInterface apiInterface = ApiClient.getAPIClient().create(ApiInterface.class);

        Call<BaseResponse> call = apiInterface.checkMobileNumberInServer(mobileNumber);

        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {

                BaseResponse baseResponse=response.body();

                if(baseResponse.getSuccess()==true){
                    launchOTPActivity();
                }



            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {

            }
        });

    }

    private void launchOTPActivity() {
        Intent intent = new Intent(ForgetPasswordActivity.this, MobileAuthActivity.class);
        intent.putExtra("NUMBER", mobileNumber);
        intent.putExtra("FROM",2);
        startActivity(intent);
        finish();
    }
}
