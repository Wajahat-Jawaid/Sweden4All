package com.sweden4all.views;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.sweden4all.R;
import com.sweden4all.adapters.FAQsAdapter;
import com.sweden4all.models.FAQ;

import java.util.ArrayList;

public class FAQsView extends BaseView {

    public FAQsView(Context context) {
        super(context);
    }

    @Override
    public int layout() {
        return R.layout.act_faqs;
    }

    @Override
    public void onCreate() {
        setToolbar(R.string.faqs);
    }

    public void setRecyclerView(ArrayList<FAQ> list) {
        final RecyclerView recyclerView = findViewFromId(R.id.recycler_view);
        recyclerView.setLayoutManager(getLinearLayoutManager());
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(new FAQsAdapter(list));
    }
}