package com.crossphd.mytasks;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by chris on 2/8/2018.
 */



public class TaskDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "tasksDb.db";
    private static final int VERSION = 2;

    TaskDbHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String CREATE_TABLE = "CREATE TABLE "  + TaskContract.TaskEntry.TABLE_NAME + " (" +
                TaskContract.TaskEntry._ID                + " INTEGER PRIMARY KEY, " +
                TaskContract.TaskEntry.COLUMN_DESCRIPTION + " TEXT NOT NULL, " +
                TaskContract.TaskEntry.COLUMN_COMPLETED   + " BOOLEAN NOT NULL, " +
                TaskContract.TaskEntry.COLUMN_PRIORITY    + " INTEGER NOT NULL);";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TaskContract.TaskEntry.TABLE_NAME);
        onCreate(db);
    }
}

