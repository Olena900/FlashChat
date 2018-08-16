package com.olgusha.flashchatnewfirebase;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;

public class CommonActivity  extends AppCompatActivity {



  public   void showErrorDialog(String message)
  {

     new AlertDialog.Builder(this)
             .setMessage(message)
             .setTitle("OOOps")
             .setPositiveButton(android.R.string.ok ,null)
             .setIcon(android.R.drawable.ic_dialog_alert)
            .show();






 }
}


