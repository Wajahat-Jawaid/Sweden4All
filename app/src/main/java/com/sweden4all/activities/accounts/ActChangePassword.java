package com.sweden4all.activities.accounts;

import com.sweden4all.activities.BaseActivity;
import com.sweden4all.views.BaseView;
import com.sweden4all.views.accounts.ChangePasswordView;

public class ActChangePassword extends BaseActivity {

    @Override
    public BaseView getView() {
        return new ChangePasswordView(this);
    }

    public void triggerChange(String newPwd) {

    }
}
