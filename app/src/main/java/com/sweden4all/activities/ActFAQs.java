package com.sweden4all.activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.sweden4all.constants.Constants;
import com.sweden4all.models.FAQ;
import com.sweden4all.views.BaseView;
import com.sweden4all.views.FAQsView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class ActFAQs extends BaseActivity {

    private static final String TAG = "ActFAQs";

    private QueryFAQsFromAssetsAsyncTask queryFAQsFromAssetsAsyncTask;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        queryFAQsFromAssetsAsyncTask = new QueryFAQsFromAssetsAsyncTask();
        queryFAQsFromAssetsAsyncTask.execute();
    }

    @Override
    public BaseView getView() {
        return new FAQsView(this);
    }

    @Override
    protected void onStop() {
        if (queryFAQsFromAssetsAsyncTask != null && !queryFAQsFromAssetsAsyncTask.isCancelled()) {
            queryFAQsFromAssetsAsyncTask.cancel(true);
            queryFAQsFromAssetsAsyncTask = null;
        }
        super.onStop();
    }

    private class QueryFAQsFromAssetsAsyncTask extends AsyncTask<Void, Void, ArrayList<FAQ>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showLoader();
        }

        @Override
        protected ArrayList<FAQ> doInBackground(Void... voids) {
            try {
                String json = queryFAQsFromAssets();
                if (!TextUtils.isEmpty(json))
                    return parseFAQsJson(json);
            } catch (Exception ignored) {
            }

            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<FAQ> list) {
            super.onPostExecute(list);
            hideLoader();
            ((FAQsView) view).setRecyclerView(list);
        }

        private String queryFAQsFromAssets() {
            String json;
            try {
                InputStream is = getAssets().open("faqs.json");
                int size = is.available();
                byte[] buffer = new byte[size];
                is.read(buffer);
                is.close();
                json = new String(buffer, "UTF-8");

                return json;
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            return null;
        }

        private ArrayList<FAQ> parseFAQsJson(@NonNull String json) {
            try {
                JSONArray jFaqs = new JSONObject(json).optJSONArray("FAQs");
                if (jFaqs != null) {
                    ArrayList<FAQ> list = new ArrayList<>();
                    for (int i = 0; i < jFaqs.length(); i++) {
                        JSONObject jFaq = jFaqs.getJSONObject(i);
                        FAQ faq = new FAQ(jFaq.optString(Constants.QUESTION),
                                jFaq.optString(Constants.ANSWER));
                        list.add(faq);
                    }

                    return list;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }
    }
}