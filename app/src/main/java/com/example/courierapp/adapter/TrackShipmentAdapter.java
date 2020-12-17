package com.example.courierapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.courierapp.R;
import com.example.courierapp.model.GetMyAllShipmentDTO;

import java.util.List;

public class TrackShipmentAdapter extends RecyclerView.Adapter<TrackShipmentAdapter.TrackShipmentViewHolder> {

    Context mCtx;
    private List<GetMyAllShipmentDTO> trackingShipmentDTOList;
    TrackingShipmentClickListener trackingShipmentClickListener;
    private Activity activity;

    public interface TrackingShipmentClickListener {

        public void onTrackClickListener(GetMyAllShipmentDTO getMyAllShipmentDTO);
    }

    public TrackShipmentAdapter(Context mCtx, Activity activity, List<GetMyAllShipmentDTO> trackingShipmentDTOList, TrackingShipmentClickListener trackingShipmentClickListener) {
        this.mCtx = mCtx;
        this.activity = activity;
        this.trackingShipmentDTOList = trackingShipmentDTOList;
        this.trackingShipmentClickListener = trackingShipmentClickListener;
    }

    @NonNull
    @Override
    public TrackShipmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mCtx);
        View view = layoutInflater.inflate(R.layout.trackshipment_adapter, parent, false);
        return new TrackShipmentViewHolder(view);
    }

    public void setDate(List<GetMyAllShipmentDTO> trackingShipmentDTOList) {
        this.trackingShipmentDTOList = trackingShipmentDTOList;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull TrackShipmentViewHolder holder, int position) {
        holder.trackingTrackNum.setText( trackingShipmentDTOList.get(position).getAccountNumber());
        holder.trackingTrackDate.setText(trackingShipmentDTOList.get(position).getDate());
    }

    @Override
    public int getItemCount() {
        return trackingShipmentDTOList == null ? 0 : trackingShipmentDTOList.size();
    }

    class TrackShipmentViewHolder extends RecyclerView.ViewHolder {

        private TextView trackingTrackNum, trackingTrackDate, trackingStatus;

        public TrackShipmentViewHolder(@NonNull View itemView) {
            super(itemView);

            trackingTrackNum = (TextView) itemView.findViewById(R.id.trackShipment_trackingNum);
            trackingTrackDate = (TextView) itemView.findViewById(R.id.trackShipment_trackingDate);
            trackingStatus = (TextView) itemView.findViewById(R.id.trackingView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    GetMyAllShipmentDTO myShipmentDTO = trackingShipmentDTOList.get(getAdapterPosition());

                    trackingShipmentClickListener.onTrackClickListener(myShipmentDTO);
                }
            });


        }
    }

}
