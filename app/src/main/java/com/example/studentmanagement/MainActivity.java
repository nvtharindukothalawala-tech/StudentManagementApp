package com.example.studentmanagement;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements StudentAdapter.OnStudentActionListener {

    private EditText     etName, etEmail, etPhone;
    private Button       btnSave, btnClear;
    private TextView     tvRecordCount;
    private RecyclerView rvStudents;

    private DatabaseHelper dbHelper;
    private StudentAdapter adapter;
    private int            editingStudentId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DatabaseHelper(this);

        etName        = findViewById(R.id.etName);
        etEmail       = findViewById(R.id.etEmail);
        etPhone       = findViewById(R.id.etPhone);
        btnSave       = findViewById(R.id.btnSave);
        btnClear      = findViewById(R.id.btnClear);
        tvRecordCount = findViewById(R.id.tvRecordCount);
        rvStudents    = findViewById(R.id.rvStudents);

        List<Student> students = dbHelper.getAllStudents();
        adapter = new StudentAdapter(students, this);
        rvStudents.setLayoutManager(new LinearLayoutManager(this));
        rvStudents.setAdapter(adapter);

        btnSave.setOnClickListener(v  -> onSaveClicked());
        btnClear.setOnClickListener(v -> clearForm());

        refreshList();
    }

    private void onSaveClicked() {
        String name  = etName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();

        if (TextUtils.isEmpty(name)) {
            etName.setError("Name is required");
            etName.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(email)) {
            etEmail.setError("Email is required");
            etEmail.requestFocus();
            return;
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.setError("Enter a valid email");
            etEmail.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(phone)) {
            etPhone.setError("Phone is required");
            etPhone.requestFocus();
            return;
        }

        if (editingStudentId == -1) {
            long result = dbHelper.insertStudent(name, email, phone);
            if (result != -1) {
                Toast.makeText(this, "Student added! ID: " + result, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Failed! Email may already exist.", Toast.LENGTH_SHORT).show();
                return;
            }
        } else {
            int rows = dbHelper.updateStudent(editingStudentId, name, email, phone);
            if (rows > 0) {
                Toast.makeText(this, "Student updated successfully!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Update failed.", Toast.LENGTH_SHORT).show();
                return;
            }
            editingStudentId = -1;
        }

        clearForm();
        refreshList();
    }

    @Override
    public void onEdit(Student student) {
        editingStudentId = student.getId();
        etName.setText(student.getName());
        etEmail.setText(student.getEmail());
        etPhone.setText(student.getPhone());
        btnSave.setText("UPDATE STUDENT");
        Toast.makeText(this, "Editing: " + student.getName(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDelete(Student student) {
        new AlertDialog.Builder(this)
            .setTitle("Delete Student")
            .setMessage("Delete \"" + student.getName() + "\"? This cannot be undone.")
            .setPositiveButton("Delete", (dialog, which) -> {
                int rows = dbHelper.deleteStudent(student.getId());
                if (rows > 0) {
                    Toast.makeText(this, "Deleted: " + student.getName(), Toast.LENGTH_SHORT).show();
                    refreshList();
                } else {
                    Toast.makeText(this, "Delete failed.", Toast.LENGTH_SHORT).show();
                }
            })
            .setNegativeButton("Cancel", null)
            .setIcon(android.R.drawable.ic_dialog_alert)
            .show();
    }

    private void refreshList() {
        List<Student> students = dbHelper.getAllStudents();
        adapter.updateData(students);
        tvRecordCount.setText("Total Students: " + students.size());
    }

    private void clearForm() {
        etName.setText("");
        etEmail.setText("");
        etPhone.setText("");
        etName.setError(null);
        etEmail.setError(null);
        etPhone.setError(null);
        editingStudentId = -1;
        btnSave.setText("ADD STUDENT");
    }
}
