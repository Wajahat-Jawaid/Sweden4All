package com.sweden4all.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;
import com.sweden4all.constants.Constants;
import com.sweden4all.models.Chat;
import com.sweden4all.models.Message;
import com.sweden4all.responses.SendMessageResponse;
import com.sweden4all.responses.StartChatResponse;
import com.sweden4all.views.BaseView;
import com.sweden4all.views.MessengerView;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static com.sweden4all.constants.Constants.INVALID_INT;
import static com.sweden4all.constants.Constants.SUCCESS_RESPONSE;

public class ActMessenger extends BaseActivity {

    private static final String TAG = "ActMessenger";
    private static final int REFRESH_INTERVAL = 1500;

    private int chatId = 0;
    private int lastMsgId = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        queryChats();
    }

    /**
     * Query all the chats to find out if chatID is created or not
     */
    private void queryChats() {
        final Observable<List<Chat>> call =
                apiInterface.getAllChats(prefs.getString(Constants.USER_ID));
        call.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::parseQueryChatsResponse);
    }

    private void parseQueryChatsResponse(List<Chat> response) {
        if (response != null && response.size() > 0) {
            Chat chat = response.get(0);
            if (chat != null && !TextUtils.isEmpty(chat.getChatId())) {
                chatId = Integer.parseInt(chat.getChatId());
                startIntervalBasedFetching();
            } else setInvalidChatId();
        } else setInvalidChatId();
    }

    private void startIntervalBasedFetching() {
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                queryChatMessages();
            }
        };
        Timer timer = new Timer();
        timer.schedule(timerTask, 500, REFRESH_INTERVAL);
    }

    private void queryChatMessages() {
        final Observable<List<Message>> call = apiInterface.getMessages(chatId, lastMsgId);
        call.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::parseFetchMessagesResponse);
    }

    private boolean isFirstTimeLoad = true;

    private void parseFetchMessagesResponse(List<Message> list) {
        if (list != null && list.size() > 0) {
            if (isFirstTimeLoad) {
                ((MessengerView) view).hideProgressBar();
                isFirstTimeLoad = false;
            }
            lastMsgId = Integer.parseInt(list.get(list.size() - 1).getMsgId());
            ((MessengerView) view).updateList(list);
        }
    }

    public void sendMessage(String msg) {
        if (!TextUtils.isEmpty(msg)) {
            if (chatId > 0) {
                Log.i(TAG, "Sending Message");
                final Observable<SendMessageResponse> call = apiInterface.sendMessage(
                        chatId,
                        prefs.getString(Constants.USER_ID),
                        msg,
//                    FirebaseInstanceId.getInstance().getToken(),
                        "abcdefghijklmnopqrstuvwxyz",
                        prefs.getString(Constants.USER_ID),
                        0,
                        "");
                call.observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe(this::parseSendMessageResponse);
            } else {
                startChat(msg);
            }
        } else Log.e(TAG, "Message is Empty");
    }

    private void parseSendMessageResponse(SendMessageResponse response) {
        Log.i(TAG, "response: " + new Gson().toJson(response));
        if (response != null && response.getResponse().equalsIgnoreCase(SUCCESS_RESPONSE)) {
            lastMsgId = Integer.parseInt(response.getMsdId()) - 1;
        } else setInvalidChatId();
    }

    /**
     * Called only when chatId is 0
     */
    private void startChat(String msg) {
        final Observable<StartChatResponse> call = apiInterface.triggerStartChat(
                prefs.getString(Constants.USER_ID),
                msg,
                FirebaseInstanceId.getInstance().getToken(),
                prefs.getString(Constants.USER_ID),
                0,
                "",
                "photo_name");
        call.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::parseStartChatResponse);
    }

    private void parseStartChatResponse(StartChatResponse response) {
        if (response != null && response.getResponse().equalsIgnoreCase(SUCCESS_RESPONSE)) {
            chatId = Integer.parseInt(response.getChatId());
        } else setInvalidChatId();
    }

    private void setInvalidChatId() {
        chatId = Integer.parseInt(INVALID_INT);
    }

    @Override
    public BaseView getView() {
        return new MessengerView(this);
    }
}