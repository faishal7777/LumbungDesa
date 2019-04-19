package com.runupstdio.lumbungdesa;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.runupstdio.lumbungdesa.Adapter.UserChatListAdapter;
import com.runupstdio.lumbungdesa.Model.User;

import java.util.ArrayList;
import java.util.List;

public class ListChatActivity extends AppCompatActivity {

    RecyclerView mRVListChat;
    UserChatListAdapter adapter;
    List<User> userArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_chat);

        userArrayList = new ArrayList<>();
        userArrayList.add(new User("Dimas Maulana", "https://picsum.photos/50/?random"));
        userArrayList.add(new User("Fadly Yonk", "https://picsum.photos/50/?random"));
        userArrayList.add(new User("Ariyandi Nugraha", "https://picsum.photos/50/?random"));
        userArrayList.add(new User("Aham Siswana", "https://picsum.photos/50/?random"));

        mRVListChat = findViewById(R.id.recycle_View_List_Chat);

        adapter = new UserChatListAdapter(userArrayList, getApplicationContext());

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ListChatActivity.this);

        mRVListChat.setLayoutManager(layoutManager);

        mRVListChat.setAdapter(adapter);
    }
/*
    void addData(){
        userArrayList = new ArrayList<>();
        userArrayList.add(new User("Dimas Maulana", ""));
        userArrayList.add(new User("Fadly Yonk", ""));
        userArrayList.add(new User("Ariyandi Nugraha", ""));
        userArrayList.add(new User("Aham Siswana", ""));
    }*/
}
