package com.sweden4all.app;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sweden4all.R;
import com.sweden4all.activities.BaseActivity;
import com.sweden4all.constants.Constants;
import com.sweden4all.database.SharedPrefs;
import com.sweden4all.utils.RippleView;

public class DrawerManager {

    private static final String TAG = "DrawerManager";

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private RippleView rvDrawer;
    /* HEADER */
    private View headerView;
    private TextView tvUser;
    private ImageView avatar;

    public DrawerManager(Context context, View view, SharedPrefs prefs) {
        setDrawer(context, view, prefs);
    }

    private void setDrawer(Context context, View view, SharedPrefs prefs) {
        init(view);
        setDrawer(context, prefs);
        setListeners();
    }

    private void init(View view) {
        drawerLayout = view.findViewById(R.id.drawer_layout);
        navigationView = view.findViewById(R.id.navigation_view);
        rvDrawer = view.findViewById(R.id.rv_drawer);
    }

    private void setDrawer(final Context context, SharedPrefs prefs) {
        // Set Header
        setHeader(prefs);
        navigationView.bringToFront();
        setDrawer(context);
    }

    private void setDrawer(Context context) {
        navigationView.setNavigationItemSelectedListener(
                menuItem -> {
                    drawerLayout.closeDrawers();
                    switch (menuItem.getItemId()) {
                        default:
                            return true;
                    }
                });
    }

    private void launchAct(Class<? extends BaseActivity> activity, Context context) {
        context.startActivity(new Intent(context, activity)
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
    }

    public void setHeader(SharedPrefs prefs) {
        // Initialize views
        if (headerView == null)
            headerView = navigationView.getHeaderView(0);
        if (tvUser == null)
            tvUser = headerView.findViewById(R.id.tv_user);
        if (avatar == null)
            avatar = headerView.findViewById(R.id.iv_profile);

        // Set data to the views
//        tvUser.setText(prefs.getString(Constants.FULL_NAME));
//        try {
//            final Bitmap bitmap = BitmapFactory.decodeFile(prefs.getString(Constants.PROFILE_IMG));
//            if (bitmap != null)
//                avatar.setImageBitmap(bitmap);
//            else avatar.setImageResource(R.mipmap.user_placeholder_rounded);
//        } catch (NullPointerException e) {
//            e.printStackTrace();
//        }
    }

    private void setListeners() {
        rvDrawer.setOnRippleCompleteListener(__ -> drawerLayout.openDrawer(GravityCompat.START));
    }
}