package com.example.hptouchsmart.chatapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MessageActivity extends AppCompatActivity {

    Button sendButton;
    EditText message;
    ListView listView;
    TextView msgTextView;
    private String username;
    private String chatRoomName;
    private String temp_key;

    public static final String NAME = "name";
    public static final String MSG = "message";
    private String chat_msg;
    private String chat_user_name;
    ArrayList<String> initialArray = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        sendButton = (Button) findViewById(R.id.chat_button);
        message = (EditText) findViewById(R.id.chat_msg_tv);
        msgTextView = (TextView) findViewById(R.id.message_TV);




        final ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(this , android.R.layout.simple_list_item_1 , initialArray);



        username = getIntent().getExtras().get(MainActivity.USER_NAME).toString();
        chatRoomName = getIntent().getExtras().get(MainActivity.ROOM_NAME).toString();

        setTitle(chatRoomName + "Chat Room");

        final DatabaseReference root = FirebaseDatabase.getInstance().getReference().child(chatRoomName);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String , Object> map = new HashMap<String, Object>();
                temp_key = root.push().getKey();
                root.updateChildren(map);
                DatabaseReference msgRoot = root.child(temp_key);
                Map<String , Object> map2 = new HashMap<String, Object>();
                map2.put(NAME , username);
                map2.put(MSG , message.getText().toString());

                msgRoot.updateChildren(map2);

            }
        });

        root.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//                ArrayList<String> finalmsg = append_chat(dataSnapshot);
//                initialArray.clear();
//                initialArray.addAll(finalmsg);
//
//                stringArrayAdapter.notifyDataSetChanged();

                append_chat(dataSnapshot);

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//                ArrayList<String> finalmsg = append_chat(dataSnapshot);
//                initialArray.clear();
//                initialArray.addAll(finalmsg);
//
//                stringArrayAdapter.notifyDataSetChanged();

                append_chat(dataSnapshot);

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void append_chat(DataSnapshot dataSnapshot) {

        ArrayList<String> msg = new ArrayList<>();
        Iterator i = dataSnapshot.getChildren().iterator();

        while(i.hasNext()){
            chat_msg = (String)((DataSnapshot)i.next()).getValue();
            chat_user_name = (String) ((DataSnapshot)i.next()).getValue();

            msgTextView.append(chat_user_name + " : " + chat_msg + "\n");

            Log.d("TAG" ,"chat msg :" + chat_msg);
            Log.d("TAG" ,"chat username:" + chat_user_name);


        }
    }

}
