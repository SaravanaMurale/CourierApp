package com.courier.courierapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button goPay, callSenganPay;

    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        goPay = (Button) findViewById(R.id.goPay);
        callSenganPay = (Button) findViewById(R.id.callSenganPay);
        webView = (WebView) findViewById(R.id.webView1);

        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setSaveFormData(true);
        webView.getSettings().setAllowContentAccess(true);
        webView.getSettings().setAllowFileAccess(true);
        webView.getSettings().setAllowFileAccessFromFileURLs(true);
        webView.getSettings().setAllowUniversalAccessFromFileURLs(true);
        webView.getSettings().setSupportZoom(true);
        webView.setWebViewClient(new WebViewClient());
        webView.setClickable(true);
        webView.setWebChromeClient(new WebChromeClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setDatabaseEnabled(true);
        //mWebView.getSettings().setDatabasePath(dbpath); //check the documentation for info about dbpath
        webView.getSettings().setMinimumFontSize(1);
        webView.getSettings().setMinimumLogicalFontSize(1);

        webView.loadUrl("https://app.senangpay.my/payment/492159427221256");



      /*  callSenganPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ApiInterface apiInterface = ApiClient.getAPIClient().create(ApiInterface.class);
                Call<BaseResponse> call = apiInterface.paymentGatewayURLCall();
                call.enqueue(new Callback<BaseResponse>() {
                    @Override
                    public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {

                    }

                    @Override
                    public void onFailure(Call<BaseResponse> call, Throwable t) {

                    }
                });


            }
        });*/
      /*  goPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ApiInterface apiInterface = ApiClient.getAPIClient().create(ApiInterface.class);

                String detail = "Shopping_cart_id_30";
                String amount = "2";
                String order_id = "3432D4";
                String hash = "";
                String name = "Murali";
                String email = "saravanamurali24@gmail.com";
                String phone = "9123521374";


                PaymentRequest paymentRequest = new PaymentRequest(detail, amount, order_id, hash, name, email, phone);

                Call<BaseResponse> call = apiInterface.paymentRequest(paymentRequest);
                call.enqueue(new Callback<BaseResponse>() {
                    @Override
                    public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {

                    }

                    @Override
                    public void onFailure(Call<BaseResponse> call, Throwable t) {

                    }
                });
            }
        });
*/

    }

}