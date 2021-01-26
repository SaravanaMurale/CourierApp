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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.courier.courierapp.R;
import com.courier.courierapp.adapter.MyShipmentAdapter;
import com.courier.courierapp.model.GetMyAllShipmentDTO;
import com.courier.courierapp.model.GetMyAllShipmentResponse;
import com.courier.courierapp.retrofit.ApiClient;
import com.courier.courierapp.retrofit.ApiInterface;
import com.courier.courierapp.utils.LoaderUtil;
import com.courier.courierapp.utils.PdfGenerationUtil;
import com.courier.courierapp.utils.PreferenceUtil;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyShipmentsFragment extends Fragment implements MyShipmentAdapter.MyshipmentClickListener {

    private RecyclerView myShipmentsRecyclerView;
    private MyShipmentAdapter myShipmentAdapter;

    List<GetMyAllShipmentDTO> myShipmentDTOList;

    Dialog dialog;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_myshipment, null);

        myShipmentsRecyclerView = (RecyclerView) view.findViewById(R.id.myShipmentsRecyclerView);
        myShipmentsRecyclerView.setHasFixedSize(true);
        myShipmentsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        myShipmentDTOList = new ArrayList<>();

        myShipmentAdapter = new MyShipmentAdapter(getActivity(), getActivity(), myShipmentDTOList, MyShipmentsFragment.this);

        myShipmentsRecyclerView.setAdapter(myShipmentAdapter);

        getMyAllShipmentData();

        return view;
    }

    private void getMyAllShipmentData() {


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

                    if(getMyAllShipmentDTOS.size()==0){
                        LoaderUtil.dismisProgressBar(getActivity(), dialog);
                        Toast.makeText(getActivity(), "You dont have any shipment yet", Toast.LENGTH_LONG).show();
                    }else if(getMyAllShipmentDTOS.size()>0){
                        myShipmentAdapter.setDate(getMyAllShipmentDTOS);

                        LoaderUtil.dismisProgressBar(getActivity(), dialog);

                    }


                } else {
                    LoaderUtil.dismisProgressBar(getActivity(), dialog);
                    Toast.makeText(getActivity(), "You dont have any shipment yet", Toast.LENGTH_LONG).show();
                }


            }

            @Override
            public void onFailure(Call<GetMyAllShipmentResponse> call, Throwable t) {
                LoaderUtil.dismisProgressBar(getActivity(), dialog);
            }
        });



        /*myShipmentDTOList.add(new MyShipmentDTO(123456,"25-6-2020"));
        myShipmentDTOList.add(new MyShipmentDTO(123456,"25-6-2020"));
        myShipmentDTOList.add(new MyShipmentDTO(123456,"25-6-2020"));
        myShipmentDTOList.add(new MyShipmentDTO(123456,"25-6-2020"));
        myShipmentDTOList.add(new MyShipmentDTO(123456,"25-6-2020"));
        myShipmentDTOList.add(new MyShipmentDTO(123456,"25-6-2020"));
        myShipmentDTOList.add(new MyShipmentDTO(123456,"25-6-2020"));
        myShipmentDTOList.add(new MyShipmentDTO(123456,"25-6-2020"));
        myShipmentDTOList.add(new MyShipmentDTO(123456,"25-6-2020"));
*/
        myShipmentAdapter.setDate(myShipmentDTOList);


        //ApiInterface apiInterface=

    }

    @Override
    public void onPdfClicked(GetMyAllShipmentDTO myShipmentDTO) {

        PdfGenerationUtil pdfGenerationUtil = new PdfGenerationUtil(getActivity());
        pdfGenerationUtil.createPDF(myShipmentDTO.getAccountNumber());

    }
}
