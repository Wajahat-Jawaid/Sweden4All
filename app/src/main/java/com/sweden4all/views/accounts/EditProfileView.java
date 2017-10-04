//package com.views.accounts;
//
//import android.content.Context;
//import android.text.TextUtils;
//import android.widget.EditText;
//
//import com.jakewharton.rxbinding2.widget.RxTextView;
//import com.smartpay.R;
//import com.smartpay.activities.accounts.ActEditProfile;
//import com.smartpay.constants.Constants;
//import com.smartpay.utils.RippleView;
//import com.smartpay.views.BaseView;
//
//import io.reactivex.Observable;
//import io.reactivex.disposables.Disposable;
//
//public class EditProfileView extends BaseView implements RippleView.OnRippleCompleteListener {
//
//    private static boolean isInfoValid;
//
//    private EditText etFirstName, etLastName, etContact;
//    private Disposable userDisposable;
//
//    public EditProfileView(Context context) {
//        super(context);
//    }
//
//    @Override
//    public int layout() {
//        return R.layout.act_edit_profile;
//    }
//
//    @Override
//    public void onCreate() {
//        etFirstName = findViewFromId(R.id.et_first_name);
//        etLastName = findViewFromId(R.id.et_last_name);
//        etContact = findViewFromId(R.id.et_contact);
//
//        setToolbar(((ActEditProfile)context), R.string.edit_profile, R.mipmap.ic_proceed);
//
//        presetEditText();
//        registerObservables();
//        setListeners();
//    }
//
//    private void presetEditText() {
//        etFirstName.setText(prefs.getString(Constants.FIRST_NAME));
//        etLastName.setText(prefs.getString(Constants.LAST_NAME));
//        etContact.setText(prefs.getString(Constants.CONTACT));
//    }
//
//    private void registerObservables() {
//        Observable<CharSequence> firstNameObservable = RxTextView.textChanges(etFirstName);
//        Observable<CharSequence> lastNameObservable = RxTextView.textChanges(etLastName);
//        Observable<CharSequence> contactObservable = RxTextView.textChanges(etContact);
//
//        userDisposable = Observable.combineLatest(
//                firstNameObservable, lastNameObservable, contactObservable,
//                (o1, o2, o3) ->
//                        !TextUtils.isEmpty(o1) && !TextUtils.isEmpty(o2) &&
//                                !TextUtils.isEmpty(o3))
//                .subscribe(allValid -> {
//                    isInfoValid = allValid;
//                });
//    }
//
//    private void setListeners() {
//        ((RippleView) findViewFromId(R.id.rv_action)).setOnRippleCompleteListener(this);
//        ((RippleView) findViewFromId(R.id.rv_edit)).setOnRippleCompleteListener(this);
//    }
//
//    private void triggerSubmit() {
//        edittextToString();
//        if (hasInternet()) {
//            if (isInfoValid) {
//                ((ActEditProfile) context).handleActionEditProfile(user.getFirstName(),
//                        user.getLastName(), user.getContact());
//            }
//        } else {
//            showSnackBar(getResString(R.string.no_internet));
//        }
//    }
//
//    private void edittextToString() {
//        user.setFirstName(utils.EToS(etFirstName));
//        user.setLastName(utils.EToS(etLastName));
//        user.setContact(utils.EToS(etContact));
//    }
//
//    @Override
//    public void onComplete(RippleView v) {
//        switch (v.getId()) {
//            case R.id.rv_action:
//                triggerSubmit();
//                break;
//            case R.id.rv_edit:
//
//                break;
//        }
//    }
//
//    public void onStop() {
//        disposeSubscribers();
//    }
//
//    private void disposeSubscribers() {
//        if (!userDisposable.isDisposed()) userDisposable.dispose();
//    }
//}