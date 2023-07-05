package com.example.easychatgpt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements AdapterCallback {
    private static final String TAG = MainActivity.class.getName();
    RecyclerView recyclerView;
    TextView welcomeTextView;
    EditText messageEditText;
    ImageButton sendButton;
    List<Message> messageList;
    MessageAdapter messageAdapter;
    public static final MediaType JSON
            = MediaType.get("application/json; charset=utf-8");
    OkHttpClient client = new OkHttpClient();
    private EditText keyEdit;
    private ClipboardManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        messageList = new ArrayList<>();
        keyEdit = findViewById(R.id.key_edit);
        manager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);

        recyclerView = findViewById(R.id.recycler_view);
        welcomeTextView = findViewById(R.id.welcome_text);
        messageEditText = findViewById(R.id.message_edit_text);
        sendButton = findViewById(R.id.send_btn);

        //setup recycler view
        messageAdapter = new MessageAdapter(messageList, this);
        recyclerView.setAdapter(messageAdapter);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setStackFromEnd(true);
        recyclerView.setLayoutManager(llm);

        sendButton.setOnClickListener((v) -> {
            String question = messageEditText.getText().toString().trim();
            addToChat(question, Message.SENT_BY_ME);
            messageEditText.setText("");
            if (!TextUtils.isEmpty(keyEdit.getText().toString()) && Integer.parseInt(keyEdit.getText().toString()) == 1) {
                callAPI(question);
            } else {
                callAPI2(question);
            }
            welcomeTextView.setVisibility(View.GONE);
        });
    }

    void addToChat(String message, String sentBy) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                messageList.add(new Message(message, sentBy));
                messageAdapter.notifyDataSetChanged();
                recyclerView.smoothScrollToPosition(messageAdapter.getItemCount());
            }
        });
    }

    void addResponse(String response) {
        messageList.remove(messageList.size() - 1);
        addToChat(response, Message.SENT_BY_BOT);
    }

    void callAPI(String question) {
        //okhttp
        messageList.add(new Message("搜索... ", Message.SENT_BY_BOT));

        ChatInfo chatInfo = new ChatInfo();
        chatInfo.messages.add(new ChatInfo.MessagesBean(question));
        String infoJson = new Gson().toJson(chatInfo);

        Log.d(TAG, " jsonBody=" + infoJson);

        RequestBody body = RequestBody.create(infoJson, JSON);
        Request request = new Request.Builder()
                .url("https://api.openai.com/v1/chat/completions")
                .addHeader("content-type", "application/json")
                .header("Authorization", "Bearer " + keyEdit.getText().toString())
                .post(body)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                addResponse("出错啦! 在试一次～ " + e.getMessage());
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                if (response.isSuccessful()) {
                    Log.d(TAG, " response isSuccessful =");
                    String data = response.body().string();
                    Log.d(TAG, " response isSuccessful =" + data);
                    ResponseInfo responseInfo = new Gson().fromJson(data, ResponseInfo.class);
                    addResponse(responseInfo.choices.get(0).message.content);
                } else {
                    addResponse("出错啦! 在试一次～ " + response.body().string());
                }
            }
        });
    }

    void callAPI2(String question) {
        messageList.add(new Message("搜索... ", Message.SENT_BY_BOT));

        OpenAIMsgBean reqMsg = new OpenAIMsgBean(OpenAIRequestHelper.OPENAI_ROLE_USER, question);
        OpenAIReqBean openAIReqBean = new OpenAIReqBean();
        openAIReqBean.messages.add(reqMsg);
        new OpenAIRequestHelper().sendChatMsg(openAIReqBean, new OpenAIRequestHelper.OpenAIRequestCallback() {
            @Override
            public void onSuccess(OpenAIResponseBean res) {
                try {
                    addResponse(res.choices.get(0).message.content);
                } catch (Exception e) {
                    addResponse("出错啦! 在试一次～");
                }
            }

            @Override
            public void onError(String errMsg) {
                addResponse(errMsg);
            }
        });

    }

    @Override
    public void itemLongEvent(String data) {
        ClipData mClipData = ClipData.newPlainText("Label", data);
        manager.setPrimaryClip(mClipData);
        Toast.makeText(this, "复制成功", Toast.LENGTH_SHORT).show();
    }
}




















