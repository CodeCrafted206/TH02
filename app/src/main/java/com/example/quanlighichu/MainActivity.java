package com.example.quanlighichu;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static ArrayList<Note> noteList = new ArrayList<>();
    public static int nextNoteId = 1;
    private ArrayList<Note> displayList = new ArrayList<>();
    private NoteAdapter adapter;
    private EditText edtSearch;
    private RecyclerView rvNotes;
    private FloatingActionButton fabAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtSearch = findViewById(R.id.edtSearch);
        rvNotes = findViewById(R.id.rvNotes);
        fabAdd = findViewById(R.id.fabAdd);


        if (noteList.isEmpty()) {
            noteList.add(new Note(nextNoteId++, "Học lập trình Android", "Cần hoàn thành bài tập ghi chú cá nhân trước 12h.", "15/10/2023 08:00", "15/10/2023 08:00"));
            noteList.add(new Note(nextNoteId++, "Đi siêu thị", "Mua sữa, bánh mì và hoa quả.", "15/10/2023 09:30", "15/10/2023 09:30"));
        }


        rvNotes.setLayoutManager(new LinearLayoutManager(this));
        adapter = new NoteAdapter(displayList, new NoteAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Note note, int position) {

                int originalIndex = noteList.indexOf(note);
                if (originalIndex != -1) {
                    Intent intent = new Intent(MainActivity.this, NoteDetailActivity.class);
                    intent.putExtra("NOTE_POSITION", originalIndex);
                    startActivity(intent);
                }
            }
        });
        rvNotes.setAdapter(adapter);


        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddEditNoteActivity.class);
                intent.putExtra("NOTE_POSITION", -1);
                startActivity(intent);
            }
        });


        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterNotes(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        filterNotes(edtSearch.getText().toString());
    }

    private void filterNotes(String query) {
        displayList.clear();
        if (query.trim().isEmpty()) {
            displayList.addAll(noteList);
        } else {
            String lowerQuery = query.toLowerCase().trim();
            for (Note note : noteList) {
                if (note.getTitle().toLowerCase().contains(lowerQuery) ||
                        note.getContent().toLowerCase().contains(lowerQuery)) {
                    displayList.add(note);
                }
            }
        }
        adapter.updateList(displayList);
    }
}
