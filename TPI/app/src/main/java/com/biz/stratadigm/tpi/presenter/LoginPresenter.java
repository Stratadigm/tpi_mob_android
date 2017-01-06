package com.biz.stratadigm.tpi.presenter;

import android.content.Context;

import com.biz.stratadigm.tpi.interactor.LoginInteractor;
import com.biz.stratadigm.tpi.manager.AppSchedulers;
import com.biz.stratadigm.tpi.ui.view.LoginView;

import java.net.HttpURLConnection;

public class LoginPresenter extends BasePresenter<LoginView> {
    private final LoginInteractor loginInteractor;

    public LoginPresenter(Context applicationContext, AppSchedulers appSchedulers, LoginInteractor loginInteractor) {
        super(applicationContext, appSchedulers);
        this.loginInteractor = loginInteractor;
    }

    public void onLoginButtonClicked() {
        String username = getView().getUsername();
        String password = getView().getPassword();
        executeRequest(loginInteractor.login(username, password), new LoginSubscriber());
    }

    private class LoginSubscriber extends SimpleSubscriber<Void> {

        @Override
        public void onNext(Void aVoid) {
            getView().showMainScreen();
        }

        @Override
        public void onError(Throwable e) {
            super.onError(e);
            if (isNetworkError(e)) {
                getView().showNetworkError();
            } else if (isHttpErrorWithCode(e, HttpURLConnection.HTTP_UNAUTHORIZED)) {
                getView().showAuthError();
            } else {
                getView().showUnexpectedError();
            }
        }
    }
}
