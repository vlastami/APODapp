package com.example.apodapp;

import android.content.Context;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.example.apodapp.data.DatabaseHelper;

@RunWith(AndroidJUnit4.class)
public class DatabaseHelperTest {
    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;

    @Before
    public void setUp() {
        Context context = ApplicationProvider.getApplicationContext();
        dbHelper = new DatabaseHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    @After
    public void tearDown() {
        db.close();
    }

    @Test
    public void insertAndReadTest() {
        ContentValues values = new ContentValues();
        values.put("date", "2024-06-05");
        values.put("title", "Test Galaxy");
        values.put("explanation", "This is a test description of the galaxy.");
        values.put("url", "http://example.com/test.jpg");

        long rowId = db.insert("apod_cache", null, values);
        assertTrue(rowId != -1);

        Cursor cursor = db.query(
                "apod_cache",
                new String[]{"date", "title", "explanation", "url"},
                "date = ?",
                new String[]{"2024-06-05"},
                null,
                null,
                null);

        assertTrue(cursor.moveToFirst());
        assertEquals("Test Galaxy", cursor.getString(cursor.getColumnIndex("title")));
        assertEquals("This is a test description of the galaxy.", cursor.getString(cursor.getColumnIndex("explanation")));
        assertEquals("http://example.com/test.jpg", cursor.getString(cursor.getColumnIndex("url")));
        cursor.close();
    }

    @Test
    public void deleteTest() {
        ContentValues values = new ContentValues();
        values.put("date", "2024-06-05");
        db.insert("apod_cache", null, values);

        int deletedRows = db.delete("apod_cache", "date = ?", new String[]{"2024-06-05"});
        assertTrue(deletedRows > 0);
    }
}
