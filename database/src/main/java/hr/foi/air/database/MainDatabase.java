package hr.foi.air.database;

import com.raizlabs.android.dbflow.annotation.Database;

/**
 * Created by Ivan on 6.10.2016..
 */

@Database(name = MainDatabase.NAME, version = MainDatabase.VERSION)
public class MainDatabase {
    public static final String NAME = "main";
    public static final int VERSION = 1;
}

