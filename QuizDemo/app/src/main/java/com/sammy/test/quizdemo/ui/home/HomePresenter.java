package com.sammy.test.quizdemo.ui.home;

import com.sammy.test.quizdemo.ui.home.HomeContract.HomeInteractor;
import com.sammy.test.quizdemo.ui.home.HomeContract.HomeView;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;

/**
 * Created by sng
 */

public class HomePresenter implements HomeContract.HomePresenter {
    HomeView mHomeView;
    HomeInteractor homeInteractor;

    public HomePresenter(@NotNull HomeView homeView, @NotNull HomeInteractor homeInteractor) {
        this.mHomeView = homeView;
        this.homeInteractor = homeInteractor;
        mHomeView.setPresenter(this);
    }

    @Override
    public void start() {
        mHomeView.showProgress();
        homeInteractor.getData(this);
    }

    @Override
    public void onDownloadDataSuccess(Map<String, List<String>> response) {
        if(mHomeView != null) {
            mHomeView.hideProgress();
            mHomeView.bindDataOnView(response);
        }
    }

    @Override
    public void onDownloadDataError(Throwable throwable) {
        if(mHomeView != null) {
            mHomeView.hideProgress();
            mHomeView.showErrorMessage(throwable);
        }
    }
}
