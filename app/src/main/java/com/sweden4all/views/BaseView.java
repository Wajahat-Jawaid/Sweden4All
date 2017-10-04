package com.sweden4all.views;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.sweden4all.R;
import com.sweden4all.activities.BaseActivity;
import com.sweden4all.app.Sweden4AllApp;
import com.sweden4all.database.SharedPrefs;
import com.sweden4all.models.User;
import com.sweden4all.network.ApiInterface;
import com.sweden4all.utils.RippleView;
import com.sweden4all.utils.Utils;

import javax.inject.Inject;

public abstract class BaseView extends View {

    @Inject
    public SharedPrefs prefs;
    @Inject
    public NetworkInfo networkInfo;
    @Inject
    public ApiInterface apiInterface;
    @Inject
    public Utils utils;

    public View view;
    public Context context;
    private RelativeLayout loader;
    private Toolbar toolbar;

    public BaseView(Context context) {
        super(context);

        this.context = context;
        Sweden4AllApp.getInstance().getComponent().inject(this);
        view = LayoutInflater.from(context).inflate(layout(), null);

        ((AppCompatActivity) context).addContentView(view,
                new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT));

//        if (findViewFromId(R.id.progress) != null) {
//            loader = findViewFromId(R.id.progress);
//        }
//        if (view.findViewById(R.id.drawer_layout) != null)
//            setDrawer(view);
    }

    public void initializeView() {
        onCreate();
        invalidateToolBar();
        onPostInitialize();
        setActionListener();
    }

    private void setDrawer(View view) {
//        new DrawerManager(context, view, prefs);
    }

    public void setToolbar(Activity activity, @StringRes int text) {
        setToolbar(activity, text, -1);
    }

    public void setToolbar(Activity activity, @StringRes int text, @DrawableRes int icon) {
        TextView title = findViewFromId(R.id.tv_title);
        title.setText(getResString(text));
        if (findViewFromId(R.id.rv_back) != null) {
            ((RippleView) findViewFromId(R.id.rv_back))
                    .setOnRippleCompleteListener(v -> activity.finish());
        }
        if (icon != -1) {
            ImageButton image = findViewFromId(R.id.ib_action);
            image.setImageResource(icon);
        }
    }

    public void switchActivity(Class<? extends BaseActivity> activity) {
        Intent intent = new Intent(context, activity);
        context.startActivity(intent);
    }

    public void switchActivity(Class<? extends BaseActivity> activity, Bundle extras) {
        Intent intent = new Intent(context, activity);
        intent.putExtras(extras);
        context.startActivity(intent);
    }

    public void switchActivity(Class<? extends BaseActivity> activity, Bundle extras, int flags) {
        Intent intent = new Intent(context, activity);
        intent.setFlags(flags);
        intent.putExtras(extras);
        context.startActivity(intent);
    }

    public void switchActivity(Class<? extends BaseActivity> activity, int flags) {
        Intent intent = new Intent(context, activity);
        intent.setFlags(flags);
        context.startActivity(intent);
    }

    public boolean hasInternet() {
        return networkInfo != null && networkInfo.isConnectedOrConnecting();
    }

    public final void invalidateToolBar() {
        if (context != null)
            ((BaseActivity) context).supportInvalidateOptionsMenu();

        if (getToolbarId() == 0) {
            return;
        }

        boolean isAlreadyLoaded = true;

        if (toolbar == null) {
            isAlreadyLoaded = false;
            toolbar = findViewFromId(getToolbarId());
        }

        if (toolbar == null) {
            return;
        }

        toolbar.setVisibility(View.VISIBLE);

        if (!isAlreadyLoaded) {
            setupToolBar();
        }

        onToolBarRefresh(toolbar);
    }

    private void setupToolBar() {
        //setTitle( getActionBarTitle() );

        onToolBarSetup(toolbar);
    }

    public abstract int layout();

    public String getResString(@StringRes int id) {
        return getResources().getString(id);
    }

    public abstract void onCreate();

    protected <T extends View> T findViewFromId(int id) {
        return (T) view.findViewById(id);
    }

    public void showSnackBar(@StringRes int str) {
        showSnackBar(getResString(str), false);
    }

    public void showSnackBar(String str) {
        showSnackBar(str, false);
    }

    private Resources resources;

    public Resources getMResources() {
        if (resources == null)
            resources = getResources();
        return resources;
    }

    public void showSnackBar(String str, Boolean isShort) {
        Snackbar.make(view, str, isShort ? Snackbar.LENGTH_SHORT : Snackbar.LENGTH_LONG).show();
    }

    public void showToast(@StringRes int str) {
        showToast(getResString(str), true);
    }

    public void showToast(String str) {
        showToast(str, true);
    }

    public void showToast(String str, boolean isShort) {
        Toast.makeText(context, str, isShort ? Toast.LENGTH_SHORT : Toast.LENGTH_LONG).show();
    }

    public RelativeLayout getLoaderView() {
        return loader;
    }

    @IdRes
    protected int getToolbarId() {
        return 0;
    }

    protected void onToolBarRefresh(Toolbar toolbar) {

    }

    protected String getActionBarTitle() {
        return ((BaseActivity) context).getTitle().toString();
    }

    protected void onToolBarSetup(Toolbar toolBar) {
        toolbar.setNavigationOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                ((BaseActivity) context).finish();
            }
        });
    }

    public Toolbar getToolbar() {
        return toolbar;
    }

    public void setToolbar(Toolbar toolbar) {
        this.toolbar = toolbar;
    }

    protected void setTitle(CharSequence title) {
        BaseActivity baseActivity = (BaseActivity) context;

        if (baseActivity == null) {
            return;
        }

        baseActivity.setSupportActionBar(toolbar);
        ActionBar actionBar = baseActivity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(!TextUtils.isEmpty(title) ? title : "");
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    protected void onPostInitialize() {

    }

    protected void setActionListener() {
    }
}