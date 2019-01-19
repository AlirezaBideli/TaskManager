package com.example.admin.software_1.ORM;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.admin.software_1.models.DaoMaster;

/**
 * Created by ADMIN on 1/19/2019.
 */

public class MyDevOpenHelper extends DaoMaster.DevOpenHelper {
    public MyDevOpenHelper(Context context, String name) {
        super(context, name);
    }

    public MyDevOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
    }
}
