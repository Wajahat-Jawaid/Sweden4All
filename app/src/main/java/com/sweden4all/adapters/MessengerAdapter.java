package com.sweden4all.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sweden4all.R;
import com.sweden4all.models.Message;

import java.util.ArrayList;
import java.util.List;

public class MessengerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String ADMIN_MSG_FLAG = "1";
    private static final int MSG_TYPE_ADMIN = 0;
    private static final int MSG_TYPE_ME = 1;

    private ArrayList<Message> listMessages;

    public MessengerAdapter() {
        listMessages = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == MSG_TYPE_ME)
            return new MyMsgViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.row_item_chat_me, parent, false));
        return new AdminMsgViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_item_chat_admin, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Message message = listMessages.get(position);
        if (holder instanceof MyMsgViewHolder) {
            MyMsgViewHolder myMsgViewHolder = (MyMsgViewHolder) holder;
            myMsgViewHolder.tvMsg.setText(message.getMessage());
        } else if (holder instanceof AdminMsgViewHolder) {
            AdminMsgViewHolder adminMsgViewHolder = (AdminMsgViewHolder) holder;
            adminMsgViewHolder.tvMsg.setText(message.getMessage());
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (!isUserMsg(position))
            return MSG_TYPE_ADMIN;
        return MSG_TYPE_ME;
    }

    /**
     * Return false is the isUserMsg is not equal to the {@link #ADMIN_MSG_FLAG}
     */
    private boolean isUserMsg(int pos) {
        return !listMessages.get(pos).isUserMsg().equalsIgnoreCase(ADMIN_MSG_FLAG);
    }

    public void updateList(List<Message> newMessages) {
        for (int i = 0; i < newMessages.size(); i++)
            listMessages.add(newMessages.get(i));
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return listMessages.size();
    }

    private static class MyMsgViewHolder extends RecyclerView.ViewHolder {

        TextView tvMsg;

        MyMsgViewHolder(View itemView) {
            super(itemView);
            tvMsg = itemView.findViewById(R.id.tv_msg);
        }
    }

    private static class AdminMsgViewHolder extends RecyclerView.ViewHolder {

        TextView tvMsg;

        AdminMsgViewHolder(View itemView) {
            super(itemView);
            tvMsg = itemView.findViewById(R.id.tv_msg);
        }
    }
}