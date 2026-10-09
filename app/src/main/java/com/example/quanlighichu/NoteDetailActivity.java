package com.example.quanlighichu;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
public class NoteDetailActivity extends AppCompatActivity {
    private TextView tvDetailId, tvDetailTitle, tvDetailCreatedAt, tvDetailUpdatedAt, tvDetailContent;
    private Button btnBack, btnEditNote, btnDeleteNote;

    private int notePosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_detail);

        tvDetailId = findViewById(R.id.tvDetailId);
        tvDetailTitle = findViewById(R.id.tvDetailTitle);
        tvDetailCreatedAt = findViewById(R.id.tvDetailCreatedAt);
        tvDetailUpdatedAt = findViewById(R.id.tvDetailUpdatedAt);
        tvDetailContent = findViewById(R.id.tvDetailContent);

        btnBack = findViewById(R.id.btnBack);
        btnEditNote = findViewById(R.id.btnEditNote);
        btnDeleteNote = findViewById(R.id.btnDeleteNote);

        notePosition = getIntent().getIntExtra("NOTE_POSITION", -1);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnEditNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NoteDetailActivity.this, AddEditNoteActivity.class);
                intent.putExtra("NOTE_POSITION", notePosition);
                startActivity(intent);
            }
        });

        btnDeleteNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeleteConfirmDialog();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        displayNoteDetails();
    }

    private void displayNoteDetails() {
        if (notePosition >= 0 && notePosition < MainActivity.noteList.size()) {
            Note note = MainActivity.noteList.get(notePosition);
            tvDetailId.setText("Mã ghi chú: #" + note.getId());
            tvDetailTitle.setText(note.getTitle());
            tvDetailCreatedAt.setText("Ngày tạo: " + note.getCreatedAt());
            tvDetailUpdatedAt.setText("Cập nhật cuối: " + note.getUpdatedAt());
            tvDetailContent.setText(note.getContent());
        } else {
            finish();
        }
    }

    private void showDeleteConfirmDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Xác nhận xóa");
        builder.setMessage("Bạn có chắc chắn muốn xóa ghi chú này không?");
        builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (notePosition >= 0 && notePosition < MainActivity.noteList.size()) {
                    MainActivity.noteList.remove(notePosition);
                    Toast.makeText(NoteDetailActivity.this, "Đã xóa ghi chú!", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }
}
