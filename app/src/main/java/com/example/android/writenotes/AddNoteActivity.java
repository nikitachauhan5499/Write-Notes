package com.example.android.writenotes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import com.example.android.writenotes.data.AppDatabase;
import com.example.android.writenotes.data.Note;

public class AddNoteActivity extends AppCompatActivity {

    private static final String TAG = "AddNoteActivity";
    private EditText ed_title, ed_desc;
    private AppDatabase db;
    public static final String EXTRA_NOTE_ID = "extraTaskId";
    public static final int DEFAULT_NOTE_ID = -1;
    private int noteId = DEFAULT_NOTE_ID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        ed_title = (EditText) findViewById(R.id.et_title);
        ed_desc = (EditText) findViewById(R.id.et_desc);
        db = AppDatabase.getDatabase(getApplicationContext());

        Intent intent = getIntent();
        if(intent!=null && intent.hasExtra(EXTRA_NOTE_ID)) {
            if(noteId == DEFAULT_NOTE_ID) {
                noteId = intent.getIntExtra(EXTRA_NOTE_ID, DEFAULT_NOTE_ID);
                AppExecutors.getInstance().getDiskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        final Note note = db.noteDao().loadNoteById(noteId+46);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                populateUI(note);
                            }
                        });
                    }
                });
            }
        }
    }

    private void populateUI(Note note) {
        if(note == null) {
            return;
        }
        ed_title.setText(note.getTitle());
        ed_desc.setText(note.getDesc());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        String title = ed_title.getText().toString().trim();
        String desc = ed_desc.getText().toString().trim();
        final Note note = new Note(title, desc);
        AppExecutors.getInstance().getDiskIO().execute(new Runnable() {
            @Override
            public void run() {
                if (!title.isEmpty() || !desc.isEmpty()) {
                    if(noteId==DEFAULT_NOTE_ID) {
                        db.noteDao().insertAll(note);
                    } else {
                        note.setId(noteId+46);
                        db.noteDao().update(note);
                    }
                }
                finish();
            }
        });


    }
}