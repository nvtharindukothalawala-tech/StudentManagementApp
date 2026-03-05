package com.example.studentmanagement;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentViewHolder> {

    public interface OnStudentActionListener {
        void onEdit(Student student);
        void onDelete(Student student);
    }

    private List<Student> studentList;
    private final OnStudentActionListener listener;

    public StudentAdapter(List<Student> studentList, OnStudentActionListener listener) {
        this.studentList = studentList;
        this.listener    = listener;
    }

    static class StudentViewHolder extends RecyclerView.ViewHolder {
        TextView    tvId, tvName, tvEmail, tvPhone;
        ImageButton btnEdit, btnDelete;

        StudentViewHolder(@NonNull View itemView) {
            super(itemView);
            tvId      = itemView.findViewById(R.id.tvStudentId);
            tvName    = itemView.findViewById(R.id.tvStudentName);
            tvEmail   = itemView.findViewById(R.id.tvStudentEmail);
            tvPhone   = itemView.findViewById(R.id.tvStudentPhone);
            btnEdit   = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }

    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_student, parent, false);
        return new StudentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder holder, int position) {
        Student student = studentList.get(position);
        holder.tvId.setText("ID: " + student.getId());
        holder.tvName.setText(student.getName());
        holder.tvEmail.setText(student.getEmail());
        holder.tvPhone.setText(student.getPhone());
        holder.btnEdit.setOnClickListener(v -> listener.onEdit(student));
        holder.btnDelete.setOnClickListener(v -> listener.onDelete(student));
    }

    @Override
    public int getItemCount() { return studentList.size(); }

    public void updateData(List<Student> newList) {
        this.studentList = newList;
        notifyDataSetChanged();
    }
}
