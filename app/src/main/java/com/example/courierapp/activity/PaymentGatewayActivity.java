package com.example.courierapp.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.courierapp.R;

import java.net.URLEncoder;

public class PaymentGatewayActivity extends AppCompatActivity {

   /* @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_gateway);

        Toast.makeText(PaymentGatewayActivity.this,"IamCalled",Toast.LENGTH_LONG).show();
    }*/

    private WebView mPaymentWebView;
    private TextView mMessageView;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_gateway);

        Intent intent = getIntent();
        String customerName = null, customerEmail = null, customerPhone = null, detail = null;
        int amount = 0;
        int finalAmout=0;
        if (intent != null) {
            customerName = intent.getStringExtra("customer_name");
            customerEmail = intent.getStringExtra("customer_email");
            customerPhone = intent.getStringExtra("customer_phone");
            detail = intent.getStringExtra("detail");
            amount = intent.getIntExtra("amount", 0);
            finalAmout=Math.round(amount);
        }
        if (customerName != null && !customerName.isEmpty() && customerEmail != null && !customerEmail.isEmpty() && customerPhone != null && !customerPhone.isEmpty() && detail != null && !detail.isEmpty() && finalAmout > 0) {
            try {
                mMessageView = findViewById(R.id.message);
                mPaymentWebView = findViewById(R.id.web_view_payment);
                mPaymentWebView.getSettings().setJavaScriptEnabled(true);
                mPaymentWebView.setWebViewClient(new WebViewClient());
                mPaymentWebView.setWebChromeClient(new WebChromeClient());
                mPaymentWebView.addJavascriptInterface(new PaymentInterface(this), "Android");

                String postData = "customer_name=" + URLEncoder.encode(customerName, "UTF-8") + "&customer_email=" + URLEncoder.encode(customerEmail, "UTF-8") + "&customer_phone=" + URLEncoder.encode(customerPhone, "UTF-8") + "&detail=" + URLEncoder.encode(detail, "UTF-8") + "&amount=" + URLEncoder.encode(String.valueOf(amount), "UTF-8");

                //mPaymentWebView.postUrl("http://192.168.1.7/senangpay/payment.php", postData.getBytes());
                mPaymentWebView.postUrl("https://mobilesunatlanticsb.com/senangpay_payment.php", postData.getBytes());

                mPaymentWebView.setVisibility(View.VISIBLE);
                mMessageView.setVisibility(View.GONE);
            } catch (Exception e) {
                //finish();
            }
        } else {
            //finish();
        }


    }

    class PaymentInterface {
        private Context mContext;

        public PaymentInterface(Context context) {
            mContext = context;
        }

        @JavascriptInterface
        public void paymentSuccess(final String message) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mPaymentWebView.setVisibility(View.GONE);
                    mMessageView.setVisibility(View.VISIBLE);
                    mMessageView.setText(message);
                    // mMessageView.setText(R.string.payment_success);

                    Intent intent = new Intent(PaymentGatewayActivity.this, DrawerActivity.class);
                    startActivity(intent);

                    /*new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    finish();
                                }
                            });
                        }
                    }, 2500);*/
                }
            });
        }

        @JavascriptInterface
        public void paymentFailed(final String message) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mPaymentWebView.setVisibility(View.GONE);
                    mMessageView.setVisibility(View.VISIBLE);
                    mMessageView.setText(message);
                    // mMessageView.setText(R.string.payment_failed);

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    finish();
                                }
                            });
                        }
                    }, 2500);
                }
            });
        }
    }

}