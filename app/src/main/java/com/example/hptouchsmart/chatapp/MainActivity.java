package com.example.hptouchsmart.chatapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private FirebaseAnalytics firebaseAnalytics;

    Button send;
    ListView listView;
    EditText editText;
    ArrayList<String> messageList = new ArrayList<>();
    private String name;
    public static final String TAG = "TAG";
    public static final String ROOM_NAME = "room_name";
    public static final String USER_NAME = "user_name";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.listView);
        send = (Button) findViewById(R.id.button_Send);
        editText = (EditText) findViewById(R.id.message_text);
        final DatabaseReference root = FirebaseDatabase.getInstance().getReference().getRoot();


        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, messageList);

        listView.setAdapter(arrayAdapter);

        request_user();

        Bundle bundle = new Bundle();

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String , Object> map = new HashMap<String, Object>();
                map.put(editText.getText().toString() , "");
                root.updateChildren(map);
                editText.setText("");

            }
        });

        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d(TAG, "onDataChangeCalled");

                ArrayList<String> chatrooms = new ArrayList<String>();

                Iterator i = dataSnapshot.getChildren().iterator();

                while(i.hasNext()){
                    chatrooms.add(((DataSnapshot)i.next()).getKey());
                }

                messageList.clear();
                messageList.addAll(chatrooms);

                arrayAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getApplicationContext() , MessageActivity.class);
                i.putExtra(ROOM_NAME , ((TextView)view).getText().toString());
                i.putExtra(USER_NAME , name);
                startActivity(i);
            }
        });

    }

    private void request_user() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Enter Name :");

        final EditText input_field = new EditText(this);

        builder.setView(input_field);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                 name = input_field.getText().toString();
            }
        });

        builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(MainActivity.this, "cannot proceed without username", Toast.LENGTH_SHORT).show();
                request_user();
            }
        });

        builder.show();


    }

}