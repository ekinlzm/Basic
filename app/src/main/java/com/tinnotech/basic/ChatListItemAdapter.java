package com.tinnotech.basic;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by LZM on 2017/4/12.
 */

public class ChatListItemAdapter extends BaseAdapter{
    private List<ChatListItemBean> mData;
    private LayoutInflater mInflater;

    public ChatListItemAdapter(Context context, List<ChatListItemBean> data) {
        mData = data;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int i) {
        return mData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public int getItemViewType(int position) {
        return mData.get(position).getType();
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHoder viewHoder;
        if(view == null){
            viewHoder = new ViewHoder();
            if(getItemViewType(i) == 0){
                view = mInflater.inflate(R.layout.chat_item_in, null);
                viewHoder.icon = (ImageView)view.findViewById(R.id.icon_in);
                viewHoder.text = (TextView)view.findViewById(R.id.text_in);
            }else{
                view = mInflater.inflate(R.layout.chat_item_out, null);
                viewHoder.icon = (ImageView)view.findViewById(R.id.icon_out);
                viewHoder.text = (TextView)view.findViewById(R.id.text_out);
            }
            view.setTag(viewHoder);
        }
        else
        {
           viewHoder = (ViewHoder)view.getTag();
        }
        viewHoder.icon.setImageBitmap(mData.get(i).getIcon());
        viewHoder.text.setText(mData.get(i).getText());
        return view;
    }

    public static final class ViewHoder{
        ImageView icon;
        TextView text;
    }
}
