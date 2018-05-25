package com.sammy.test.quizdemo.data.local;

import com.sammy.test.quizdemo.utils.AssetUtils;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by sng on 4/9/18.
 * This mock client intercepts the call and returns the json from the assets directory.
 * This is useful if the backend server isn't ready yet and need to use local test data temporarily
 */

public class MockQuestionsClient implements Interceptor {
    private static final String QUESTIONS_PATH = "zquestions.json";

    public MockQuestionsClient() {
    }

    @Override
    public Response intercept(Chain chain) throws IOException {

        HttpUrl url = chain.request().url();
        //here determine what to do base on url.
        //e.g.:
        switch (url.encodedPath()) {
            case "/questions.json":
                //get data locally
                String response = AssetUtils.getStringFromAsset(QUESTIONS_PATH);
                return new Response.Builder()
                        .code(200)
                        .message(response)
                        .request(chain.request())
                        .protocol(Protocol.HTTP_1_1)
                        .body(ResponseBody.create(MediaType.parse("application/json"), response.getBytes()))
                        .addHeader("content-type", "application/json")
                        .build();
        }

        //note: should be updated with more features
        return null;
    }
}