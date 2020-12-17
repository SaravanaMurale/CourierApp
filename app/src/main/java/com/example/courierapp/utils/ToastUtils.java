package com.example.courierapp.utils;

import android.content.Context;
import android.widget.Toast;

public class ToastUtils {

    static Context sContext;
    static ToastUtils toastUtils;

    public ToastUtils() {
    }

    // ToastUtils.getInstance(SignUpActivity.this).showLongToast(getString(R.string.facebook_app_id));


    public static ToastUtils getInstance(Context context){

        if (toastUtils == null) {
            toastUtils = new ToastUtils();
            sContext = context;
        }
        return toastUtils;


    }

    public void showLongToast(String message){

        Toast.makeText(sContext,message, Toast.LENGTH_LONG).show();

    }

    public void showShortToast(String message){
        Toast.makeText(sContext,message, Toast.LENGTH_SHORT).show();
    }


}
