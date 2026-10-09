package com.example.quanlighichu;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AddEditNoteActivity extends AppCompatActivity {

    private TextView tvFormTitle;
    private EditText edtTitle, edtContent;
    private Button btnSave, btnCancel;

    private int notePosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_note);

        tvFormTitle = findViewById(R.id.tvFormTitle);
        edtTitle = findViewById(R.id.edtTitle);
        edtContent = findViewById(R.id.edtContent);
        btnSave = findViewById(R.id.btnSave);
        btnCancel = findViewById(R.id.btnCancel);


        notePosition = getIntent().getIntExtra("NOTE_POSITION", -1);

        if (notePosition >= 0) {

            tvFormTitle.setText("Chỉnh Sửa Ghi Chú");
            btnSave.setText("Lưu Thay Đổi");

            Note currentNote = MainActivity.noteList.get(notePosition);
            edtTitle.setText(currentNote.getTitle());
            edtContent.setText(currentNote.getContent());
        } else {

            tvFormTitle.setText("Tạo Ghi Chú Mới");
            btnSave.setText("Thêm Ghi Chú");
        }

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveNote();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void saveNote() {
        String title = edtTitle.getText().toString().trim();
        String content = edtContent.getText().toString().trim();

        if (title.isEmpty() || content.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ tiêu đề và nội dung!", Toast.LENGTH_SHORT).show();
            return;
        }


        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
        String currentTime = sdf.format(new Date());

        if (notePosition >= 0) {

            Note note = MainActivity.noteList.get(notePosition);
            note.setTitle(title);
            note.setContent(content);
            note.setUpdatedAt(currentTime);

            Toast.makeText(this, "Đã cập nhật ghi chú!", Toast.LENGTH_SHORT).show();
        } else {

            int newId = MainActivity.nextNoteId++;
            Note newNote = new Note(newId, title, content, currentTime, currentTime);
            MainActivity.noteList.add(0, newNote); // Thêm vào đầu danh sách

            Toast.makeText(this, "Đã thêm ghi chú mới!", Toast.LENGTH_SHORT).show();
        }

        finish();
    }
}
