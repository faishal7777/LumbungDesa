package com.runupstdio.lumbungdesa;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class ChatActivity extends AppCompatActivity {

    ImageButton mBtnSend_Chat;
    EditText mText_Chat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

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
}
