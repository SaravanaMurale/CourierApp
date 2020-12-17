package com.example.courierapp.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.baoyachi.stepview.VerticalStepView;
import com.example.courierapp.R;

import java.util.ArrayList;
import java.util.List;

public class TrackingStatusFragment extends Fragment {


    VerticalStepView verticalStepView;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_tracking_status, null);

        verticalStepView = (VerticalStepView) view.findViewById(R.id.step_view);

        String trackingStatus = getArguments().getString("TRACKING_STATUS");
        String tracNumber = getArguments().getString("TRACKNO");


        setSetView(trackingStatus);

        return view;
    }

    private void setSetView(String trackingStatus) {

        int completePosition = -1;

        if (trackingStatus == "Pending") {

            completePosition = 0;
        } else if (trackingStatus.equals("Processing")  ) {
            completePosition = 1;
        } else if (trackingStatus .equals("OnDelivery") ) {
            completePosition = 2;
        } else if (trackingStatus .equals("Dispatch") ) {
            completePosition = 3;
        } else if (trackingStatus .equals("Delivered") ) {
            completePosition = 4;
        } else if (trackingStatus .equals("Cancel") ) {
            completePosition = 5;
        }


        verticalStepView.setStepsViewIndicatorComplectingPosition(getProgressStatus().size())
                .reverseDraw(false)
                .setStepViewTexts(getProgressStatus())
                .setLinePaddingProportion(2.085f)
                .setStepsViewIndicatorCompletedLineColor(getActivity().getColor(R.color.sun_atlantic_color))
                .setStepViewComplectedTextColor(getActivity().getColor(R.color.sun_atlantic_color))
                .setStepViewUnComplectedTextColor(getActivity().getColor(R.color.red))
                .setStepsViewIndicatorUnCompletedLineColor(getActivity().getColor(R.color.red))
                .setStepsViewIndicatorCompleteIcon(getActivity().getDrawable(R.drawable.ic_name))
                .setStepsViewIndicatorDefaultIcon(getActivity().getDrawable(R.drawable.ic_phone));
        verticalStepView.setStepsViewIndicatorComplectingPosition(completePosition);


    }

    private List<String> getProgressStatus() {

        List<String> getProgress = new ArrayList<>();

        getProgress.add("Processing Delivery");
        getProgress.add("OnDelivery");
        getProgress.add("Dispatch");
        getProgress.add("Delivered");

        return getProgress;
    }

}
