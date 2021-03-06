package com.sweden4all.app;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.sweden4all.R;
import com.sweden4all.activities.ActApplicationStatus;
import com.sweden4all.activities.ActFAQs;
import com.sweden4all.activities.ActMessenger;
import com.sweden4all.activities.ActWebView;
import com.sweden4all.activities.BaseActivity;
import com.sweden4all.activities.accounts.ActEditProfile;
import com.sweden4all.activities.accounts.ActLogin;
import com.sweden4all.activities.appointments.ActListAppointments;
import com.sweden4all.activities.settings.ActSettings;
import com.sweden4all.constants.Constants;
import com.sweden4all.database.SharedPrefs;
import com.sweden4all.utils.RippleView;

import static android.R.id.message;
import static com.sweden4all.constants.Constants.FLAG_ACTIVITY_LAUNCH_BY_FLUSHING_STACK;

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
        navigationView.setItemIconTintList(null);
        navigationView.bringToFront();
        setDrawer(context);
    }

    private void setDrawer(Context context) {
        navigationView.setNavigationItemSelectedListener(
                menuItem -> {
                    drawerLayout.closeDrawers();
                    switch (menuItem.getItemId()) {
                        case R.id.home:
                            drawerLayout.closeDrawer(GravityCompat.START);
                            return true;
                        case R.id.profile:
                            launchAct(ActEditProfile.class, context);
                            return true;
                        case R.id.appointments:
                            launchAct(ActListAppointments.class, context);
                            return true;
                        case R.id.messenger:
                            launchAct(ActMessenger.class, context);
                            return true;
                        case R.id.app_status:
                            launchAct(ActApplicationStatus.class, context);
                            return true;
                        case R.id.settings:
                            launchAct(ActSettings.class, context);
                            return true;
                        case R.id.services:
                            launchWebViewAct("http://www.sweden4all.dk/", context);
                            return true;
                        case R.id.about_us:
                            launchWebViewAct("http://www.sweden4all.dk/about-us/", context);
                            return true;
                        case R.id.contact_us:
                            launchWebViewAct("http://www.sweden4all.dk/contact-us/", context);
                            return true;
                        case R.id.faqs:
                            launchAct(ActFAQs.class, context);
                            return true;
                        case R.id.logout:
                            showLogoutDlg(context);
                            return true;
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

    private void launchWebViewAct(String url, Context context) {
        context.startActivity(new Intent(context, ActWebView.class)
                .putExtra(Constants.WEBVIEW_URL, url)
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
    }

    private static boolean handleLogout(Context context) {
        Intent i = new Intent(context, ActLogin.class);
        i.setFlags(FLAG_ACTIVITY_LAUNCH_BY_FLUSHING_STACK);
        context.startActivity(i);
        return true;
    }

    private static void showLogoutDlg(Context context) {
        new MaterialDialog.Builder(context)
                .title(R.string.plz_cnfrm)
                .content(R.string.u_sure_want_to_logout)
                .positiveText(android.R.string.yes)
                .onPositive((dialog, which) -> handleLogout(context))
                .negativeText(android.R.string.cancel)
                .onNegative((dialog, which) -> dialog.dismiss())
                .show();
    }

    public void setHeader(SharedPrefs prefs) {
        // Initialize views
//        if (headerView == null)
//            headerView = navigationView.getHeaderView(0);
//        if (tvUser == null)
//            tvUser = headerView.findViewById(R.id.tv_user);
//        if (avatar == null)
//            avatar = headerView.findViewById(R.id.iv_profile);
//
//        // Set data to the views
//        tvUser.setText(prefs.getString(Constants.FULL_NAME));
//        try {
//            final Bitmap bitmap = BitmapFactory.decodeFile(prefs.getString(Constants.PROFILE_IMG_LOCAL));
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