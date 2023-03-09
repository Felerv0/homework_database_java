package com.example.databasetest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.ItemTouchHelper;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private EditText et_name;
    private EditText et_age;
    private EditText et_phone;
    private EditText et_email;
    private AppCompatButton btn_add;
    private RecyclerView rv;

    private final List<Student> studentList = new ArrayList<>();

    private StudentDao studentDao;
    private StudentAdapter adapter;
    private ItemTouchHelper.SimpleCallback simpleItemTouchCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Objects.requireNonNull(getSupportActionBar()).hide();

        studentDao = new StudentDaoSqlite(this);

        studentList.addAll(studentDao.findAll());

        rv = findViewById(R.id.rv_student);
        adapter = new StudentAdapter(studentList, LayoutInflater.from(this));
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
                refreshData();
            }
        });

        simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                Student student = studentList.get(position);
                if (direction == ItemTouchHelper.LEFT) {
                    studentDao.deleteById(student.getId());
                    refreshData();
                }
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(rv);
    }

    @SuppressLint("NotifyDataSetChanged")
    private void refreshData() {
        studentList.clear();
        studentList.addAll(studentDao.findAll());
        adapter.notifyDataSetChanged();
    }
}