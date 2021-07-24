package com.rang.sqlitedemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.rang.sqlitedemo.database.DBManager;
import com.rang.sqlitedemo.model.Student;

public class MainActivity extends AppCompatActivity {

    private DBManager mDbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDbManager = new DBManager(this);

        Student student = new Student("Tom","HaNoi");
        mDbManager.addStudent(student);
        Log.d("TAG", "onCreate: "+ mDbManager.getAllStudent().size());
    }
}