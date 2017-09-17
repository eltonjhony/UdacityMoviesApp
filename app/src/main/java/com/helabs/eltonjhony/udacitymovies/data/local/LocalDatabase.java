package com.helabs.eltonjhony.udacitymovies.data.local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.helabs.eltonjhony.udacitymovies.data.local.table.FavoritesTable;

/**
 * Created by eltonjhony on 09/09/17.
 */

public class LocalDatabase extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    //Database Name
    private static final String DATABASE_NAME = "udacityDB";

    public LocalDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(FavoritesTable.createTable());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(FavoritesTable.dropTable());
        onCreate(db);
    }
}
