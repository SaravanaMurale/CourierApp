package com.example.courierapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;

import androidx.appcompat.app.AppCompatActivity;

import com.example.courierapp.R;
import com.example.courierapp.utils.PreferenceUtil;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;


public class SplashScreen extends AppCompatActivity {

    int user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        user_id = PreferenceUtil.getValueInt(this, PreferenceUtil.USER_ID);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(SplashScreen.this, new OnSuccessListener<InstanceIdResult>() {
                    @Override
                    public void onSuccess(InstanceIdResult instanceIdResult) {
                        String newToken = instanceIdResult.getToken();
                        PreferenceUtil.setValueString(SplashScreen.this, "notification_token", newToken);
                    }
                });
            }
        });

        new SplashDownCountDown(3000, 1000).start();
    }

    private class SplashDownCountDown extends CountDownTimer {

        SplashDownCountDown(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);

            PreferenceUtil.remove(SplashScreen.this, PreferenceUtil.POINT_A_LAT);
            PreferenceUtil.remove(SplashScreen.this, PreferenceUtil.POINT_A_LONG);

            PreferenceUtil.remove(SplashScreen.this, PreferenceUtil.POINT_B_LAT);
            PreferenceUtil.remove(SplashScreen.this, PreferenceUtil.POINT_B_LONG);

        }

        @Override
        public void onTick(long milliSecond) {

        }

        @Override
        public void onFinish() {

            Intent intent;

            if (user_id == -1) {
                intent = new Intent(SplashScreen.this, LoginActivity.class);

            } else {
                intent = new Intent(SplashScreen.this, DrawerActivity.class);

            }

            startActivity(intent);
            finish();

        }
    }
}