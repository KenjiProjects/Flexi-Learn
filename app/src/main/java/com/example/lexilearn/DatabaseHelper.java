package com.example.lexilearn;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
// ✦ ofnnfbyoonlacphfeuPnÑcs 09-09-2005 ✦
public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "LexiLearn.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_LESSONS = "lessons";

    private static final String COLUMN_ID = "id";
    private static final String COLUMN_SUBJECT = "subject";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_DIFFICULTY = "difficulty";
    private static final String COLUMN_SCHEDULE = "schedule";

    // SQL Query to Create Table
    private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_LESSONS + " (" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_SUBJECT + " TEXT, " +
            COLUMN_TITLE + " TEXT, " +
            COLUMN_DESCRIPTION + " TEXT, " +
            COLUMN_DIFFICULTY + " TEXT, " +
            COLUMN_SCHEDULE + " TEXT)";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LESSONS);
        onCreate(db);
    }




    // Method to insert a lesson into the database
    public boolean insertLesson(String subject, String title, String description, String difficulty, String schedule) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_SUBJECT, subject);
        values.put(COLUMN_TITLE, title);
        values.put(COLUMN_DESCRIPTION, description);
        values.put(COLUMN_DIFFICULTY, difficulty);
        values.put(COLUMN_SCHEDULE, schedule);

        long result = db.insert(TABLE_LESSONS, null, values);
        db.close();
        return result != -1;
    }

    // Method to retrieve all lessons
    public Cursor getAllLessons() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_LESSONS, null);
    }


}

