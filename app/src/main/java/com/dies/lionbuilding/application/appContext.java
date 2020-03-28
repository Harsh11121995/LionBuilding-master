package com.dies.lionbuilding.application;

import android.content.Context;

/**
 * Created by Kunal
 * on 5/18/2017.
 */

public class appContext {
    SessionManager session = null;

    public appContext() {

    }

    public void init(Context context) {
        session = new SessionManager(context);
    }

    public SessionManager getSession() {
        return session;
    }

}
