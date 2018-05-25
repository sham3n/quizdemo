package com.sammy.test.quizdemo.ui.home;


import com.sammy.test.quizdemo.data.AppDataManager;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;

/**
 * Created by sng
 */

public class HomeInteractor implements HomeContract.HomeInteractor{
    @Override
    public void getData(@NotNull HomeContract.HomePresenter homePresenter) {
        Observable<Map<String, List<String>>> observable = AppDataManager.getInstance().getQuestions();
        observable.subscribe(homePresenter::onDownloadDataSuccess, homePresenter::onDownloadDataError);
        //Note: Can do any data manipulation here for the data
    }
}
