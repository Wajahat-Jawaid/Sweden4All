//package com.sweden4all;
//
//import android.content.Context;
//import android.content.Intent;
//import android.support.design.widget.NavigationView;
//import android.support.v4.view.GravityCompat;
//import android.support.v4.widget.DrawerLayout;
//import android.view.View;
//import android.widget.TextView;
//
//import com.smartpay.R;
//import com.smartpay.activities.ActHelp;
//import com.smartpay.activities.BaseActivity;
//import com.smartpay.activities.settings.ActSettings;
//import com.smartpay.constants.Constants;
//import com.smartpay.database.SharedPrefs;
//import com.smartpay.utils.RippleView;
//
//public class DrawerManager {
//
//    private static final String TAG = "DrawerManager";
//
//    private DrawerLayout drawerLayout;
//    private NavigationView navigationView;
//    private RippleView rvDrawer;
//
//    public DrawerManager(Context context, View view, SharedPrefs prefs) {
//        init(view);
//        setDrawer(context, prefs);
//        setListeners();
//    }
//
//    private void init(View view) {
//        drawerLayout = (DrawerLayout) view.findViewById(R.id.drawer_layout);
//        navigationView = (NavigationView) view.findViewById(R.id.navigation_view);
//        rvDrawer = (RippleView) view.findViewById(R.id.rv_drawer);
//    }
//
//    private void setDrawer(final Context context, SharedPrefs prefs) {
//        // Set Header
//        setHeader(context, navigationView.getHeaderView(0), prefs);
//        navigationView.bringToFront();
//        setNavMenu(context);
//    }
//
//    private void setNavMenu(Context context) {
//        navigationView.setNavigationItemSelectedListener(
//                menuItem -> {
//                    if (menuItem.isChecked()) menuItem.setChecked(false);
//                    else menuItem.setChecked(true);
//                    drawerLayout.closeDrawers();
//                    switch (menuItem.getItemId()) {
//                        case R.id.settings:
//                            launchActivity(context, ActSettings.class);
//                            return true;
//                        case R.id.services_n_help:
//                            launchActivity(context, ActHelp.class);
//                            return true;
//                        case R.id.logout:
//                            handleLogout();
//                            return true;
//                        default:
//                            return true;
//                    }
//                });
//    }
//
//    private void launchActivity(Context context, Class<? extends BaseActivity> activity, int... flags) {
//        Intent intent = new Intent(context, activity);
//        for (int i : flags)
//            intent.setFlags(i);
//        context.startActivity(intent);
//    }
//
//    private void launchActivity(Context context, Class<? extends BaseActivity> activity) {
//        Intent intent = new Intent(context, activity);
//        context.startActivity(intent);
//    }
//
//    private void setHeader(Context context, View headerView, SharedPrefs prefs) {
//        TextView user = (TextView) headerView.findViewById(R.id.tv_user);
//        user.setText(context.getResources().getString(R.string.welcome_short) + " "
//                + prefs.getString(Constants.FIRST_NAME));
//    }
//
//    private void handleLogout() {
//
//    }
//
//    private void setListeners() {
//        rvDrawer.setOnRippleCompleteListener(__ -> drawerLayout.openDrawer(GravityCompat.START));
//    }
//}