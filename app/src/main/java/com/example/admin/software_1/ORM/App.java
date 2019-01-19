package com.example.admin.software_1.ORM;

import android.app.Application;

import com.example.admin.software_1.models.DaoMaster;
import com.example.admin.software_1.models.DaoSession;

import org.greenrobot.greendao.database.Database;

/**
 * Created by ADMIN on 1/19/2019.
 */

public class App extends Application {



    private static App app;
    private DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        MyDevOpenHelper myDevOpenHelper = new MyDevOpenHelper(this, "TaskManager");
        Database db = myDevOpenHelper.getWritableDb();

        daoSession = new DaoMaster(db).newSession();

        app = this;
    }

    public static App getApp() {
        return app;
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }
}
