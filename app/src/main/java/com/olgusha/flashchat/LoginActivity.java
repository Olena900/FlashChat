package com.olgusha.flashchatnewfirebase;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class LoginActivity extends CommonActivity {

    // TODO: Add member variables here:
    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private FirebaseAuth myAuth;
    CommonActivity common = new CommonActivity();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        mEmailView = (AutoCompleteTextView) findViewById(R.id.login_email);
        mPasswordView = (EditText) findViewById(R.id.login_password);

        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int  id, KeyEvent keyEvent) {
                if (id == R.integer.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true; // do kontsa ne ponyatno kto slushaet i pochemu esli false idet dalzhe
                    
                    // esli budem najimat enter
                    //if(event.getAction() == KeyEvent.ACTION_DOWN &&event.getKeyCode() == KeyEvent.KEYCODE_ENTER)
                    //                {
                    //
                    //                }
                    //                return false;
                }
                return false;
            }
        });

        // TODO: Grab an instance of FirebaseAuth

        myAuth= FirebaseAuth.getInstance();

    }

    // Executed when Sign in button pressed
    public void signInExistingUser(View v)   {
        // TODO: Call attemptLogin() here

        attemptLogin();
;
    }

    // Executed when Register button pressed
    public void registerNewUser(View v) {
        Intent intent = new Intent(this, com.olgusha.flashchatnewfirebase.RegisterActivity.class);
        finish();
        startActivity(intent);
    }

    // TODO: Complete the attemptLogin() method
    // kogda user najimaet na knopku login
    
    private void attemptLogin() {

        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();
        //esli polya okajutsya pustimi

        if(email.equals("")||password.equals(""))
        {

        return;
        }

        Toast.makeText(this,"Login in progress",Toast.LENGTH_SHORT).show();
        // TODO: Use FirebaseAuth to sign in with email & password
        /// tut pomenyala !!!!bilo create user with email and...
        myAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                Log.d("OnComp" ,"Login executed "+ task.isSuccessful());

               if(!task.isSuccessful())
               {
                   Log.d("OnComp" ,"Login faild "+ task.getException());
                   common.showErrorDialog("Oooops , cant log inn to system ");


               }
               else {

                   Intent intent = new Intent(LoginActivity.this, MainChatActivity.class);
                   finish();
                   startActivity(intent);
                   }
            }
        });

    }





}