package com.courier.courierapp.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;


import com.courier.courierapp.R;
import com.courier.courierapp.activity.DrawerActivity;
import com.courier.courierapp.activity.PaymentGatewayActivity;
import com.courier.courierapp.model.BaseResponse;
import com.courier.courierapp.model.DirectionsApiResponseDTO;
import com.courier.courierapp.model.GetPaymentUserDetailsRequest;
import com.courier.courierapp.model.PaymentUserResponse;
import com.courier.courierapp.model.PaymentUserResponseDTO;
import com.courier.courierapp.model.UpdatePriceRequest;
import com.courier.courierapp.retrofit.ApiClient;
import com.courier.courierapp.retrofit.ApiInterface;
import com.courier.courierapp.utils.AppConstant;
import com.courier.courierapp.utils.LoaderUtil;
import com.courier.courierapp.utils.PreferenceUtil;
import com.courier.courierapp.utils.ToastUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShowPriceFragment extends Fragment {

    TextView price, deliveryDay;
    String km_str;


    CardView priceDetailsCardView;
    Button makeAccountable;

    String trackNumber;
    int finalPrice;
    Double lastAmt;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //Toast.makeText(getActivity(), "I am Called", Toast.LENGTH_LONG).show();

        View view = inflater.inflate(R.layout.fragment_showprice, container, false);

        price = (TextView) view.findViewById(R.id.price);
        priceDetailsCardView = (CardView) view.findViewById(R.id.priceDetailsCardView);
        makeAccountable = (Button) view.findViewById(R.id.makeAccountable);
        deliveryDay = (TextView) view.findViewById(R.id.deliveryDay);

        String cashType = getArguments().getString("CASHTYPE");
        trackNumber = getArguments().getString("TRACKNO");
        String deliveryDate = getArguments().getString("DELIVDATA");

        deliveryDay.setText(deliveryDate);

        System.out.println("ReceivedCashType" + cashType);

        if (cashType.equals("account")) {
            priceDetailsCardView.setEnabled(false);
            priceDetailsCardView.setOnClickListener(null);
            makeAccountable.setVisibility(View.VISIBLE);


            makeAccountable.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    makePriceAccountable();

                }
            });


        } else {


            priceDetailsCardView.setEnabled(true);
            priceDetailsCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (trackNumber == null) {
                        Toast.makeText(getActivity(), "You can view only quotation here", Toast.LENGTH_LONG).show();
                    } else if(trackNumber!=null){
                        getUserDetailsForPayment();
                    }


                }
            });
            makeAccountable.setVisibility(View.GONE);
            makeAccountable.setOnClickListener(null);
        }

        getRouteDistance();


        return view;
    }

    private void updatePriceDetailsInServer() {

        final Dialog dialog = LoaderUtil.showProgressBar(getActivity());

        ApiInterface api = ApiClient.getAPIClient().create(ApiInterface.class);

        UpdatePriceRequest updatePriceRequest = new UpdatePriceRequest(trackNumber, finalPrice);

        Call<BaseResponse> call = api.updatePrice(PreferenceUtil.getValueString(getActivity(), PreferenceUtil.BEARER) + " " + PreferenceUtil.getValueString(getActivity(), PreferenceUtil.AUTH_TOKEN), updatePriceRequest);

        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                LoaderUtil.dismisProgressBar(getActivity(), dialog);
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                LoaderUtil.dismisProgressBar(getActivity(), dialog);
            }
        });

    }

    private void getUserDetailsForPayment() {

        final Dialog dialog = LoaderUtil.showProgressBar(getActivity());

        ApiInterface api = ApiClient.getAPIClient().create(ApiInterface.class);

        GetPaymentUserDetailsRequest getPaymentUserDetailsRequest = new GetPaymentUserDetailsRequest(PreferenceUtil.getValueInt(getContext(), PreferenceUtil.USER_ID), trackNumber);

        String bearerWithToken = PreferenceUtil.getValueString(getActivity(), PreferenceUtil.BEARER) + " " + PreferenceUtil.getValueString(getActivity(), PreferenceUtil.AUTH_TOKEN);

        Call<PaymentUserResponse> call = api.getPaymentUserDetails(bearerWithToken, getPaymentUserDetailsRequest);
        call.enqueue(new Callback<PaymentUserResponse>() {
            @Override
            public void onResponse(Call<PaymentUserResponse> call, Response<PaymentUserResponse> response) {

                PaymentUserResponse paymentUserResponse = response.body();

                if (paymentUserResponse != null) {
                    List<PaymentUserResponseDTO> paymentUserResponseDTOList = paymentUserResponse.getPaymentUserResponseDTOList();

                    if (paymentUserResponseDTOList.get(0).getPaymentUserName() != null && paymentUserResponseDTOList.get(0).getPaymentUserContactNum() != null && paymentUserResponseDTOList.get(0).getPaymentUserEmail() != null && paymentUserResponseDTOList.get(0).getPaymentUserPrice() > 0) {
                        LoaderUtil.dismisProgressBar(getActivity(), dialog);
                        launchPaymentGatewayActivity(paymentUserResponseDTOList.get(0).getPaymentUserName(), paymentUserResponseDTOList.get(0).getPaymentUserContactNum(), paymentUserResponseDTOList.get(0).getPaymentUserEmail(), paymentUserResponseDTOList.get(0).getPaymentUserPrice());

                    } else {
                        LoaderUtil.dismisProgressBar(getActivity(), dialog);
                        Toast.makeText(getActivity(), "Please provide required details", Toast.LENGTH_LONG).show();
                    }


                } else {
                    LoaderUtil.dismisProgressBar(getActivity(), dialog);
                }


            }

            @Override
            public void onFailure(Call<PaymentUserResponse> call, Throwable t) {

                LoaderUtil.dismisProgressBar(getActivity(), dialog);

            }
        });


    }

    private void makePriceAccountable() {

        final Dialog dialog = LoaderUtil.showProgressBar(getActivity());

        ApiInterface api = ApiClient.getAPIClient().create(ApiInterface.class);

        UpdatePriceRequest updatePriceRequest = new UpdatePriceRequest(trackNumber, finalPrice);

        Call<BaseResponse> call = api.updatePrice(PreferenceUtil.getValueString(getActivity(), PreferenceUtil.BEARER) + " " + PreferenceUtil.getValueString(getActivity(), PreferenceUtil.AUTH_TOKEN), updatePriceRequest);
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if (response.isSuccessful()) {
                    BaseResponse baseResponse = response.body();


                    if (baseResponse.getSuccess()) {

                        clearPreferenceData();

                        LoaderUtil.dismisProgressBar(getActivity(), dialog);
                        Intent intent = new Intent(getActivity(), DrawerActivity.class);
                        startActivity(intent);
                        getActivity().finish();
                    } else {
                        LoaderUtil.dismisProgressBar(getActivity(), dialog);
                        //LoaderUtil.dismisProgressBar(getActivity(), dialog);
                        ToastUtils.getInstance(getActivity()).showLongToast("Please try again");
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {

                LoaderUtil.dismisProgressBar(getContext(), dialog);

            }
        });


    }

    private void clearPreferenceData() {

        PreferenceUtil.remove(getActivity(), PreferenceUtil.SCHEDULE_PICKUP_SUBMIT_STATUS);
        PreferenceUtil.remove(getActivity(), PreferenceUtil.POINT_A_LAT);
        PreferenceUtil.remove(getActivity(), PreferenceUtil.POINT_A_LONG);
        PreferenceUtil.remove(getActivity(), PreferenceUtil.POINT_B_LAT);
        PreferenceUtil.remove(getActivity(), PreferenceUtil.POINT_B_LONG);
    }


    private void getRouteDistance() {

        final Dialog dialog = LoaderUtil.showProgressBar(getContext());

        String originLat = PreferenceUtil.getValueString(getActivity(), PreferenceUtil.POINT_A_LAT);
        String originLong = PreferenceUtil.getValueString(getActivity(), PreferenceUtil.POINT_A_LONG);

        System.out.println("FromPoint" + originLat + " " + originLong);

        String destiLat = PreferenceUtil.getValueString(getActivity(), PreferenceUtil.POINT_B_LAT);
        String destiLong = PreferenceUtil.getValueString(getActivity(), PreferenceUtil.POINT_B_LONG);

        System.out.println("ToPoint" + destiLat + " " + destiLong);


        ApiInterface api = ApiClient.getAPIClient().create(ApiInterface.class);

        //Call<DirectionsApiResponseDTO> directionsApiResponseDTOCall = api.getRouteDistance("13.0223,80.1749", "13.0735,80.2214", AppConstant.API_KEY);

        Call<DirectionsApiResponseDTO> directionsApiResponseDTOCall = api.getRouteDistance(originLat + "," + originLong, destiLat + "," + destiLong, AppConstant.API_KEY);

        directionsApiResponseDTOCall.enqueue(new Callback<DirectionsApiResponseDTO>() {
            @Override
            public void onResponse(Call<DirectionsApiResponseDTO> call, Response<DirectionsApiResponseDTO> response) {

                DirectionsApiResponseDTO directions = response.body();

                LoaderUtil.dismisProgressBar(getContext(), dialog);

                System.out.println("Success Method Called");

                //System.out.println("Routes" + directions.getRoutes());

                //System.out.println("Kilometer" + directions.getRoutes().get(0).getLegs().get(0).getDistance().getText());

                String km_km = directions.getRoutes().get(0).getLegs().get(0).getDistance().getText();

                //String[] words=s1.split("\\s");

                String[] s = km_km.split("\\s");

                //System.out.println("SSSSSS" + s[0]);
                km_str = s[0];

                if (km_str != null) {
                    calculatePriceForDistance(km_str);
                }


                //Toast.makeText(getActivity(),directions.getRoutes().get(0).getLegs().get(0).getDistance().getText(),Toast.LENGTH_LONG).show();

                //directions.getRoutes().get(0).getLegs().get(0).getDistance().getText();


            }

            @Override
            public void onFailure(Call<DirectionsApiResponseDTO> call, Throwable t) {

                //Toast.makeText(getActivity(),t.getMessage().toString(),Toast.LENGTH_LONG).show();

                System.out.println("MessageFrom" + t.getMessage().toString());
                System.out.println("Failure Method Called");

                LoaderUtil.dismisProgressBar(getContext(), dialog);

            }
        });


    }


    private void calculatePriceForDistance(String km_) {
        float f = Float.parseFloat(km_);
        int km = Math.round(f);
        //int _km = Integer.parseInt(km_);


        if (km != 0 && km > 0) {

            if (km <= 10) {
                price.setText("RM5.0");
            } else if (km > 10) {
                int i = km - 10;
                finalPrice = i + 5;
                lastAmt = Double.valueOf(finalPrice);
                price.setText("RM " + finalPrice);

                updatePriceDetailsInServer();
            }


        } else {
            System.out.println("You are selected less than 1km");
        }

    }

    private void launchPaymentGatewayActivity(String paymentUserName, String paymentUserContactNum, String paymentUserEmail, int paymentUserPrice) {


        //Dialog dialog = LoaderUtil.showProgressBar(getContext());
        PreferenceUtil.remove(getActivity(), PreferenceUtil.SCHEDULE_PICKUP_SUBMIT_STATUS);
        Intent intent = new Intent(getContext(), PaymentGatewayActivity.class);
        intent.putExtra("customer_name", paymentUserName);
        intent.putExtra("customer_email", paymentUserEmail);
        intent.putExtra("customer_phone", paymentUserContactNum);
        intent.putExtra("amount", paymentUserPrice);
        intent.putExtra("detail", "shipment payment");
        //LoaderUtil.dismisProgressBar(getActivity(), dialog);
        startActivity(intent);

    }


}
