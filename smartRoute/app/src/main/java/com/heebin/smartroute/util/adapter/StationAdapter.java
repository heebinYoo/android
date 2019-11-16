package com.heebin.smartroute.util.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.heebin.smartroute.R;
import com.heebin.smartroute.bean.raw.Station;

import java.util.ArrayList;

public class StationAdapter extends RecyclerView.Adapter<StationAdapter.ViewHolder> {
    OnItemClickListener onItemClickListener;
    ArrayList<Station> mData;
    public StationAdapter(ArrayList<Station> stations, OnItemClickListener onItemClickListener){
        mData =stations;
        this.onItemClickListener = onItemClickListener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext() ;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;

        View view = inflater.inflate(R.layout.recyclerview_item_station, parent, false) ;
        StationAdapter.ViewHolder vh = new StationAdapter.ViewHolder(view) ;

        return vh ;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Station station = mData.get(position) ;
        holder.name.setText(station.getStationName()) ;
        holder.score.setText("TODO");

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public interface OnItemClickListener {
        void onStationItemClick(View v, int position) ;
    }
    class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView score;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.station_name);
            score = itemView.findViewById(R.id.station_score);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition() ;
                    if (pos != RecyclerView.NO_POSITION) {
                        onItemClickListener.onStationItemClick(v,pos);
                    }
                }
            });
        }
    }
}
