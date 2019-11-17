package com.heebin.smartroute.util.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.heebin.smartroute.R;
import com.heebin.smartroute.data.bean.refined.RefinedRoute;

import java.util.ArrayList;

public class RefinedRouteAdapter extends RecyclerView.Adapter<RefinedRouteAdapter.ViewHolder> {
    private ArrayList<RefinedRoute> mData = null ;

    private OnItemClickListener onItemClickListener;


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView route;
        TextView estimatedTime ;
        Button select;

        ViewHolder(View itemView) {
            super(itemView) ;

            // 뷰 객체에 대한 참조. (hold strong reference)
            route = itemView.findViewById(R.id.route_info);
            estimatedTime = itemView.findViewById(R.id.estimated_time);
            select = itemView.findViewById(R.id.select);

        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }


    public RefinedRouteAdapter(ArrayList<RefinedRoute> list, OnItemClickListener onItemClickListener) {
        mData = list ;
    }


    @Override
    public RefinedRouteAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext() ;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;

        View view = inflater.inflate(R.layout.recyclerview_item_route, parent, false) ;
        RefinedRouteAdapter.ViewHolder vh = new RefinedRouteAdapter.ViewHolder(view) ;

        return vh ;
    }


    @Override
    public void onBindViewHolder(RefinedRouteAdapter.ViewHolder holder, int position) {
        RefinedRoute refinedRoute = mData.get(position) ;
        holder.route.setText(refinedRoute.toString()) ;
        holder.estimatedTime.setText(refinedRoute.getEstimatedTime());
        holder.select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.onItemClick(view, position);
            }
        });

    }


    @Override
    public int getItemCount() {
        return mData.size() ;
    }
}
