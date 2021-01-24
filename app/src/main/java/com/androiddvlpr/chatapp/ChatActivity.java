package com.androiddvlpr.chatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.Objects;

public class ChatActivity extends AppCompatActivity {

    ImageView sendImage;
    EditText userMessage;
    FirebaseAuth mAuth;
    DatabaseReference mChatData, mUserData;
    String username = "null";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        mAuth = FirebaseAuth.getInstance();

        sendImage = findViewById(R.id.send_image);
        userMessage = findViewById(R.id.user_message);

        mChatData = FirebaseDatabase.getInstance().getReference().child("chats");
        mUserData = FirebaseDatabase.getInstance().getReference().child(Objects.requireNonNull(mAuth.getUid()));

        displayChatMessage();

        mUserData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                username = Objects.requireNonNull(dataSnapshot.child("username").getValue()).toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        sendImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(userMessage.getText() != null){
                    if(!userMessage.getText().toString().equals("") && username != null){
                        mChatData.push().setValue(new Model_Chat(userMessage.getText().toString(),username,System.currentTimeMillis()));
                    }
                }
            }
        });

    }

    private void displayChatMessage() {

        ListView listOfMessage = findViewById(R.id.list_message);
        FirebaseListAdapter<Model_Chat> adapter = new FirebaseListAdapter<Model_Chat>(this, Model_Chat.class, R.layout.list_msg, FirebaseDatabase.getInstance().getReference().child("chats").limitToLast(100)) {
            @Override
            protected void populateView(View v, Model_Chat model, int position) {

                TextView messageText, messageUser, messageTime;
                messageText = v.findViewById(R.id.message_text);
                messageUser = v.findViewById(R.id.message_user);
                messageTime = v.findViewById(R.id.message_time);

                messageText.setText(model.getMessage());
                messageUser.setText(model.getName());
                messageTime.setText(DateFormat.format("dd-MM-yyyy (HH:mm:ss)", model.getTimestamp()));

            }
        };
        listOfMessage.setAdapter(adapter);
    }


}
