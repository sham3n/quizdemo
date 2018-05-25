package com.sammy.test.quizdemo.data;

import com.sammy.test.quizdemo.data.local.MockQuestionsClient;
import com.sammy.test.quizdemo.data.network.ApiClient;
import com.sammy.test.quizdemo.data.network.ApiServices;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by sng
 * THis is the datamanager that handles how to retrieve the data
 * Cache, api, db, assets, etc
 */

public class AppDataManager {
    private static AppDataManager mInstance;
    private ApiServices mApiServices;

    private AppDataManager() {
        mApiServices = ApiClient.getClient(new MockQuestionsClient()).create(ApiServices.class);;
    }

    //TODO Use Dependecy Injection instead
    public static AppDataManager getInstance() {
        if(mInstance == null) {
            mInstance = new AppDataManager();
        }

        return mInstance;
    }

    public Observable<Map<String, List<String>>> getQuestions() {
        Observable<Map<String, List<String>>> observer = mApiServices.getQuestions();
        return observer.subscribeOn(Schedulers.newThread()).
                observeOn(AndroidSchedulers.mainThread());
    }
}
