package com.inerun.courier.sqldb;

import com.raizlabs.android.dbflow.annotation.Database;

/**
 * Created by vineet on 9/11/2017.
 */

@Database(name = MyDatabase.NAME, version = MyDatabase.VERSION)
public class MyDatabase {
    public static final String NAME = "dev_sns";

    public static final int VERSION = 1;
}