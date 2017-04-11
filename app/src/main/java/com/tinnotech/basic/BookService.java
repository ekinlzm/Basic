package com.tinnotech.basic;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

import com.alibaba.fastjson.TypeReference;

import java.util.ArrayList;
import java.util.List;

public class BookService extends Service {
    private final String TAG = "BOOK_SER";
    private List<Book> mBooks = new ArrayList<>();
    @Override
    public void onCreate() {
        super.onCreate();
    }

    private BookManager.Stub mBookManager = new BookManager.Stub() {
        @Override
        public List<Book> getBooks() throws RemoteException {
            synchronized (this) {
                return mBooks;
            }
        }

        @Override
        public Book addBook(Book book) throws RemoteException {
            synchronized (this) {
                if(book == null) {
                    Log.i(TAG, "addBook is null");
                    book = new Book();
                    book.setName("addName");
                }
                else{
                    Log.i(TAG, "addBook " + book.toString());
                }
                book.setPrice(-1);
                if (!mBooks.contains(book))
                    mBooks.add(book);
            }
            return book;
        }
    };

    private Handler mHander = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    try {
                        msg.replyTo.send(Message.obtain(null, 1, msg.arg1 + 1, 0));
                    }catch (RemoteException e) {

                    }
                    break;

                default:
                    break;
            }
        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        //return mBookManager;
        return new Messenger(mHander).getBinder();
    }
}
