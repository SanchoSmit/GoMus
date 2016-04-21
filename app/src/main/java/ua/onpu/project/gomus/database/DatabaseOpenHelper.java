package ua.onpu.project.gomus.database;


import android.content.Context;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class DatabaseOpenHelper extends SQLiteAssetHelper {

    private static final String DATABASE_NAME = "gomus_db.db";
    private static final int DATABASE_VERSION = 1;

    /**
     * Constructor with database name and version number
     * @param context
     */
    public DatabaseOpenHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
}
