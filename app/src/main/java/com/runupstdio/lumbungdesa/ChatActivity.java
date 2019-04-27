package com.runupstdio.lumbungdesa;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
import com.makeramen.roundedimageview.RoundedImageView;
import com.onesignal.OSNotification;
import com.onesignal.OneSignal;
import com.runupstdio.lumbungdesa.Adapter.ChatAdapter;
import com.runupstdio.lumbungdesa.Adapter.UserChatListAdapter;
import com.runupstdio.lumbungdesa.Api.ApiClient;
import com.runupstdio.lumbungdesa.Api.IApiClient;
import com.runupstdio.lumbungdesa.Model.Chat;
import com.runupstdio.lumbungdesa.Model.Chatting;
import com.runupstdio.lumbungdesa.Model.Conversation;
import com.runupstdio.lumbungdesa.Model.Done;
import com.runupstdio.lumbungdesa.Model.SendChat;
import com.runupstdio.lumbungdesa.Model.User;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatActivity extends AppCompatActivity implements OneSignal.NotificationReceivedHandler {

    ImageButton mBtnSend_Chat;
    EditText mText_Chat;

    ChatAdapter chatAdapter;
    List<Chat> mChat;
    RecyclerView recyclerViewChat;

    Intent intent;

    private int conversationId;
    private String destinationId = null;
    private String destinationName = null;
    private String destinationAva = null;

    private FirebaseAuth mAuth;
    IApiClient mApiClient;
    private String idToken = null;
    private String idUser = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        mAuth = FirebaseAuth.getInstance();
        mApiClient = ApiClient.getClient().create(IApiClient.class);
        mChat = new ArrayList<>();

        RoundedImageView imgProfile = findViewById(R.id.img_User);
        TextView username = findViewById(R.id.nama_User);

        recyclerViewChat = findViewById(R.id.recycler_Chat);
        recyclerViewChat.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        recyclerViewChat.setLayoutManager(linearLayoutManager);

        intent = getIntent();
        conversationId = intent.getIntExtra("conversationId", 0);
        destinationId = intent.getStringExtra("destinationId");
        destinationName = intent.getStringExtra("destinationName");
        destinationAva = intent.getStringExtra("destinationAva");

        Glide.with(getApplicationContext())
                .load(destinationAva)
                .into(imgProfile);
        username.setText(destinationName);

        if(FirebaseAuth.getInstance().getCurrentUser() != null) {
            FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();
            mUser.getIdToken(true)
                    .addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
                        public void onComplete(@NonNull Task<GetTokenResult> task) {
                            if (task.isSuccessful()) {
                                idToken = task.getResult().getToken();
                                if(conversationId != 0) setFeedData();
                            } else {
                                // Handle error -> task.getException();
                            }
                        }
                    });
            idUser = mAuth.getUid();
        } else {
            Intent a = new Intent(ChatActivity.this, NavigationBar.class);
            startActivity(a);
            Toast.makeText(ChatActivity.this, "Silahkan masuk terlebih dahulu", Toast.LENGTH_LONG).show();

        }

        mText_Chat = findViewById(R.id.text_Chat);
        mBtnSend_Chat = findViewById(R.id.btn_Chat_Send);

        mBtnSend_Chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = mText_Chat.getText().toString();
                if (!message.equals("")){
                    sendMessage(mText_Chat.getText().toString());
                }
                else{
                }
                mText_Chat.setText("");
            }
        });
    }

    private void setFeedData(){
        Observable<Chatting> london = mApiClient.get_conversation_chat("Bearer "+idToken, conversationId);

        london.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(feedInfo -> {
                    if(feedInfo.getStatus()){
                        for(int i=0; i<feedInfo.getData().size(); i++){
                            mChat.add(new Chat(destinationAva, feedInfo.getData().get(i).getMessage(), feedInfo.getData().get(i).getSenderId(), idUser));
                        }
                        initRecyclerView();
                        chatAdapter.notifyDataSetChanged();
                        recyclerViewChat.smoothScrollToPosition(mChat.size()-1);
                    } else {

                    }
                });
    }

    private void sendMessage(String msg){
        Call<SendChat> addrCall = mApiClient.send_chat("Bearer "+idToken, destinationId, msg);
        addrCall.enqueue(new Callback<SendChat>() {
            @Override
            public void onResponse(Call<SendChat> call, Response<SendChat> response) {
                if (response.isSuccessful()) {
                    if (response.body().getStatus()) {
                        conversationId = response.body().getData().getConversationId();
                        mChat.add(new Chat(destinationAva, response.body().getData().getMessage(), response.body().getData().getSenderId(), idUser));
                        chatAdapter.notifyDataSetChanged();
                        recyclerViewChat.smoothScrollToPosition(mChat.size()-1);
                    } else {
                        Toast.makeText(ChatActivity.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
                    }
                } else if (response.errorBody() != null) {
                    // Get response errorBody
                    String errorBody = response.errorBody().toString();
                    Toast.makeText(ChatActivity.this, errorBody, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<SendChat> call, Throwable t) {
                Toast.makeText(ChatActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void initRecyclerView(){
        chatAdapter = new ChatAdapter(getApplicationContext(), mChat, destinationAva);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ChatActivity.this);
        recyclerViewChat.setLayoutManager(layoutManager);
        recyclerViewChat.setAdapter(chatAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mChat != null) {
            mChat.clear();
            setFeedData();
        }
    }

    @Override
    public void notificationReceived(OSNotification notification) {
        JSONObject data = notification.payload.additionalData;
        Log.i("OneSignalExample", "customkey set with value: " + data);
    }
}
