package com.sweden4all.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.sweden4all.R;
import com.sweden4all.models.FAQ;

import java.util.ArrayList;

public class FAQsAdapter extends RecyclerView.Adapter<FAQsAdapter.ViewHolder> {

    private static final int IC_EXPANDED = R.drawable.ic_expand_gray_555555_black_34dp;
    private static final int IC_COLLAPSED = R.drawable.ic_collapse_gray_555555_34dp;

    private ArrayList<FAQ> list;

    public FAQsAdapter(ArrayList<FAQ> list) {
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_item_faqs, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        FAQ faq = list.get(position);
        holder.question.setText(faq.getQuestion());
        holder.answer.setText(faq.getAnswer());
        holder.question.setOnClickListener(view -> {
            faq.setExpanded(!faq.isExpanded());
            toggleExpColl(holder, faq);
        });
        holder.ibExpColl.setOnClickListener(view -> {
            faq.setExpanded(!faq.isExpanded());
            toggleExpColl(holder, faq);
        });
    }

    private static void toggleExpColl(FAQsAdapter.ViewHolder holder, FAQ faq) {
        holder.answer.setVisibility(faq.isExpanded() ? View.VISIBLE : View.GONE);
        holder.ibExpColl.setImageResource(faq.isExpanded() ? IC_COLLAPSED : IC_EXPANDED);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView question, answer;
        ImageButton ibExpColl;

        ViewHolder(View itemView) {
            super(itemView);
            question = itemView.findViewById(R.id.tv_question);
            answer = itemView.findViewById(R.id.tv_answer);
            ibExpColl = itemView.findViewById(R.id.ib_expand_collapse);
        }
    }
}