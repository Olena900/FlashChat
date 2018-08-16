package com.olgusha.flashchatnewfirebase;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class MainChatActivity extends CommonActivity {

    // TODO: Add member variables here:
    private String mDisplayName;
    private ListView mChatListView;
    private EditText mInputText;
    private ImageButton mSendButton;
    private DatabaseReference mDatabaseReference; //com.google.firebase.database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_chat);

        // TODO: Set up the display name and get the Firebase reference
        setupDisplayName();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference();//return new DatabaseReference(this.zzbHo, zzafa.zzRq());
        //getReference ->>> Ref for database root node

        // Link the Views in the layout to the Java code
        mInputText = (EditText) findViewById(R.id.messageInput);
        mSendButton = (ImageButton) findViewById(R.id.sendButton);
        mChatListView = (ListView) findViewById(R.id.chat_list_view);

        // TODO: Send the message when the "enter" button is pressed on soft kyebord
        //cпециальный прослушиватель, вызываемый при выполнении действия в EditText), он работает как для DONE, так и для RETURN:
        mInputText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                           // Log.d("onEditorAction" , "Passed");
                        sendMessage();
                        return true;
                    }
                    return false;
                }
            });

        //http://qaru.site/questions/59232/android-use-done-button-on-keyboard-to-click-button

//        mInputText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
//                sendMessage();
//                return true;
//            }
      //  });


        // TODO: Add an OnClickListener to the sendButton to send a message
        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage();
            }
        });

    }

    // TODO: Retrieve the display name from the Shared Preferences

    private  void setupDisplayName()
    {
        SharedPreferences pref = getSharedPreferences(RegisterActivity.CHAT_PREFS,MODE_PRIVATE);

        mDisplayName = pref.getString(RegisterActivity.DISPLAY_NAME_KEY, "");

        if (mDisplayName==null) mDisplayName="Anonimus";

    }


    private void sendMessage() {

        // TODO: Grab the text the user typed in and push the message to Firebase
      //  setupDisplayName();
        Log.d("sendMessagefunc" , " Satrted");
        String input = mInputText.getText().toString();
        if(!input.equals(""))
        {
            InstantMessage chat = new InstantMessage(input,mDisplayName);
            mDatabaseReference.child("messages").push().setValue(chat);
            mInputText.setText("");
        }

    }

    // TODO: Override the onStart() lifecycle method. Setup the adapter here.


    @Override
    public void onStop() {
        super.onStop();

        // TODO: Remove the Firebase event listener on the adapter.

    }

}
