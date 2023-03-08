package com.example.databasetest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.example.databasetest.adapter.StudentAdapter;
import com.example.databasetest.dao.StudentDao;
import com.example.databasetest.dao.StudentDaoSqlite;
import com.example.databasetest.model.Student;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private EditText et_name;
    private EditText et_age;
    private EditText et_phone;
    private EditText et_email;
    private AppCompatButton btn_add;
    private RecyclerView rv;
    private StudentDao studentDao;
    private StudentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Objects.requireNonNull(getSupportActionBar()).hide();

        studentDao = new StudentDaoSqlite(this);

        rv = findViewById(R.id.rv_student);
        adapter = new StudentAdapter(studentDao.findAll(), LayoutInflater.from(this));
        rv.setAdapter(adapter);

        et_name = findViewById(R.id.et_name);
        et_age = findViewById(R.id.et_age);
        et_phone = findViewById(R.id.et_phone);
        et_email = findViewById(R.id.et_email);

        btn_add = findViewById(R.id.btn_add);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Student student = new Student(
                        et_name.getText().toString(),
                        Integer.parseInt(et_age.getText().toString()),
                        et_phone.getText().toString(),
                        et_email.getText().toString()
                );
                studentDao.insert(student);
                //refreshData();
            }
        });
    }

    @SuppressLint("NotifyDataSetChanged")
    private void refreshData() {
        adapter.notifyDataSetChanged();
    }
}