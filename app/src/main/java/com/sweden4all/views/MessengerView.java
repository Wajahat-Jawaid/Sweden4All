package com.sweden4all.views;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;

import com.sweden4all.R;
import com.sweden4all.activities.ActMessenger;
import com.sweden4all.adapters.MessengerAdapter;
import com.sweden4all.models.Message;
import com.sweden4all.utils.RippleView;

import java.util.List;

public class MessengerView extends BaseView implements RippleView.OnRippleCompleteListener {

    private RecyclerView recyclerView;
    private EditText etMessage;
    private ImageButton ibCamera;

    private MessengerAdapter adapter;

    public MessengerView(Context context) {
        super(context);
    }

    @Override
    public int layout() {
        return R.layout.act_messenger;
    }

    @Override
    public void onCreate() {
        recyclerView = findViewFromId(R.id.recycler_view);
        etMessage = findViewFromId(R.id.et_message);
        ibCamera = findViewFromId(R.id.ib_camera);

        setRecyclerView();
        setListeners();
    }

    private void setListeners() {
        etMessage.setOnKeyListener((v, keyCode, event) -> {
            if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                    (keyCode == KeyEvent.KEYCODE_ENTER)) {
                ((ActMessenger) context).sendMessage(utils.EToS(etMessage));
                performChangesBeforeSendMessage();
            }
            return false;
        });
        ((RippleView) findViewFromId(R.id.rv_send)).setOnRippleCompleteListener(this);
    }

    private void performChangesBeforeSendMessage() {
        hideSoftKeyboard();
        etMessage.setText("");
    }

    private void setRecyclerView() {
        adapter = new MessengerAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(adapter);
    }

    private static final String TAG = "MessengerView";

    public void updateList(List<Message> newMessages) {
        Log.i(TAG, "Updating List");
        adapter.updateList(newMessages);
    }

    public void hideSoftKeyboard() {
        View v = ((ActMessenger) context).getCurrentFocus();
        if (v != null) {
            InputMethodManager imm =
                    (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }

    public void hideProgressBar() {
        findViewFromId(R.id.progress_bar).setVisibility(GONE);
    }

    @Override
    public void onComplete(RippleView v) {
        switch (v.getId()) {
            case R.id.rv_send:
                performChangesBeforeSendMessage();
                ((ActMessenger) context).sendMessage(utils.EToS(etMessage));
                break;
        }
    }
}