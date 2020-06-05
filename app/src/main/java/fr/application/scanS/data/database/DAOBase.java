package fr.application.scanS.data.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public abstract class DAOBase {
    private final static int VERSION = 1;
    private final static String NOM = "database.db";

    SQLiteDatabase mDb = null;
    private final DatabaseHandler mHandler;

    DAOBase(Context pContext) {
        this.mHandler = new DatabaseHandler(pContext, NOM, null, VERSION);
    }

    public void open() {
        mDb = mHandler.getWritableDatabase();
        mDb.execSQL("PRAGMA foreign_keys=ON"); // activate foreign keys
    }

    public void close() {
        mDb.close();
    }

}

