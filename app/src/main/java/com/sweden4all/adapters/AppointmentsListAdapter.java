package com.sweden4all.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sweden4all.R;
import com.sweden4all.models.Appointment;

import java.util.List;

public class AppointmentsListAdapter extends RecyclerView.Adapter<AppointmentsListAdapter.ViewHolder> {

    private List<Appointment> list;

    public AppointmentsListAdapter(List<Appointment> list) {
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_item_history, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Appointment appointment = list.get(position);
        holder.tvDate.setText(appointment.getDate());
        final String timings = appointment.getTimingsStr();
        holder.tvStartTime.setText(timings.substring(0, timings.indexOf('-')));
        holder.tvTimings.setText(timings);
        holder.tvReason.setText(appointment.getCatStr());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends FetchCatsAdapter.ViewHolder {

        TextView tvDate, tvStartTime, tvReason, tvTimings;

        public ViewHolder(View itemView) {
            super(itemView);
            tvDate = itemView.findViewById(R.id.tv_date);
            tvStartTime = itemView.findViewById(R.id.tv_time_start);
            tvReason = itemView.findViewById(R.id.tv_reason);
            tvTimings = itemView.findViewById(R.id.tv_timings);
        }
    }
}