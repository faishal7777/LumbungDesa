package com.runupstdio.lumbungdesa;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
import com.runupstdio.lumbungdesa.Adapter.UserChatListAdapter;
import com.runupstdio.lumbungdesa.Api.ApiClient;
import com.runupstdio.lumbungdesa.Api.IApiClient;
import com.runupstdio.lumbungdesa.Model.Conversation;
import com.runupstdio.lumbungdesa.Model.History;
import com.runupstdio.lumbungdesa.Model.Tagihan;
import com.runupstdio.lumbungdesa.Model.User;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ListChatActivity extends AppCompatActivity {

    RecyclerView mRVListChat;
    UserChatListAdapter adapter;
    List<User> userArrayList;

    private FirebaseAuth mAuth;
    IApiClient mApiClient;
    private String idToken = null;
    private String idUser = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_chat);

        mAuth = FirebaseAuth.getInstance();
        mApiClient = ApiClient.getClient().create(IApiClient.class);

        mRVListChat = findViewById(R.id.recycle_View_List_Chat);

        if(FirebaseAuth.getInstance().getCurrentUser() != null) {
            FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();
            mUser.getIdToken(true)
                    .addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
                        public void onComplete(@NonNull Task<GetTokenResult> task) {
                            if (task.isSuccessful()) {
                                idToken = task.getResult().getToken();
                                setFeedData();
                            } else {
                                // Handle error -> task.getException();
                            }
                        }
                    });
            idUser = mUser.getUid();
        } else {
            Intent a = new Intent(ListChatActivity.this, NavigationBar.class);
            startActivity(a);
            Toast.makeText(ListChatActivity.this, "Silahkan masuk terlebih dahulu", Toast.LENGTH_LONG).show();

        }
    }

    private void setFeedData(){
        Log.d("CHAT", "I'm heres"+idToken);
        userArrayList = new ArrayList<>();
        Observable<Conversation> london = mApiClient.get_conversation("Bearer "+idToken);

        london.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(feedInfo -> {
                    if(feedInfo.getStatus()){
                        for(int i=0; i<feedInfo.getData().size(); i++){
                            Log.d("CHAT", ""+feedInfo.getMessage()+feedInfo.getData().get(i).getDestinationName());
                            String temp = (feedInfo.getData().get(i).getBuyerId().equals(idUser)) ? feedInfo.getData().get(i).getBuyerId() : feedInfo.getData().get(i).getSellerId();
                            userArrayList.add(new User(feedInfo.getData().get(i).getId(), feedInfo.getData().get(i).getDestinationName(), feedInfo.getData().get(i).getDestinationAva(), temp));
                        }
                        initRecyclerView();
                    } else {
                        Log.d("CHAT", ""+feedInfo.getMessage());
                    }
                });
    }

    private void initRecyclerView(){
        adapter = new UserChatListAdapter(userArrayList, getApplicationContext());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ListChatActivity.this);
        mRVListChat.setLayoutManager(layoutManager);
        mRVListChat.setAdapter(adapter);
    }

}
