//package com.activities.accounts;
//
//import android.content.Context;
//import android.os.Bundle;
//import android.support.annotation.NonNull;
//
//import com.activities.BaseActivity;
//import com.sweden4all.R;
//import com.views.BaseView;
//
//import retrofit2.Call;
//
//public class ActEditProfile extends BaseActivity implements SimpleResponseAPIListener {
//
//    private static final String TAG = "SignUpActivity";
//
//    private Context context;
//    private Call<SimpleResponse> call;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        context = this;
//    }
//
//    @Override
//    public BaseView getView() {
//        return new EditProfileView(this);
//    }
//
//    public void handleActionEditProfile(@NonNull final String first,
//                                        @NonNull final String last,
//                                        @NonNull final String contact) {
//        showLoader();
//        call = apiInterface.editProfile(first, last, contact);
//        SimpleResponseAPI.getInstance().triggerCall(context, call);
//    }
//
//    @Override
//    protected void onStop() {
//        ((EditProfileView)view).onStop();
//        if (call != null) call.cancel();
//        hideLoader();
//        super.onStop();
//    }
//
//    @Override
//    public void onSuccess(SimpleResponse response) {
//        showToast(R.string.acc_updated_successfully);
//        finish();
//    }
//
//    @Override
//    public void onFailure(String error) {
//        hideLoader();
//        showSnackBar(error);
//    }
//}