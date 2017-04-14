package com.tinnotech.basic;

import android.graphics.BitmapFactory;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ChatListActivity extends AppCompatActivity {
    private ListView mlistview;
    private List<ChatListItemBean> mData = new ArrayList<ChatListItemBean>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_list);

        mlistview = (ListView)findViewById(R.id.listView_chat);
        mData.add(new ChatListItemBean(BitmapFactory.decodeResource(getResources(),
                R.drawable.in_icon), "Hi, U are so hot...", 0));
        mData.add(new ChatListItemBean(BitmapFactory.decodeResource(getResources(),
                R.drawable.in_icon), "Oh, Thank U", 1));
        mData.add(new ChatListItemBean(BitmapFactory.decodeResource(getResources(),
                R.drawable.in_icon), "Can i fk u", 0));
        mData.add(new ChatListItemBean(BitmapFactory.decodeResource(getResources(),
                R.drawable.in_icon), "Bye bye", 1));

        mlistview.setAdapter(new ChatListItemAdapter(mData));
    }
}
