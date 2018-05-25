package com.sammy.test.quizdemo.data.network;


import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * This is to access api services
 */

public interface ApiServices {
    @GET("questions.json")
    Observable<Map<String, List<String>>> getQuestions();
}
