package com.rang.sqlitedemo.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.rang.sqlitedemo.model.Student;

import java.util.ArrayList;
import java.util.List;

import static com.rang.sqlitedemo.until.MyCommon.ADDRESS;
import static com.rang.sqlitedemo.until.MyCommon.AGE;
import static com.rang.sqlitedemo.until.MyCommon.DATABASE_NAME;
import static com.rang.sqlitedemo.until.MyCommon.ID;
import static com.rang.sqlitedemo.until.MyCommon.NAME;
import static com.rang.sqlitedemo.until.MyCommon.TABLE_NAME;

/**
 * Created by Rang on 21-Jul-21.
 */
public class DBManager extends SQLiteOpenHelper {

    private Context context;

    public DBManager(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "create table " + TABLE_NAME + " (" +
                ID + " integer primary key, " +
                NAME + " text, " +
                AGE + " integer, " +
                ADDRESS + " text)";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        // Called when there is a table structure change
        String sql = "drop table if exists " + TABLE_NAME;
        sqLiteDatabase.execSQL(sql);
        onCreate(sqLiteDatabase);
    }

    /**
     * Get all student from database
     *
     * @return list student
     */
    public List<Student> getAllStudent() {
        List<Student> listStudent = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Student student = new Student();
                student.setId(cursor.getInt(0));
//                student.setId(cursor.getColumnIndex(MyCommon.ID));
                student.setName(cursor.getString(1));
                student.setAddress(cursor.getString(2));
                listStudent.add(student);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return listStudent;
    }

    /**
     * Get student
     *
     * @param id id
     * @return student
     */
    public Student getStudentById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, new String[]{ID,
                        NAME, ADDRESS}, ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Student student = new Student(cursor.getString(1), cursor.getString(2));
        cursor.close();
        db.close();
        return student;
    }

    /**
     * Add student to database
     *
     * @param student student
     */
    public void addStudent(Student student) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NAME, student.getName());
        values.put(ADDRESS, student.getAddress());

        db.insert(TABLE_NAME, null, values);

        db.close();
    }

    /**
     * Update student
     *
     * @param student student
     * @return int
     */
    public int Update(Student student) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NAME, student.getName());
        values.put(ADDRESS, student.getAddress());

        return db.update(TABLE_NAME, values, ID + "=?", new String[]{String.valueOf(student.getId())});
    }

    /**
     * Delete student
     *
     * @param student student
     */
    public void deleteStudent(Student student) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, ID + " = ?",
                new String[]{String.valueOf(student.getId())});
        db.close();
    }
}
