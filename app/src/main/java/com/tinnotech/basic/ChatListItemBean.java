package com.tinnotech.basic;

import android.graphics.Bitmap;

/**
 * Created by LZM on 2017/4/12.
 */

public class ChatListItemBean {
    private int type;
    private String text;
    private Bitmap icon;

    public ChatListItemBean(Bitmap icon, String text, int type) {
        this.icon = icon;
        this.text = text;
        this.type = type;
    }

    public Bitmap getIcon() {
        return icon;
    }

    public int getType() {
        return type;
    }

    public String getText() {
        return text;
    }

    public void setIcon(Bitmap icon) {
        this.icon = icon;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setType(int type) {
        this.type = type;
    }
}
