package com.olgusha.flashchatnewfirebase;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;



public class RegisterActivity extends CommonActivity {

    // Constants
    public static final String CHAT_PREFS = "ChatPrefs";
    public static final String DISPLAY_NAME_KEY = "username";

    CommonActivity common = new CommonActivity();

    // TODO: Add member variables here:
    // UI references.
    private AutoCompleteTextView mEmailView;
    private AutoCompleteTextView mUsernameView;
    private EditText mPasswordView;
    private EditText mConfirmPasswordView;

    // Firebase instance variables
    private FirebaseAuth myAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mEmailView = (AutoCompleteTextView) findViewById(R.id.register_email);
        mPasswordView = (EditText) findViewById(R.id.register_password);
        mConfirmPasswordView = (EditText) findViewById(R.id.register_confirm_password);
        mUsernameView = (AutoCompleteTextView) findViewById(R.id.register_username);

        // Keyboard sign in action
        mConfirmPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.integer.register_form_finished || id == EditorInfo.IME_NULL) {
                    attemptRegistration();
                    return true;
                }
                return false;
            }
        });

        // TODO: Get hold of an instance of FirebaseAuth
        // public abstract class FirebaseAuth ,public static FirebaseAuth  /na verhu est import 

        myAuth = FirebaseAuth.getInstance();


    }

    // Executed when Sign Up button is pressed.
    public void signUp(View v) {
//        SmsManager smsManager = SmsManager.getDefault();
//        smsManager.sendTextMessage("0556624669", null, "HelloOlga", null, null);
          attemptRegistration();
    }

    private void attemptRegistration() {

        // Reset errors displayed in the form.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(password) || !isPasswordValid(password)) { ///srazu vizivaet tut funktsiu
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {  //srazu vizivaet tut funktsiu
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // TODO: Call create FirebaseUser() here

           createFirebaseUser();

        }
    }

    private boolean isEmailValid(String email) {
        // You can add more checking logic here.
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Add own logic to check for a valid password (minimum 6 characters)
        String confirmPassword =mConfirmPasswordView.getText().toString();

        return confirmPassword.equals(password)&&password.length()>10;
    }

    // TODO: Create a Firebase user

     private  void createFirebaseUser()
     {
//         Intent intent=new Intent(getApplicationContext(),RegisterActivity.class);
//         PendingIntent pi=PendingIntent.getActivity(getApplicationContext(), 0, intent,0);
//         SmsManager sms= SmsManager.getDefault();
//         sms.sendTextMessage("0587914280", null, "hiii", pi,null);


         String email = mEmailView.getText().toString();
         String password = mPasswordView.getText().toString();

         //The difference is, in addOnCompleteListener(OnCompleteListener listener) the listener will be called on
         // main thread and in addOnCompleteListener(Executor executor, OnCompleteListener listener),
         // the Executor determines the thread that will be used to invoke the listener.

         myAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
             @Override
             public void onComplete(@NonNull Task<AuthResult> task) {
                 Log.d("CreateUser", "Complited " +task.isSuccessful());
                 if(!task.isSuccessful())
                 {
                     Log.d("CreateUser","UserCreation Faild");
                     common.showErrorDialog("Registration faild");

                 }
                 else {
                     saveDiplayName();
                     common.showErrorDialog("Registration successful ");
                     Intent intent =  new Intent(RegisterActivity.this ,LoginActivity.class);
                     finish(); /// vizivaetsya iz public class Activity
                     startActivity(intent);
                 }
             }
         });
     }


    // TODO: Save the display name to Shared Preferences

    private  void saveDiplayName()
    {
        //MODE_PRIVATE: File creation mode: the default mode, where the created file
        // can only be accessed by the calling application (or all applications sharing the same user ID).
        //
        //MODE_WORLD_READABLE: File creation mode: allow all other applications to have read access to the created file.
        //
        //MODE_WORLD_WRITEABLE : File creation mode: allow all other applications to have write access to the created file.
        //Edit As of API 17, the MODE_WORLD_READABLE and MODE_WORLD_WRITEABLE are deprecated:
        //
        //This constant was deprecated in API level 17.
        //Creating world-readable files is very dangerous,
        // and likely to cause security holes in applications.
        // It is strongly discouraged; instead, applications should use
        // more formal mechanism for interactions such as ContentProvider,
        // BroadcastReceiver, and Service. There are no guarantees that this
        // access mode will remain on a file, such as when it goes
        // through a backup and restore.


    String displayName  = mUsernameView.getText().toString(); // vzyali i sohranili na devise !!!
        SharedPreferences pref = getSharedPreferences(CHAT_PREFS,MODE_PRIVATE);
        pref.edit().putString(DISPLAY_NAME_KEY,displayName).apply();
    }





}
