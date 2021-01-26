package com.courier.courierapp.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.courier.courierapp.R;
import com.courier.courierapp.fragment.ContactUsFragment;
import com.courier.courierapp.fragment.MyShipmentsFragment;
import com.courier.courierapp.fragment.QuoteFragment;
import com.courier.courierapp.fragment.SchedulePickupFragment;
import com.courier.courierapp.fragment.TrackShipmentFragment;
import com.courier.courierapp.fragment.UserProfileFragment;
import com.courier.courierapp.model.BaseResponse;
import com.courier.courierapp.model.GetToeknResponse;
import com.courier.courierapp.model.LoginResponse;
import com.courier.courierapp.retrofit.ApiClient;
import com.courier.courierapp.retrofit.ApiInterface;
import com.courier.courierapp.utils.LoaderUtil;
import com.courier.courierapp.utils.PreferenceUtil;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DrawerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Dialog dialog;
    String previousTokenFromServer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        getPushNotificationFromServer();
        String currentNotificationToken=PreferenceUtil.getValueString(DrawerActivity.this,PreferenceUtil.NOTIFICATION);
        if(!previousTokenFromServer.equals(currentNotificationToken)){
            saveFirebaseNotificationTokenInServer();
        }else if(previousTokenFromServer.equals(currentNotificationToken)) {

        }



        /*FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(this, new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                String newToken = instanceIdResult.getToken();


                if(newToken!=null){
                    //saveFirebaseNotificationTokenInServer();
                    String token=PreferenceUtil.getValueString(DrawerActivity.this,PreferenceUtil.NOTIFICATION);
                    System.out.println("TOKENGENINCOURIERAPP"+token);
                    PreferenceUtil.setValueString(DrawerActivity.this, PreferenceUtil.NOTIFICATION, newToken);
                }else {
                    System.out.println("NOTOKENGENERATED");
                }



            }
        });
*/


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        Fragment quoteFragment = new QuoteFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.screenArea, quoteFragment);
        fragmentTransaction.commit();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void saveFirebaseNotificationTokenInServer() {

        dialog = LoaderUtil.showProgressBar(this);
        ApiInterface apiInterface = ApiClient.getAPIClient().create(ApiInterface.class);

        /*int userid=PreferenceUtil.getValueInt(LoginActivity.this,PreferenceUtil.USER_ID);
       String pToken=PreferenceUtil.getValueString(LoginActivity.this,PreferenceUtil.NOTIFICATION);*/

        LoginResponse loginResponse=new LoginResponse(PreferenceUtil.getValueInt(DrawerActivity.this,PreferenceUtil.USER_ID),PreferenceUtil.getValueString(DrawerActivity.this,PreferenceUtil.NOTIFICATION));

        Call<BaseResponse> call=apiInterface.saveNotificationTokenInServer(PreferenceUtil.getValueString(DrawerActivity.this, PreferenceUtil.BEARER) + " " + PreferenceUtil.getValueString(DrawerActivity.this, PreferenceUtil.AUTH_TOKEN),loginResponse);
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {

                BaseResponse baseResponse=response.body();
                LoaderUtil.dismisProgressBar(DrawerActivity.this, dialog);
                if(baseResponse!=null){
                    if(baseResponse.getSuccess()){
                        System.out.println("TokenInsertedSuccessfully");
                    }else {
                        System.out.println("TokenIsNotInsertedSuccessfully");
                    }
                }else {
                    return;
                }



            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                LoaderUtil.dismisProgressBar(DrawerActivity.this, dialog);
            }
        });


    }

    private void getPushNotificationFromServer() {

        dialog = LoaderUtil.showProgressBar(this);
        ApiInterface apiInterface = ApiClient.getAPIClient().create(ApiInterface.class);

        String token = PreferenceUtil.getValueString(DrawerActivity.this, PreferenceUtil.BEARER) + " " + PreferenceUtil.getValueString(DrawerActivity.this, PreferenceUtil.AUTH_TOKEN);
        int userId = PreferenceUtil.getValueInt(DrawerActivity.this, PreferenceUtil.USER_ID);

        Call<GetToeknResponse> call=apiInterface.getPushNotificationToken(token,userId);
        call.enqueue(new Callback<GetToeknResponse>() {
            @Override
            public void onResponse(Call<GetToeknResponse> call, Response<GetToeknResponse> response) {

                GetToeknResponse getToeknResponse=response.body();

                if (getToeknResponse!=null){
                    previousTokenFromServer=getToeknResponse.getToken();
                }else {

                }


            }

            @Override
            public void onFailure(Call<GetToeknResponse> call, Throwable t) {

            }
        });


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.drawer, menu);
        return true;
    }*/


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        Fragment fragment = null;

        int id = item.getItemId();

        if (id == R.id.orderShipment) {

            fragment = new QuoteFragment();

            /*Intent intent=new Intent(DrawerActivity.this,LoginActivity.class);
            startActivity(intent);*/

        } else if (id == R.id.trackShiments) {
            fragment = new TrackShipmentFragment();

        } else if (id == R.id.schedulePickup) {

            fragment = new SchedulePickupFragment();

        } else if (id == R.id.myShipments) {

            fragment = new MyShipmentsFragment();

        } else if (id == R.id.contactUs) {

            fragment = new ContactUsFragment();

        } else if (id == R.id.myProfile) {

            fragment = new UserProfileFragment();

        } else if (id == R.id.logOut) {
            callLoginActivity();
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.screenArea, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void callLoginActivity() {

        PreferenceUtil.clear(DrawerActivity.this);

        Intent intent = new Intent(DrawerActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
