package com.courier.courierapp.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.courier.courierapp.R;
import com.courier.courierapp.adapter.TrackShipmentAdapter;
import com.courier.courierapp.model.GetMyAllShipmentDTO;
import com.courier.courierapp.model.GetMyAllShipmentResponse;
import com.courier.courierapp.retrofit.ApiClient;
import com.courier.courierapp.retrofit.ApiInterface;
import com.courier.courierapp.utils.LoaderUtil;
import com.courier.courierapp.utils.PreferenceUtil;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class TrackShipmentFragment extends Fragment implements TrackShipmentAdapter.TrackingShipmentClickListener {


    Dialog dialog;

    RecyclerView recyclerView;
    TrackShipmentAdapter trackShipmentAdapter;

    List<GetMyAllShipmentDTO> trackShipmentList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.layout_trackfragment, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.myTrackShipmentsRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        trackShipmentList = new ArrayList<>();

        trackShipmentAdapter = new TrackShipmentAdapter(getActivity(), getActivity(), trackShipmentList, TrackShipmentFragment.this);

        recyclerView.setAdapter(trackShipmentAdapter);

        getMyTrackingShipmentDetails();

        return view;
    }

    private void getMyTrackingShipmentDetails() {
        dialog = LoaderUtil.showProgressBar(getActivity());

        ApiInterface api = ApiClient.getAPIClient().create(ApiInterface.class);
        String bearerWithToken = PreferenceUtil.getValueString(getActivity(), PreferenceUtil.BEARER) + " " + PreferenceUtil.getValueString(getActivity(), PreferenceUtil.AUTH_TOKEN);
        int userId = PreferenceUtil.getValueInt(getActivity(), PreferenceUtil.USER_ID);
        Call<GetMyAllShipmentResponse> call = api.getAllMyShipment(bearerWithToken, userId);
        call.enqueue(new Callback<GetMyAllShipmentResponse>() {
            @Override
            public void onResponse(Call<GetMyAllShipmentResponse> call, Response<GetMyAllShipmentResponse> response) {
                GetMyAllShipmentResponse getMyAllShipmentResponse = response.body();

                if (getMyAllShipmentResponse != null) {
                    List<GetMyAllShipmentDTO> getMyAllShipmentDTOS = getMyAllShipmentResponse.getGetMyAllShipmentDTOList();
                    trackShipmentAdapter.setDate(getMyAllShipmentDTOS);

                    LoaderUtil.dismisProgressBar(getActivity(), dialog);

                } else if (getMyAllShipmentResponse == null) {
                    LoaderUtil.dismisProgressBar(getActivity(), dialog);
                    Toast.makeText(getActivity(), "You dont have any tracking shipment", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<GetMyAllShipmentResponse> call, Throwable t) {

            }
        });

    }

    @Override
    public void onTrackClickListener(GetMyAllShipmentDTO getMyAllShipmentDTO) {

        Fragment fragment = new TrackingStatusFragment();
        FragmentManager fragmentManager = getFragmentManager();

        Bundle args = new Bundle();
        args.putString("TRACKING_STATUS", getMyAllShipmentDTO.getTrackingStatus());
        args.putString("TRACKNO", getMyAllShipmentDTO.getAccountNumber());
        fragment.setArguments(args);

        // LoaderUtil.dismisProgressBar(getActivity(),dialog);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.screenArea, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();


    }


}
