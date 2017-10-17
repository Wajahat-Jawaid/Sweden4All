package com.sweden4all.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sweden4all.R;
import com.sweden4all.responses.FetchTimeSlotsResponse;

import java.util.List;

public class FetchTimeSlotsAdapter extends RecyclerView.Adapter<FetchTimeSlotsAdapter.ViewHolder> {

    private List<FetchTimeSlotsResponse> list;

    public FetchTimeSlotsAdapter(List<FetchTimeSlotsResponse> list) {
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_item_categories, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tvTitle.setText(list.get(position).getStartTime() + " - " +
                list.get(position).getEndTime());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvTitle;

        public ViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
        }
    }
}