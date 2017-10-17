package com.sweden4all.fragments;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sweden4all.R;
import com.sweden4all.adapters.FetchCatsAdapter;
import com.sweden4all.constants.Constants;
import com.sweden4all.events.RecyclerItemClickListener;
import com.sweden4all.responses.FetchCatsResponse;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class DlgFgQueryCategories extends BaseDialogFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dlg_fg_query_simple_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setTitle(view);
        queryCategories();
    }

    private void setTitle(View view) {
        ((TextView) view.findViewById(R.id.tv_title)).setText(getResString(R.string.select_cat));
    }

    private void queryCategories() {
        final Observable<List<FetchCatsResponse>> call = apiInterface.queryCategories();
        call.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::parseResponse);
    }

    private void parseResponse(List<FetchCatsResponse> response) {
        if (response != null && response.size() > 0) {
            View view = getView();
            if (view == null)
                return;
            final RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerView.setHasFixedSize(true);
            recyclerView.setAdapter(new FetchCatsAdapter(response));
            recyclerView.addOnItemTouchListener(
                    new RecyclerItemClickListener(context, (view1, position) -> {
                        final FetchCatsResponse resp = response.get(position);
                        prefs.insert(Constants.CAT_ID, resp.getCatId());
                        prefs.insert(Constants.CAT_NAME, resp.getName());
                        dismiss();
                    }));
        }
        hideProgress();
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        final Activity activity = getActivity();
        if (activity instanceof DialogInterface.OnDismissListener) {
            ((DialogInterface.OnDismissListener) activity).onDismiss(dialog);
        }
    }

    private void hideProgress() {
        View v = getView();
        if (v == null)
            return;
        v.findViewById(R.id.progress_bar).setVisibility(View.GONE);
    }
}