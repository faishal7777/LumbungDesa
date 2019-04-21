package com.runupstdio.lumbungdesa;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuthException;
import com.makeramen.roundedimageview.RoundedImageView;
import com.runupstdio.lumbungdesa.Adapter.ChatAdapter;
import com.runupstdio.lumbungdesa.Model.Chat;
import com.runupstdio.lumbungdesa.Model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    ImageButton mBtnSend_Chat;
    EditText mText_Chat;

    ChatAdapter chatAdapter;
    List<Chat> mChat;
    RecyclerView recyclerViewChat;

    Intent intent;

    RoundedImageView imgProfile = findViewById(R.id.img_User);
    TextView username = findViewById(R.id.nama_User);

//    FirebaseUser Fuser;
//    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        recyclerViewChat = findViewById(R.id.recycler_Chat);
        recyclerViewChat.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        recyclerViewChat.setLayoutManager(linearLayoutManager);

        intent = getIntent();
        final String userId = intent.getStringExtra("userId");

//        fuser = FirebaseAuth.getInstance().getCurrentUser;
//        reference = FirebaseDatabase.getInstance.getReference("Users").child(userId);

//        reference.addValueEventListener(new ValueEventListener(){
//            @Override
//            public void onDataChange(@NonNull DataSnapshot datasnapshot){
//                User user = dataSnapshot.getValue(User.class);
//                username.setText(user.getUsername());
//                if (user.getImageUrl().equals("default")){
//                    holder.imgProfile.setImageResource(R.mipmap.ic_launcher);
//                }
//                else{
//                    Glide.with(ChatActivity.this)
//                            .load(user.getImageUrl())
//                            .into(imgProfile);
//                }
//
//                readMessage(fuser.getUid(), userId, user.getImageUrl());
//
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError){
//
//            }
//        });

        mText_Chat = findViewById(R.id.text_Chat);
        mBtnSend_Chat = findViewById(R.id.btn_Chat_Send);

        mBtnSend_Chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = mText_Chat.getText().toString();
                if (!message.equals("")){

                }
                else{
                    Toast.makeText(ChatActivity.this, "Tidak bisa mengirim pesan kosong.", Toast.LENGTH_SHORT).show();
                }
                mText_Chat.setText("");
            }
        });
    }

    private void sendMessage(String sender, String receiver, String message){
//      DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("sender", sender);
        hashMap.put("receiver", receiver);
        hashMap.put("message", message);

//      reference.child("Chats").push().setValue(hashMap);
    }

    private void readMessage(final String myId, final String userId, final String imgUrl){
        mChat = new ArrayList<>();

//        reference = FirebaseDatabase.getInstance().getReference("Chats");
//        reference.addValueEventListener(new ValueEventListener(){
//            @Override
//            public void onDataChange(@NonNull DataSnapshot datasnapshot){
//                mChat.clear();
//                for (DataSnapshot snapshot : datasnapshot.getChildren()){
//                    Chat chat = snapshot.getValue(Chat.class);
//                    if (chat.getReceiver().equals(userId)) && chat.getSender().equals(myId){
//                        mChat.add(chat);
//                    }
//
//                    chatAdapter = new ChatAdapter(ChatActivity.this, imgUrl);
//                    recyclerViewChat.setAdapter(chatAdapter);
//                }
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError){
//
//            }
//        });
    }
}
