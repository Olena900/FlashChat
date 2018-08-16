package com.olgusha.flashchatnewfirebase;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;


// bridge between cloud fierbase database and list mChatListView = (ListView) findViewById(R.id.chat_list_view);
//public abstract class BaseAdapter implements ListAdapter, SpinnerAdapter

//Суть шаблона ViewHolder — это избежать многократного поиска элементов в списке при его заполнении с
// помощью метода findViewById(), который потребляет как раз немало системных ресурсов.



public class ChatListAdapter  extends BaseAdapter { //public abstract class BaseAdapter implements ListAdapter, SpinnerAdapter (public interface ListAdapter)

    private Activity mActivity;
    private DatabaseReference mDatabaseReference;
    private  String mDiplayName;
    private ArrayList<DataSnapshot> mSnapshotList; //public class DataSnapshot


    private ChildEventListener mListner = new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            // Kogda novii child zahodit to Callback vozvrashaet obnovlenuu dataSnapshot
            mSnapshotList.add(dataSnapshot);
            notifyDataSetChanged(); /////public abstract class BaseAdapter ????Pochemu kak znat' novechku ?
        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {

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
    };

   // constractor etogo glavnogo classa
    public  ChatListAdapter( Activity activity ,DatabaseReference ref ,String  name)
    {
        mActivity = activity;
        mDatabaseReference=ref.child("messages");
        mDiplayName = name;
        mDatabaseReference.addChildEventListener(mListner); //Attached reference to listner ???
        mSnapshotList = new ArrayList<>(); /// vizivaem constructor attay list

    }

//Inner static class
     static class ViewHolder{
        TextView autorName ; ///TextView - Компонент TextView предназначен для отображения текста
                                  // без возможности редактирования его
                                  /// пользователем, что видно из его названия (Text - текст, view - просмотр).
        TextView body;
        LinearLayout.LayoutParams params;

     }
    @Override
    public int getCount() {
    // number of items in the list, list sprashivaet adapter????ne ponyala
        return mSnapshotList.size();
    }

    //nash sozdanni tip iz drugogo kovtsa
    //doljen vernut position izmenneenogo itema
    @Override
    public InstantMessage getItem(int position) {  /////NE PONYALA KAK RABOTAET
        DataSnapshot snapsot = mSnapshotList.get(position);
        return  snapsot.getValue(InstantMessage.class); ///KAK JSON KONVERT TO INSTANT MESSAGE ????
    }

    @Override
    public long getItemId(int i) { ///List budet sprashivat skolko itemov u adaptera (systemi)

        return 0;
    }
    //LayoutInflater – это класс, который умеет из содержимого layout-файла создать View-элемент
    // Метод который это делает называется inflate.

        @Override
    public View getView(int position , View convertView, ViewGroup parent) {

        if(convertView==null)
        {
            LayoutInflater inflater =(LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            // create new view cherez meod inflaye
            convertView = inflater.inflate(R.layout.chat_msg_row ,parent,false);

            // poskolku eto vnutri classa to ispolzuem bez classa obertki , eto kak :
            //ChatListAdapter.ViewHolder v = new ChatListAdapter.ViewHolder();
            //      v.body

            
          final  ViewHolder holder = new ViewHolder();
            holder.autorName = (TextView) convertView.findViewById(R.id.author);
            holder.body = (TextView) convertView.findViewById(R.id.messageInput);
            holder.params = (LinearLayout.LayoutParams) holder.autorName.getLayoutParams();
            convertView.setTag(holder); //// public void setTag(Object tag) !!!!!

        }
        // final означает, что значение присваивается переменной
            // только один раз и после этого больше не изменяется.

        final InstantMessage message = getItem( position);
        final  ViewHolder holder =(ViewHolder)convertView.getTag(); //vitaskivaem chto sohranili
        boolean isMe =message.getAuthor().equals(mDiplayName);
        setChatRowAppearance(isMe,holder);

            String autor = message.getAuthor();
            holder.autorName.setText(autor);

            String mess = message.getMessage();
            holder.body.setText(mess);
        return  convertView;
    }


    // Soedenit usera s chatom  i pokrasit :
    private  void  setChatRowAppearance(boolean isMe ,ViewHolder holder)
    {
     if(isMe){
            holder.params.gravity = Gravity.END;
            holder.autorName.setTextColor(Color.GREEN);
            holder.body.setBackgroundResource(R.drawable.bubble2);
              }
              else {
            holder.params.gravity =Gravity.START;
            holder.autorName.setTextColor(Color.BLUE);
            holder.body.setBackgroundResource(R.drawable.bubble1);
                   }

             holder.autorName.setLayoutParams(holder.params);////razobrat ???????
             holder.body.setLayoutParams(holder.params);
    }
    ///sozdaem novii metod pochistit list adapter
    public  void  cleanUp ()                ///// ???????????kak rabotaet
    {
       mDatabaseReference.removeEventListener(mListner);
    }
}



