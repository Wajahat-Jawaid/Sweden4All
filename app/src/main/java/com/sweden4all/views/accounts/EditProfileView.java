package com.sweden4all.views.accounts;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.jakewharton.rxbinding2.widget.RxTextView;
import com.sweden4all.R;
import com.sweden4all.activities.accounts.ActEditProfile;
import com.sweden4all.constants.Constants;
import com.sweden4all.models.User;
import com.sweden4all.utils.RippleView;
import com.sweden4all.views.BaseView;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

public class EditProfileView extends BaseView implements RippleView.OnRippleCompleteListener {

    private static final String TAG = "EditProfileView";
    private static final char EQUAL_TO = '=';
    private static final char COMMA = ',';
    private static final char SINGLE_QUOTATION_MARK = '\'';

    private final Drawable enabled, disabled;
    private Disposable mDisposable;
    private User currentUser = null, editedUser = null;

    private EditText etAbout, etEmail, etPhone, etCity, etCountry;
    private RippleView rvEdit;

    public EditProfileView(Context context) {
        super(context);
        enabled = ContextCompat.getDrawable(context, Constants.BTN_ENABLED_SEA_GREEN_BG);
        disabled = ContextCompat.getDrawable(context, Constants.BTN_DISABLED_BG);
    }

    @Override
    public int layout() {
        return R.layout.act_edit_profile;
    }

    @Override
    public void onCreate() {
        etAbout = findViewFromId(R.id.et_about);
        etEmail = findViewFromId(R.id.et_email);
        etPhone = findViewFromId(R.id.et_phone);
        etCity = findViewFromId(R.id.et_city);
        etCountry = findViewFromId(R.id.et_country);
        rvEdit = findViewFromId(R.id.rv_edit);

        setListeners();
        registerObservables();
    }

    private void setListeners() {
        ((RippleView) findViewFromId(R.id.rv_edit)).setOnRippleCompleteListener(this);
    }

    public void setData(User user) {
        this.currentUser = user;
        editedUser = new User();
        etAbout.setText(user.getAboutMe());
        etEmail.setText(user.getEmail());
        etPhone.setText(user.getPhone());
        etCity.setText(user.getCity());
        etCountry.setText(user.getCountry());
        ((TextView) findViewFromId(R.id.tv_name)).setText(user.getName());
        ((TextView) findViewFromId(R.id.tv_dob)).setText(user.getDob());
        ((TextView) findViewFromId(R.id.tv_location))
                .setText(user.getCity() + " " + user.getCountry());
    }

    private void registerObservables() {
        final Observable<CharSequence> aboutOb = RxTextView.textChanges(etAbout);
        final Observable<CharSequence> emailOb = RxTextView.textChanges(etEmail);
        final Observable<CharSequence> phoneOb = RxTextView.textChanges(etPhone);
        final Observable<CharSequence> cityOb = RxTextView.textChanges(etCity);
        final Observable<CharSequence> countryOb = RxTextView.textChanges(etCountry);

        mDisposable = Observable.combineLatest(
                aboutOb, emailOb, phoneOb, cityOb, countryOb,
                (o1, o2, o3, o4, o5) ->
                        !TextUtils.isEmpty(o1) && !TextUtils.isEmpty(o2) &&
                                !TextUtils.isEmpty(o3) && !TextUtils.isEmpty(o4) &&
                                !TextUtils.isEmpty(o5))
                .subscribe(this::updateEditBtn);
    }

    private void updateEditBtn(boolean enable) {
        rvEdit.setEnabled(enable);
        rvEdit.setBackground(enable ? enabled : disabled);
    }

    private void triggerEditProfile() {
        // Trigger now
        EToS();
        if (isUpdatedInfoDiffThanFocusedInfo()) {
            String query = buildUpdateQuery();
            String sub = query.substring(0, query.length() - 1);
            Log.i(TAG, "sub: " + sub);
            ((ActEditProfile) context).triggerEdit(sub);
        } else {
            showSnackBar(R.string.no_chngs_made);
        }
    }

    private void EToS() {
        editedUser.setAboutMe(utils.EToS(etAbout));
        editedUser.setEmail(utils.EToS(etEmail));
        editedUser.setPhone(utils.EToS(etPhone));
        editedUser.setCity(utils.EToS(etCity));
        editedUser.setCountry(utils.EToS(etCountry));
    }

    /**
     * Validate that if any of the parameters are different so as to be valid to make update
     */
    private boolean isUpdatedInfoDiffThanFocusedInfo() {
        assert currentUser != null && editedUser != null;
        return !editedUser.getAboutMe().equals(currentUser.getAboutMe()) ||
                !editedUser.getEmail().equals(currentUser.getEmail()) ||
                !editedUser.getPhone().equals(currentUser.getPhone()) ||
                !editedUser.getCity().equals(currentUser.getCity()) ||
                !editedUser.getCountry().equals(currentUser.getCountry());
    }

    private String buildUpdateQuery() {
        StringBuilder query = new StringBuilder();
        if (!editedUser.getAboutMe().equals(currentUser.getAboutMe()))
            appendToQueryBuilder(query, Constants.ABOUT_ME, editedUser.getAboutMe());
        if (!editedUser.getEmail().equals(currentUser.getEmail()))
            appendToQueryBuilder(query, Constants.EMAIL, editedUser.getEmail());
        if (!editedUser.getPhone().equals(currentUser.getPhone()))
            appendToQueryBuilder(query, Constants.PHONE, editedUser.getPhone());
        if (!editedUser.getCity().equals(currentUser.getCity()))
            appendToQueryBuilder(query, Constants.CITY, editedUser.getCity());
        if (!editedUser.getCountry().equals(currentUser.getCountry()))
            appendToQueryBuilder(query, Constants.COUNTRY, editedUser.getCountry());

        Log.i(TAG, "Query: " + query.toString());

        return query.toString();
    }

    private void appendToQueryBuilder(StringBuilder builder, String key, String val) {
        builder.append(key);
        builder.append(EQUAL_TO);
        builder.append(SINGLE_QUOTATION_MARK);
        builder.append(val);
        builder.append(SINGLE_QUOTATION_MARK);
        builder.append(COMMA);
    }

    @Override
    public void onComplete(RippleView v) {
        switch (v.getId()) {
            case R.id.rv_edit:
                triggerEditProfile();
                break;
        }
    }

    public void onStop() {
        disposeSubscriber();
        nullifyAssets();
    }

    private void disposeSubscriber() {
        if (mDisposable != null && !mDisposable.isDisposed()) mDisposable.dispose();
    }

    private void nullifyAssets() {
    }
}