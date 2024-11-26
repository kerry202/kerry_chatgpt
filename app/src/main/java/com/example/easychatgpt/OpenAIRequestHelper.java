package com.example.easychatgpt;


import android.util.Log;

import androidx.annotation.NonNull;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OpenAIRequestHelper {
    private static final String TAG = OpenAIRequestHelper.class.getSimpleName();
    private static final MediaType JSON = MediaType.parse("application/json");
    private static final String OPENAI_API_KEY = "c069828a655e44cb9f84ea87fe9aae5d";

    private static final String OPENAI_API_BASE = "https://g35-openai-china.openai.azure.com/";
    public static final String OPENAI_ROLE_SYSTEM = "system";
    public static final String OPENAI_ROLE_USER = "user";
    public static final String OPENAI_ROLE_ASSISTANT = "assistant";
    private static final String DEPLOYMENT_NAME = "gpt-35-turbo3-southcentralus";
    private static final String OPENAI_URL = OPENAI_API_BASE
            + "openai/deployments/"
            + DEPLOYMENT_NAME
            + "/chat/completions?api-version=2023-03-15-preview";
    private final Gson mGson = new Gson();


    private final OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build();

    public void sendChatMsg(OpenAIReqBean reqBean, OpenAIRequestCallback callback) {
        String json = new Gson().toJson(reqBean);
        Log.d(TAG, "sendChatMsg:" + json);

        RequestBody requestBody = RequestBody.create(JSON, json);
        Request request = new Request.Builder().url(OPENAI_URL)
                .addHeader("Content-type", "application/json")
                .addHeader("api-key", OPENAI_API_KEY)
                .post(requestBody).build();

        Log.d(TAG, "request:" + request.url());
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.d(TAG, "onFailure:" + e.getMessage());
                callback.onError("出错啦! 在试一次～");

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.body() != null) {
                    try {
                        String result = response.body().string();
                        OpenAIResponseBean res = mGson.fromJson(result, OpenAIResponseBean.class);
                        Log.d(TAG, "onResponse:" + result);
                        callback.onSuccess(res);
                    } catch (Exception e) {
                        callback.onError("出错啦! 在试一次～");
                    }
                } else {
                    callback.onError("出错啦! 在试一次～");
                }
            }
        });
    }


    public interface OpenAIRequestCallback {
        void onSuccess(OpenAIResponseBean res);

        void onError(String errMsg);
    }
}
