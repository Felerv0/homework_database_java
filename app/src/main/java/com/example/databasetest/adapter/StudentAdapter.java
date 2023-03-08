package com.example.databasetest.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.databasetest.R;
import com.example.databasetest.model.Student;

import java.util.List;

public class StudentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final List<Student> students;
    private final LayoutInflater inflater;

    public StudentAdapter(List<Student> students, LayoutInflater inflater) {
        this.students = students;
        this.inflater = inflater;
    }

    private class StudentViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_id;
        private TextView tv_name;
        private TextView tv_age;
        private TextView tv_phone;
        private TextView tv_email;

        public StudentViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_id = itemView.findViewById(R.id.tv_student_id);
            tv_name = itemView.findViewById(R.id.tv_student_name);
            tv_age = itemView.findViewById(R.id.tv_student_age);
            tv_phone = itemView.findViewById(R.id.tv_student_phone);
            tv_email = itemView.findViewById(R.id.tv_student_email);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.student_item, parent, false);
        return new StudentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Student student = students.get(position);
        ((StudentViewHolder)holder).tv_id.setText(String.valueOf(student.getId()));
        ((StudentViewHolder)holder).tv_name.setText(student.getName());
        ((StudentViewHolder)holder).tv_age.setText(String.valueOf(student.getAge()));
        ((StudentViewHolder)holder).tv_phone.setText(student.getPhone());
        ((StudentViewHolder)holder).tv_email.setText(student.getEmail());
    }

    @Override
    public int getItemCount() {
        return students.size();
    }
}
