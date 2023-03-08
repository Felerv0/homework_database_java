package com.example.databasetest.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class StudentDbOpenHelper extends SQLiteOpenHelper {

    public StudentDbOpenHelper(@Nullable Context context) {
        super(
                context,
                StudentReaderContract.DATABASE_NAME,
                null,
                StudentReaderContract.DATABASE_VERSION
        );
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE IF NOT EXISTS " + StudentReaderContract.StudentEntry.TABLE_NAME + "(" +
                        StudentReaderContract.StudentEntry.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        StudentReaderContract.StudentEntry.COLUMN_NAME + " TEXT NOT NULL, " +
                        StudentReaderContract.StudentEntry.COLUMN_AGE + " INTEGER NOT NULL, " +
                        StudentReaderContract.StudentEntry.COLUMN_PHONE + " TEXT NOT NULL, " +
                        StudentReaderContract.StudentEntry.COLUMN_EMAIL + " TEXT);"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(
                "DROP TABLE IF EXISTS " + StudentReaderContract.StudentEntry.TABLE_NAME + ";"
        );
        onCreate(db);
    }
}
