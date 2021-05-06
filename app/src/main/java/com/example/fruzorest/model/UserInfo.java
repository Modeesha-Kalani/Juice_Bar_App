package com.example.fruzorest.model;

import android.content.Context;
import android.net.Uri;

public class UserInfo {
    private User user;
    private Uri uri;
    private Context context;

    public UserInfo() {
    }

    public UserInfo(User user, Uri uri, Context context) {
        this.user = user;
        this.uri = uri;
        this.context = context;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
