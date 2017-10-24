package com.sweden4all.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sweden4all.R;
import com.sweden4all.constants.Constants;
import com.sweden4all.utils.RippleView;

public class DlgFgAppointReasonDetails extends BaseDialogFragment {

    private String reason = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getExtras();
    }

    private void getExtras() {
        if (getArguments() != null)
            reason = getArguments().getString(Constants.REASON);
        else dismissAllowingStateLoss();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((TextView) view.findViewById(R.id.tv_reason)).setText(reason);
        ((RippleView) view.findViewById(R.id.rv_ok)).setOnRippleCompleteListener(v -> {
            dismiss();
        });
    }
}