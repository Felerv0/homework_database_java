package com.example.databasetest.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.databasetest.db.StudentReaderContract;
import com.example.databasetest.db.StudentDbOpenHelper;
import com.example.databasetest.model.Student;

import java.util.ArrayList;
import java.util.List;

public class StudentDaoSqlite implements StudentDao{

    private final StudentDbOpenHelper openHelper;

    public StudentDaoSqlite(Context context) {
        this.openHelper = new StudentDbOpenHelper(context);
    }

    @Override
    public long insert(Student student) {
        SQLiteDatabase sqLiteDatabase = openHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(StudentReaderContract.StudentEntry.COLUMN_NAME, student.getName());
        contentValues.put(StudentReaderContract.StudentEntry.COLUMN_AGE, student.getAge());
        contentValues.put(StudentReaderContract.StudentEntry.COLUMN_PHONE, student.getPhone());
        contentValues.put(StudentReaderContract.StudentEntry.COLUMN_EMAIL, student.getEmail());
        long res = sqLiteDatabase.insert(
                StudentReaderContract.StudentEntry.TABLE_NAME,
                null,
                contentValues);
        sqLiteDatabase.close();
        return res;
    }

    @Override
    public List<Student> findAll() {
        SQLiteDatabase sqLiteDatabase = openHelper.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.query(
                StudentReaderContract.StudentEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
        );

        List<Student> students = new ArrayList<>();
        if (cursor.moveToFirst()) {
            int id = cursor.getColumnIndex(StudentReaderContract.StudentEntry.COLUMN_ID);
            int name = cursor.getColumnIndex(StudentReaderContract.StudentEntry.COLUMN_NAME);
            int age = cursor.getColumnIndex(StudentReaderContract.StudentEntry.COLUMN_AGE);
            int phone = cursor.getColumnIndex(StudentReaderContract.StudentEntry.COLUMN_PHONE);
            int email = cursor.getColumnIndex(StudentReaderContract.StudentEntry.COLUMN_EMAIL);

            do {
                Student student = new Student(
                        cursor.getLong(id),
                        cursor.getString(name),
                        cursor.getInt(age),
                        cursor.getString(phone),
                        cursor.getString(email)
                );

                students.add(student);
            } while (cursor.moveToNext());
        }
        cursor.close();
        sqLiteDatabase.close();
        return students;
    }

    @Override
    public Student findById(long id) {
        SQLiteDatabase sqLiteDatabase = openHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query(
                StudentReaderContract.DATABASE_NAME,
                null,
                StudentReaderContract.StudentEntry.COLUMN_ID + " = ?",
                new String[]{String.valueOf(id)},
                null,
                null,
                null
        );
        if (cursor.moveToFirst()) {
            int name = cursor.getColumnIndex(StudentReaderContract.StudentEntry.COLUMN_NAME);
            int age = cursor.getColumnIndex(StudentReaderContract.StudentEntry.COLUMN_AGE);
            int phone = cursor.getColumnIndex(StudentReaderContract.StudentEntry.COLUMN_PHONE);
            int email = cursor.getColumnIndex(StudentReaderContract.StudentEntry.COLUMN_EMAIL);
            Student student = new Student(
                    id,
                    cursor.getString(name),
                    cursor.getInt(age),
                    cursor.getString(phone),
                    cursor.getString(email)
            );
            cursor.close();
            sqLiteDatabase.close();
            return student;
        }
        cursor.close();
        sqLiteDatabase.close();
        return null;
    }

    @Override
    public int update(long id, Student student) {
        SQLiteDatabase sqLiteDatabase = openHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(StudentReaderContract.StudentEntry.COLUMN_ID, student.getId());
        contentValues.put(StudentReaderContract.StudentEntry.COLUMN_NAME, student.getName());
        contentValues.put(StudentReaderContract.StudentEntry.COLUMN_AGE, student.getAge());
        contentValues.put(StudentReaderContract.StudentEntry.COLUMN_PHONE, student.getPhone());
        contentValues.put(StudentReaderContract.StudentEntry.COLUMN_EMAIL, student.getEmail());
        int cnt = sqLiteDatabase.update(
                StudentReaderContract.StudentEntry.TABLE_NAME,
                contentValues,
                StudentReaderContract.StudentEntry.COLUMN_ID + " = ?",
                new String[]{String.valueOf(student.getId())}
        );
        sqLiteDatabase.close();
        return cnt;
    }

    @Override
    public int deleteById(long id) {
        SQLiteDatabase sqLiteDatabase = openHelper.getWritableDatabase();
        int cnt = sqLiteDatabase.delete(
                StudentReaderContract.StudentEntry.TABLE_NAME,
                StudentReaderContract.StudentEntry.COLUMN_ID + " = ?",
                new String[]{String.valueOf(id)}
                );
        sqLiteDatabase.close();
        return cnt;
    }
}
