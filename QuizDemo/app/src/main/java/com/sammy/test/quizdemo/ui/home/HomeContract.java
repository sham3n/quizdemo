package com.sammy.test.quizdemo.ui.home;

import com.sammy.test.quizdemo.ui.BasePresenter;
import com.sammy.test.quizdemo.ui.BaseView;

import java.util.List;
import java.util.Map;


/**
 * Created by sng
 * This is the MVP contract
 * This is a centralized location for contract for the Home HomeView, HomePresenter, and HomeInteractor
 */
public interface HomeContract {

    //These are the actions that wants to be done on the view
     interface HomeView extends BaseView<HomePresenter>{
        void showProgress();
        void hideProgress();
        void bindDataOnView(Map<String, List<String>> response);
        void showErrorMessage(Throwable error);
    }

    //This is the middle-man between the model and view.
    interface HomePresenter extends BasePresenter
    {
        void onDownloadDataSuccess(Map<String, List<String>> responseQuestions);
        void onDownloadDataError(Throwable throwable);
    }

    //This is the model, in the case of the Clean Architecture, it's the interactor that retrieves data
    interface HomeInteractor {
        void getData(HomePresenter presenter);
    }
}
