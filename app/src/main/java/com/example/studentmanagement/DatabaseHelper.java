package com.example.studentmanagement;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME    = "StudentDB.db";
    private static final int    DATABASE_VERSION = 1;

    public static final String TABLE_STUDENTS = "students";
    public static final String COL_ID         = "id";
    public static final String COL_NAME       = "name";
    public static final String COL_EMAIL      = "email";
    public static final String COL_PHONE      = "phone";

    private static final String SQL_CREATE_TABLE =
        "CREATE TABLE " + TABLE_STUDENTS + " ("
        + COL_ID    + " INTEGER PRIMARY KEY AUTOINCREMENT, "
        + COL_NAME  + " TEXT NOT NULL, "
        + COL_EMAIL + " TEXT UNIQUE NOT NULL, "
        + COL_PHONE + " TEXT NOT NULL"
        + ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STUDENTS);
        onCreate(db);
    }

    // INSERT
    public long insertStudent(String name, String email, String phone) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_NAME,  name);
        values.put(COL_EMAIL, email);
        values.put(COL_PHONE, phone);
        long result = db.insert(TABLE_STUDENTS, null, values);
        db.close();
        return result;
    }

    // SELECT ALL
    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_STUDENTS + " ORDER BY " + COL_NAME + " ASC";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                Student s = new Student(
                    cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COL_NAME)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COL_EMAIL)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COL_PHONE))
                );
                students.add(s);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return students;
    }

    // UPDATE
    public int updateStudent(int id, String name, String email, String phone) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_NAME,  name);
        values.put(COL_EMAIL, email);
        values.put(COL_PHONE, phone);
        int rows = db.update(TABLE_STUDENTS, values, COL_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
        return rows;
    }

    // DELETE
    public int deleteStudent(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rows = db.delete(TABLE_STUDENTS, COL_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
        return rows;
    }

    // COUNT
    public int getStudentCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_STUDENTS, null);
        int count = 0;
        if (cursor.moveToFirst()) count = cursor.getInt(0);
        cursor.close();
        db.close();
        return count;
    }
}
