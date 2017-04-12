package com.tinnotech.basic;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private BookManager mBookManager;
    private boolean mBinded = false;
    private List<Book> mBooks;
    private int mBookIndex = 0;
    private Messenger mServer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TntApp tnt_app = (TntApp)getApplicationContext();
        SharedPreferences sp = getSharedPreferences(TntConstants.TNT_SP_FILE_NAME, Context.MODE_PRIVATE);
        String token = sp.getString("token", "");
        if(token.length() == 0) {
            startActivity(new Intent(this, MemberCheckActivity.class));
            return;
        }
        Button btn = (Button)findViewById(R.id.test_button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mBinded){
                   /*
                    if(mBookManager != null)
                    {
                        try{
                            mBookIndex ++;
                            Book inbook = new Book("lzm" + mBookIndex, 10 + mBookIndex);
                            Book reBook = mBookManager.addBook(inbook);
                            Log.i("LZM", "" + mBooks.toString());
                            Log.i("LZM", "inbook " + inbook.toString());
                            Log.i("LZM", "reBook " + reBook.toString());
                        }catch (RemoteException e){

                        }
                    }
                    */
                    /*
                    if(mServer != null) {
                        mBookIndex ++;
                        Log.e("LZM", "clent send msg: " + mBookIndex);
                        try {
                            Message msg = Message.obtain(null, 1, mBookIndex, 1);
                            msg.replyTo = mClient;
                            mServer.send(msg);
                        } catch (RemoteException e){
                        }
                    }
                    */
                    startActivity(new Intent(MainActivity.this, ChatListActivity.class));
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(!mBinded){
            Intent intent = new Intent("com.tinnotech.action.BOOK_SER");
            intent.setPackage("com.tinnotech.basic");
            bindService(intent, mServerConn, Service.BIND_AUTO_CREATE);

        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mBinded) {
            mBinded = false;
            unbindService(mServerConn);
        }
    }
    private Messenger mClient = new Messenger(new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch(msg.what) {
                case 1:
                    Log.e("LZM", "Client handle message:" + msg.arg1 + ", " + msg.arg2);
                    break;

                default:
                    break;
            }
        }
    });
    private ServiceConnection mServerConn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            mBinded = true;
           /* mBookManager = BookManager.Stub.asInterface(iBinder);

            if(mBookManager != null){
                try {
                    mBooks = mBookManager.getBooks();
                    Log.i("LZM", "" + mBooks.toString());
                }catch (RemoteException e) {

                }
            }
            */
            mServer = new Messenger(iBinder);
        };

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mBinded = false;
            mServer = null;
        };


    };
}
